package com.akistudio.editor;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.akistudio.R;
import com.akistudio.databinding.ActivityEditorBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * EditorActivity provides a simple text editor to view and modify files.
 */
public class EditorActivity extends AppCompatActivity {

    public static final String EXTRA_FILE_PATH = "extra_file_path";
    private ActivityEditorBinding binding;
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbarEditor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        filePath = getIntent().getStringExtra(EXTRA_FILE_PATH);
        if (filePath != null) {
            File file = new File(filePath);
            getSupportActionBar().setTitle(file.getName());
            loadFileContent();
        } else {
            Toast.makeText(this, "Error: No file path provided.", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    /**
     * Reads the content from the file and displays it in the CodeEditorView.
     */
    private void loadFileContent() {
        File file = new File(filePath);
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append('\n');
            }
            binding.codeEditor.setText(content.toString());
        } catch (IOException e) {
            Toast.makeText(this, "Error reading file: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            binding.codeEditor.setText("// Error loading file: " + e.getMessage());
        }
    }

    /**
     * Saves the current content of the CodeEditorView back to the file.
     */
    private void saveFileContent() {
        File file = new File(filePath);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(binding.codeEditor.getText().toString());
            Toast.makeText(this, "File saved successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error saving file: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            saveFileContent();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
