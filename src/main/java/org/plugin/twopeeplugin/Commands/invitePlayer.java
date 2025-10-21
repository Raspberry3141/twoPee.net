package org.plugin.twopeeplugin.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.plugin.twopeeplugin.Utils.chatMessenger;

import static org.bukkit.Bukkit.getPlayer;

public class invitePlayer implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            if (strings.length==1) {
                Player executer = (Player) commandSender;
                Player player = getPlayer(strings[0]);
                String worldname = executer.getWorld().getName();
                chatMessenger.sendInvitation(player,executer,worldname);
                return true;
            }
        }
        return false;
    }
}
