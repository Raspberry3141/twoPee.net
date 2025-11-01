package org.plugin.twopeeplugin.Utils;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class chatMessenger {

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
