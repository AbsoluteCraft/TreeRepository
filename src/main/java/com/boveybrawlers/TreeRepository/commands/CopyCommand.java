package com.boveybrawlers.TreeRepository.commands;

import com.boveybrawlers.TreeRepository.TreeRepository;
import com.boveybrawlers.TreeRepository.objects.Tree;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CopyCommand implements CommandExecutor {

    private TreeRepository plugin;

    public CopyCommand(TreeRepository plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "TreeRepository can only be used by players");
            return true;
        }

        Player player = (Player) sender;

        if(args.length > 1) {
            Tree tree = this.plugin.trees.find(args[1]);
            if(tree == null) {
                player.sendMessage(ChatColor.RED + "That tree could not be found");
                return true;
            }

            this.plugin.worldedit.copySchematic(player, tree.getSchematic());
            player.sendMessage(ChatColor.GREEN + "Tree " + tree.getName() + " loaded. Paste it with //paste");

            return true;
        }

        return false;
    }

}
