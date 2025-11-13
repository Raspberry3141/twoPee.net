package org.plugin.twopeeplugin.Core.parkour;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.plugin.twopeeplugin.Utils.chatMessenger;

import java.util.HashMap;

public class pracManager implements Listener {
    private HashMap<Player, Location> pracLocation;
	private progressManager progressmanager;

    @EventHandler
    public void onPlayerHitInPrac(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            if (pracLocation.containsKey(event.getDamager())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerInteractInPrac(PlayerInteractEvent event) {
        if (event.getAction()== Action.PHYSICAL) {
            if (pracLocation.containsKey(event.getPlayer())) {
                event.setCancelled(true);
            }
        }
    }

    public pracManager() {
        pracLocation = new HashMap<>();

    }

    public void enterPrac(Player player){
		if (player.isOnGround()) {
			progressmanager.saveLastPos(player);
			pracLocation.put(player,player.getLocation());
			chatMessenger.sendPracEnter(player);
		} else {
			chatMessenger.sendPracFail(player);
		}
    }

    public void leavePrac(Player player) {
        if (pracLocation.get(player)!=null) {
            player.teleport(pracLocation.get(player));
        }
        pracLocation.remove(player);
        chatMessenger.sendPracLeave(player);
    }

    public void teleportToPrac(Player player) {
        if (pracLocation.get(player)!=null) {
            player.teleport(pracLocation.get(player));
        }
    }

    public  boolean isInPrac(Player player) {
        return pracLocation.containsKey(player);
    }

	public void getProgressManager(progressManager pm) {
		progressmanager = pm;
	}
}
