package com.rcallum.CalEcoTools.Data;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.rcallum.CalEcoTools.CalEcoTools;

public class CaneTokens {
	private static CaneTokens instance;

	public CaneTokens() {
		instance = this;
	}

	public static CaneTokens getInstance() {
		if (instance == null) {
			instance = new CaneTokens();
		}
		return instance;
	}
	
	public double getTokens(Player p) {
		FileConfiguration c = CalEcoTools.hoeData;
		String uuid = p.getUniqueId().toString();
		if (!c.contains("Tokens")) {
			return 0;
		}
		if (!c.contains("Tokens." + uuid)) {
			return 0;
		}
		return c.getDouble("Tokens."+uuid);
		
	}
	public void removeTokens(Player p, double amount) {
		FileConfiguration c = CalEcoTools.hoeData;
		String uuid = p.getUniqueId().toString();
		if (!c.contains("Tokens")) {
			return;
		}
		if (!c.contains("Tokens."+uuid)) {
			return;
		}
		double current = c.getDouble("Tokens."+uuid);
		c.set("Tokens."+uuid, current-amount);
		CalEcoTools.getInstance().hoeDataFile.saveData();
	}
	public void addTokens(Player p, double amount) {
		FileConfiguration c = CalEcoTools.hoeData;
		String uuid = p.getUniqueId().toString();
		if (!c.contains("Tokens")) {
			c.createSection("Tokens");
		}
		if (!c.contains("Tokens." + uuid)) {
			c.set("Tokens." + uuid, 0);
		}
		double current = c.getDouble("Tokens."+uuid);
		c.set("Tokens."+uuid, current+amount);
		CalEcoTools.getInstance().hoeDataFile.saveData();
	}
}
