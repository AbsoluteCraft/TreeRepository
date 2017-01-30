package com.boveybrawlers.TreeRepository;

import com.boveybrawlers.TreeRepository.commands.ListCommand;
import com.boveybrawlers.TreeRepository.commands.TreeRepoCommand;
import com.boveybrawlers.TreeRepository.listeners.WandInteract;
import com.boveybrawlers.TreeRepository.managers.TreeManager;
import com.boveybrawlers.TreeRepository.utils.WorldEditWrapper;
import de.schlichtherle.io.File;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class TreeRepository extends JavaPlugin {

    public WorldEditWrapper worldedit;
    public TreeManager trees;
    public File treesFile;

    public void onEnable() {
        this.saveDefaultTrees();

        this.worldedit = new WorldEditWrapper(this);
        this.trees = new TreeManager(this);

        this.registerCommands();
        this.registerListeners();

        if(!this.worldedit.hasPlugin()) {
            this.getLogger().log(Level.SEVERE, ChatColor.RED + "Disabling TreeRepo because WorldEdit was not found. Is it installed?");
            this.getServer().getPluginManager().disablePlugin(this);
        }
    }

    private void registerCommands() {
        getCommand("treerepo").setExecutor(new TreeRepoCommand(this));
        getCommand("tr").setExecutor(new TreeRepoCommand(this));
        getCommand("trees").setExecutor(new ListCommand(this));
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new WandInteract(this), this);
    }

    private void saveDefaultTrees() {
        this.treesFile = new File(this.getDataFolder() + File.separator + "trees.yml");
        if(!this.treesFile.exists()) {
            this.saveResource("trees.yml", false);
        }
    }

}
