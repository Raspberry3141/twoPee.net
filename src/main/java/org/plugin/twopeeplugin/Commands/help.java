package org.plugin.twopeeplugin.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.plugin.twopeeplugin.Utils.chatMessenger;

public class help implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		if (commandSender instanceof Player) {
			chatMessenger.sendCommandList((Player) commandSender);
			return true;
		}
		return false;
	}
}
