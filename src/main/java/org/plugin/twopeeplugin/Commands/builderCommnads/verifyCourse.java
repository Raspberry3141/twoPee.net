package org.plugin.twopeeplugin.Commands.builderCommnads;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.plugin.twopeeplugin.Core.groupManager;
import org.plugin.twopeeplugin.Core.progressManager;
import org.plugin.twopeeplugin.Utils.chatMessenger;
import org.plugin.twopeeplugin.Core.checkpointListener;
import org.plugin.twopeeplugin.Core.courseBuilder;

public class verifyCourse implements CommandExecutor {
    private courseBuilder coursebuilder;
    private groupManager groupmanager;
	private progressManager progressmanager;
	private checkpointListener checkpointlistener;

    public verifyCourse(courseBuilder cb, groupManager gp, progressManager pm,checkpointListener cl) {
        coursebuilder = cb;
        groupmanager = gp;
		progressmanager = pm;
		checkpointlistener = cl;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            coursebuilder.setVerifiable((Player) commandSender,true);
            chatMessenger.sendVerifiable((Player) commandSender);
            groupmanager.leaveBuildingMode((Player) commandSender);

			Player player = (Player) commandSender;
			player.setGameMode(GameMode.ADVENTURE);
			player.getInventory().clear();
			player.teleport(player.getWorld().getSpawnLocation());
			progressmanager.clearLastPos(player);
			checkpointlistener.resetParkour(player.getWorld().getName(),player.getUniqueId());
        }
        return false;
    }
}
