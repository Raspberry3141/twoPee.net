package org.plugin.twopeeplugin.Commands.builderCommnads;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.plugin.twopeeplugin.Utils.chatMessenger;

import java.util.Collections;
import java.util.List;

public class setSpawn implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            if (strings.length==0) {
                int x=((Player) commandSender).getLocation().getBlockX();
                int y=((Player) commandSender).getLocation().getBlockY();
                int z=((Player) commandSender).getLocation().getBlockZ();
                World world = ((Player) commandSender).getWorld();
                world.setSpawnLocation(x,y,z);
                chatMessenger.sendSetSpawn(((Player) commandSender).getPlayer(), x,y,z);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return Collections.emptyList();
    }
}
