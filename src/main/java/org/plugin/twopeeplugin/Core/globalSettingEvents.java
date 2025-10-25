package org.plugin.twopeeplugin.Core;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.bukkit.util.Vector;
import org.plugin.twopeeplugin.Utils.chatMessenger;
import org.plugin.twopeeplugin.Utils.courseYamlConfig;

import java.util.*;

import static org.bukkit.Bukkit.getPlayer;
import static org.bukkit.Bukkit.getWorld;

public class globalSettingEvents implements Listener {

    private progressManager progressmanager;
    private groupManager groupmanager;
    private itemManager itemmanager;

    public globalSettingEvents(progressManager pm, groupManager gp,itemManager im) {
        progressmanager = pm;
        groupmanager = gp;
        itemmanager = im;
    }

    @EventHandler
    public void onPlayerReelIn(PlayerFishEvent event) {
        if (event.getState()== PlayerFishEvent.State.CAUGHT_ENTITY) {
            Entity caught = event.getCaught();
            Player player = event.getPlayer();

            if (caught != null) {
                Location playerLoc = player.getLocation();
                Location entityLoc = caught.getLocation();

                Vector pullVector = playerLoc.toVector().subtract(entityLoc.toVector()).normalize().multiply(0.5);
                pullVector.setY(0.4);

                caught.setVelocity(pullVector);
            }
        }
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        if (event.getFrom()!=null) {
            progressmanager.leaveCourse(event.getPlayer(),event.getFrom().getName());
            groupmanager.leaveBuildingMode(event.getPlayer());
            event.getPlayer().setGameMode(GameMode.ADVENTURE);
        }
        if (isACoures(event.getPlayer().getWorld().getName())) {
            progressmanager.enterCourse(event.getPlayer());
            if (isBuildingMode(event.getPlayer().getWorld().getName())) {
                groupmanager.enterBuildingMode(event.getPlayer());
            }
        }
        itemmanager.resetInventory(event.getPlayer());
    }

    private boolean isBuildingMode(String worldName) {
        boolean verified = courseYamlConfig.getConfig().getBoolean("course."+worldName+".verified");
        boolean verifiable = courseYamlConfig.getConfig().getBoolean("course."+worldName+".verifiable");
        if (!verified) {
            if (!verifiable) {
                return true;
            }
        }
        return false;
    }

    private boolean isACoures(String worldName) {
        String author = courseYamlConfig.getConfig().getString("course."+worldName+".author");
        if (author==null) {
            return false;
        }
        return true;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        progressmanager.leaveCourse(event.getPlayer());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().setSaturation(Float.MAX_VALUE);
        if (getWorld("lobby")==null) {
            new WorldCreator("lobby").createWorld();
        }
        event.getPlayer().teleport(getWorld("lobby").getSpawnLocation());
        if (!event.getPlayer().isOp()) {
            setGroupBerry(event.getPlayer());
            event.getPlayer().setGameMode(GameMode.ADVENTURE);
        }
    }

    private void setGroupBerry(Player player) {
        if (!player.hasPlayedBefore()) {
            groupmanager.setDefaultGroup(player);
        }
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        World world = event.getLocation().getWorld();
        long count = world.getEntitiesByClass(event.getEntity().getClass()).stream().count();
        if (count >= 90) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntityType()==EntityType.ARROW) {
            event.getEntity().remove();
        }

    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();

        if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            if (event.getEntity() instanceof Player) {
                event.setCancelled(true);
                return;
            }
        }
        if (player.getHealth() - event.getFinalDamage() <= 0) {
            event.setCancelled(true);
            player.playEffect(player.getLocation(), Effect.SMOKE, 1);

            player.setHealth(player.getMaxHealth());
            player.setFoodLevel(20);
            player.setSaturation(Float.MAX_VALUE);
            player.setFireTicks(0);
            int currentCp = courseYamlConfig.getConfig().getInt("course."+ player.getWorld().getName()
                    + ".progress." + player.getUniqueId() + ".last cp");
            if (currentCp!=-1) {
                teleportToCheckpoint(player,currentCp);
            } else {
                progressManager.teleportToSpawn(player);
                chatMessenger.sendTpToSpawn(player.getPlayer());
            }
        }
    }


    @EventHandler
    public void onPlayerEnterVoid(PlayerMoveEvent event) {
        if (event.getPlayer().getLocation().getY() < -20) {
            Player player = event.getPlayer();
            int currentCp = courseYamlConfig.getConfig().getInt("course."+ player.getWorld().getName()
                    + ".progress." + player.getUniqueId() + ".last cp");
            if (currentCp!=-1) {
                teleportToCheckpoint(player,currentCp);
            } else {
                progressManager.teleportToSpawn(player);
                chatMessenger.sendTpToSpawn(player.getPlayer());
            }
        }
    }

    @EventHandler
    private void onChestOpen(InventoryOpenEvent event) {
        if (event.getInventory().getHolder()!=null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onTNTExplode(EntityExplodeEvent event) {
        if (!(event.getEntity() instanceof TNTPrimed)) return;
        if (event.getEntity() instanceof TNTPrimed) {
            event.blockList().clear();
        }
    }

    @EventHandler
    private void onSandFall(EntityChangeBlockEvent event){
        if(event.getEntityType()== EntityType.FALLING_BLOCK && event.getTo()==Material.AIR){
            event.setCancelled(true);
            event.getBlock().getState().update(false, false);
        }
    }

    @EventHandler
    public void onWaterFlow(BlockFromToEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onDroppers(BlockPhysicsEvent event) {
        Block block = event.getBlock();
        if (block.getType() == Material.DROPPER) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDispense(BlockDispenseEvent event) {
        Block block = event.getBlock();
        if (block.getType() == Material.DISPENSER) {
            event.setCancelled(true);
        }
    }

    private void teleportToCheckpoint(Player player, int currentCp) {
        String currentWorld = player.getWorld().getName();
        List<Map<?,?>> checkpoints = courseYamlConfig.getConfig().getMapList("course." + currentWorld +
                ".checkpoint");
        if (currentCp<=0 || checkpoints.isEmpty()) {
            progressManager.teleportToSpawn(player);
        } else if (checkpoints.size()>=currentCp) {
            double x = (Integer) checkpoints.get(currentCp).get("x") + 0.5D;
            double y = (Integer) checkpoints.get(currentCp).get("y");
            double z = (Integer) checkpoints.get(currentCp).get("z") + 0.5D;
            Location location = new Location(player.getWorld(), x,y,z);
            progressManager.teleport((Player) player,location);
            chatMessenger.sendPreviousCheckpointMessage(player.getPlayer(), currentCp);
        }
    }


}
