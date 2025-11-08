package org.plugin.twopeeplugin.Commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.plugin.twopeeplugin.Utils.chatMessenger;
import org.plugin.twopeeplugin.Utils.courseYamlConfig;
import org.plugin.twopeeplugin.Core.progressManager;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class previousCheckpoint implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            String currentWorld = ((Player) commandSender).getWorld().getName();
            UUID uuid = ((Player) commandSender).getUniqueId();
            int currentCp = courseYamlConfig.getConfig().getInt("course."+ currentWorld + ".progress." + uuid + ".last cp");
            if (strings.length==0) {
                if (currentCp!=-1) {
                    teleportToCheckpoint((Player) commandSender,currentCp);
                    return true;
                } else {
                    progressManager.teleportToSpawn((Player) commandSender);
                    chatMessenger.sendTpToSpawn(((Player) commandSender).getPlayer());
                    return true;
                }
            } else if (strings.length==1) {
                int desiredCp = Integer.parseInt(strings[0]);
                if (desiredCp<=currentCp) {
                    teleportToCheckpoint(((Player) commandSender), desiredCp);
                    return true;
                }
            }
        }
        return false;
    }
    
    private void teleportToCheckpoint(Player player,int currentCp) {
        String currentWorld = player.getWorld().getName();
        List<Map<?,?>> checkpoints = courseYamlConfig.getConfig().getMapList("course." + currentWorld + ".checkpoint");
        if (currentCp==0 || checkpoints.isEmpty()) {
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
