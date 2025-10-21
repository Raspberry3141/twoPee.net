package org.plugin.twopeeplugin.Commands;

import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getWorld;

public class lobby implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            if (getWorld("lobby")==null) {
                new WorldCreator("lobby").createWorld();
            }
            ((Player) commandSender).teleport(getWorld("lobby").getSpawnLocation());
        }
        return true;
    }
}
