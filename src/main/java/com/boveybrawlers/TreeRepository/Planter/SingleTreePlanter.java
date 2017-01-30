package com.boveybrawlers.TreeRepository.Planter;

import com.boveybrawlers.TreeRepository.TreeRepository;
import com.boveybrawlers.TreeRepository.objects.Tree;
import com.sk89q.worldedit.Vector;

public class SingleTreePlanter implements IPlanter {

    private TreeRepository plugin;

    private org.bukkit.entity.Player player;
    private Tree tree;

    public SingleTreePlanter(TreeRepository plugin, org.bukkit.entity.Player player, Tree tree) {
        this.plugin = plugin;
        this.player = player;
        this.tree = tree;
    }

    public void actPrimary(Vector location) {
        this.plugin.worldedit.pasteSchematic(this.player, this.tree.getSchematic(), location);
    }

}
