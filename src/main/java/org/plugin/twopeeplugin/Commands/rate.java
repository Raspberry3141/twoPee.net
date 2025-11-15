package org.plugin.twopeeplugin.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.plugin.twopeeplugin.Utils.courseYamlConfig;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;

public class rate implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
			if (strings.length==1) {
				String worldname = ((Player) commandSender).getWorld().getName();
				switch (strings[0]) {
					case "unrated":
						courseYamlConfig.getConfig().set("course." + worldname + ".difficulty","unrated");
						courseYamlConfig.getInstance().save();
						break;
					case "starter":
						courseYamlConfig.getConfig().set("course." + worldname + ".difficulty","starter");
						courseYamlConfig.getInstance().save();
						break;
					case "easy":
						courseYamlConfig.getConfig().set("course." + worldname + ".difficulty","easy");
						courseYamlConfig.getInstance().save();
						break;
					case "medium":
						courseYamlConfig.getConfig().set("course." + worldname + ".difficulty","medium");
						courseYamlConfig.getInstance().save();
						break;
					case "difficult":
						courseYamlConfig.getConfig().set("course." + worldname + ".difficulty","difficult");
						courseYamlConfig.getInstance().save();
						break;
					case "challenge":
						courseYamlConfig.getConfig().set("course." + worldname + ".difficulty","challenge");
						courseYamlConfig.getInstance().save();
						break;
					default:
						courseYamlConfig.getConfig().set("course." + worldname + ".difficulty","unrated");
						courseYamlConfig.getInstance().save();
						break;
				}
            return true;
			}
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("starter", "easy", "medium", "difficult", "challenge", "unrated");
        }
        return Collections.singletonList(sender.getName());
    }
}
