package org.plugin.twopeeplugin.Core.housing;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.plugin.twopeeplugin.TwoPeePlugin;
import org.plugin.twopeeplugin.Utils.courseYamlConfig;

public class PlayerCountTracker {
	private HashMap<String,Integer> mapping;

	public PlayerCountTracker()  {
		mapping = new HashMap<>();

		new BukkitRunnable() {
			@Override
			public void run() {
				mapping.clear();
				updateCount();
			}
		}.runTaskTimer(TwoPeePlugin.getInstance(), 0L, 80L);
	}

	private void updateCount() {
		for (Player player: Bukkit.getOnlinePlayers()) {
			String worldName = player.getWorld().getName();
			if (isVerified(worldName)) {
				int players = mapping.getOrDefault(worldName,0);
				mapping.put(worldName, ++players);
			}
		}
	}


	private boolean isVerified(String worldName) {
		return courseYamlConfig.getConfig().getBoolean("course." + worldName + ".verified");
	}

	public int getPlayerCount(String worldName) {
		return mapping.getOrDefault(worldName, 0);
	}
}
