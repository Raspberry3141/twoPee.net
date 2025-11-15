package org.plugin.twopeeplugin.Commands.builderCommnads;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.plugin.twopeeplugin.Utils.courseYamlConfig;
import org.plugin.twopeeplugin.Utils.playerYamlConfig;

public class delete implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
			String worldname = ((Player) commandSender).getWorld().getName();
			courseYamlConfig.getConfig().set("course." + worldname, null);
			playerYamlConfig.getConfig().set(((Player)commandSender).getName() + "working world", null);
			playerYamlConfig.getInstance().save();
			courseYamlConfig.getInstance().save();
            return true;
        }
        return false;
    }
}
