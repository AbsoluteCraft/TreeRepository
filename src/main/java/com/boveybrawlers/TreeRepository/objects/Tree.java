package com.boveybrawlers.TreeRepository.objects;

import com.boveybrawlers.TreeRepository.TreeRepository;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Tree {

    private File schematicFile;

    private String code;
    private String name;
    private String description;

    public Tree(TreeRepository plugin, String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;

//        plugin.getLogger().log(Level.INFO, plugin.getDataFolder() + File.separator + "schematics", this.code + ".schematic");
        // TODO: Move to manager?
        String path = "schematics" + File.separator + this.code + ".schematic";
        this.schematicFile = new File(plugin.getDataFolder() + File.separator + path);
        InputStream is = plugin.getResource(path);
        try {
            FileUtils.copyInputStreamToFile(is, this.schematicFile);
        } catch(IOException e) {
            e.printStackTrace();
        }
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

    public File getSchematic() {
        return this.schematicFile;
    }

}
