package org.plugin.twopeeplugin.Utils;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class chatMessenger {

	public static void sendCommandList(Player player) {
		player.sendMessage(ChatColor.YELLOW + "/browse : Open map browser");
		player.sendMessage(ChatColor.YELLOW + "/lobby, /l : teleport to lobby");
		player.sendMessage(ChatColor.YELLOW + "/pcp <#>, /p  <#>: teleport to previous checkpoint or specified checkpoint");
		player.sendMessage(ChatColor.YELLOW + "/gap /sword /pcpitem : gives specified item");
		player.sendMessage(ChatColor.YELLOW + "/join [map id|username]/j : Teleport to the specified map or the map the user specified is in");
		player.sendMessage(ChatColor.YELLOW + "/invite [username] /i : Invite a player to your map");
		player.sendMessage(ChatColor.YELLOW + "/prac /unprac /u : enter/leave prac mode");
		player.sendMessage(ChatColor.YELLOW + "/mapbuilder : craete/teleport to your world");
		player.sendMessage("=============Builder Mode Command=============");
		player.sendMessage(ChatColor.YELLOW + "/platform : spawns a platform at 0 64 0");
		player.sendMessage(ChatColor.YELLOW + "/setnewspawn : sets the world spawnpoint");
		player.sendMessage(ChatColor.YELLOW + "/setdisplay : sets the display name");
		player.sendMessage(ChatColor.YELLOW + "/verify : verify your map to publish");
		player.sendMessage(ChatColor.YELLOW + "/cancelverify : cancel verification");
		player.sendMessage(ChatColor.YELLOW + "/gms : enter adventure mode");
		player.sendMessage(ChatColor.YELLOW + "/gmc : enter creative mode");
		player.sendMessage(ChatColor.YELLOW + "/fly : toggle fly");
		player.sendMessage(ChatColor.YELLOW + "/whitelist : configure whitelist for your map");
		player.sendMessage(ChatColor.YELLOW + "/itemblock : create an item block at the block looking at");
		player.sendMessage(ChatColor.YELLOW + "/tpblock : create a tp block at the block looking at");

	}

	public static void sendPracFail(Player player) {
		player.sendMessage(ChatColor.YELLOW + "Failed Entering Practice Mode..");
	}
	public static void sendPracEnter(Player player) {
		player.sendMessage(ChatColor.YELLOW + "Entering Practice Mode..");
	}

	public static void sendPracLeave(Player player) {
		player.sendMessage(ChatColor.YELLOW + "Leaving Practice Mode..");
	}

	public static void sendItemGiven(Player player, String itemName) {
		player.sendMessage(ChatColor.YELLOW + "You got " + itemName + "!");
		playLevelUpSound(player);
	}

	public static void sendUpdateRank(Player player, String rank) {
		player.sendMessage(ChatColor.YELLOW + "Ranked up! You Are Now " + rank);
		playLevelUpSound(player);
	}

	public static void sendCurrentCompletions(Player player, int completion) {
		player.sendMessage(ChatColor.YELLOW + "Not Enough Completions: " + completion);
	}

	public static void sendFlyToggle(Player player, boolean fly) {
		String string = "disabled";
		if (fly) {
			string = "Enabled";
		}
		player.sendMessage(ChatColor.YELLOW + "Flight " + string);
	}

	public static void sendTpToSpawn(Player player) {
		player.sendMessage(ChatColor.YELLOW + "Teleporting to Spawn..");
	}

	public static void sendDiscordLink(Player player) {
		TextComponent inviteToWorld = new TextComponent(
				ChatColor.YELLOW + "Join Our Discord! Click HERE to Redirect..");
		inviteToWorld.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/9JfqVsEhMX"));
		player.spigot().sendMessage(inviteToWorld);
		playLevelUpSound(player);
	}

	public static void sendWhiteListAction(Player player, String action) {
		player.sendMessage(ChatColor.YELLOW + "Whitelist " + action);
	}

	public static void sendWhiteListed(Player player, String courseName) {
		player.sendMessage(ChatColor.YELLOW + "Map " + courseName + " is whiteListed! Ask Owner to Whitelist " +
				"You and Try Again!");
	}

	public static void sendInvitation(Player invitee, Player inviter, String world) {
		TextComponent inviteToWorld = new TextComponent(ChatColor.YELLOW + inviter.getName() +
				" Invited You to " + world + "! Click to Join!");
		inviteToWorld.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/join " + inviter.getName()));
		invitee.spigot().sendMessage(inviteToWorld);
	}

	public static void sendMapVerified(Player player, String courseName) {
		player.sendMessage(ChatColor.YELLOW + "Map " + courseName + " Verified! " + courseName + " is Now Published!");
		playLevelUpSound(player);
	}

	public static void sendVerifiable(Player player) {
		player.sendMessage(ChatColor.YELLOW + "Verify the Course to Publish the Map! /cancelverify to Cancel");
		playLevelUpSound(player);
	}

	public static void sendNonVerifiable(Player player) {
		player.sendMessage(ChatColor.YELLOW + "Verification Canceled");
		playLevelUpSound(player);
	}

	public static void sendcreateTpBlock(Player player, int x, int y, int z) {
		player.sendMessage(ChatColor.YELLOW + "Created a tp Block at " + x + " " + y + " " + z);
		playLevelUpSound(player);
	}

	public static void sendSetSpawn(Player player, int x, int y, int z) {
		player.sendMessage(ChatColor.YELLOW + "Spawnpoint Set to " + x + " " + y + " " + z);
		playLevelUpSound(player);
	}

	public static void sendChangeDisPlayName(Player player, String str) {
		player.sendMessage(ChatColor.YELLOW + "World Name Changed to " + str);
		playLevelUpSound(player);
	}

	public static void sendTeleportMessage(Player player, Location location) {
		player.sendMessage(ChatColor.YELLOW + "Teleporting to " + location.getX() + " " + location.getY() + " "
				+ location.getZ() + " " + location.getYaw() + " " + location.getPitch());
		playLevelUpSound(player);
	}

	public static void sendPreviousCheckpointMessage(Player player, int cp) {
		player.sendMessage(ChatColor.YELLOW + "Teleporting to Checkpoint #" + cp);
		playLevelUpSound(player);
	}

	public static void sendCheckpointReached(Player player, int cp) {
		player.sendMessage(ChatColor.YELLOW + "Checkpoint Reached: #" + cp);
		playLevelUpSound(player);
	}

	public static void sendParkourEnd(Player player, String time) {
		player.sendMessage(ChatColor.YELLOW + "Parkour Finished in " + time + "!");
		playLevelUpSound(player);
	}

	public static void sendParkourStart(Player player) {
		player.sendMessage(ChatColor.YELLOW + "Parkour Started!");
		playLevelUpSound(player);
	}

	public static void sendGiveItem(Player player,String name) {
		player.sendMessage(ChatColor.YELLOW + "You got " + name + "!");
		playLevelUpSound(player);
	}

	private static void playLevelUpSound(Player player) {
		player.playSound(player.getLocation(), Sound.LEVEL_UP, 10.0f, 1.0f);
	}
}
