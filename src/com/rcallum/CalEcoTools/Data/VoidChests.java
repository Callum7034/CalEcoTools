package com.rcallum.CalEcoTools.Data;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.rcallum.CalEcoTools.CalEcoTools;
import com.rcallum.CalEcoTools.Holograms.VoidChestHoloManager;
import com.rcallum.CalEcoTools.Items.VoidChestItem;
import com.rcallum.CalEcoTools.Manager.VoidChest.VCManager;
import com.rcallum.CalEcoTools.Manager.VoidChest.VoidChest;

public class VoidChests {
	public static void placeChest(String ownerUUID, Location loc, double multi, boolean autopickup) {
		FileConfiguration dataFile = CalEcoTools.data;
		if (!dataFile.contains("VoidChests")) {
			dataFile.createSection("VoidChests");
		}
		ConfigurationSection data = dataFile.getConfigurationSection("VoidChests");
		data.createSection(locToString(loc));
		data = data.getConfigurationSection(locToString(loc));
		data.set("owner", ownerUUID);
		data.set("autopickup", autopickup);
		data.set("multiplier", multi);
		data.set("Items", 0);
		data.set("SellAmount", 0D);
		data.createSection("stats");
		data.set("stats.TotalItems", 0);
		data.set("stats.TotalSold", 0D);
		data.set("stats.TimesSold", 0);

		CalEcoTools.getInstance().dataFile.saveData();
		VCManager.getInstance().addChest(locToString(loc));
	}
	public static void removeChest(Player p, String id) {
		
		//Sells whats in it
		VoidChest vc = VCManager.getInstance().getVoidChest(id);
		vc.withdraw(p);
		p.closeInventory();
		
		//check if space in player inv
		if (p.getInventory().firstEmpty() == -1){
			p.sendMessage("You have no room in your inventory");
		} else {
			//Remove hologram
			VoidChestHoloManager.getInstance().breakHolo(vc.getHolo());
			
			double multi = vc.getMulti();
			boolean pickup = vc.getConfig().getBoolean("autopickup");
			//REMOVE from data
			//GIVE ITEM
			p.getInventory().addItem(VoidChestItem.getVoidChest(multi, pickup));
			ConfigurationSection data = CalEcoTools.data.getConfigurationSection("VoidChests");
			data.set(id, null);
			Set<String> keys = data.getKeys(false);
			keys.remove(id);
			CalEcoTools.getInstance().dataFile.saveData();
			
			VCManager.getInstance().getVoidChest(id).getLoc().getBlock().setType(Material.AIR);
			VCManager.getInstance().removeChest(id);
		}
		
	}

	public static Location stringToLoc(String s) {
		String[] words = s.split(",");
		World world = Bukkit.getWorld(words[0]);
		int x = Integer.parseInt(words[1]);
		int y = Integer.parseInt(words[2]);
		int z = Integer.parseInt(words[3]);
		Location loc = new Location(world, x, y, z);
		return loc;
	}

	public static String locToString(Location loc) {
		String world = loc.getWorld().getName();
		String x = "," + loc.getBlockX();
		String y = "," + loc.getBlockY();
		String z = "," + loc.getBlockZ();
		String loc1 = (world + x + y + z);
		return loc1;
	}

	public static boolean isVoidChest(Location loc) {
		return CalEcoTools.data.contains("VoidChests." + locToString(loc));
	}

	public static double getMulti(String locID) {
		ConfigurationSection data = CalEcoTools.data.getConfigurationSection("VoidChests." + locID);
		return data.getDouble("multiplier");
	}

	public static int getItemAmount(String locID) {
		ConfigurationSection data = CalEcoTools.data.getConfigurationSection("VoidChests." + locID);
		return data.getInt("Items");
	}

	public static double getSellAmount(String locID) {
		ConfigurationSection data = CalEcoTools.data.getConfigurationSection("VoidChests." + locID);
		return data.getDouble("SellAmount");
	}

	public static int getItemTotal(String locID) {
		ConfigurationSection data = CalEcoTools.data.getConfigurationSection("VoidChests." + locID);
		return data.getInt("stats.TotalItems");
	}

	public static double getSellTotal(String locID) {
		ConfigurationSection data = CalEcoTools.data.getConfigurationSection("VoidChests." + locID);
		return data.getDouble("stats.TotalSold");
	}

	public static int getTimesSold(String locID) {
		ConfigurationSection data = CalEcoTools.data.getConfigurationSection("VoidChests." + locID);
		return data.getInt("stats.TimesSold");
	}
}
