package com.boveybrawlers.TreeRepository.utils;

import com.boveybrawlers.TreeRepository.TreeRepository;
import com.sk89q.worldedit.*;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.transform.AffineTransform;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.util.io.Closer;
import com.sk89q.worldedit.world.registry.WorldData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;

public class WorldEditWrapper {

    private TreeRepository plugin;
    private WorldEditPlugin we;

    public WorldEditWrapper(TreeRepository plugin) {
        this.plugin = plugin;

        this.we = this.getWorldEdit();
    }

    public boolean hasPlugin() {
        return this.we != null;
    }

    public ClipboardHolder copySchematic(Player player, File file) {
        com.sk89q.worldedit.entity.Player wePlayer = this.getPlayer(player);
        WorldData worldData = wePlayer.getWorld().getWorldData();
        LocalSession session = this.we.getSession(player);

        Closer closer = Closer.create();
        Clipboard clipboard = null;

        try {
            FileInputStream fis = closer.register(new FileInputStream(file));
            BufferedInputStream bis = closer.register(new BufferedInputStream(fis));
            ClipboardReader reader = ClipboardFormat.SCHEMATIC.getReader(bis);

            clipboard = reader.read(worldData);
        } catch(IOException e) {
            player.sendMessage(ChatColor.RED + "Failed to copy tree " + ChatColor.DARK_AQUA + file.getName());
            return null;
        } finally {
            try {
                closer.close();
            } catch(IOException ignored) {}
        }

        // Center the tree on the location
        Vector center = clipboard.getRegion().getCenter();
        double halfHeight = Math.ceil(clipboard.getRegion().getHeight() / 2);
        center = center.setY(center.getBlockY() - halfHeight);
        clipboard.setOrigin(center);

        ClipboardHolder holder = new ClipboardHolder(clipboard, worldData);
        session.setClipboard(holder);

        return holder;
    }

    @SuppressWarnings("deprecation")
    public void pasteSchematic(Player player, File file, Vector location) {
        com.sk89q.worldedit.entity.Player wePlayer = this.getPlayer(player);
        EditSession session = this.we.getSession(player).createEditSession(wePlayer);

        // Move the tree above the block
        location = location.add(0, 1, 0);

        ClipboardHolder clipboard = this.copySchematic(player, file);

        // Randomly rotate the tree for variety
        // If random is 2, rotate is 180 etc
        int rotate = (new Random()).nextInt(4) * 90;
        AffineTransform transform = new AffineTransform();
        transform = transform.rotateY(rotate);
        clipboard.setTransform(clipboard.getTransform().combine(transform));

        Operation operation = clipboard.createPaste(session, wePlayer.getWorld().getWorldData())
            .ignoreAirBlocks(true)
            .to(location)
            .build();

        try {
            Operations.complete(operation);
        } catch(WorldEditException e) {
            e.printStackTrace();
        }
    }

    public com.sk89q.worldedit.entity.Player getPlayer(Player player) {
        return new BukkitPlayer(this.we, WorldEdit.getInstance().getServer(), player);
    }

    private WorldEditPlugin getWorldEdit() {
        Plugin we = this.plugin.getServer().getPluginManager().getPlugin("WorldEdit");
        if(we == null || !(we instanceof WorldEditPlugin)) {
            this.plugin.getLogger().log(Level.SEVERE, "Failed to load WorldEdit. Is it installed?");
            return null;
        }

        return (WorldEditPlugin) we;
    }

}
