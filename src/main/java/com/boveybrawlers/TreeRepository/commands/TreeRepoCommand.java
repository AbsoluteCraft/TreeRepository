package com.boveybrawlers.TreeRepository.commands;

import com.boveybrawlers.TreeRepository.TreeRepository;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TreeRepoCommand implements CommandExecutor {

    private TreeRepository plugin;

    public TreeRepoCommand(TreeRepository plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!sender.hasPermission("treerepo.use")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use TreeRepository");
            sender.sendMessage(ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "treerepo.use");
        }

        Map<String, CommandExecutor> subCommands = new HashMap<>();
        subCommands.put("copy", new CopyCommand(this.plugin));
        subCommands.put("group", new GroupCommand(this.plugin));
        subCommands.put("help", new HelpCommand());
        subCommands.put("list", new ListCommand(this.plugin));
        subCommands.put("tool", new ToolCommand(this.plugin));

        // TODO: Command to build a temporary collection of trees to use in a tool

        if(args.length > 0) {
            CommandExecutor executor = subCommands.get(args[0].toLowerCase());
            if(executor == null) {
                String subcmds = subCommands.keySet().stream()
                    .map(subCmd -> ChatColor.DARK_GREEN + subCmd)
                    .collect(Collectors.joining(ChatColor.GRAY + ", "));

                sender.sendMessage(ChatColor.RED + "Unknown subcommand");
                sender.sendMessage(ChatColor.GRAY + "Valid subcommands are " + subcmds + ChatColor.GRAY + ". See /tr help for more info.");
                return true;
            }

            return executor.onCommand(sender, cmd, label, args);
        }

        return (new HelpCommand()).onCommand(sender);
    }

}
