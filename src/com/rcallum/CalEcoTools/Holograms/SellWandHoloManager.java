package com.rcallum.CalEcoTools.Holograms;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.rcallum.CalEcoTools.CalEcoTools;
import com.rcallum.CalEcoTools.Utils.NumberFormatter;

public class SellWandHoloManager {
	private static SellWandHoloManager instance;

	public SellWandHoloManager() {
		instance = this;
	}

	public static SellWandHoloManager getInstance() {
		if (instance == null) {
			instance = new SellWandHoloManager();
		}
		return instance;
	}

	
	public void createHolo(Player p, BlockFace face, Block chest, double amount, int items) {
		FileConfiguration config = CalEcoTools.sellwandConfig;
		ConfigurationSection sec = config.getConfigurationSection("hologram");
		if (!sec.getBoolean("enabled"))
			return;
		
		
		List<String> text = new ArrayList<String>();
		for (String s : sec.getStringList("text")) {
			String ss = ChatColor.translateAlternateColorCodes('&', s);
			ss = ss.replaceAll("%amount%", NumberFormatter.withLargeIntegers(amount));
			ss = ss.replaceAll("%items%", NumberFormatter.withLargeIntegers(items));
			
			text.add(ss);
		}
		// Check clicked blockface
		double x = 0;
		double y = 0;
		double z = 0;
		if (face == BlockFace.EAST) {
			z = 0.5;
			x = 1;
		}
		if (face == BlockFace.NORTH) {
			z = -1;
			x = 0.5;
		}
		if (face == BlockFace.WEST) {
			z = 0.5;
			x = -1;
		}
		if (face == BlockFace.SOUTH) {
			z = 1;
			x = 0.5;
		}
		if (face == BlockFace.UP) {
			y = 1;
			x = 0.5;
			z = 0.5;
		}
		if (face == BlockFace.DOWN) {
			y = -1;
			x = 0.5;
			z = 0.5;
		}

		HoloAPI h = HoloAPI.newInstance(chest.getLocation().clone().add(x, y, z), text, null);
		h.display(p);
		long delay = sec.getLong("deleteDelay");
		Bukkit.getScheduler().scheduleSyncDelayedTask(CalEcoTools.getInstance(), new Runnable() {

			@Override
			public void run() {
				h.destroy(p);
			}
			
		}, delay);
	}
}
