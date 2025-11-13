package org.plugin.twopeeplugin.Commands.builderCommnads;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.plugin.twopeeplugin.Core.parkour.courseBuilder;

public class mapbuilder implements CommandExecutor {
    courseBuilder coursebiulder;

    public mapbuilder(courseBuilder cb) {
        coursebiulder = cb;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player)commandSender;
            coursebiulder.createOrJoinNewCourse(player);
            return true;
        }
        return false;
    }
}
