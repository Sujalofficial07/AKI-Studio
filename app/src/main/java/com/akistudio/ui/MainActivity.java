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
import com.akistudio.tasks.GradleTaskRunner;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * MainActivity serves as the main screen of AkiStudio.
 * It displays the file manager, terminal, and provides access to Gradle tasks.
 * Includes modern permission handling for all Android versions.
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
                        // Permission granted
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
     */
    private void initializeUI() {
        // Use a base project directory in public storage for easy access
        File projectRoot = new File(Environment.getExternalStorageDirectory(), "AkiStudioProjects/SampleProject");
        if (!projectRoot.exists()) {
            projectRoot.mkdirs();
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

    /**
     * Sets up the RecyclerView for the file manager.
     * @param directory The root directory to display.
     */
    private void setupRecyclerView(File directory) {
        binding.recyclerViewFiles.setLayoutManager(new LinearLayoutManager(this));
        List<File> files = fileManager.getFiles(directory);
        fileAdapter = new FileAdapter(files, this);
        binding.recyclerViewFiles.setAdapter(fileAdapter);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setSubtitle(fileManager.getCurrentPath());
        }
    }
    
    /**
     * Sets up the Gradle task runner.
     * @param projectRoot The root of the Gradle project.
     */
    private void setupGradleRunner(File projectRoot) {
        gradleTaskRunner = new GradleTaskRunner(projectRoot, binding.terminalView);
    }

    @Override
    public void onFileClick(File file) {
        if (file.isDirectory()) {
            // Navigate into directory
            List<File> newFiles = fileManager.getFiles(file);
            fileAdapter.updateFiles(newFiles);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setSubtitle(fileManager.getCurrentPath());
            }
        } else {
            // Open file in editor
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
        int itemId = item.getItemId();
        if (itemId == R.id.action_run_build) {
            if (gradleTaskRunner == null) return true;
            binding.terminalView.clear();
            binding.terminalView.appendText("> Executing 'gradle assembleDebug'...\n\n");
            gradleTaskRunner.runTask("assembleDebug");
            return true;
        } else if (itemId == R.id.action_clean_project) {
            if (gradleTaskRunner == null) return true;
            binding.terminalView.clear();
            binding.terminalView.appendText("> Executing 'gradle clean'...\n\n");
            gradleTaskRunner.runTask("clean");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
