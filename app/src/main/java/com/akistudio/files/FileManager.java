package com.akistudio.files;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Manages file and directory operations.
 */
public class FileManager {

    private File rootDirectory;
    private File currentDirectory;

    public FileManager(File rootDirectory) {
        this.rootDirectory = rootDirectory;
        this.currentDirectory = rootDirectory;
    }

    /**
     * Gets a sorted list of files and directories for the given path.
     * @param directory The directory to list files from.
     * @return A list of File objects.
     */
    public List<File> getFiles(File directory) {
        if (directory == null || !directory.isDirectory()) {
            return new ArrayList<>();
        }
        this.currentDirectory = directory;
        File[] files = directory.listFiles();
        if (files == null) {
            return new ArrayList<>();
        }

        List<File> fileList = new ArrayList<>(Arrays.asList(files));
        // Sort with directories first, then by name
        Collections.sort(fileList, (f1, f2) -> {
            if (f1.isDirectory() && !f2.isDirectory()) {
                return -1;
            }
            if (!f1.isDirectory() && f2.isDirectory()) {
                return 1;
            }
            return f1.getName().compareToIgnoreCase(f2.getName());
        });

        return fileList;
    }

    /**
     * Navigates up to the parent directory.
     * @return The parent directory, or null if at the root.
     */
    public File navigateUp() {
        if (!isAtRoot()) {
            currentDirectory = currentDirectory.getParentFile();
            return currentDirectory;
        }
        return null;
    }

    /**
     * Checks if the current directory is the root directory.
     */
    public boolean isAtRoot() {
        return currentDirectory.equals(rootDirectory);
    }

    /**
     * Gets the path of the current directory relative to the root.
     */
    public String getCurrentPath() {
        return "/" + rootDirectory.toURI().relativize(currentDirectory.toURI()).getPath();
    }
}
