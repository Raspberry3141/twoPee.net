package org.plugin.twopeeplugin.Core;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.plugin.twopeeplugin.Utils.chatMessenger;
import org.plugin.twopeeplugin.Utils.courseYamlConfig;

import java.util.*;

import static org.bukkit.Bukkit.getWorld;

public class progressManager {
    private parkourTimer parkourtimer;
    private YamlConfiguration config;
    private itemManager itemmanager;
    private pracManager pracmanager;
    public progressManager(parkourTimer timer, itemManager im,pracManager pm) {
        config = courseYamlConfig.getConfig();
        parkourtimer = timer;
        itemmanager = im;
        pracmanager = pm;
    }


    public void enterCourse(Player player) {
        if (!(player.getWorld()==null)) {
            parkourtimer.startTime(player.getUniqueId(),player.getWorld().getName());
            tpToLastPos(player);
            itemmanager.loadInventory(player);
        }
    }

    public void startCourse(Player player) {
        if (!(player.getWorld()==null)) {
            parkourtimer.startTime(player.getUniqueId(),player.getWorld().getName());
            itemmanager.loadInventory(player);
        }
    }

    public void leaveCourse(Player player) {
        parkourtimer.stopTime(player.getUniqueId(),player.getWorld().getName());
        if (!pracmanager.isInPrac(player)) {
            saveLastPos(player);
        }
        itemmanager.saveInventory(player);
    }

    public void saveLastPos(Player player) {
        Location pos = player.getLocation();
        String worldName = player.getWorld().getName();
        config.set("course." + worldName + ".progress." + player.getUniqueId() + ".last pos.x",pos.getX());
        config.set("course." + worldName + ".progress." + player.getUniqueId() + ".last pos.y",pos.getY());
        config.set("course." + worldName + ".progress." + player.getUniqueId() + ".last pos.z",pos.getZ());
        config.set("course." + worldName + ".progress." + player.getUniqueId() + ".last pos.yaw",pos.getYaw());
        config.set("course." + worldName + ".progress." + player.getUniqueId() + ".last pos.pitch",pos.getPitch());
        courseYamlConfig.getInstance().save();
    }

    public void clearLastPos(Player player) {
        String worldName = player.getWorld().getName();
        config.set("course." + worldName + ".progress." + player.getUniqueId() + ".last pos.x",null);
        config.set("course." + worldName + ".progress." + player.getUniqueId() + ".last pos.y",null);
        config.set("course." + worldName + ".progress." + player.getUniqueId() + ".last pos.z",null);
        config.set("course." + worldName + ".progress." + player.getUniqueId() + ".last pos.yaw",null);
        config.set("course." + worldName + ".progress." + player.getUniqueId() + ".last pos.pitch",null);
        courseYamlConfig.getInstance().save();
    }

    private void tpToLastPos(Player player) {
        Location pos = player.getLocation();
        String worldName = player.getWorld().getName();
        double x = config.getDouble("course." + worldName + ".progress." + player.getUniqueId() + ".last pos.x");
        double y = config.getDouble("course." + worldName + ".progress." + player.getUniqueId() + ".last pos.y");
        double z = config.getDouble("course." + worldName + ".progress." + player.getUniqueId() + ".last pos.z");
        float yaw = (float) config.getDouble("course." + worldName + ".progress." + player.getUniqueId() + ".last pos.yaw");
        float pitch = (float) config.getDouble("course." + worldName + ".progress." + player.getUniqueId() + ".last pos.pitch");
        pos.setX(x);
        pos.setY(y);
        pos.setZ(z);
        pos.setYaw(yaw);
        pos.setPitch(pitch);
        teleport(player,pos);
    }

	public void clearInv(Player player) {
		itemmanager.resetInventory(player);
	}

    public void tpToLastCp(Player player) {
        String worldname = player.getWorld().getName();
        if (getWorld(worldname)!=null) {
            int index = getLastCp(player);
            tpToCpAt(player,worldname,index);
        }
    }

	private int getLastCp(Player player) {
		return courseYamlConfig.getConfig().getInt("course." + player.getWorld().getName() +".progress." + player.getUniqueId() +".last cp");
	}

    private void tpToCpAt(Player player, String worldName, Integer index) {
        if (getWorld(worldName)!=null) {
			if (index<0) {
				teleport(player,player.getWorld().getSpawnLocation());
				return;
			}
            double x = (Integer) getCheckpoints(worldName).get(index).get("x") + 0.5D;
            double y = (Integer) getCheckpoints(worldName).get(index).get("y");
            double z = (Integer) getCheckpoints(worldName).get(index).get("z") + 0.5D;
            Location location = new Location(getWorld(worldName),x,y,z);
			System.out.println(index);
			System.out.println(x +" "+" "+ y +" "+ z);
            teleport(player,location);
        }
    }

    public String getFormattedTime(Player player) {
        return parkourtimer.getTime(player.getUniqueId());
    }

    private List<Map<?,?>> getCheckpoints(String worldName) {
        List<Map<?,?>> checkpoints = Collections.emptyList();
        if (getWorld(worldName)!=null) {
            checkpoints = config.getMapList("course." + worldName + ".checkpoint");
        }
        return checkpoints;
    }

    public int getPreviousCheckpoint(Player player) {
        return config.getInt("course."+player.getWorld().getName()+".progress."+player.getUniqueId()+".last cp");

    }

    public static void teleport(Player player, Location location) {
        World world = location.getWorld();
        if (world == null) {
            new WorldCreator(location.getWorld().getName()).createWorld();
        }
        if (isAccessible(player,location.getWorld())) {
            player.teleport(location);
            chatMessenger.sendTeleportMessage(player,location);
        } else {
            chatMessenger.sendWhiteListed(player,location.getWorld().getName());
        }
    }

    public static void teleportToSpawn(Player player) {
        player.teleport(player.getWorld().getSpawnLocation());
    }

    public static void teleport(Player player, String worldName) {
        if (getWorld(worldName) == null) {
            new WorldCreator(worldName).createWorld();
        }
        if (isAccessible(player,getWorld(worldName))) {
            player.teleport(getWorld(worldName).getSpawnLocation());
            chatMessenger.sendTeleportMessage(player,getWorld(worldName).getSpawnLocation());
        }  else {
            chatMessenger.sendWhiteListed(player,worldName);
        }
    }

    private static boolean isAccessible(Player player, World world) {
        if (player.isOp()) return true;
        String owner = courseYamlConfig.getConfig().getString("course."+world.getName() + "author");
        if (owner == player.getName()) return true;
        boolean isWhiteListEnabled = courseYamlConfig.getConfig().getBoolean("course."+world.getName() + ".whitelist");
        List<String> list = courseYamlConfig.getConfig().getStringList("course."+world.getName() + ".list");
        Set<String> set = new HashSet<>(list);
        if (isWhiteListEnabled) {
            return set.contains(player.getName());
        } else {
            return true;
        }
    }
}
