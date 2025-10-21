package org.plugin.twopeeplugin.Core;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.plugin.twopeeplugin.Utils.chatMessenger;
import org.plugin.twopeeplugin.Utils.tpLocationsYamlConfig;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.bukkit.Bukkit.getWorld;

public class tpLocationManager implements Listener {
    private final Set<Material> clickableBlocks = new HashSet<>(Arrays.asList(Material.WALL_SIGN, Material.SIGN,
            Material.SIGN_POST, Material.STONE_BUTTON, Material.WOOD_BUTTON, Material.STONE_PLATE, Material.IRON_PLATE));

    @EventHandler
    private void onBlockUsed(PlayerInteractEvent event) {
        if (event.getAction()==null) return;
        if (event.getPlayer()==null) return;
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction()==Action.PHYSICAL) {
            if (event.getClickedBlock()==null) return;
            if (event.getClickedBlock().getType()== Material.GOLD_PLATE) return;
            Location blockLoc = event.getClickedBlock().getLocation();
            String lookUpKey = blockLoc.getBlockX() + " " + blockLoc.getBlockY() + " " + blockLoc.getBlockZ() + " " +
                    blockLoc.getWorld().getName();
            String tpLoc = tpLocationsYamlConfig.getConfig().getString(lookUpKey);
            if (!(tpLoc==null)) {
                Location newTpLoc = parseTpLoc(event.getPlayer(),tpLoc);
                progressManager.teleport(event.getPlayer(),newTpLoc);
            }
        }
    }

    @EventHandler
    private void onBlockBroken(BlockBreakEvent event) {
        if (event.getPlayer()==null) return;
        if (event.getBlock()==null) return;
        if (clickableBlocks.contains(event.getBlock().getType())) {
            Location blockLoc = event.getBlock().getLocation();
            String lookUpKey = blockLoc.getBlockX() + " " + blockLoc.getBlockY() + " " + blockLoc.getBlockZ() + " " +
                    blockLoc.getWorld().getName();
            tpLocationsYamlConfig.getConfig().set(lookUpKey,null);
            tpLocationsYamlConfig.getInstance().save();
        }

    }

    public void createTpBlock(Player player, Location blockPos, String tpLoc) {
        Location tpLocation = parseTpLoc(player,tpLoc);
        String storedLoc = tpLocation.getWorld().getName() + " " + tpLocation.getX() + " " + tpLocation.getY() + " "
                + tpLocation.getZ() + " " + tpLocation.getYaw() + " " + tpLocation.getPitch();
        String keyBlockPos = parseBlockPos(blockPos);
        tpLocationsYamlConfig.getConfig().set(keyBlockPos,storedLoc);
        tpLocationsYamlConfig.getInstance().save();
        chatMessenger.sendcreateTpBlock(player, blockPos.getBlockX(), blockPos.getBlockY(), blockPos.getBlockZ());
    }

    private String parseBlockPos(Location blockPos) {
        return blockPos.getBlockX() + " " + blockPos.getBlockY() + " " + blockPos.getBlockZ() +
                " " +  blockPos.getWorld().getName();
    }

    private Location parseTpLoc(Player player,String tpLoc) {
        World world = player.getWorld();
        System.out.println(tpLoc);
        String[] locs = tpLoc.split(" ");
        System.out.println(Arrays.toString(locs));
        double x = player.getLocation().getX();
        double y = player.getLocation().getY();
        double z = player.getLocation().getZ();
        float yaw = player.getLocation().getYaw();
        float pitch = player.getLocation().getPitch();
        if (locs.length>0 && getWorld(locs[0])!=null) world = getWorld(locs[0]);
        if (locs.length>1) x = Double.parseDouble(locs[1]);
        if (locs.length>2) y = Double.parseDouble(locs[2]);
        if (locs.length>3) z = Double.parseDouble(locs[3]);
        if (locs.length>4) yaw = Float.parseFloat(locs[4]);
        if (locs.length>5) pitch = Float.parseFloat(locs[5]);
        return new Location(world,x,y,z,yaw,pitch);
    }
}
