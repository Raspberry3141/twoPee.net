package org.plugin.twopeeplugin.Core.housing;

import java.util.HashMap;

import org.bukkit.scheduler.BukkitRunnable;
import org.plugin.twopeeplugin.Utils.courseYamlConfig;

public class mostPopularTracker {
	private HashMap<String,Integer> mapping;

	public mostPopularTracker()  {
		mapping = new HashMap<>();

		new BukkitRunnable() {
			
		};
	}
	//TODO implement mapping to world and player #.


	private boolean isVerified(String worldName) {
		return courseYamlConfig.getConfig().getBoolean("course." + worldName + ".verified");
	}
}
