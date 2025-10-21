package org.plugin.twopeeplugin.Core;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.types.InheritanceNode;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.plugin.twopeeplugin.TwoPeePlugin;
import org.plugin.twopeeplugin.Utils.chatMessenger;
import org.plugin.twopeeplugin.Utils.playerYamlConfig;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class groupManager {
    private LuckPerms luckPerms = LuckPermsProvider.get();

    public void enterBuildingMode(Player player) {
        addGroup(player, "building");
    }

    public void leaveBuildingMode(Player player) {
        removegroup(player, "building");
    }

    public void promote(Player player) {
        promoteGroup(player);
    }

    public void setDefaultGroup(Player player) {
        addGroup(player,"berry");
    }

    private void addGroup(Set<String> groups,Player player) {
        //REFACTOR: use enum
        int completions = playerYamlConfig.getConfig().getInt(player.getName() + ".completion");
        if (completions>99 && !groups.contains("toaster")) {
            addGroup(player,"toaster");
            chatMessenger.sendUpdateRank(player,"toaster");
        } else if (completions>49 && !groups.contains("ravioli")) {
            addGroup(player,"ravioli");
            chatMessenger.sendUpdateRank(player,"ravioli");
        } else if (completions>19 && !groups.contains("raspberry")) {
            addGroup(player,"raspberry");
            chatMessenger.sendUpdateRank(player,"raspberry");
        } else if (completions>9 && !groups.contains("mochi")) {
            addGroup(player,"mochi");
            chatMessenger.sendUpdateRank(player,"mochi");
        } else if (completions>2 && !groups.contains("waffle")) {
            addGroup(player,"waffle");
            chatMessenger.sendUpdateRank(player,"waffle");
        } else {
            chatMessenger.sendCurrentCompletions(player,completions);
        }
    }

    private void promoteGroup(Player player) {
        UUID uuid = player.getUniqueId();
        fetchGroups(uuid).thenAccept(groupNames -> {
            Bukkit.getScheduler().runTask(TwoPeePlugin.getInstance(), () -> {
                addGroup(groupNames,player);
            });
        });
    }

    private CompletableFuture<Set<String>> fetchGroups(UUID uuid) {
        return luckPerms.getUserManager().loadUser(uuid).thenApplyAsync(user -> {
            try {
                QueryOptions queryOptions = luckPerms.getContextManager().getQueryOptions(user)
                        .orElse(luckPerms.getContextManager().getStaticQueryOptions());
                return user.getInheritedGroups(queryOptions).stream()
                        .map(Group::getName)
                        .collect(Collectors.toSet());
            } finally {
                luckPerms.getUserManager().cleanupUser(user);
            }
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
