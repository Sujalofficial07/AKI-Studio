package com.akistudio.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.akistudio.R;
import com.akistudio.databinding.ActivityMainBinding;
import com.akistudio.editor.EditorActivity;
import com.akistudio.files.FileAdapter;
import com.akistudio.files.FileManager;
import com.akistudio.tasks.GradleTaskRunner;
import com.akistudio.terminal.TerminalView;

import java.io.File;
import java.util.List;

/**
 * MainActivity serves as the main screen of AkiStudio.
 * It displays the file manager, terminal, and provides access to Gradle tasks.
 */
public class MainActivity extends AppCompatActivity implements FileAdapter.OnFileClickListener {

    private static final int STORAGE_PERMISSION_CODE = 101;

    private ActivityMainBinding binding;
    private FileManager fileManager;
    private FileAdapter fileAdapter;
    private GradleTaskRunner gradleTaskRunner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        checkPermissions();
    }

    /**
     * Checks for necessary storage permissions. If not granted, it requests them.
     * If granted, it initializes the UI.
     */
    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_CODE);
        } else {
            initializeUI();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initializeUI();
            } else {
                Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
                binding.terminalView.appendText("Error: Storage permission is required to function.");
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

    /**
     * Sets up the RecyclerView for the file manager.
     * @param directory The root directory to display.
     */
    private void setupRecyclerView(File directory) {
        binding.recyclerViewFiles.setLayoutManager(new LinearLayoutManager(this));
        List<File> files = fileManager.getFiles(directory);
        fileAdapter = new FileAdapter(files, this);
        binding.recyclerViewFiles.setAdapter(fileAdapter);
        getSupportActionBar().setSubtitle(fileManager.getCurrentPath());
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
            getSupportActionBar().setSubtitle(fileManager.getCurrentPath());
        } else {
            // Open file in editor
            Intent intent = new Intent(this, EditorActivity.class);
            intent.putExtra(EditorActivity.EXTRA_FILE_PATH, file.getAbsolutePath());
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if (!fileManager.isAtRoot()) {
            File parent = fileManager.navigateUp();
            if (parent != null) {
                fileAdapter.updateFiles(fileManager.getFiles(parent));
                getSupportActionBar().setSubtitle(fileManager.getCurrentPath());
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
            binding.terminalView.clear();
            binding.terminalView.appendText("> Executing 'gradle assembleDebug'...\n\n");
            gradleTaskRunner.runTask("assembleDebug");
            return true;
        } else if (itemId == R.id.action_clean_project) {
            binding.terminalView.clear();
            binding.terminalView.appendText("> Executing 'gradle clean'...\n\n");
            gradleTaskRunner.runTask("clean");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
