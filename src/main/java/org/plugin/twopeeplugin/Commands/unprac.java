package org.plugin.twopeeplugin.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.plugin.twopeeplugin.Core.pracManager;

public class unprac implements CommandExecutor {
    pracManager pracmanager;
    public unprac(pracManager pm) {
        pracmanager = pm;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            pracmanager.leavePrac(((Player) commandSender).getPlayer());
            return true;
        }
        return false;
    }
}
