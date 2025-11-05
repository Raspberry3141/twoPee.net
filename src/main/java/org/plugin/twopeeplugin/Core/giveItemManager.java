package org.plugin.twopeeplugin.Core;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.plugin.twopeeplugin.Utils.chatMessenger;
import org.plugin.twopeeplugin.Utils.itemYamlConfig;
import org.plugin.twopeeplugin.Utils.items;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class giveItemManager implements Listener {
    private final Set<Material> clickableBlocks = new HashSet<>(Arrays.asList(Material.WALL_SIGN, Material.SIGN,
            Material.SIGN_POST, Material.STONE_BUTTON, Material.WOOD_BUTTON, Material.STONE_PLATE, Material.IRON_PLATE));
    private pracManager pracmanager;

    public giveItemManager(pracManager p) {
        pracmanager = p;
    }

    @EventHandler
    private void onBlockUsed(PlayerInteractEvent event) {
        if (event.getAction()==null) return;
        if (event.getPlayer()==null) return;
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction()==Action.PHYSICAL) {
            if (event.getClickedBlock()==null) return;
            if (event.getClickedBlock().getType()== Material.GOLD_PLATE) return;
            Location blockLoc = event.getClickedBlock().getLocation();
            String lookUpKey = blockLoc.getBlockX() + " " + blockLoc.getBlockY() + " " + blockLoc.getBlockZ() + " " +
                    blockLoc.getWorld().getName();
            ItemStack item = (ItemStack) itemYamlConfig.getConfig().get(lookUpKey);
            if (!(item==null)) {
                if (!event.getPlayer().getInventory().contains(item)) {
                    if (!pracmanager.isInPrac(event.getPlayer())) {
                        event.getPlayer().getInventory().addItem(item);
                        chatMessenger.sendItemGiven(event.getPlayer(),item.getItemMeta().getDisplayName());
                    }
                }
            }
        }
    }

    @EventHandler
    private void onBlockBroken(BlockBreakEvent event) {
        if (event.getPlayer()==null) return;
        if (event.getBlock()==null) return;
        if (clickableBlocks.contains(event.getBlock().getType())) {
            Location blockLoc = event.getBlock().getLocation();
            String lookUpKey = blockLoc.getBlockX() + " " + blockLoc.getBlockY() + " " + blockLoc.getBlockZ() + " " +
                    blockLoc.getWorld().getName();
            itemYamlConfig.getConfig().set(lookUpKey,null);
            itemYamlConfig.getInstance().save();
        }
    }

    public void createGiveBlock(Player player,items it, int kb) {
        Set<Material> transparent = new HashSet<>();
        transparent.add(Material.AIR);
        Block block = player.getTargetBlock(transparent,20);
        if (clickableBlocks.contains(block.getType())) {
            writeData(block.getLocation(),it,kb);
        }
    }

    private void writeData(Location loc, items it, int kb) {
        String key = loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ() + " " + loc.getWorld().getName();
        ItemStack item = createItem(it,kb);
        if (item!=null) {
            itemYamlConfig.getConfig().set(key, item);
            itemYamlConfig.getInstance().save();
        }
    }

	public ItemStack getPcpItem() {
		return createItem(items.pcp, 1);
	}

    private ItemStack createItem(items it, int kb) {
        if (Objects.equals(it, items.stick)) {
            ItemStack item = new ItemStack(Material.STICK);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + "Knockback " + kb);
            item.setItemMeta(meta);
			if (kb>0) {
				item.addUnsafeEnchantment(Enchantment.KNOCKBACK,kb);
			}
            return item;
        } else if (Objects.equals(it, items.left)) {
            ItemStack item = new ItemStack(Material.BLAZE_ROD);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + "Lefty Knockback " + kb);
            item.setItemMeta(meta);
			if (kb>0) {
				item.addUnsafeEnchantment(Enchantment.KNOCKBACK,kb);
			}
            return item;
        } else if (Objects.equals(it, items.right)) {
            ItemStack item = new ItemStack(Material.BONE);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + "Righty Knockback " + kb);
            item.setItemMeta(meta);
			if (kb>0) {
				item.addUnsafeEnchantment(Enchantment.KNOCKBACK,kb);
			}
            return item;
        } else if (Objects.equals(it, items.pcp)) {
            ItemStack item = new ItemStack(Material.CARROT_STICK);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + "Previous Checkpoint");
            item.setItemMeta(meta);
            return item;
        } else if (Objects.equals(it, items.fish)) {
            ItemStack item = new ItemStack(Material.FISHING_ROD);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + "Fishing Rod");
            meta.spigot().setUnbreakable(true);
            item.setItemMeta(meta);
            return item;
        } else if (Objects.equals(it, items.inverse)) {
            ItemStack item = new ItemStack(Material.PRISMARINE_SHARD);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + "Inverse Knockback" + kb);
            item.setItemMeta(meta);
			if (kb>0) {
				item.addUnsafeEnchantment(Enchantment.KNOCKBACK,kb);
			}
			return item;
        }
        return null;
    }
}
