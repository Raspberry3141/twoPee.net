package org.plugin.twopeeplugin.Core;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class itemEvent implements Listener {
    pracManager pracmanager;
    progressManager progressmanager;

    public  itemEvent(pracManager pm,progressManager pmgr) {
        pracmanager = pm;
        progressmanager = pmgr;
    }
    @EventHandler
    public void onPlayerRightClickOnItem(PlayerInteractEvent event) {
        if (event.getPlayer()==null) return;
        if (event.getItem()==null) return;
        if (event.getAction()== Action.RIGHT_CLICK_AIR || event.getAction()==Action.RIGHT_CLICK_AIR) {
            ItemStack item = event.getItem();
            Player player = event.getPlayer();
            if (item.getType()== Material.CARROT_STICK) {
                if (pracmanager.isInPrac(player)) {
                    pracmanager.teleportToPrac(player);
                } else {
                    progressmanager.tpToLastCp(player);
                }
            }
        }
    }
}
