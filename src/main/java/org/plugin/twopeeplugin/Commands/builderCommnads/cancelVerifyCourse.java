package org.plugin.twopeeplugin.Commands.builderCommnads;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.plugin.twopeeplugin.Core.groupManager;
import org.plugin.twopeeplugin.Utils.chatMessenger;
import org.plugin.twopeeplugin.Core.courseBuilder;

public class cancelVerifyCourse implements CommandExecutor {
    courseBuilder coursebuilder;
    groupManager groupmanager;

    public cancelVerifyCourse(courseBuilder cb, groupManager gp) {
        coursebuilder = cb;
        groupmanager = gp;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            coursebuilder.setVerifiable((Player) commandSender,false);
            chatMessenger.sendNonVerifiable((Player) commandSender);
            groupmanager.enterBuildingMode((Player) commandSender);
        }
        return false;
    }
}
