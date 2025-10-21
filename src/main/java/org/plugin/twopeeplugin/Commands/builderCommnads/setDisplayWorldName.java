package org.plugin.twopeeplugin.Commands.builderCommnads;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.plugin.twopeeplugin.Utils.chatMessenger;
import org.plugin.twopeeplugin.Utils.courseYamlConfig;

import java.util.Arrays;

public class setDisplayWorldName implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            if (strings.length>0) {
                String worldName = ((Player) commandSender).getWorld().getName();
                String newName = String.join(" ",strings);
                courseYamlConfig.getConfig().set("course." + worldName + ".display name",newName);
                courseYamlConfig.getInstance().save();
                chatMessenger.sendChangeDisPlayName((Player) commandSender,newName);
                return true;
            }
        }
        return false;
    }
}
