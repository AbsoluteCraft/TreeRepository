package com.boveybrawlers.TreeRepository.commands;

import com.boveybrawlers.TreeRepository.TreeRepository;
import com.boveybrawlers.TreeRepository.objects.Group;
import com.boveybrawlers.TreeRepository.objects.Tree;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ToolCommand implements CommandExecutor {

    private TreeRepository plugin;

    public ToolCommand(TreeRepository plugin) {
        this.plugin = plugin;
    }

    public static String toolType = "treerepo.tool.type";
    public static String toolCode = "treerepo.tool.code";

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "TreeRepository can only be used by players");
            return true;
        }

        Player player = (Player) sender;

        if(args.length > 1) {
            ItemStack wand = new ItemStack(Material.WOOD_HOE);
            ItemMeta meta = wand.getItemMeta();
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

            if(args.length > 2 && args[1].equalsIgnoreCase("group")) {
                Group group = this.plugin.trees.getGroup(args[2]);
                if(group == null) {
                    player.sendMessage(ChatColor.RED + "That group could not be found");
                    return true;
                }

                meta.setDisplayName(ChatColor.DARK_GREEN + "Tree Group: " + group.getNiceName());
            } else {
                Tree tree = this.plugin.trees.find(args[1]);
                if(tree == null) {
                    player.sendMessage(ChatColor.RED + "That tree could not be found");
                    return true;
                }

                meta.setDisplayName(ChatColor.DARK_GREEN + "Tree: " + tree.getCode());
            }

            wand.setItemMeta(meta);

            player.getInventory().addItem(wand);

            return true;
        }

        player.sendMessage(ChatColor.RED + "Usage: /tr tool [code]");
        return true;
    }

}
