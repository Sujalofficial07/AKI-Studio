package com.akistudio.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.akistudio.R;
import com.akistudio.adapters.FileAdapter;
import com.akistudio.databinding.ActivityIdeBinding;
import com.akistudio.files.FileManager;
import com.akistudio.tasks.GradleTaskRunner;

import java.io.File;

public class IdeActivity extends AppCompatActivity {

    private ActivityIdeBinding binding;
    private String projectPath;
    private String projectName;

    private FileManager fileManager;
    private FileAdapter fileAdapter;
    private GradleTaskRunner gradleTaskRunner;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIdeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        projectPath = getIntent().getStringExtra("PROJECT_PATH");
        projectName = getIntent().getStringExtra("PROJECT_NAME");

        if (projectPath == null) {
            Toast.makeText(this, "Error: Project path not found.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(projectName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        initializeIde(new File(projectPath));
    }
    
    private void initializeIde(File projectRoot) {
        fileManager = new FileManager(projectRoot);
        gradleTaskRunner = new GradleTaskRunner(projectRoot, binding.terminalView);

        // Setup file manager view
        binding.recyclerViewFiles.setLayoutManager(new LinearLayoutManager(this));
        fileAdapter = new FileAdapter(fileManager.getFiles(projectRoot), file -> {
            // Handle file click to open in editor
        });
        binding.recyclerViewFiles.setAdapter(fileAdapter);

        // TODO: Setup BottomNavigationView to switch between views
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ide_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            finish();
            return true;
        } else if (itemId == R.id.action_run_build) {
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
