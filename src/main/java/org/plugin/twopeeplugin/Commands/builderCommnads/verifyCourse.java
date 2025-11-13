package org.plugin.twopeeplugin.Commands.builderCommnads;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.World;
import org.plugin.twopeeplugin.Core.housing.groupManager;
import org.plugin.twopeeplugin.Core.parkour.progressManager;
import org.plugin.twopeeplugin.Utils.chatMessenger;
import org.plugin.twopeeplugin.Core.parkour.checkpointListener;
import org.plugin.twopeeplugin.Core.parkour.courseBuilder;

import java.util.List;

public class verifyCourse implements CommandExecutor {
	private courseBuilder coursebuilder;
	private groupManager groupmanager;
	private progressManager progressmanager;
	private checkpointListener checkpointlistener;

	public verifyCourse(courseBuilder cb, groupManager gp, progressManager pm, checkpointListener cl) {
		coursebuilder = cb;
		groupmanager = gp;
		progressmanager = pm;
		checkpointlistener = cl;
	}

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		if (commandSender instanceof Player) {
			coursebuilder.setVerifiable((Player) commandSender, true);
			chatMessenger.sendVerifiable((Player) commandSender);
			groupmanager.leaveBuildingMode((Player) commandSender);

			Player player = (Player) commandSender;
			World world = player.getWorld();
			List<Player> players = world.getPlayers();
			for (Player p : players) {
				p.setGameMode(GameMode.ADVENTURE);
				p.getInventory().clear();
				p.teleport(p.getWorld().getSpawnLocation());
				progressmanager.clearLastPos(p);
				checkpointlistener.resetParkour(p.getWorld().getName(), p.getUniqueId());
			}
		}
		return false;
	}
}
