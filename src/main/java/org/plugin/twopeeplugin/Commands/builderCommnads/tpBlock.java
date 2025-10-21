package org.plugin.twopeeplugin.Commands.builderCommnads;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.plugin.twopeeplugin.Core.tpLocationManager;

import java.util.*;

public class tpBlock implements CommandExecutor {
    private tpLocationManager tplocationmanager;
    private final Set<Material> clickableBlocks = new HashSet<>(Arrays.asList(Material.WALL_SIGN, Material.SIGN,
    Material.SIGN_POST, Material.STONE_BUTTON, Material.WOOD_BUTTON, Material.STONE_PLATE, Material.IRON_PLATE));

    public tpBlock(tpLocationManager man) {
        tplocationmanager = man;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            String loc = ((Player) commandSender).getWorld().getName() + " " + String.join(" ",strings);
            Player player = (Player) commandSender;
            Set<Material> transparent = new HashSet<>();
            transparent.add(Material.AIR);
            Block block = player.getTargetBlock(transparent,20);
            if (clickableBlocks.contains(block.getType())) {
                tplocationmanager.createTpBlock(player,block.getLocation(),loc);
                return true;
            }
        }
        return false;
    }
}
