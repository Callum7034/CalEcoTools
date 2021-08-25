package com.rcallum.CalEcoTools.GUI.HarvesterHoe;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.rcallum.CalEcoTools.Data.CaneTokens;
import com.rcallum.CalEcoTools.Manager.HarvesterHoeManager;
import com.rcallum.CalEcoTools.Manager.HarvesterHoe.UpgradeHoe;
import com.rcallum.CalEcoTools.Manager.HarvesterHoe.UpgradeHoe.Upgrade;
import com.rcallum.CalEcoTools.Utils.NBT;
import com.rcallum.CalEcoTools.Utils.NumberFormatter;

public class HoeGUI {

	private static HoeGUI instance;

	public HoeGUI() {
		instance = this;
	}

	public static HoeGUI getInstance() {
		if (instance == null) {
			instance = new HoeGUI();
		}
		return instance;
	}

	public void openMainGUI(Player p, ItemStack hoe) {
		Inventory GUI = Bukkit.createInventory(null, 27,
				ChatColor.translateAlternateColorCodes('&', "&9&lHarvester &9&lHoe"));

		String FillerItem = "STAINED_GLASS_PANE";
		int FillerItemDamage = 3;
		ItemStack filler = new ItemStack(Material.getMaterial(FillerItem), 1, (short) FillerItemDamage);
		ItemMeta a = filler.getItemMeta();
		a.setDisplayName(" ");
		filler.setItemMeta(a);
		for (int i = 0; i < GUI.getSize(); i++) {
			GUI.setItem(i, filler);
		}

		// Info
		ItemStack info = new ItemStack(Material.ENCHANTED_BOOK);
		ItemMeta im = info.getItemMeta();
		im.setDisplayName(c("&e&lInfo"));

		List<String> lore = new ArrayList<String>();
		lore.add(c("&8---------------------------"));
		lore.add(c("&9Raw Cane: &e" + f(HarvesterHoeManager.getInstance().getRawCane(hoe))));
		lore.add(c("&9Cane Collected: &e" + f(HarvesterHoeManager.getInstance().getTotalCane(hoe))));
		lore.add(c("&8---------------------------"));
		lore.add(c("&9Token Balance: &e" + fd(CaneTokens.getInstance().getTokens(p))));
		lore.add(c("&8---------------------------"));

		im.setLore(lore);
		info.setItemMeta(im);
		GUI.setItem(11, info);

		// AutoSell
		String autoSellEnabled = NBT.getNBT(hoe, "autosell");
		String material;
		String state;
		if (autoSellEnabled.equalsIgnoreCase("true")) {
			material = "EMERALD_BLOCK";
			state = "Enabled";
		} else {
			material = "REDSTONE_BLOCK";
			state = "Disabled";
		}
		int id = HarvesterHoeManager.getInstance().getID(hoe);
		ItemStack autosell = new ItemStack(Material.getMaterial(material));
		autosell = NBT.setNBT(autosell, "id", id + "");
		im = autosell.getItemMeta();
		im.setDisplayName(c("&6&lAutoSell"));

		lore = new ArrayList<String>();
		lore.add(c("&9AutoSell: &e" + state));
		lore.add(c("&8---------------------------"));
		lore.add(c("&a- Auto Sell will automatically sell the &fcane &athat you break"));
		lore.add(" ");
		lore.add(c("&b- You must purchase the &fautosell upgrade &bbefore enabling"));
		lore.add(" ");
		lore.add(c("&a- You can increase the &fsell multiplier &bby buying the upgrade"));
		lore.add(c("&8---------------------------"));
		im.setLore(lore);
		autosell.setItemMeta(im);
		GUI.setItem(13, autosell);

		// Upgrades

		ItemStack upgrade = new ItemStack(Material.BEACON);
		upgrade = NBT.setNBT(upgrade, "id", id + "");
		im = upgrade.getItemMeta();
		im.setDisplayName(c("&3&lUpgrades"));

		lore = new ArrayList<String>();

		lore.add(c("&8---------------------------"));
		lore.add(c("&a- Use &ftokens &ato upgrade your harvester hoe"));
		lore.add(c("&a- You can get &ftokens &afrom hitting cane"));
		lore.add(c("&8---------------------------"));
		im.setLore(lore);
		upgrade.setItemMeta(im);
		GUI.setItem(15, upgrade);
		p.openInventory(GUI);
	}
	public String f(int format) {
		return NumberFormatter.withLargeIntegers(format);
	}
	public String fd(double format) {
		return NumberFormatter.withLargeIntegers(format);
	}
	public void openUpgradeGUI(Player p, ItemStack hoe) {
		Inventory GUI = Bukkit.createInventory(null, 36,
				ChatColor.translateAlternateColorCodes('&', "&9&lHarvester &9&lHoe &9&lUpgrades"));

		String FillerItem = "STAINED_GLASS_PANE";
		int FillerItemDamage = 3;
		ItemStack filler = new ItemStack(Material.getMaterial(FillerItem), 1, (short) FillerItemDamage);
		ItemMeta a = filler.getItemMeta();
		a.setDisplayName(" ");
		filler.setItemMeta(a);
		for (int i = 0; i < GUI.getSize(); i++) {
			GUI.setItem(i, filler);
		}
		// ID
		int id = HarvesterHoeManager.getInstance().getID(hoe);
		UpgradeHoe u = UpgradeHoe.getInstance();
		
		
		// Cyborg
		ItemStack i = new ItemStack(Material.SUGAR);
		i = NBT.setNBT(i, "id", id + "");
		i.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(c("&e&lCyborg"));

		List<String> lore = new ArrayList<String>();
		int level = u.getLevel(p, Upgrade.CYBORG);
		lore.add(c("&9Level: &e" + level));
		lore.add(c("&9Upgrade Cost: &e" + fd(u.getCost(Upgrade.CYBORG, level)) + " &9Tokens"));
		lore.add(" ");
		lore.add(c("&8---------------------------"));
		lore.add(c("&a- Cyborg will grant the holder of this hoe with"));
		lore.add(c("&a             - Speed and Haste"));
		lore.add(c("&8---------------------------"));
		
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		im.setLore(lore);
		i.setItemMeta(im);
		GUI.setItem(10, i);

		// Drop Booster
		i = new ItemStack(Material.SUGAR_CANE);
		i = NBT.setNBT(i, "id", id + "");
		i.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		im = i.getItemMeta();
		im.setDisplayName(c("&2&lDrop Booster"));

		lore = new ArrayList<String>();
		level = u.getLevel(p, Upgrade.DROPS);
		lore.add(c("&9Level: &e" + level));
		lore.add(c("&9Upgrade Cost: &e" + fd(u.getCost(Upgrade.DROPS, level)) + " &9Tokens"));
		lore.add(" ");
		lore.add(c("&8---------------------------"));
		lore.add(c("&a- Drop Booster increases the amount of sugarcane"));
		lore.add(c("&a   - dropped each time you break one crop"));
		lore.add(c("&8---------------------------"));

		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		im.setLore(lore);
		i.setItemMeta(im);
		GUI.setItem(12, i);

		// Sell Multi
		i = new ItemStack(Material.DIAMOND);
		i = NBT.setNBT(i, "id", id + "");
		i.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		im = i.getItemMeta();
		im.setDisplayName(c("&1&lSell Multiplier"));

		lore = new ArrayList<String>();
		level = u.getLevel(p, Upgrade.SELL);
		lore.add(c("&9Level: &e" + level));
		lore.add(c("&9Upgrade Cost: &e" + fd(u.getCost(Upgrade.SELL, level)) + " &9Tokens"));
		lore.add(" ");
		lore.add(c("&8---------------------------"));
		lore.add(c("&a- Sell Multiplier increases the amount of money"));
		lore.add(c("&a- gained per cane with the autosell function"));
		lore.add(c("&8---------------------------"));

		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		im.setLore(lore);
		i.setItemMeta(im);
		GUI.setItem(14, i);

		// Token Booster
		i = new ItemStack(Material.REDSTONE);
		i = NBT.setNBT(i, "id", id + "");
		i.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		im = i.getItemMeta();
		im.setDisplayName(c("&a&lToken Multiplier"));

		lore = new ArrayList<String>();
		level = u.getLevel(p, Upgrade.TOKEN);
		lore.add(c("&9Level: &e" + level));
		lore.add(c("&9Upgrade Cost: &e" + fd(u.getCost(Upgrade.TOKEN, level)) + " &9Tokens"));
		lore.add(" ");
		lore.add(c("&8---------------------------"));
		lore.add(c("&a- Token Multiplier increases the amount of tokens"));
		lore.add(c("&a          - gained per cane break"));
		lore.add(c("&8---------------------------"));

		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		im.setLore(lore);
		i.setItemMeta(im);
		GUI.setItem(16, i);

		// Mobcoin Finder
		i = new ItemStack(Material.DOUBLE_PLANT, 1, (short) 0);
		i = NBT.setNBT(i, "id", id + "");
		i.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		im = i.getItemMeta();
		im.setDisplayName(c("&b&lMobcoin Finder"));

		lore = new ArrayList<String>();
		level = u.getLevel(p, Upgrade.MOBCOIN);
		lore.add(c("&9Level: &e" + level));
		lore.add(c("&9Upgrade Cost: &e" + fd(u.getCost(Upgrade.MOBCOIN, level)) + " &9Tokens"));
		lore.add(" ");
		lore.add(c("&8---------------------------"));
		lore.add(c("&a- Mobcoin Finder increases the chance to get a mobcoin"));
		lore.add(c("&a          - each time you break a cane"));
		lore.add(c("&8---------------------------"));

		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		im.setLore(lore);
		i.setItemMeta(im);
		GUI.setItem(20, i);

		// Auto Sell
		i = new ItemStack(Material.NETHER_STAR);
		i = NBT.setNBT(i, "id", id + "");
		i.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		im = i.getItemMeta();
		im.setDisplayName(c("&6&lAuto Sell"));

		lore = new ArrayList<String>();
		level = u.getLevel(p, Upgrade.AUTOSELL);
		lore.add(c("&9Level: &e" + level));
		lore.add(c("&9Upgrade Cost: &e" + fd(u.getCost(Upgrade.AUTOSELL, level)) + " &9Tokens"));
		lore.add(" ");
		lore.add(c("&8---------------------------"));
		lore.add(c("&a- Autosell will automatically sell all the cane"));
		lore.add(c("&a           - that you break"));
		lore.add(c("&8---------------------------"));

		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		im.setLore(lore);
		i.setItemMeta(im);
		GUI.setItem(22, i);

		p.openInventory(GUI);

		// Key Finder
		i = new ItemStack(Material.TRIPWIRE_HOOK);
		i = NBT.setNBT(i, "id", id + "");
		i.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		im = i.getItemMeta();
		im.setDisplayName(c("&6&lKey Finder"));

		lore = new ArrayList<String>();
		level = u.getLevel(p, Upgrade.KEYS);
		lore.add(c("&9Level: &e" + level));
		lore.add(c("&9Upgrade Cost: &e" + fd(u.getCost(Upgrade.KEYS, level)) + " &9Tokens"));
		lore.add(" ");
		lore.add(c("&8---------------------------"));
		lore.add(c("&a- Key Finder increases the chance to find a crate key"));
		lore.add(c("&a          - each time you break a cane"));
		lore.add(c("&8---------------------------"));

		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		im.setLore(lore);
		i.setItemMeta(im);
		GUI.setItem(24, i);

		p.openInventory(GUI);
	}

	public String c(String i) {
		return ChatColor.translateAlternateColorCodes('&', i);
	}
}
