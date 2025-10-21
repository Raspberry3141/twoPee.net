package org.plugin.twopeeplugin.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.plugin.twopeeplugin.Utils.courseYamlConfig;
import org.plugin.twopeeplugin.Core.progressManager;

import static org.bukkit.Bukkit.getPlayer;

public class join implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            if (strings.length==1) {
                Player player = getPlayer(strings[0]);
                String world = courseYamlConfig.getConfig().getString("course." + strings[0] + ".author");
                if (player!=null) {
                    String worldName = player.getWorld().getName();
                    progressManager.teleport((Player) commandSender,worldName);
                    return true;
                } else if (world!=null) {
                    progressManager.teleport((Player) commandSender,strings[0]);
                    return true;
                }
            }
        }
        return false;
    }
}
