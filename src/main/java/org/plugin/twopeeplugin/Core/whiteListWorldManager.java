package org.plugin.twopeeplugin.Core;

import org.bukkit.entity.Player;
import org.plugin.twopeeplugin.Utils.chatMessenger;
import org.plugin.twopeeplugin.Utils.courseYamlConfig;

import java.util.*;

public class whiteListWorldManager {
    public void enableWhiteList(Player player) {
        String worldName = player.getWorld().getName();
        courseYamlConfig.getConfig().set("course."+worldName+".whitelist",true);
        courseYamlConfig.getInstance().save();
        chatMessenger.sendWhiteListAction(player,"List Enabled");
    }

    public void disableWhiteList(Player player) {
        String worldName = player.getWorld().getName();
        courseYamlConfig.getConfig().set("course."+worldName+".whitelist",true);
        courseYamlConfig.getInstance().save();
        chatMessenger.sendWhiteListAction(player,"List Disabled");
    }

    public void addPlayer(Player self,String worldName,String player) {
        List<String> list  = courseYamlConfig.getConfig().getStringList("course."+worldName+".list");
        Set<String> set = new HashSet<>(list);
        set.add(player);
        List<String> setlist = new ArrayList<>(set);
        courseYamlConfig.getConfig().set("course."+worldName+".list",setlist);
        courseYamlConfig.getInstance().save();
        chatMessenger.sendWhiteListAction(self,"Player Added");
    }

    public void removePlayer(Player self,String worldName,String player) {
        List<String> list  = courseYamlConfig.getConfig().getStringList("course."+worldName+".list");
        Set<String> set = new HashSet<>(list);
        set.remove(player);
        List<String> setlist = new ArrayList<>(set);
        courseYamlConfig.getConfig().set("course."+worldName+".list",setlist);
        courseYamlConfig.getInstance().save();
        chatMessenger.sendWhiteListAction(self,"Player removed");
    }

    public List<String> listPlayer(Player player) {
        String worldName = player.getWorld().getName();
        return courseYamlConfig.getConfig().getStringList("course."+worldName+".list");
    }

    public void clearList(Player player) {
        String worldName = player.getWorld().getName();
        Set<String> set = Collections.emptySet();
        courseYamlConfig.getConfig().set("course."+worldName+".list",set);
        courseYamlConfig.getInstance().save();
        chatMessenger.sendWhiteListAction(player,"List Cleared");
    }
}
