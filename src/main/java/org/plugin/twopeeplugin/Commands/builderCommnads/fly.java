package org.plugin.twopeeplugin.Commands.builderCommnads;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.plugin.twopeeplugin.Utils.chatMessenger;

public class fly implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            ((Player) commandSender).setAllowFlight(!((Player) commandSender).getAllowFlight());
            chatMessenger.sendFlyToggle(((Player) commandSender).getPlayer(), ((Player) commandSender).getAllowFlight());
        }
        return true;
    }
}
