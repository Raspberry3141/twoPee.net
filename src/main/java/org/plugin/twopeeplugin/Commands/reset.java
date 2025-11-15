package org.plugin.twopeeplugin.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.plugin.twopeeplugin.Core.housing.itemManager;
import org.plugin.twopeeplugin.Core.parkour.checkpointListener;
import org.plugin.twopeeplugin.Core.parkour.progressManager;
import org.plugin.twopeeplugin.Utils.chatMessenger;

public class reset implements CommandExecutor {
	private checkpointListener checkpointListener;
	private itemManager itemmanager;

	public reset(checkpointListener cl,itemManager im) {
		checkpointListener = cl;
		itemmanager = im;
	}

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;
			if (player!=null) {
				checkpointListener.resetParkour(player.getWorld().getName(), player.getUniqueId());
				progressManager.teleportToSpawn(player);
				itemmanager.resetInventory(player);
				chatMessenger.sendParkourReset(player);
				return true;
			}
		}
        return false;
    }
}
