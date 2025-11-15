package org.plugin.twopeeplugin.Core.housing;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.plugin.twopeeplugin.Utils.courseYamlConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class gui {
	private int currentPage;
	private Player player;
	private Inventory inventory;
	private YamlConfiguration config;

	public gui(Player plyr, String title) {
		config = courseYamlConfig.getConfig();
		player = plyr;
		currentPage = 1;
		inventory = Bukkit.createInventory(null, 54, title);
		composeGui();
		player.openInventory(inventory);
	}

	private void composeGui() {
		inventory.clear();
		createButtons();
		List<ItemStack> items = normalizeItemList(createItems());
		fillInItems(items);
	}

	private List<ItemStack> normalizeItemList(List<ItemStack> items) {
		int start = 45 * (currentPage - 1);
		// case 1: the items fill all the 45 slots => start+45
		// case 2: the items do not fill all the 45 slots => items.size()-start
		int end = Math.min(start + 45, items.size());
		if (items.get(start) != null && items.get(end - 1) != null) {
			items = items.subList(start, end);
		} else {
			items = new ArrayList<ItemStack>();
		}
		return items;
	}

	public void changePage(int newPage) {
		ArrayList<ItemStack> items = createItems();
		int maxPageNumber = 1;
		if (!items.isEmpty())
			maxPageNumber = (int) Math.ceil(items.size() / 45.0F);
		System.out.println(maxPageNumber + " " + items.size());
		if (newPage < 1) {
			return;
		} else if (newPage > maxPageNumber) {
			return;
		}
		currentPage = newPage;
		composeGui();
	}

	private ArrayList<ItemStack> createItems() {
		ArrayList<ItemStack> courseItems = new ArrayList<ItemStack>();
		ConfigurationSection configSection = config.getConfigurationSection("course");
		Set<String> courseNamesSet = configSection.getKeys(false);
		List<String> courseNames = new ArrayList<>(courseNamesSet);
		for (String course : courseNames) {
			if (isVerified(course)) {
				ItemStack item = new ItemStack(Material.IRON_DOOR);
				addInfo(item, course);
				courseItems.add(item);
			}
		}
		return courseItems;
	}

	private boolean isVerified(String course) {
		return config.getBoolean("course." + course + ".verified");
	}

	private void addInfo(ItemStack item, String course) {
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(parseDifficulty(config.getString("course." + course + ".difficulty")));
		lore.add("&6&lStatus: " + completionInfo(course));
		lore.add(parseColor("&2&lAuthor: " + config.getString("course." + course + ".author")));
		lore.add(parseColor("&e&lPlayers: "));
		//TODO implement player counter and difficulty system
		lore.add("ID: "+ course);
		meta.setDisplayName(parseColor("&l&e" + config.getString("course." + course + ".display name")));
		meta.setLore(lore);
		item.setItemMeta(meta);
	}

	private String parseDifficulty(String s) {
		switch (s) {
		 	case "unrated":
				return parseColor("&7&l[Unrated]");
			case "starter":
				return parseColor("&a&l[Starter]");
			case "easy":
				return parseColor("&1&l[Easy]");
			case "medium":
				return parseColor("&6&l[Medium]");
			case "difficult":
				return parseColor("&4&l[Difficult]");
			case "challenge":
				return parseColor("&e&l[Challenge]");
		 	default:
				return parseColor("&7&l[Unrated]");
		}
	}

	private String parseColor(String s){
		return ChatColor.translateAlternateColorCodes('&',s);
	}


	private String completionInfo(String course) {
		UUID uuid = player.getUniqueId();
		int completion = courseYamlConfig.getConfig().getInt("course." + course + ".progress." + uuid + ".completion");
		if (completion == 0) {
			return "New";
		} else
			return "Finished";
	}

	private void fillInItems(List<ItemStack> items) {
		for (int i = 0; i < items.size(); i++) {
			inventory.setItem(i, items.get(i));
		}
	}

	private void createButtons() {
		ItemStack nextPageButton = new ItemStack(Material.ARROW);
		ItemMeta nextPageMeta = nextPageButton.getItemMeta();
		nextPageMeta.setDisplayName("Next Page");
		nextPageButton.setItemMeta(nextPageMeta);

		ItemStack prevPageButton = new ItemStack(Material.ARROW);
		ItemMeta prevPageMeta = prevPageButton.getItemMeta();
		prevPageMeta.setDisplayName("Previous Page");
		prevPageButton.setItemMeta(prevPageMeta);
		inventory.setItem(50, nextPageButton);
		inventory.setItem(48, prevPageButton);
	}

	public int getCurrentPage() {
		return this.currentPage;
	}

}
