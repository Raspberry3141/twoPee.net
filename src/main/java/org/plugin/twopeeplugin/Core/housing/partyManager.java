package org.plugin.twopeeplugin.Core.housing;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.plugin.twopeeplugin.Core.housing.party;

public class partyManager {
	private Map<Player, party> parties;

	public partyManager() {
		parties = new HashMap<>();
	}
	public void createParty(Player leader) {
		party party = new party();
		parties.put(leader, party);
	}

	public void joinParty(Player leader, Player player) {
		parties.get(leader).members.add(player);
	}

	public void invitePlayer() {
	}

	public void changeLeader() {
	}

	public void disbandParty(Player leader) {
	}

	public void toggleProtection() {
	}
}
