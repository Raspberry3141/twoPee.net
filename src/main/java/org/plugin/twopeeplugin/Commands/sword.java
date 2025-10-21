package org.plugin.twopeeplugin.Commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.plugin.twopeeplugin.Utils.chatMessenger;

public class sword implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            ItemStack sword = new ItemStack(Material.IRON_SWORD);
            player.getInventory().addItem(sword);
            chatMessenger.sendGiveItem(player,sword.getItemMeta().getDisplayName());
        }
        return true;
    }
}
