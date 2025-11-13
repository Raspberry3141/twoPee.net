package org.plugin.twopeeplugin.Core.parkour;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import org.plugin.twopeeplugin.TwoPeePlugin;
import org.plugin.twopeeplugin.Utils.courseYamlConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.bukkit.Bukkit.getPlayer;

public class parkourTimer {
    private Map<UUID,Long> timer;
    private YamlConfiguration config;
    public parkourTimer() {
        config = courseYamlConfig.getConfig();
        timer = new HashMap<>();
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Map.Entry<UUID,Long> entry: timer.entrySet()) {
                    timer.put(entry.getKey(),entry.getValue()+1);
                }
            }
        }.runTaskTimer(TwoPeePlugin.getInstance(),0L,1L);

        new BukkitRunnable() {
            @Override
            public void run() {
                writeAllTimeToDisk();
            }
        }.runTaskTimer(TwoPeePlugin.getInstance(),0L,10000L);
    }

    public void startTime(UUID uuid, String worldName) {
        Long tick = config.getLong("course." + worldName + ".progress." + uuid + ".tick");
        timer.put(uuid,tick);
    }

    public void stopTime(UUID uuid, String worldName) {
        writeTimeToDisk(uuid, worldName);
        timer.remove(uuid);
    }

    public void writeAllTimeToDisk() {
        if (!timer.isEmpty()) {
            for (Map.Entry<UUID,Long> map: timer.entrySet()) {
                writeTimeToDisk(map.getKey(),getPlayer(map.getKey()).getWorld().getName());
            }
        }
    }

    private void writeTimeToDisk(UUID uuid, String worldName) {
        config.set("course." + worldName + ".progress." + uuid.toString() + ".tick",timer.get(uuid));
        courseYamlConfig.getInstance().save();
    }

    private String formatTime(long t) {
        double Insecond =  t/20.0;
        int hours = (int) (Insecond/3600);
        int minutes = (int) Insecond / 60;
        double seconds = Insecond % 60;
        return String.format("%02d:%02d:%05.2f", hours, minutes, seconds);
    }

    public String getTime(UUID uuid) {
        Long ticks_elapsed = timer.get(uuid);
        if (!(ticks_elapsed==null)) {
            return formatTime(ticks_elapsed);
        } else {
            return "00:00:00";
        }

    }
}
