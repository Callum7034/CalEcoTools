package com.rcallum.CalEcoTools.Holograms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.rcallum.CalEcoTools.CalEcoTools;
import com.rcallum.CalEcoTools.Manager.VoidChest.VCManager;
import com.rcallum.CalEcoTools.Manager.VoidChest.VoidChest;
import com.rcallum.CalEcoTools.Statistics.Stats;
import com.rcallum.CalEcoTools.Utils.NumberFormatter;

public class VoidChestHoloManager {
	private static VoidChestHoloManager instance;

	public VoidChestHoloManager() {
		instance = this;
	}

	public static VoidChestHoloManager getInstance() {
		if (instance == null) {
			instance = new VoidChestHoloManager();
		}
		return instance;
	}

	HashMap<HoloAPI, List<Player>> holograms = new HashMap<HoloAPI, List<Player>>();

	public void onDisable() {
		// break all existing holograms
		for (HoloAPI h : holograms.keySet()) {
			List<Player> players = holograms.get(h);
			for (Player p : players) {
				h.destroy(p);
			}
		}
		// Reset HashMap
		holograms = new HashMap<HoloAPI, List<Player>>();
	}

	public void onEnable() {
		Bukkit.getScheduler().scheduleSyncDelayedTask(CalEcoTools.getInstance(), new Runnable() {

			@Override
			public void run() {
				Collection<VoidChest> vcs = VCManager.getInstance().voidChests.values();
				for (VoidChest vc : vcs) {
					createHolo(vc);
				}
			}

		}, 50L);
	}

	public void joinEvent(Player p) {
		Collection<VoidChest> vcs = VCManager.getInstance().voidChests.values();
		for (VoidChest vc : vcs) {
			Location loc = vc.getLoc();
			if (loc.distance(p.getLocation()) < 150) {
				addPlayer(vc.getHolo(), p);
			}
		}
	}

	public void leaveEvent(Player p) {
		for (HoloAPI holo : holograms.keySet()) {
			List<Player> players = holograms.get(holo);
			if (players.contains(p)) {
				removePlayer(holo, p);
			}
		}
	}

	public void repeatingTask() {
		// UPDATE HOLOGRAMS
		// break all existing holograms
		for (HoloAPI h : holograms.keySet()) {
			List<Player> players = holograms.get(h);
			for (Player p : players) {
				h.destroy(p);
			}
		}
		// Reset HashMap
		holograms = new HashMap<HoloAPI, List<Player>>();
		// Create new ones
		Collection<VoidChest> vcs = VCManager.getInstance().voidChests.values();
		for (VoidChest vc : vcs) {
			createHolo(vc);
			Stats.addVC(1);
		}
	}

	public void breakHolo(HoloAPI h) {
		List<Player> players = holograms.get(h);
		for (Player p : players) {
			removePlayer(h, p);
		}
		holograms.remove(h);
	}

	public void addPlayer(HoloAPI h, Player p) {
		h.display(p);
		holograms.get(h).add(p);
	}

	public void removePlayer(HoloAPI h, Player p) {
		h.destroy(p);
	}

	public void createHolo(VoidChest vc) {
		FileConfiguration vcConfig = CalEcoTools.voidConfig;
		ConfigurationSection sec = vcConfig.getConfigurationSection("hologram");
		if (!sec.getBoolean("enabled"))
			return;
		boolean pickup = vc.getConfig().getBoolean("autopickup");
		String Pickup = "";
		if (pickup) {
			Pickup = "Enabled";
		} else {
			Pickup = "Disabled";
		}
		List<String> text = new ArrayList<String>();
		for (String s : sec.getStringList("text")) {
			String ss = ChatColor.translateAlternateColorCodes('&', s);
			ss = ss.replaceAll("%multi%", vc.getMulti() + "");
			ss = ss.replaceAll("%owner%", Bukkit.getOfflinePlayer(vc.getOwner()).getName());
			ss = ss.replaceAll("%money%", NumberFormatter.withLargeIntegers(vc.getSellAmount()));
			ss = ss.replaceAll("%autopickup%", Pickup);
			text.add(ss);
		}

		double height = sec.getDouble("height");
		HoloAPI h = HoloAPI.newInstance(vc.getLoc().clone().add(0.5, height, 0.5), text, vc);
		holograms.put(h, new ArrayList<Player>());
		addNearPlayers(h);
		vc.setHolo(h);
	}

	public void addNearPlayers(HoloAPI hologram) {
		VoidChest vc = hologram.getVC();
		Location loc = vc.getLoc();
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			if (loc.distance(p.getLocation()) < 150) {
				addPlayer(hologram, p);
			}
		}
	}
}
