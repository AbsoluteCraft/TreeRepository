package com.boveybrawlers.TreeRepository.Planter;

import com.boveybrawlers.TreeRepository.TreeRepository;
import com.boveybrawlers.TreeRepository.objects.Group;
import com.boveybrawlers.TreeRepository.objects.Tree;
import com.sk89q.worldedit.Vector;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

public class GroupTreePlanter implements IPlanter {

    private TreeRepository plugin;
    private Player player;
    private Group group;

    public GroupTreePlanter(TreeRepository plugin, Player player, Group group) {
        this.plugin = plugin;
        this.player = player;
        this.group = group;
    }

    public void actPrimary(Vector location) {
        // Choose a random tree from the group to place down
        List<Tree> trees = this.group.getList();
        int index = (new Random()).nextInt(trees.size());
        Tree tree = trees.get(index);

        new SingleTreePlanter(this.plugin, this.player, tree)
            .actPrimary(location);
    }

}
