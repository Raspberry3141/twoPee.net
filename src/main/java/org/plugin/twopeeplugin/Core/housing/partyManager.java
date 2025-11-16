package org.plugin.twopeeplugin.Core.housing;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.plugin.twopeeplugin.Core.housing.Party;

public class partyManager {
    public final Map<Player, Party> playerParty = new HashMap<>();
    private final Map<Player, Invite> invites = new HashMap<>();

    public Party getParty(Player p) {
        return playerParty.get(p);
    }

    public boolean isInParty(Player p) {
        return playerParty.containsKey(p);
    }

    public Party createParty(Player leader) {
        Party party = new Party(leader);
        playerParty.put(leader, party);
        return party;
    }

    public void disband(Party party) {
        for (Player p : party.getMembers()) {
            playerParty.remove(p);
            p.sendMessage("§cYour party has been disbanded!");
        }
    }

    public void addInvite(Player sender, Player target) {
        invites.put(target, new Invite(sender, target));
    }

    public Invite getInvite(Player target) {
        Invite inv = invites.get(target);
        if (inv != null && inv.isExpired()) {
            invites.remove(target);
            return null;
        }
        return inv;
    }

    public void acceptInvite(Player target) {
        Invite inv = getInvite(target);
        if (inv == null) return;

        Player leader = inv.getSender();
        Party p = playerParty.get(leader);

        if (p == null) return;

        p.addMember(target);
        playerParty.put(target, p);

        p.sendMessage("§a" + target.getName() + " joined the party!");
        invites.remove(target);
    }
}
