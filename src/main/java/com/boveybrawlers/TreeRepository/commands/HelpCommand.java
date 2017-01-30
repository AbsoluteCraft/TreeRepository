package com.boveybrawlers.TreeRepository.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

@SuppressWarnings("WeakerAccess")
public class HelpCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender) {
        sender.sendMessage(ChatColor.DARK_GRAY + " ------------------------------------------");
        sender.sendMessage(ChatColor.DARK_GREEN + "Tree Repository");
        sender.sendMessage(ChatColor.DARK_GRAY + " - " + ChatColor.WHITE + "Plugin by " + ChatColor.RED + "boveybrawlers");
        sender.sendMessage(ChatColor.DARK_GRAY + " - " + ChatColor.WHITE + "Trees by " + ChatColor.GOLD + "lentebriesje");
        sender.sendMessage("");
        sender.sendMessage(ChatColor.GREEN + "Quick Guide: " + ChatColor.GRAY + "Use " + ChatColor.DARK_GREEN + "/trees " + ChatColor.GRAY + "and use the clickable buttons in Chat");
        sender.sendMessage(ChatColor.GREEN + "Commands:");
        sender.sendMessage(ChatColor.DARK_GREEN + "/tr list (page)");
        sender.sendMessage(ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "List all groups");
        sender.sendMessage(ChatColor.DARK_GREEN + "/tr group [name] (page)");
        sender.sendMessage(ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "List all trees in a group");
        sender.sendMessage(ChatColor.DARK_GREEN + "/tr copy [code]");
        sender.sendMessage(ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Copy a tree with name to the clipboard");
        sender.sendMessage(ChatColor.DARK_GREEN + "/tr tool [code]");
        sender.sendMessage(ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Receive a wand to place a tree wherever you click");
        sender.sendMessage(ChatColor.DARK_GRAY + " ------------------------------------------");

        return true;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return this.onCommand(sender);
    }

}
