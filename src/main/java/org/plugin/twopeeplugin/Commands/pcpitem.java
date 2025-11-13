package org.plugin.twopeeplugin.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.plugin.twopeeplugin.Core.housing.giveItemManager;
import org.plugin.twopeeplugin.Utils.chatMessenger;

public class pcpitem implements CommandExecutor {
	giveItemManager giveitemmanager;

	public pcpitem(giveItemManager gim) {
		giveitemmanager = gim;
	}
	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		if (commandSender instanceof Player) {
			Player player = (Player) commandSender;
			ItemStack item = giveitemmanager.getPcpItem();
			if (item==null) {
				return false;
			}
			player.getInventory().addItem(item);
			chatMessenger.sendGiveItem(player,item.getItemMeta().getDisplayName());
			return true;
		}
		return false;
	}
}
