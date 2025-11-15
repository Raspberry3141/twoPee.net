package org.plugin.twopeeplugin.Core.housing;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.plugin.twopeeplugin.Utils.inventoryYamlConfig;
import org.plugin.twopeeplugin.Utils.items;

import java.util.*;

public class itemManager {

    public void saveInventory(Player player) {
        ItemStack[] main = player.getInventory().getContents();
        inventoryYamlConfig.getConfig().set(player.getWorld().getName()+"."+player.getUniqueId()+".inventory", null);
        for (int i = 0; i < main.length; i++) {
            ItemStack item = main[i];
            inventoryYamlConfig.getConfig().set(player.getWorld().getName()+"."+player.getUniqueId()+".inventory." + i, item);
        }
        inventoryYamlConfig.getInstance().save();
    }

    public void saveInventory(Player player, String worldName) {
        ItemStack[] main = player.getInventory().getContents();
        inventoryYamlConfig.getConfig().set(worldName+"."+player.getUniqueId()+".inventory", null);
        inventoryYamlConfig.getInstance().save();
        for (int i = 0; i < main.length; i++) {
            ItemStack item = main[i];
            inventoryYamlConfig.getConfig().set(worldName+"."+player.getUniqueId()+".inventory." + i, item);
        }
        inventoryYamlConfig.getInstance().save();
    }

    public void loadInventory(Player player) {
		resetInventory(player);
        ConfigurationSection section = inventoryYamlConfig.getConfig().getConfigurationSection(player.getWorld().getName()+"."+player.getUniqueId()+".inventory");
        if (section==null) return;
        for (String key : section.getKeys(false)) {
            ItemStack item = inventoryYamlConfig.getConfig().getItemStack(player.getWorld().getName()+"."+player.getUniqueId()+".inventory." + key);
            if (item != null) {
                int amount = inventoryYamlConfig.getConfig().getInt(player.getWorld().getName()+"."+player.getUniqueId()+".inventory." + key);
                if (amount==0) {
                    item.setAmount(1);
                } else {
                    item.setAmount(amount);
                }
                player.getInventory().setItem(Integer.parseInt(key), item);
            }
        }
    }

    public void resetInventory(Player player) {
        player.getInventory().clear();
        ItemStack newItem = createItem(items.pcp.name(),-1);
        player.getInventory().addItem(newItem);
        player.getInventory().setArmorContents(new ItemStack[4]);
    }

    public void giveItem(Player player) {
    }

    
    private ItemStack createItem(String itemName, int kb) {
        if (Objects.equals(itemName, items.stick.name())) {
            ItemStack item = new ItemStack(Material.STICK);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + "Knockback " + kb);
            item.setItemMeta(meta);
            item.addEnchantment(Enchantment.KNOCKBACK,kb);
            return item;
        } else if (Objects.equals(itemName, items.left.name())) {
            ItemStack item = new ItemStack(Material.BLAZE_ROD);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + "Lefty Knockback " + kb);
            item.setItemMeta(meta);
            item.addEnchantment(Enchantment.KNOCKBACK,kb);
            return item;
        } else if (Objects.equals(itemName, items.right.name())) {
            ItemStack item = new ItemStack(Material.BONE);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + "Righty Knockback " + kb);
            item.setItemMeta(meta);
            item.addEnchantment(Enchantment.KNOCKBACK,kb);
            return item;
        } else if (Objects.equals(itemName, items.pcp.name())) {
            ItemStack item = new ItemStack(Material.CARROT_STICK);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + "Previous Checkpoint");
            item.setItemMeta(meta);
            return item;
        } else if (Objects.equals(itemName, items.fish.name())) {
            ItemStack item = new ItemStack(Material.FISHING_ROD);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + "Fishing Rod");
            item.setItemMeta(meta);
            return item;
        }
        return null;
    }
}
