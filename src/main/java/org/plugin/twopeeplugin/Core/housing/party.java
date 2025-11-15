package org.plugin.twopeeplugin.Core.housing;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;

public class party {
	public boolean partyProtection;
	public Player leader;
	public Set<Player> members;

	public party() {
		partyProtection = false;
		members = new HashSet<>();
	}

}
