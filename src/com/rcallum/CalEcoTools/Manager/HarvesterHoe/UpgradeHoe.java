package com.rcallum.CalEcoTools.Manager.HarvesterHoe;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.rcallum.CalEcoTools.CalEcoTools;
import com.rcallum.CalEcoTools.Data.CaneTokens;
import com.rcallum.CalEcoTools.Messages.msg;
import com.rcallum.CalEcoTools.Utils.NBT;
import com.rcallum.CalEcoTools.Utils.RomanNumberConverter;

public class UpgradeHoe {
	private static UpgradeHoe instance;

	public UpgradeHoe() {
		instance = this;
	}

	public static UpgradeHoe getInstance() {
		if (instance == null) {
			instance = new UpgradeHoe();
		}
		return instance;
	}

	public enum Upgrade {
		CYBORG, DROPS, SELL, TOKEN, MOBCOIN, AUTOSELL, KEYS;
	}

	public boolean upgradeHoe(Upgrade up, Player p) {
		if (!NBT.hasNBT(p.getItemInHand(), "HarvesterHoe")) {
			p.sendMessage(c("&cYou must be holding a Harvester Hoe to use this GUI"));
			p.closeInventory();
			return false;
		}
		int level = getLevel(p, up);
		double cost = getCost(up, level);
		if (CaneTokens.getInstance().getTokens(p) < cost) {
			p.sendMessage(msg.notEnoughTokens());
			return false;
		}
		if (level == getMaxLevel(up)) {
			p.sendMessage(c("&cYou have reached the max level for this upgrade"));
		} else {
			CaneTokens.getInstance().removeTokens(p, cost);
			addLevel(p, up);
		}
		
		return true;
	}

	public String convertToString(Upgrade up) {
		String ret = null;
		if (up == Upgrade.AUTOSELL) {
			ret = "Autosell";
		}
		if (up == Upgrade.CYBORG) {
			ret = "Cyborg";
		}
		if (up == Upgrade.DROPS) {
			ret = "DropBooster";
		}
		if (up == Upgrade.KEYS) {
			ret = "KeyFinder";
		}
		if (up == Upgrade.MOBCOIN) {
			ret = "MobcoinFinder";
		}
		if (up == Upgrade.SELL) {
			ret = "SellMulti";
		}
		if (up == Upgrade.TOKEN) {
			ret = "TokenBooster";
		}
		return ret;
	}

	public int getLevel(Player p, Upgrade up) {
		ItemStack i = p.getItemInHand();
		int level = Integer.valueOf(NBT.getNBT(i, convertToString(up)));
		return level;
	}

	public void addLevel(Player p, Upgrade up) {
		ItemStack i = p.getItemInHand();
		String upgrade = convertToString(up);
		int level = Integer.valueOf(NBT.getNBT(i, upgrade));
		level++;
		i = NBT.setNBT(i, upgrade, level+"");
		p.setItemInHand(updateLore(i, up, level));
	}
	public ItemStack updateLore(ItemStack i, Upgrade up, int level) {
		ItemMeta im = i.getItemMeta();
		List<String> lore = im.getLore();
		String upgrade = convertToString(up);
		List<String> newLore = new ArrayList<String>();
		boolean done = false;
		for (String s : lore) {
			if (s.contains(upgrade)) {
				s = c("   &7"+ upgrade + " " + RomanNumberConverter.toRoman(level));
				done = true;
			}
			newLore.add(s);
		}
		if (!done) {
			newLore.add(c("   &7"+ upgrade + " " + RomanNumberConverter.toRoman(level)));
		}
		im.setLore(newLore);
		i.setItemMeta(im);
		return i;
	}
	public double getCost(Upgrade up, int level) {
		// Cost * (Multi^Level)
		String upgrade = convertToString(up);
		ConfigurationSection sec = CalEcoTools.harvesterConfig.getConfigurationSection("Enchants");
		double baseCost = sec.getDouble(upgrade + ".StartCost");
		double increasePerLevel = sec.getDouble(upgrade + ".IncreasePerLevel");
		
		double cost = baseCost * (Math.pow(increasePerLevel, level));
		return cost;

	}

	public int getMaxLevel(Upgrade up) {
		String upgrade = convertToString(up);
		ConfigurationSection sec = CalEcoTools.harvesterConfig.getConfigurationSection("Enchants");
		int maxLevel = sec.getInt(upgrade + ".Levels");
		return maxLevel;

	}
	public String c(String i) {
		return ChatColor.translateAlternateColorCodes('&', i);
	}
}
