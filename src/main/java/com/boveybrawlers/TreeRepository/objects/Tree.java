package com.boveybrawlers.TreeRepository.objects;

import java.io.File;

public class Tree {

    private File schematicFile;

    private String code;
    private String name;
    private String description;
    private Integer roots;

    public Tree(String directory, String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.roots = 0;

        // Store the reference to the file
        this.schematicFile = new File(directory + File.separator + this.code + ".schematic");

    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Integer getRoots() {
        return this.roots;
    }

    public void setRoots(Integer roots) {
        this.roots = roots;
    }

    public File getSchematic() {
        return this.schematicFile;
    }

}
