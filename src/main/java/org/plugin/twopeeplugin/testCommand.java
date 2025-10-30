package org.plugin.twopeeplugin;

import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.plugin.twopeeplugin.Core.courseBuilder;
import org.plugin.twopeeplugin.Core.guiEventListener;
import org.plugin.twopeeplugin.Core.progressManager;

import static org.bukkit.Bukkit.getWorld;

public class testCommand implements CommandExecutor {
    private courseBuilder coursebuilder;
    private guiEventListener guilistener;
    private progressManager progressmanager;

    public testCommand(courseBuilder cb, guiEventListener gui,progressManager pr) {
        coursebuilder = cb;
        guilistener = gui;
        progressmanager = pr;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (Integer.parseInt(strings[0]) == 0) {
            coursebuilder.initializeNewCourse((Player) commandSender);
        } else if (Integer.parseInt(strings[0]) == 1) {
            if (getWorld(strings[1])==null) {
                new WorldCreator(strings[1]).createWorld();
            }
            ((Player) commandSender).teleport(getWorld(strings[1]).getSpawnLocation());
        } else if (Integer.parseInt(strings[0]) == 2) {
            guilistener.open((Player) commandSender);
        } else if (Integer.parseInt(strings[0])==3) {
            progressmanager.tpToLastCp((Player) commandSender);
        } else if (Integer.parseInt(strings[0])==4) {
            ((Player)commandSender).sendMessage(progressmanager.getFormattedTime((Player) commandSender));
        } else if (Integer.parseInt(strings[0])==5) {
            progressmanager.leaveCourse((Player) commandSender);
        } else if (Integer.parseInt(strings[0])==6) {
            progressmanager.enterCourse((Player) commandSender);
        }
        return false;
    }

}
