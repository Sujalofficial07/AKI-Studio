package com.akistudio.tasks;

import com.akistudio.terminal.TerminalView;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * Executes Gradle tasks for a given project.
 * IMPORTANT: This is a simplified simulation. A real implementation would require
 * bundling a Gradle distribution or using a library that embeds it, which is
 * a significant undertaking. This class uses the system's `sh` to demonstrate
 * the concept, assuming a `gradlew` script exists and is executable.
 */
public class GradleTaskRunner {

    private final File projectRoot;
    private final TerminalView terminal;

    public GradleTaskRunner(File projectRoot, TerminalView terminal) {
        this.projectRoot = projectRoot;
        this.terminal = terminal;
    }

    /**
     * Runs a specified Gradle task in a background thread.
     * @param taskName The name of the task to run (e.g., "assembleDebug").
     */
    public void runTask(String taskName) {
        new Thread(() -> {
            Process process = null;
            try {
                File gradlew = new File(projectRoot, "gradlew");
                if (!gradlew.exists()) {
                    terminal.appendText("\nERROR: 'gradlew' script not found in project root.\n");
                    return;
                }
                
                // Make gradlew executable
                gradlew.setExecutable(true);

                ProcessBuilder processBuilder = new ProcessBuilder("./gradlew", taskName);
                processBuilder.directory(projectRoot);
                processBuilder.redirectErrorStream(true); // Combine stdout and stderr
                
                process = processBuilder.start();

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        terminal.appendText(line + "\n");
                    }
                }

                int exitCode = process.waitFor();
                terminal.appendText("\n> Task '" + taskName + "' finished with exit code: " + exitCode + "\n");

            } catch (IOException | InterruptedException e) {
                terminal.appendText("\nERROR: Failed to execute Gradle task. " + e.getMessage() + "\n");
                Thread.currentThread().interrupt();
            } finally {
                if (process != null) {
                    process.destroy();
                }
            }
        }).start();
    }
}
