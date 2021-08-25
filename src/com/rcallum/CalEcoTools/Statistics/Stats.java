package com.rcallum.CalEcoTools.Statistics;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.rcallum.CalEcoTools.CalEcoTools;

public class Stats {
	
	private static double VCoperationsTotal;
	private static double VCoperationsHour;
	
	private static double CaneBrokenTotal;
	private static double CaneBrokenHour;
	
	private static double SellWandUsesTotal;
	private static double SellWandUsesHour;
	
	private static double CondenseWandUsesTotal;
	private static double CondenseWandUsesHour;
	
	public static void init() {
		VCoperationsTotal = 0;
		CaneBrokenTotal = 0;
		SellWandUsesTotal = 0;
		CondenseWandUsesTotal = 0;
		resetHourClock();
		FileConfiguration f = CalEcoTools.data;
		if (f.contains("Stats")) {
			ConfigurationSection s = f.getConfigurationSection("Stats");
			VCoperationsTotal = s.getDouble("VoidChest");
			CaneBrokenTotal = s.getDouble("HarvesterHoe");
			SellWandUsesTotal = s.getDouble("SellWand");
			CondenseWandUsesTotal = s.getDouble("CondenseWand");
		}
	}
	
	public static void resetHourClock() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(CalEcoTools.getInstance(), new Runnable() {

			@Override
			public void run() {
				VCoperationsHour = 0;
				CaneBrokenHour = 0;
				SellWandUsesHour = 0;
				CondenseWandUsesHour = 0;
			}

		}, 0L, 72000L);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(CalEcoTools.getInstance(), new Runnable() {

			@Override
			public void run() {
				//SAVE DATA
				FileConfiguration f = CalEcoTools.data;
				if (!f.contains("Stats")) {
					f.createSection("Stats");
					ConfigurationSection s = f.getConfigurationSection("Stats");
					s.set("VoidChest", VCoperationsTotal);
					s.set("SellWand", SellWandUsesTotal);
					s.set("HarvesterHoe", CaneBrokenTotal);
					s.set("CondenseWand", CondenseWandUsesTotal);
				} else {
					ConfigurationSection s = f.getConfigurationSection("Stats");
					s.set("VoidChest", VCoperationsTotal);
					s.set("SellWand", SellWandUsesTotal);
					s.set("HarvesterHoe", CaneBrokenTotal);
					s.set("CondenseWand", CondenseWandUsesTotal);
				}
				CalEcoTools.getInstance().dataFile.saveData();
			}

		}, 0L, 1200L);
	}
	
	public static void addVC(int amount) {
		VCoperationsTotal = VCoperationsTotal + amount;
		VCoperationsHour = VCoperationsHour + amount;
	}
	public static void addSellWand(int amount) {
		SellWandUsesTotal = SellWandUsesTotal + amount;
		SellWandUsesHour = SellWandUsesHour + amount;
	}
	public static void addCaneBreak(int amount) {
		CaneBrokenTotal = CaneBrokenTotal + amount;
		CaneBrokenHour = CaneBrokenHour + amount;
	}
	public static void addCondense(int amount) {
		CondenseWandUsesTotal = CondenseWandUsesTotal + amount;
		CondenseWandUsesHour = CondenseWandUsesHour + amount;
	}
	
	public static double getVCOpsTotal() {
		return VCoperationsTotal;
	}
	public static double getVCOpsHour() {
		return VCoperationsHour;
	}
	
	public static double getCaneTotal() {
		return CaneBrokenTotal;
	}
	public static double getCaneHour() {
		return CaneBrokenHour;
	}
	
	public static double getSellWandTotal() {
		return SellWandUsesTotal;
	}
	public static double getSellWandHour() {
		return SellWandUsesHour;
	}
	
	public static double getCondenseWandTotal() {
		return CondenseWandUsesTotal;
	}
	public static double getCondenseWandHour() {
		return CondenseWandUsesHour;
	}
}
