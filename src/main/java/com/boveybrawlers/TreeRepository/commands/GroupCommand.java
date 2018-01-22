package com.boveybrawlers.TreeRepository.commands;

import com.boveybrawlers.TreeRepository.TreeRepository;
import com.boveybrawlers.TreeRepository.objects.Group;
import com.boveybrawlers.TreeRepository.utils.Pagination;
import com.boveybrawlers.TreeRepository.utils.Str;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GroupCommand implements CommandExecutor {

    private TreeRepository plugin;

    public GroupCommand(TreeRepository plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "TreeRepository can only be used by players");
            return true;
        }

        Player player = (Player) sender;

        if(args.length > 1) {
            Group group = this.plugin.trees.getGroup(args[1]);
            if(group == null) {
                player.sendMessage(ChatColor.RED + "Could not find that group");
                return true;
            }

            int perPage = 4;
            Pagination pagination = Pagination.parseArgs(args, 2, perPage, group.getList().size());

            String page = "";
            if(pagination.hasNextPage()) {
                page = ChatColor.DARK_GREEN + " Page: " + ChatColor.WHITE + pagination.getPage();
            }
            player.sendMessage(ChatColor.DARK_GREEN + "Group: " + ChatColor.GRAY + group.getNiceName() + page);

            group.getList().subList(pagination.getFrom(), pagination.getTo()).forEach(tree -> {
                // TODO: Float and align buttons with correct amount of spacing
                TextComponent treeMsg = new TextComponent(Str.fixedFontSize(tree.getName(), 40));
                treeMsg.setColor(net.md_5.bungee.api.ChatColor.DARK_GREEN);

                TextComponent actionCopy = new TextComponent("[Copy]");
                actionCopy.setColor(net.md_5.bungee.api.ChatColor.AQUA);
                actionCopy.setClickEvent(new ClickEvent(
                    ClickEvent.Action.RUN_COMMAND,
                    "/tr copy " + tree.getCode())
                );
                actionCopy.setHoverEvent(new HoverEvent(
                    HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder("Copy this tree to the clipboard").create())
                );
                treeMsg.addExtra(actionCopy);
                treeMsg.addExtra(" ");

                TextComponent actionTool = new TextComponent("[Tool]");
                actionTool.setColor(net.md_5.bungee.api.ChatColor.AQUA);
                actionTool.setClickEvent(new ClickEvent(
                    ClickEvent.Action.RUN_COMMAND,
                    "/tr tool " + tree.getCode())
                );
                actionTool.setHoverEvent(new HoverEvent(
                    HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder("Receive a wand to place this tree").create())
                );
                treeMsg.addExtra(actionTool);

                player.spigot().sendMessage(treeMsg);
                player.sendMessage(tree.getCode() + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + tree.getDescription());

            });

            if(group.getList().size() > perPage) {
                TextComponent pageMsg = pagination.create(
                    "/tr group " + group.getName() + " {prev}",
                    "/tr group " + group.getName() + " {next}"
                );
                player.spigot().sendMessage(pageMsg);
            }

            return true;
        }

        return false;
    }

}
