package org.plugin.twopeeplugin.Commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.plugin.twopeeplugin.Utils.chatMessenger;

public class goldenApple implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            ItemStack gap = new ItemStack(Material.GOLDEN_APPLE,64);
			ItemMeta meta = gap.getItemMeta();
			meta.setDisplayName("Golden Apple");
			gap.setItemMeta(meta);
            player.getInventory().addItem(gap);
            chatMessenger.sendGiveItem(player,gap.getItemMeta().getDisplayName());
			return true;
        }
        return false;
    }
}
