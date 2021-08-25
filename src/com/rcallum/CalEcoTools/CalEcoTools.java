package com.rcallum.CalEcoTools;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.rcallum.CalEcoTools.Commands.AutoSellCMD;
import com.rcallum.CalEcoTools.Commands.CaneTop;
import com.rcallum.CalEcoTools.Commands.CondenseWandCMD;
import com.rcallum.CalEcoTools.Commands.HarvesterHoeCMD;
import com.rcallum.CalEcoTools.Commands.MainCommand;
import com.rcallum.CalEcoTools.Commands.SellWandCMD;
import com.rcallum.CalEcoTools.Commands.Upgrades;
import com.rcallum.CalEcoTools.Commands.VoidChestCMD;
import com.rcallum.CalEcoTools.Events.Connections;
import com.rcallum.CalEcoTools.Events.CondenseWand.CWClick;
import com.rcallum.CalEcoTools.Events.HarvesterHoe.BreakCane;
import com.rcallum.CalEcoTools.Events.HarvesterHoe.CyborgEffects;
import com.rcallum.CalEcoTools.Events.HarvesterHoe.OpenHoeGUI;
import com.rcallum.CalEcoTools.Events.SellWand.SWClick;
import com.rcallum.CalEcoTools.Events.VoidChest.VCBreakEvent;
import com.rcallum.CalEcoTools.Events.VoidChest.VCClick;
import com.rcallum.CalEcoTools.Events.VoidChest.VCPlaceEvent;
import com.rcallum.CalEcoTools.GUI.HarvesterHoe.HoeGUIListener;
import com.rcallum.CalEcoTools.GUI.VoidChest.VoidChestGUI;
import com.rcallum.CalEcoTools.Holograms.VoidChestHoloManager;
import com.rcallum.CalEcoTools.Manager.VoidChest.VCManager;
import com.rcallum.CalEcoTools.Placeholder.PapiHook;
import com.rcallum.CalEcoTools.ShopHook.GetPrice;
import com.rcallum.CalEcoTools.Statistics.Stats;
import com.rcallum.CalEcoTools.Utils.ConfigManager;
import com.rcallum.CalEcoTools.Utils.NumberFormatter;

import net.milkbowl.vault.economy.Economy;

public class CalEcoTools extends JavaPlugin {
	// Config
	public ConfigManager configFile;
	public static FileConfiguration config;
	// Lang File
	public ConfigManager msgFile;
	public static FileConfiguration msg;
	// Data File
	public ConfigManager dataFile;
	public static FileConfiguration data;
	// Hoe Data File
	public ConfigManager hoeDataFile;
	public static FileConfiguration hoeData;
	// Void Chest
	public ConfigManager voidFile;
	public static FileConfiguration voidConfig;
	// Harvester Hoe
	public ConfigManager harvesterFile;
	public static FileConfiguration harvesterConfig;
	// Sell wand
	public ConfigManager sellwandFile;
	public static FileConfiguration sellwandConfig;
	// Condense Wand
	public ConfigManager condenseFile;
	public static FileConfiguration condenseConfig;

	private Economy econ;

	public double CANEPRICE = 0D;
	public HashMap<Player, Double> autoSellCane = new HashMap<>();

	public void onEnable() {
		// Commands
		getServer().getPluginCommand("CalEcoTools").setExecutor(new MainCommand());
		getServer().getPluginCommand("VoidChest").setExecutor(new VoidChestCMD());
		getServer().getPluginCommand("AutoSell").setExecutor(new AutoSellCMD());
		getServer().getPluginCommand("harvesterhoe").setExecutor(new HarvesterHoeCMD());
		getServer().getPluginCommand("sellwand").setExecutor(new SellWandCMD());
		getServer().getPluginCommand("condensewand").setExecutor(new CondenseWandCMD());
		getServer().getPluginCommand("canetop").setExecutor(new CaneTop());
		getServer().getPluginCommand("vcupgrade").setExecutor(new Upgrades());
		getServer().getPluginCommand("pickupupgrade").setExecutor(new Upgrades());
		
		// Events
		getServer().getPluginManager().registerEvents(new VCPlaceEvent(), this);
		getServer().getPluginManager().registerEvents(new VCBreakEvent(), this);
		getServer().getPluginManager().registerEvents(new VCClick(), this);
		getServer().getPluginManager().registerEvents(new VoidChestGUI(), this);
		getServer().getPluginManager().registerEvents(new Connections(), this);
		getServer().getPluginManager().registerEvents(new SWClick(), this);
		getServer().getPluginManager().registerEvents(new CWClick(), this);
		getServer().getPluginManager().registerEvents(new BreakCane(), this);
		getServer().getPluginManager().registerEvents(new OpenHoeGUI(), this);
		getServer().getPluginManager().registerEvents(new HoeGUIListener(), this);
		getServer().getPluginManager().registerEvents(new CyborgEffects(), this);

		// Configs
		setupConfigs();
		
		//Placeholder API
		if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            new PapiHook(this).register();
      }

		// VoidChests
		VCManager.getInstance().initialize();
		
		//Stats
		Stats.init();

		// VoidChest Selling
		long delay = voidConfig.getLong("taskDelay");
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {
				VCManager.getInstance().sellTask();
			}

		}, 0L, delay);
		// VoidChest Hologram
		long holodelay = voidConfig.getLong("hologramDelay");
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {
				VoidChestHoloManager.getInstance().repeatingTask();
			}

		}, holodelay * 2, holodelay);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {
				for (Player p : autoSellCane.keySet()) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', harvesterConfig.getString("Message")
							.replaceAll("%amount%", NumberFormatter.withLargeIntegers(autoSellCane.get(p)))));
				}
				autoSellCane.clear();
			}

		}, 20L, 200L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

			@Override
			public void run() {
				CANEPRICE = GetPrice.getPrice(new ItemStack(Material.SUGAR_CANE));
			}

		}, 20L);
		if (!setupEconomy()) {
			this.getLogger().severe("Disabled due to no Vault dependency found!");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}

		VoidChestHoloManager.getInstance().onEnable();

	}

	public void onDisable() {
		VoidChestHoloManager.getInstance().onDisable();
	}

	public void setupConfigs() {
		configFile = new ConfigManager("config.yml", this);
		configFile.getConfig().options().copyDefaults(true);
		configFile.saveConfigOnEnable();
		config = configFile.getConfig();

		voidFile = new ConfigManager("voidchest.yml", this);
		voidFile.getConfig().options().copyDefaults(true);
		voidFile.saveConfigOnEnable();
		voidConfig = voidFile.getConfig();

		harvesterFile = new ConfigManager("harvesterhoe.yml", this);
		harvesterFile.getConfig().options().copyDefaults(true);
		harvesterFile.saveConfigOnEnable();
		harvesterConfig = harvesterFile.getConfig();

		sellwandFile = new ConfigManager("sellwand.yml", this);
		sellwandFile.getConfig().options().copyDefaults(true);
		sellwandFile.saveConfigOnEnable();
		sellwandConfig = sellwandFile.getConfig();

		condenseFile = new ConfigManager("condensewand.yml", this);
		condenseFile.getConfig().options().copyDefaults(true);
		condenseFile.saveConfigOnEnable();
		condenseConfig = condenseFile.getConfig();

		msgFile = new ConfigManager("lang.yml", this);
		msgFile.getConfig().options().copyDefaults(true);
		msgFile.saveConfigOnEnable();
		msg = msgFile.getConfig();

		dataFile = new ConfigManager("data.yml", this);
		dataFile.getConfig().options().copyDefaults(true);
		dataFile.saveConfigOnEnable();
		data = dataFile.getConfig();

		hoeDataFile = new ConfigManager("hoedata.yml", this);
		hoeDataFile.getConfig().options().copyDefaults(true);
		hoeDataFile.saveConfigOnEnable();
		hoeData = hoeDataFile.getConfig();
	}

	private boolean setupEconomy() {
		if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
			return false;
		}

		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

	public Economy getEconomy() {
		return econ;
	}

	private static CalEcoTools instance;

	public CalEcoTools() {
		instance = this;
	}

	public static CalEcoTools getInstance() {
		if (instance == null) {
			instance = new CalEcoTools();
		}
		return instance;
	}
}
