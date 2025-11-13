package org.plugin.twopeeplugin.Core.housing;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.plugin.twopeeplugin.Utils.courseYamlConfig;

public class mostPopularTracker implements Listener {
	private HashMap<String,Integer> mapping;
	private String maxKey = "Raspberry511";

	public mostPopularTracker()  {
		mapping = new HashMap<>();
		countPlayers();
	}

	// @EventHandler
	// private void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
	// 	countPlayers();
	// }
	//
	// public String getMostPopularMap() {
	// 	int max = 0;
	// 	for (Map.Entry<String, Integer> entry : mapping.entrySet()) {
	// 		if (entry.getValue() > max && !entry.getKey().equals("lobby") && isVerified(entry.getKey())) {
	// 			max = entry.getValue();
	// 			maxKey = entry.getKey();
	// 		}
	// 	}
	// 	return maxKey;
	// }

	private boolean isVerified(String worldName) {
		return courseYamlConfig.getConfig().getBoolean("course." + worldName + ".verified");
	}


	private void countPlayers() {
		mapping.clear();
		for (Player player:Bukkit.getOnlinePlayers()) {
			if (player.isOnline()) {
				String worldName = player.getWorld().getName();
				mapping.put(worldName, mapping.getOrDefault(worldName, 0) + 1);
			}
		}
	}

}
