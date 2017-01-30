package com.boveybrawlers.TreeRepository.commands;

import com.boveybrawlers.TreeRepository.TreeRepository;
import com.boveybrawlers.TreeRepository.objects.Group;
import com.boveybrawlers.TreeRepository.utils.Pagination;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ListCommand implements CommandExecutor {

    private TreeRepository plugin;

    public ListCommand(TreeRepository plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "TreeRepository can only be used by players");
            return true;
        }

        Player player = (Player) sender;

        // If using the trees command, the page arg is at 0
        int argIndex = 1;
        if(cmd.toString().equalsIgnoreCase("trees")) {
            argIndex = 0;
        }

        List<Group> groups = this.plugin.trees.getGroups();

        int perPage = 10;
        Pagination pagination = Pagination.parseArgs(args, argIndex, perPage, groups.size());

        String page = "";
        if(pagination.hasPages()) {
            page = ChatColor.DARK_GREEN + " Page: " + ChatColor.WHITE + pagination.getPage();
        }
        player.sendMessage("Available tree groups: " + ChatColor.DARK_GRAY + "(Click to view)" + page);

        groups.forEach(group -> {
            TextComponent groupMsg = new TextComponent(group.getNiceName());
            groupMsg.setColor(net.md_5.bungee.api.ChatColor.AQUA);
            groupMsg.setClickEvent(new ClickEvent(
                ClickEvent.Action.RUN_COMMAND,
                "/tr group " + group.getName())
            );
            groupMsg.setHoverEvent(new HoverEvent(
                HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("View the trees in " + group.getNiceName()).create())
            );
            player.spigot().sendMessage(groupMsg);
        });

        if(groups.size() > perPage) {
            TextComponent pageMsg = pagination.create("/tr list {prev}", "/tr list {next}");
            player.spigot().sendMessage(pageMsg);
        }

        return true;
    }

}
