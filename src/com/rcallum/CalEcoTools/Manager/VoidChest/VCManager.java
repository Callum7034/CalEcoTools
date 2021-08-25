package com.rcallum.CalEcoTools.Manager.VoidChest;

import java.util.HashMap;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.rcallum.CalEcoTools.CalEcoTools;
import com.rcallum.CalEcoTools.Statistics.Stats;

public class VCManager {
	private static VCManager instance;
	public VCManager() {
		instance = this;
	}
	public static VCManager getInstance() {
		if (instance == null) {
			instance = new VCManager();
		}
		return instance;
	}
	public HashMap<String, VoidChest> voidChests; 
	
	public void initialize() {
		voidChests = new HashMap<String, VoidChest>();
		FileConfiguration dataF = CalEcoTools.data;
		if (dataF.contains("VoidChests")) {
			ConfigurationSection data = dataF.getConfigurationSection("VoidChests");
			for (String s : data.getKeys(false)) {
				voidChests.put(s, new VoidChest(s));
			}
		}
		Stats.addVC(1);
		
	}
	
	public void addChest(String locID) {
		voidChests.put(locID, new VoidChest(locID));
	}
	
	public void sellTask() {
		for (VoidChest vc : voidChests.values()) {
			vc.checkInvForSell();
			vc.pickupNearbyItems();
			Stats.addVC(2);
		}
	}
	public VoidChest getVoidChest(String locID) {
		return voidChests.get(locID);
	}
	
	public void removeChest(String id) {
		voidChests.remove(id);
	}

}
