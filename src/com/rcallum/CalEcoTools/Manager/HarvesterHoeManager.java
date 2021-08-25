package com.rcallum.CalEcoTools.Manager;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.rcallum.CalEcoTools.CalEcoTools;
import com.rcallum.CalEcoTools.Utils.NBT;

public class HarvesterHoeManager {
	private static HarvesterHoeManager instance;

	public HarvesterHoeManager() {
		instance = this;
	}

	public static HarvesterHoeManager getInstance() {
		if (instance == null) {
			instance = new HarvesterHoeManager();
		}
		return instance;
	}

	// Data File Manager
	public int getID(ItemStack i) {
		int id = Integer.valueOf(NBT.getNBT(i, "HoeID"));
		return id;
	}

	public int getNewID() {
		int id = 0;
		FileConfiguration c = CalEcoTools.hoeData;
		if (c.contains("newID")) {
			id = c.getInt("newID");
			c.set("newID", id + 1);
		} else {
			c.set("newID", 2);
			id = 1;
			c.createSection("Hoes");
		}
		CalEcoTools.getInstance().hoeDataFile.saveData();
		return id;
	}

	public boolean toggleAutoSell(Player p) {
		ItemStack i = p.getItemInHand();
		String canS = NBT.getNBT(i, "Autosell");
		if (canS.equalsIgnoreCase("0")) {
			return false;
		}
		String en = NBT.getNBT(i, "autosell");
		if (en.equalsIgnoreCase("true")) {
			i = NBT.setNBT(i, "autosell", "false");
		} else {
			i = NBT.setNBT(i, "autosell", "true");
		}
		p.setItemInHand(i);
		return true;
	}

	public int getRawCane(ItemStack i) {
		FileConfiguration c = CalEcoTools.hoeData;
		if (!c.contains("Hoes"))
			return 0;
		int ID = getID(i);
		if (!c.contains("Hoes." + ID))
			return 0;
		ConfigurationSection sec = c.getConfigurationSection("Hoes." + ID);
		int current = 0;
		current = sec.getInt("RawCane");
		return current;
	}

	public int getTotalCane(ItemStack i) {
		FileConfiguration c = CalEcoTools.hoeData;
		if (!c.contains("Hoes"))
			return 0;
		int ID = getID(i);
		if (!c.contains("Hoes." + ID))
			return 0;
		ConfigurationSection sec = c.getConfigurationSection("Hoes." + ID);
		int current = 0;
		current = sec.getInt("TotalCane");
		return current;
	}

	public void addRawCane(ItemStack i, int amt) {
		FileConfiguration c = CalEcoTools.hoeData;
		int ID = getID(i);
		if (!c.contains("Hoes")) {
			c.createSection("Hoes");
		}
		if (!c.getConfigurationSection("Hoes").contains(ID+".RawCane")) {
			c.set("Hoes." + ID + ".RawCane", amt);
		} else {
			ConfigurationSection sec = c.getConfigurationSection("Hoes." + ID);
			sec.set("RawCane", getRawCane(i) + amt);
		}
		
		CalEcoTools.getInstance().hoeDataFile.saveData();
	}

	public void addTotalCane(ItemStack i, int amt) {
		FileConfiguration c = CalEcoTools.hoeData;
		int ID = getID(i);
		if (!c.getConfigurationSection("Hoes").contains(ID+".TotalCane")) {
			c.set("Hoes." + ID + ".TotalCane", 0);
		}
		ConfigurationSection sec = c.getConfigurationSection("Hoes." + ID);
		sec.set("TotalCane", getTotalCane(i) + amt);
		CalEcoTools.getInstance().hoeDataFile.saveData();
	}
	public void addPlayerRawCane(Player p, int amt) {
		FileConfiguration c = CalEcoTools.hoeData;
		if (!c.contains("PlayerData")) {
			c.createSection("PlayerData");
		}
		ConfigurationSection sec = c.getConfigurationSection("PlayerData");
		if (!sec.contains(p.getUniqueId().toString())) {
			sec.createSection(p.getUniqueId().toString());
			sec.set(p.getUniqueId().toString()+ ".IGN", p.getName());
			sec.set(p.getUniqueId().toString() + ".RawCane", (double)amt);
			
		} else {
			double amount = sec.getDouble(p.getUniqueId().toString()+ ".RawCane");
			sec.set(p.getUniqueId().toString()+ ".RawCane", (amount + amt));
		}
		CalEcoTools.getInstance().hoeDataFile.saveData();
	}
	public double getPlayerRawCane(Player p) {
		FileConfiguration c = CalEcoTools.hoeData;
		if (!c.contains("PlayerData")) {
			return 0;
		}
		ConfigurationSection sec = c.getConfigurationSection("PlayerData");
		if (!sec.contains(p.getUniqueId().toString())) {
			return 0;
			
		} else {
			return sec.getDouble(p.getUniqueId().toString()+ ".RawCane");
		}
	}
	public String getIGNRawCane(String UUID) {
		FileConfiguration c = CalEcoTools.hoeData;
		if (!c.contains("PlayerData")) {
			return null;
		}
		ConfigurationSection sec = c.getConfigurationSection("PlayerData");
		if (!sec.contains(UUID)) {
			return null;
			
		} else {
			return sec.getString(UUID+ ".IGN");
		}
	}
}
