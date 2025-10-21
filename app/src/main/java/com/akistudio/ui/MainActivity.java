package com.akistudio.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.akistudio.R;
import com.akistudio.databinding.ActivityMainBinding;
import com.akistudio.editor.EditorActivity;
import com.akistudio.files.FileAdapter;
import com.akistudio.files.FileManager;
import com.akistudio.files.ProjectInitializer;
import com.akistudio.tasks.GradleTaskRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * MainActivity serves as the main screen of AkiStudio.
 * It handles permissions, initializes a template project if one doesn't exist,
 * displays the file manager, terminal, and provides access to Gradle tasks.
 */
public class MainActivity extends AppCompatActivity implements FileAdapter.OnFileClickListener {

    private ActivityMainBinding binding;
    private FileManager fileManager;
    private FileAdapter fileAdapter;
    private GradleTaskRunner gradleTaskRunner;

    // Modern ActivityResultLauncher for requesting MANAGE_EXTERNAL_STORAGE on Android 11+
    private final ActivityResultLauncher<Intent> storagePermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if (Environment.isExternalStorageManager()) {
                        initializeUI();
                    } else {
                        showPermissionDeniedToast();
                    }
                }
            });

    // Modern ActivityResultLauncher for requesting legacy storage permissions
    private final ActivityResultLauncher<String[]> legacyStoragePermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), (Map<String, Boolean> isGranted) -> {
                boolean granted = true;
                for (Boolean value : isGranted.values()) {
                    if (!value) {
                        granted = false;
                        break;
                    }
                }
                if (granted) {
                    initializeUI();
                } else {
                    showPermissionDeniedToast();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        requestStoragePermissions();
    }

    /**
     * Checks and requests necessary storage permissions based on the Android version.
     */
    private void requestStoragePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11 (R) or higher - requires MANAGE_EXTERNAL_STORAGE
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                storagePermissionLauncher.launch(intent);
            } else {
                initializeUI();
            }
        } else {
            // Android 10 (Q) or lower - requires READ/WRITE_EXTERNAL_STORAGE
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                legacyStoragePermissionLauncher.launch(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                });
            } else {
                initializeUI();
            }
        }
    }

    /**
     * Initializes the user interface components after permissions are granted.
     * This now includes logic to create a sample project from assets if one does not exist.
     */
    private void initializeUI() {
        File projectRoot = new File(Environment.getExternalStorageDirectory(), "AkiStudioProjects/SampleProject");

        // Check if the project exists by looking for a key file. If not, create it.
        File settingsFile = new File(projectRoot, "settings.gradle");
        if (!settingsFile.exists()) {
            binding.terminalView.appendText("No project found. Creating new template project...\n");
            try {
                // To make a buildable project, we must also provide the gradlew executables.
                // In this example, we assume they are part of the template assets.
                // You would need to add `gradlew` and `gradlew.bat` to your assets folder.
                ProjectInitializer.copyProjectTemplate(getAssets(), "project_template", projectRoot);

                // IMPORTANT: Make the gradlew script executable
                File gradlewScript = new File(projectRoot, "gradlew");
                if(gradlewScript.exists()) {
                    gradlewScript.setExecutable(true, false);
                }

                binding.terminalView.appendText("Template project created successfully at:\n" + projectRoot.getAbsolutePath() + "\n");
            } catch (IOException e) {
                binding.terminalView.appendText("FATAL ERROR: Could not create template project. " + e.getMessage() + "\n");
                return; // Stop initialization if template fails
            }
        }

        fileManager = new FileManager(projectRoot);
        setupRecyclerView(projectRoot);
        setupGradleRunner(projectRoot);

        binding.terminalView.appendText("AkiStudio Initialized.\n");
        binding.terminalView.appendText("Project Root: " + projectRoot.getAbsolutePath() + "\n");
    }

    private void showPermissionDeniedToast() {
        Toast.makeText(this, "Storage Permission is required for AkiStudio to function.", Toast.LENGTH_LONG).show();
        binding.terminalView.appendText("Error: Storage permission denied. Please grant permission in App Settings and restart.");
    }

    private void setupRecyclerView(File directory) {
        binding.recyclerViewFiles.setLayoutManager(new LinearLayoutManager(this));
        List<File> files = fileManager.getFiles(directory);
        fileAdapter = new FileAdapter(files, this);
        binding.recyclerViewFiles.setAdapter(fileAdapter);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setSubtitle(fileManager.getCurrentPath());
        }
    }

    private void setupGradleRunner(File projectRoot) {
        gradleTaskRunner = new GradleTaskRunner(projectRoot, binding.terminalView);
    }

    @Override
    public void onFileClick(File file) {
        if (file.isDirectory()) {
            List<File> newFiles = fileManager.getFiles(file);
            fileAdapter.updateFiles(newFiles);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setSubtitle(fileManager.getCurrentPath());
            }
        } else {
            Intent intent = new Intent(this, EditorActivity.class);
            intent.putExtra(EditorActivity.EXTRA_FILE_PATH, file.getAbsolutePath());
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if (fileManager != null && !fileManager.isAtRoot()) {
            File parent = fileManager.navigateUp();
            if (parent != null) {
                fileAdapter.updateFiles(fileManager.getFiles(parent));
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setSubtitle(fileManager.getCurrentPath());
                }
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (gradleTaskRunner == null) {
            Toast.makeText(this, "Gradle runner not initialized.", Toast.LENGTH_SHORT).show();
            return true;
        }

        int itemId = item.getItemId();
        if (itemId == R.id.action_run_build) {
            binding.terminalView.clear();
            binding.terminalView.appendText("> Executing './gradlew assembleDebug'...\n\n");
            gradleTaskRunner.runTask("assembleDebug");
            return true;
        } else if (itemId == R.id.action_clean_project) {
            binding.terminalView.clear();
            binding.terminalView.appendText("> Executing './gradlew clean'...\n\n");
            gradleTaskRunner.runTask("clean");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
