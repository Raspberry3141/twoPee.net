package org.plugin.twopeeplugin.Commands.builderCommnads;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class gamemodeCreative implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            ((Player) commandSender).setGameMode(GameMode.CREATIVE);
            return true;
        }
        return false;
    }
}
