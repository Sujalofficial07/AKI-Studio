package com.akistudio.models;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A data model class representing a single project.
 */
public class Project {
    private final String name;
    private final String path;
    private final String type;
    private final long lastModified;

    public Project(File projectDirectory) {
        this.name = projectDirectory.getName();
        this.path = projectDirectory.getAbsolutePath();
        this.type = "Android App (Java)"; // For now, this is static. Can be enhanced later.
        this.lastModified = projectDirectory.lastModified();
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getType() {
        return type;
    }

    public String getLastModifiedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        return sdf.format(new Date(lastModified));
    }
}
