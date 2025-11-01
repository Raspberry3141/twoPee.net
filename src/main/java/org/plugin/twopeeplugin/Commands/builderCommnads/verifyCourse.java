package org.plugin.twopeeplugin.Commands.builderCommnads;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.plugin.twopeeplugin.Core.groupManager;
import org.plugin.twopeeplugin.Utils.chatMessenger;
import org.plugin.twopeeplugin.Core.courseBuilder;

public class verifyCourse implements CommandExecutor {
    courseBuilder coursebuilder;
    groupManager groupmanager;

    public verifyCourse(courseBuilder cb, groupManager gp) {
        coursebuilder = cb;
        groupmanager = gp;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            coursebuilder.setVerifiable((Player) commandSender,true);
            chatMessenger.sendVerifiable((Player) commandSender);
            groupmanager.leaveBuildingMode((Player) commandSender);
            //clear inv
            //reset last pos
            //tpt to spawn
            //gamemode a
            //
        }
        return false;
    }
}
