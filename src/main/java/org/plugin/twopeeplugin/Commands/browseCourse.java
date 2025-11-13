package org.plugin.twopeeplugin.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.plugin.twopeeplugin.Core.housing.guiEventListener;

import java.util.ArrayList;
import java.util.List;

public class browseCourse implements CommandExecutor, TabCompleter {
    guiEventListener guieventlistener;
    public browseCourse(guiEventListener listener) {
        guieventlistener = listener;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            guieventlistener.open((Player) commandSender);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>();
    }
}
