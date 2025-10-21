package org.plugin.twopeeplugin.Commands.builderCommnads;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.plugin.twopeeplugin.Core.progressManager;

public class platform implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            World world = ((Player) commandSender).getWorld();
            world.getBlockAt(0, 64,0).setType(Material.BEDROCK);
            Location loc = new Location(((Player) commandSender).getWorld(), 0D,65D,0D);
            progressManager.teleport((Player) commandSender,loc);
        }
        return true;
    }
}
