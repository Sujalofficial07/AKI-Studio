package com.akistudio.files;

import android.content.res.AssetManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Handles the creation of a new project from a bundled template.
 */
public class ProjectInitializer {

    /**
     * Copies a template project from the app's assets to a destination directory.
     * @param assetManager The AssetManager to access bundled assets.
     * @param sourcePath The path within the assets folder (e.g., "project_template").
     * @param destinationDir The target directory on external storage.
     * @throws IOException if a file cannot be copied.
     */
    public static void copyProjectTemplate(AssetManager assetManager, String sourcePath, File destinationDir) throws IOException {
        String[] files = assetManager.list(sourcePath);
        if (files == null) return;

        if (!destinationDir.exists()) {
            destinationDir.mkdirs();
        }

        for (String filename : files) {
            String sourceFilePath = sourcePath + "/" + filename;
            File destFile = new File(destinationDir, filename);

            // Check if it's a directory or file
            if (assetManager.list(sourceFilePath).length > 0) {
                // It's a directory, recurse
                copyProjectTemplate(assetManager, sourceFilePath, destFile);
            } else {
                // It's a file, copy it
                try (InputStream in = assetManager.open(sourceFilePath);
                     OutputStream out = new FileOutputStream(destFile)) {
                    byte[] buffer = new byte[1024];
                    int read;
                    while ((read = in.read(buffer)) != -1) {
                        out.write(buffer, 0, read);
                    }
                }
            }
        }
    }
}
