package com.boveybrawlers.TreeRepository.listeners;

import com.boveybrawlers.TreeRepository.Planter.GroupTreePlanter;
import com.boveybrawlers.TreeRepository.Planter.IPlanter;
import com.boveybrawlers.TreeRepository.Planter.SingleTreePlanter;
import com.boveybrawlers.TreeRepository.TreeRepository;
import com.boveybrawlers.TreeRepository.objects.Group;
import com.boveybrawlers.TreeRepository.objects.Tree;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.command.tool.DistanceWand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class WandInteract implements Listener {

    TreeRepository plugin;

    public WandInteract(TreeRepository plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if(item != null && item.getType() == Material.WOOD_HOE) {
            if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if(!player.hasPermission("toolrepo.use")) {
                    player.sendMessage(ChatColor.RED + "You do not have permission to use the Tree Repository tool");
                    player.sendMessage(ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "treerepo.use");
                    return;
                }

                DistanceWand wand = new DistanceWand();
                Vector vector = wand.getTarget(this.plugin.worldedit.getPlayer(player));
                if(vector == null) {
                    // No block in sight
                    return;
                }

                IPlanter planter = null;
                String displayName = item.getItemMeta().getDisplayName();
                String toolValue = displayName.substring(displayName.indexOf(':') + 1).trim();

                if(displayName.contains("Group")) {
                    // code is now acting as the group name
                    Group group = this.plugin.trees.getGroup(toolValue);
                    if(group == null) {
                        player.sendMessage(ChatColor.RED + "Your tool is bound to a group that does not exist");
                        return;
                    }

                    planter = new GroupTreePlanter(this.plugin, player, group);
                } else if(displayName.contains("Tree")) {
                    Tree tree = this.plugin.trees.find(toolValue);
                    if(tree == null) {
                        player.sendMessage(ChatColor.RED + "Your tool is bound to a tree that does not exist");
                        return;
                    }

                    planter = new SingleTreePlanter(this.plugin, player, tree);
                }

                if(planter != null) {
                    event.setCancelled(true);
                    planter.actPrimary(vector);
                }
            }
        }
    }

}
