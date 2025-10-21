package org.plugin.twopeeplugin.Core;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.plugin.twopeeplugin.Utils.chatMessenger;
import org.plugin.twopeeplugin.Utils.courseYamlConfig;
import org.plugin.twopeeplugin.Utils.playerYamlConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class checkpointListener implements Listener {
    YamlConfiguration config = courseYamlConfig.getConfig();
    progressManager progressmanager;
    public checkpointListener(progressManager pr) {
        progressmanager = pr;

    }


    @EventHandler
    public void onPlateStepped(PlayerInteractEvent event) {
        if (event.getAction() == Action.PHYSICAL) {
            if (event.getClickedBlock().getType() == Material.GOLD_PLATE) {
                int x = event.getClickedBlock().getX();
                int y = event.getClickedBlock().getY();
                int z = event.getClickedBlock().getZ();
                if (isACourse(event.getPlayer().getWorld().getName())) {
                    int newCp = findCheckpointIndex(event.getPlayer().getWorld().getName(),x,y,z);
                    updateCheckpoint(event.getPlayer(),newCp);
                }
            }
        }
    }

    @EventHandler
    public void onPlatePlaced(BlockPlaceEvent event) {
        if (event.getPlayer() ==null) return;
        if (event.getBlockPlaced().getType() != Material.GOLD_PLATE) return;
        Block block = event.getBlockPlaced();
        int x = block.getX();
        int y = block.getY();
        int z = block.getZ();
        String worldName = event.getPlayer().getWorld().getName();
        if (isACourse(worldName)) {
            List<Map<?, ?>> checkpoints = config.getMapList("course." + worldName + ".checkpoint");
            Map<String,Integer> map = new HashMap<>();
            map.put("x",x);
            map.put("y",y);
            map.put("z",z);
            checkpoints.add(map);
            config.set("course." + worldName + ".checkpoint", checkpoints);
            courseYamlConfig.getInstance().save();
        }
    }

    @EventHandler
    public void onPlateBroken(BlockBreakEvent event) {
        if (event.getPlayer()==null) return;
        if (event.getBlock().getType()!=Material.GOLD_PLATE) return;
        int x = event.getBlock().getX();
        int y = event.getBlock().getY();
        int z = event.getBlock().getZ();
        String worldName = event.getPlayer().getWorld().getName();
        if (isACourse(worldName)) {
            List<Map<?, ?>> checkpoints = config.getMapList("course." + worldName + ".checkpoint");
            checkpoints.removeIf(map ->
                    (Integer) map.get("x") == x &&
                    (Integer) map.get("y") == y &&
                    (Integer) map.get("z") == z);
            config.set("course." + worldName + ".checkpoint", checkpoints);
            courseYamlConfig.getInstance().save();
        }
    }

    private void updateCheckpoint(Player player, int newCp) {
        UUID uuid = player.getUniqueId();
        String worldName = player.getWorld().getName();
        int currentCp = config.getInt("course." + worldName+ ".progress." + uuid +".last cp");
        if (newCp==0 && currentCp==-1) {
            startParkour(worldName,uuid,player);
            config.set("course." + worldName + ".progress." + uuid + ".last cp", 0);
        } else if (newCp ==currentCp+1) {
            if (newCp >=getParkourSize(player.getWorld().getName())-1) {
                finishParkour(worldName,uuid, player);
            } else {
                chatMessenger.sendCheckpointReached(player,newCp);
                config.set("course." + worldName + ".progress." + uuid + ".last cp", newCp);
            }
        }
        courseYamlConfig.getInstance().save();

    }

    public void verify(Player player, String worldName) {
        if (courseYamlConfig.getConfig().getBoolean("course."+worldName+".verifiable")) {
            playerYamlConfig.getConfig().set(player.getName() + ".working world",null);
            playerYamlConfig.getInstance().save();
            courseYamlConfig.getConfig().set("course." + worldName +".verified",true);
            courseYamlConfig.getConfig().set("course." + worldName +".verifiable",false);
            courseYamlConfig.getInstance().save();
            String mapdisplay = courseYamlConfig.getConfig().getString("course." + worldName +".display name");
            if (mapdisplay!=null) chatMessenger.sendMapVerified(player,mapdisplay);
        }
    }

    private void startParkour(String worldName, UUID uuid, Player player) {
        failParkour(worldName,uuid);
        progressmanager.startCourse(player);
        chatMessenger.sendParkourStart(player);
    }

    private void finishParkour(String worldName,UUID uuid,Player player) {
        int completion = config.getInt("course." + worldName + ".progress." + uuid + ".completion");
        config.set("course." + worldName + ".progress." + uuid + ".completion", ++completion);
        chatMessenger.sendParkourEnd(player, progressmanager.getFormattedTime(player));
        failParkour(worldName,uuid);
        progressmanager.leaveCourse(player);
        verify(player,worldName);
    }

    private void failParkour(String worldName, UUID uuid) {
        config.set("course." + worldName + ".progress." + uuid + ".last cp", -1);
        config.set("course." + worldName + ".progress." + uuid + ".tick", 0);
        config.set("course." + worldName + ".progress." + uuid + ".last pos.x", null);
        config.set("course." + worldName + ".progress." + uuid + ".last pos.y", null);
        config.set("course." + worldName + ".progress." + uuid + ".last pos.z", null);
        config.set("course." + worldName + ".progress." + uuid + ".last pos.yaw", null);
        config.set("course." + worldName + ".progress." + uuid + ".last pos.pitch", null);
    }

    private int getParkourSize(String worldname) {
        return config.getMapList("course." + worldname + ".checkpoint").size();
    }

    private int findCheckpointIndex (String worldname,int x, int y, int z) {
        List<Map<?,?>> list = config.getMapList("course." + worldname + ".checkpoint");
        for (int i=0;i<list.size();i++) {
            if ((Integer) list.get(i).get("x")==x &&
                (Integer) list.get(i).get("y")==y &&
                (Integer) list.get(i).get("z")==z)
            {
                return i;
            }
        }
        return -1;
    }

    private boolean isACourse(String worldname) {
        return config.getString("course." + worldname + ".author") != null;
    }
}
