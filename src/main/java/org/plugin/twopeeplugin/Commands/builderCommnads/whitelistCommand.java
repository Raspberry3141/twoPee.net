package org.plugin.twopeeplugin.Commands.builderCommnads;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.plugin.twopeeplugin.Core.whiteListWorldManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class whitelistCommand implements CommandExecutor, TabCompleter {
    whiteListWorldManager whitelistmanager;
    public whitelistCommand(whiteListWorldManager wm) {
        whitelistmanager = wm;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 0) return false;
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;

        String option = strings[0].toLowerCase();
        switch (option){
            case "add":
                if (strings.length > 1) {
                    whitelistmanager.addPlayer(player,player.getWorld().getName(),strings[1]);
                    return true;
                }
                return false;
            case "remove":
                if (strings.length > 1) {
                    whitelistmanager.removePlayer(player,player.getWorld().getName(),strings[1]);
                    return true;
                }
                return false;
            case "enable":
                whitelistmanager.enableWhiteList(player);
                return true;
            case "disable":
                whitelistmanager.disableWhiteList(player);
                return true;
            case "list":
                List<String> players = whitelistmanager.listPlayer(player);
                printList(player,players);
                return true;
            case "clear":
                whitelistmanager.clearList(player);
                return true;
            default:
                return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("add", "remove", "clear", "enable", "disable", "list");
        }
        return Collections.singletonList(sender.getName());
    }

    private void printList(Player player,List<String> players) {
        if (players.isEmpty()) {
            player.sendMessage(ChatColor.YELLOW + "Empty:");
            return;
        }
        player.sendMessage(ChatColor.YELLOW + "Players:");
        for (String name:players) {
            player.sendMessage(ChatColor.YELLOW + name);
        }
    }
}
