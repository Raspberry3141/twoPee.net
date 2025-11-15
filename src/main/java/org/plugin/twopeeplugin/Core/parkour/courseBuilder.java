package org.plugin.twopeeplugin.Core.parkour;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.plugin.twopeeplugin.Utils.courseYamlConfig;
import org.plugin.twopeeplugin.Utils.playerYamlConfig;

import java.time.LocalDate;

public class courseBuilder{
    public courseBuilder() {
    }

    public void setVerifiable(Player player, boolean b) {
        courseYamlConfig.getConfig().set("course."+player.getWorld().getName()+".verifiable",b);
        courseYamlConfig.getInstance().save();
    }

    public void createOrJoinNewCourse(Player player) {
        String worldName = getCurrentWorkingWorld(player);
        if (worldName!=null) {
            boolean isVerified = courseYamlConfig.getConfig().getBoolean("course."+worldName+".verified");
            System.out.println(isVerified);
            if (isVerified) {
                initializeNewCourse(player);
                progressManager.teleport(player,worldName);
            } else {
                progressManager.teleport(player,worldName);
            }
        } else {
            String newName = initializeNewCourse(player);
            progressManager.teleport(player,newName);
        }
    }

    public String initializeNewCourse(Player player) {
        int worldNumber = 0;
        while (!(courseYamlConfig.getConfig().getString("course."+player.getName()+worldNumber)==null)) {
            worldNumber++;
        }
        String worldName = player.getName() + worldNumber;
        newWorld(worldName);
        registerNewWorld(worldName,player.getName());
        return worldName;
    }

    private String getCurrentWorkingWorld(Player player) {
        return playerYamlConfig.getConfig().getString(player.getName() + ".working world");
    }

    private void addToCurrentWorkingWorld(String worldName, String playerName) {
        String path = playerName + ".working world";
        playerYamlConfig.getConfig().set(path,worldName);
        playerYamlConfig.getInstance().save();
    }

    private void registerNewWorld(String worldName, String playerName) {
        String path = "course." + worldName;
        courseYamlConfig.getConfig().set(path + ".author", playerName);
        courseYamlConfig.getConfig().set(path + ".date", LocalDate.now().toString());
        courseYamlConfig.getConfig().set(path + ".display name", worldName);
        courseYamlConfig.getConfig().set(path + ".verified", false);
        courseYamlConfig.getConfig().set(path + ".verifiable", false);
        courseYamlConfig.getConfig().set(path + ".difficulty", "unrated");
        courseYamlConfig.getInstance().save();
        addToCurrentWorkingWorld(worldName,playerName);
    }

    private void newWorld(String worldName) {
        WorldCreator wc = new WorldCreator(worldName);
        wc.type(WorldType.FLAT);
        wc.generatorSettings("2;0;1;");
        wc.createWorld();
        disableMechanics(worldName);
		initPlatform(Bukkit.getWorld(worldName));
    }

	private void initPlatform(World world) {
		world.getBlockAt(new Location(world, 0, 64, 0)).setType(Material.BEDROCK);
		world.setSpawnLocation(0, 65, 0);
	}

    private void disableMechanics(String worldName) {
        World world = Bukkit.getWorld(worldName);
        String falseString = "false";
        String trueString = "true";
        world.setGameRuleValue("doDaylightCycle", falseString);
        world.setGameRuleValue("doEntityDrop", falseString);
        world.setGameRuleValue("doFireTick", falseString);
        world.setGameRuleValue("doMobLoot", falseString);
        world.setGameRuleValue("doMobSpawning", falseString);
        world.setGameRuleValue("doTileDrops", falseString);
        world.setGameRuleValue("KeepInventory", trueString);
        world.setGameRuleValue("mobGriefing", falseString);
        world.setGameRuleValue("showDeathMessage", falseString);
        world.setGameRuleValue("randomTickSpeed","0");
        world.setGameRuleValue("commandblockoutput",falseString);
        world.setStorm(false);
        world.setThundering(false);
        world.setWeatherDuration(Integer.MAX_VALUE);
        world.setThunderDuration(0);
        createBorder(world);
    }

    private void createBorder(World world) {
        WorldBorder border = world.getWorldBorder();
        border.setCenter(0.0,0.0);
        border.setSize(500.0);
        border.setWarningDistance(20);
        border.setWarningTime(10);
        border.setDamageBuffer(5.0);
        border.setDamageAmount(1.0);
    }

}
