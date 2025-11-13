package org.plugin.twopeeplugin.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.plugin.twopeeplugin.Core.parkour.pracManager;

public class prac implements CommandExecutor {
    pracManager pracmanager;
    public prac(pracManager pm) {
        pracmanager = pm;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            pracmanager.enterPrac(((Player) commandSender).getPlayer());
            return true;
        }
        return false;
    }
}
