package org.plugin.twopeeplugin.Core.housing;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.plugin.twopeeplugin.Core.parkour.progressManager;

import java.util.HashMap;
import java.util.UUID;

public class guiEventListener implements Listener {
    private final HashMap<UUID, gui> guiMapping;

    public guiEventListener() {
        guiMapping = new HashMap<>();

    }
    public void open(Player player) {
        guiMapping.put(player.getUniqueId(),new gui(player,"Map Browser"));
    }
    @EventHandler
    private void onclick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        if (!(event.getInventory().getHolder()==null)) return;
        if (event.getCurrentItem().getItemMeta()==null) return;
        gui GUI = guiMapping.get(event.getWhoClicked().getUniqueId());
        if (event.getRawSlot()==48) {
            int currentPage = GUI.getCurrentPage();
            GUI.changePage(--currentPage);
        } else if (event.getRawSlot()==50) {
            int currentPage = GUI.getCurrentPage();
            GUI.changePage(++currentPage);
        } else {
            String worldName = event.getCurrentItem().getItemMeta().getLore().get(0);
            progressManager.teleport((Player) event.getWhoClicked(),worldName);
        }
        event.setCancelled(true);
    }
}
