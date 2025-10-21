package org.plugin.twopeeplugin.Core;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.types.InheritanceNode;
import org.bukkit.entity.Player;
import org.plugin.twopeeplugin.Utils.chatMessenger;
import org.plugin.twopeeplugin.Utils.playerYamlConfig;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

public class GroupManager {
    private LuckPerms luckPerms = LuckPermsProvider.get();

    public void enterBuildingMode(Player player) {
        addGroup(player, "building");
    }

    public void leaveBuildingMode(Player player) {
        removegroup(player, "building");
    }

    public void promote(Player player) {
        Set<String> groupNames = getGroups(player,//TODO);
        int completions = playerYamlConfig.getConfig().getInt(player.getName() + ".completion");
        if (completions>99) {
            addGroup(player,"toaster");
            chatMessenger.sendUpdateRank(player,"toaster");
        } else if (completions>49) {
            addGroup(player,"ravioli");
            chatMessenger.sendUpdateRank(player,"ravioli");
        } else if (completions>19) {
            addGroup(player,"raspberry");
            chatMessenger.sendUpdateRank(player,"raspberry");
        } else if (completions>9) {
            addGroup(player,"mochi");
            chatMessenger.sendUpdateRank(player,"mochi");
        } else if (completions>2) {
            addGroup(player,"waffle");
            chatMessenger.sendUpdateRank(player,"waffle");
        } else {
            chatMessenger.sendCurrentCompletions(player,completions);
        }
    }

    private void getGroups(Player player, Consumer<Set<String>> callback) {
        LuckPerms luckPerms = LuckPermsProvider.get();
        UUID uuid = player.getUniqueId();
        luckPerms.getUserManager().loadUser(uuid).thenAcceptAsync(user -> {
            Set<String> groupNames = new HashSet<>();
            for (Node node : user.getNodes()) {
                if (node instanceof InheritanceNode) {
                    InheritanceNode inheritanceNode = (InheritanceNode) node;
                    groupNames.add(inheritanceNode.getGroupName());
                }
            }
            callback.accept(groupNames);
        });
    }


    private void removegroup(Player player, String groupName) {
        UUID uuid = player.getUniqueId();
        luckPerms.getUserManager().loadUser(uuid).thenAcceptAsync(user -> {
            Node node = InheritanceNode.builder(groupName).build();
            user.data().remove(node);
            luckPerms.getUserManager().saveUser(user);
        });
    }

    private void addGroup(Player player, String groupName) {
        UUID uuid = player.getUniqueId();
        luckPerms.getUserManager().loadUser(uuid).thenAcceptAsync(user -> {
            Node node = InheritanceNode.builder(groupName).build();
            user.data().add(node);
            luckPerms.getUserManager().saveUser(user);
        });
    }

}
