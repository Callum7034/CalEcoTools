package com.rcallum.CalEcoTools.Manager.VoidChest;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.bgsoftware.wildstacker.api.WildStackerAPI;
import com.bgsoftware.wildstacker.api.objects.StackedItem;
import com.rcallum.CalEcoTools.CalEcoTools;
import com.rcallum.CalEcoTools.Data.VoidChests;
import com.rcallum.CalEcoTools.EconHandler.Deposit;
import com.rcallum.CalEcoTools.Holograms.HoloAPI;
import com.rcallum.CalEcoTools.Holograms.VoidChestHoloManager;
import com.rcallum.CalEcoTools.ShopHook.GetPrice;
import com.rcallum.CalEcoTools.Statistics.Stats;
import com.rcallum.CalEcoTools.Utils.NumberFormatter;

public class VoidChest {
	private Location loc;
	private String locID;
	private UUID owner;
	private double multi;
	private ConfigurationSection section;
	private Inventory linkedInv;
	private HoloAPI holo;

	public VoidChest(String id) {
		this.section = CalEcoTools.data.getConfigurationSection("VoidChests." + id);
		this.loc = VoidChests.stringToLoc(id);
		this.owner = UUID.fromString(section.getString("owner"));
		this.multi = section.getDouble("multiplier");
		this.locID = id;
		if (loc.getBlock().getType() == Material.CHEST) {
			Chest chest = (Chest) loc.getBlock().getState();
			this.linkedInv = chest.getBlockInventory();
		}
		VoidChestHoloManager.getInstance().createHolo(this);
	}

	public Location getLoc() {
		return this.loc;
	}

	public String getID() {
		return this.locID;
	}

	public UUID getOwner() {
		return this.owner;
	}

	public double getMulti() {
		return this.multi;
	}

	public ConfigurationSection getConfig() {
		return this.section;
	}

	public Inventory getInv() {
		return this.linkedInv;
	}

	public double getSellAmount() {
		return VoidChests.getSellAmount(locID);
	}

	public void setHolo(HoloAPI h) {
		this.holo = h;
	}

	public HoloAPI getHolo() {
		return this.holo;
	}
	public void setMulti(double newMulti) {
		this.multi = newMulti;
		this.section.set("multiplier", newMulti);
		CalEcoTools.getInstance().dataFile.saveData();
	}

	public void withdraw(Player p) {
		double amt = VoidChests.getSellAmount(locID);
		section.set("SellAmount", 0);
		section.set("Items", 0);
		Deposit.pay(p.getUniqueId().toString(), amt);
		section.set("stats.TimesSold", (section.getInt("stats.TimesSold") + 1));
		// MESSAGE HERE
		if (amt > 0) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&',
					CalEcoTools.voidConfig.getString("paymentMsg").replaceAll("%amount%", NumberFormatter.withLargeIntegers(amt))));
		}
		CalEcoTools.getInstance().dataFile.saveData();
		Stats.addVC(1);
	}

	public void checkInvForSell() {
		Inventory inv = getInv();
		for (ItemStack i : inv.getContents()) {
			if (i == null)
				continue;
			if (i.getType() == null)
				continue;
			if (i.getType() == Material.AIR)
				continue;
			addItem(i);
			inv.remove(i);
		}
	}
	
	public void pickupNearbyItems() {
		ConfigurationSection sec = CalEcoTools.data.getConfigurationSection("VoidChests");
		
		if (sec.getBoolean(locID+".autopickup")) {
			
			double scanSize = CalEcoTools.voidConfig.getDouble("scanSize");
			
			Collection<Entity> entities = loc.getWorld().getNearbyEntities(loc, scanSize, scanSize, scanSize);
			
			for (Entity en : entities) {
				if (en instanceof Item) {
					
					if (Bukkit.getServer().getPluginManager().isPluginEnabled("WildStacker")) {
						StackedItem item = WildStackerAPI.getStackedItem((Item)en);
						ItemStack i = item.getItemStack();
						double price = GetPrice.getPrice(i);
						if (price > 0) {
							
							item.remove();
							addItem(i);
						}
					} else {
						Item item = (Item)en;
						ItemStack i = item.getItemStack();
						double price = GetPrice.getPrice(i);
						if (price > 0) {
							
							item.remove();
							addItem(i);
						}
					}
				}
			}
			
			
		}
	}

	public void removeVoidChestFromData() {
		ConfigurationSection sec = CalEcoTools.data.getConfigurationSection("VoidChests");
		sec.set(locID, null);
		Set<String> keys = sec.getKeys(false);
		keys.remove(locID);
		CalEcoTools.getInstance().dataFile.saveData();
	}

	public void addItem(ItemStack i) {

		int currentItems = section.getInt("Items");
		int totalItems = section.getInt("stats.TotalItems");
		double sellCurrent = section.getDouble("SellAmount");
		double sellTotal = section.getDouble("stats.TotalSold");
		double price = GetPrice.getPrice(i);
		if (price > 0) {
			section.set("SellAmount", (sellCurrent + (price * getMulti())));
			section.set("stats.TotalSold", (sellTotal + (price * getMulti())));
		}
		section.set("Items", (currentItems + i.getAmount()));
		section.set("stats.TotalItems", (totalItems + i.getAmount()));
		CalEcoTools.getInstance().dataFile.saveData();
	}

	public void addSellNumber() {
		section.set("stats.TimesSold", (section.getInt("stats.TimesSold") + 1));
	}

}
