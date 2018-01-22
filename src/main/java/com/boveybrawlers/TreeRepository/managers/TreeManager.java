package com.boveybrawlers.TreeRepository.managers;

import com.boveybrawlers.TreeRepository.TreeRepository;
import com.boveybrawlers.TreeRepository.objects.Group;
import com.boveybrawlers.TreeRepository.objects.Tree;
import org.apache.commons.io.FileUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class TreeManager {

    private TreeRepository plugin;

    private List<Tree> trees = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();

    public TreeManager(TreeRepository plugin) {
        this.plugin = plugin;

        // Try to create the schematic directory when we start
        String directory = this.plugin.getDataFolder() + File.separator + "schematics";
        File schematicDir = new File(directory);
        if(!schematicDir.exists()) {
            boolean mkdirs = schematicDir.mkdirs();
            if(!mkdirs) {
                this.plugin.getLogger().log(Level.SEVERE, "Failed to create schematics directory");
                return;
            } else {
                this.plugin.getLogger().log(Level.INFO, "Created schematics directory");
            }
        }

        // Load trees.yml
        FileConfiguration cfg = new YamlConfiguration();
        try {
            cfg.load(this.plugin.treesFile);
        } catch(Exception e) {
            this.plugin.getLogger().log(Level.SEVERE, "Failed to find trees.yml");
        }

        // Loop through groups
        ConfigurationSection treesConfig = cfg.getConfigurationSection("groups");
        treesConfig.getKeys(true).forEach(grp -> {
            Group group = new Group(grp);

            // Loop through trees in group
            List<Map<?, ?>> groupConfig = treesConfig.getMapList(grp);
            groupConfig.forEach(treeMap -> {
                String code = (String) treeMap.get("code");
                if(code == null) {
                    this.plugin.getLogger().log(Level.WARNING, "trees.yml: Tree found in " + group.getNiceName() + " group with no code");
                    return;
                }
                String name = (String) treeMap.get("name");
                String description = (String) treeMap.get("description");
                if(name == null) {
                    this.plugin.getLogger().log(Level.WARNING, "trees.yml: Tree with code " + code + " in group " + group.getNiceName() + " has no name");
                    return;
                }

                Tree tree = new Tree(directory, code, name, description);
                Integer roots = (Integer) treeMap.get("roots");
                if(roots != null) {
                    tree.setRoots(roots);
                }

                // Copy the tree to the plugin's schematics folder if it doesn't exist
                if(!tree.getSchematic().exists()) {
                    FileUtil.copy(tree.getSchematic(), new File(this.plugin.getDataFolder() + File.separator + "schematics", tree.getCode() + ".schematic"));
                }

                group.add(tree);
                trees.add(tree);
            });

            groups.add(group);
        });
    }

    public Tree find(String code) {
        return this.trees.stream().filter(tree -> tree.getCode().equalsIgnoreCase(code))
            .findFirst()
            .orElse(null);
    }

    public List<Group> getGroups() {
        return this.groups;
    }

    public Group getGroup(String name) {
        return this.groups.stream().filter(grp -> grp.getName().equals(name) || grp.getNiceName().equals(name))
            .findFirst()
            .orElse(null);
    }

}
