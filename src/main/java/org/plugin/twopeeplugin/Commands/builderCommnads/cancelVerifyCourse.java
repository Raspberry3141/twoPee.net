package org.plugin.twopeeplugin.Commands.builderCommnads;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.plugin.twopeeplugin.Utils.chatMessenger;
import org.plugin.twopeeplugin.Core.courseBuilder;

public class cancelVerifyCourse implements CommandExecutor {
    courseBuilder coursebuilder;

    public cancelVerifyCourse(courseBuilder cb) {
        coursebuilder = cb;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            coursebuilder.setVerifiable((Player) commandSender,false);
            chatMessenger.sendNonVerifiable((Player) commandSender);
            //TODO permission manager
        }
        return false;
    }
}
