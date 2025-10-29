package org.plugin.twopeeplugin.Commands.builderCommnads;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.plugin.twopeeplugin.Core.giveItemManager;
import org.plugin.twopeeplugin.Utils.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class itemBlock implements CommandExecutor, TabCompleter {
    giveItemManager itemman;

    public itemBlock(giveItemManager im) {
        itemman = im;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (strings.length==2) {
                items i = parseItem(strings[0]);
                int kb = parseKb(strings[1]);
                if (i!=null) {
                    itemman.createGiveBlock(player,i,kb);
                    return true;
                }
            }
        }
        return false;
    }

    private int parseKb(String kb) {
        try {
            return Integer.parseInt(kb);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private items parseItem(String str) {
        for (items i:items.values()) {
            if (i.name().equalsIgnoreCase(str)) {
                return i;
            }
        }
        return null;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> content = new ArrayList<>();
            for (items i:items.values()) {
                content.add(i.toString());
            }
             return content;
        }
        return Collections.singletonList(sender.getName());
    }
}
