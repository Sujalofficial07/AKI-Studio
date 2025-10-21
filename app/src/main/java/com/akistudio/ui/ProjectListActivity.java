package com.akistudio.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.akistudio.R;
import com.akistudio.adapters.ProjectAdapter;
import com.akistudio.databinding.ActivityProjectListBinding;
import com.akistudio.files.ProjectInitializer;
import com.akistudio.models.Project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProjectListActivity extends AppCompatActivity {

    private ActivityProjectListBinding binding;
    private ProjectAdapter adapter;
    private final List<Project> projectList = new ArrayList<>();
    private File projectsRoot;

    // Modern permission handling
    private final ActivityResultLauncher<Intent> storagePermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && Environment.isExternalStorageManager()) {
                    setupProjects();
                }
            });

    private final ActivityResultLauncher<String[]> legacyStoragePermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), (Map<String, Boolean> isGranted) -> {
                if (isGranted.containsValue(true)) {
                    setupProjects();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProjectListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        projectsRoot = new File(Environment.getExternalStorageDirectory(), "AkiStudioProjects");

        binding.fabAddProject.setOnClickListener(view -> showCreateProjectDialog());
        
        requestStoragePermissions();
    }

    private void requestStoragePermissions() {
        // Handle permissions and then call setupProjects()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.fromParts("package", getPackageName(), null));
                storagePermissionLauncher.launch(intent);
            } else {
                setupProjects();
            }
        } else {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                legacyStoragePermissionLauncher.launch(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE});
            } else {
                setupProjects();
            }
        }
    }
    
    private void setupProjects() {
        if (!projectsRoot.exists()) {
            projectsRoot.mkdirs();
        }
        loadProjects();
        setupRecyclerView();
    }

    private void loadProjects() {
        projectList.clear();
        File[] projectDirs = projectsRoot.listFiles(File::isDirectory);
        if (projectDirs != null) {
            for (File dir : projectDirs) {
                // A valid project should contain a settings.gradle file
                if (new File(dir, "settings.gradle").exists()) {
                    projectList.add(new Project(dir));
                }
            }
        }
    }

    private void setupRecyclerView() {
        adapter = new ProjectAdapter(projectList, project -> {
            Intent intent = new Intent(ProjectListActivity.this, IdeActivity.class);
            intent.putExtra("PROJECT_PATH", project.getPath());
            intent.putExtra("PROJECT_NAME", project.getName());
            startActivity(intent);
        });
        binding.recyclerViewProjects.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewProjects.setAdapter(adapter);
    }
    
    private void showCreateProjectDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create New Project");

        final EditText input = new EditText(this);
        input.setHint("MyFirstApp");
        builder.setView(input);

        builder.setPositiveButton("Create", (dialog, which) -> {
            String projectName = input.getText().toString().trim().replaceAll("\\s+", "");
            if (!projectName.isEmpty()) {
                createNewProject(projectName);
            } else {
                Toast.makeText(this, "Project name cannot be empty.", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }
    
    private void createNewProject(String name) {
        File newProjectDir = new File(projectsRoot, name);
        if (newProjectDir.exists()) {
            Toast.makeText(this, "Project with this name already exists.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            ProjectInitializer.copyProjectTemplate(getAssets(), "project_template", newProjectDir);
            File gradlewScript = new File(newProjectDir, "gradlew");
            if (gradlewScript.exists()) {
                gradlewScript.setExecutable(true, false);
            }
            Toast.makeText(this, "Project '" + name + "' created.", Toast.LENGTH_SHORT).show();
            // Refresh the list
            loadProjects();
            adapter.notifyDataSetChanged();
        } catch (IOException e) {
            Toast.makeText(this, "Error creating project: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
