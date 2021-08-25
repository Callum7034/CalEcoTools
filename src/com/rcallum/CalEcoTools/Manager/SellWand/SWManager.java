package com.rcallum.CalEcoTools.Manager.SellWand;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.rcallum.CalEcoTools.CalEcoTools;
import com.rcallum.CalEcoTools.EconHandler.Deposit;
import com.rcallum.CalEcoTools.Holograms.SellWandHoloManager;
import com.rcallum.CalEcoTools.Items.SellWandItem;
import com.rcallum.CalEcoTools.Messages.msg;
import com.rcallum.CalEcoTools.ShopHook.GetPrice;
import com.rcallum.CalEcoTools.Utils.NBT;
import com.rcallum.CalEcoTools.Utils.NumberFormatter;

public class SWManager {

	public static void SellWand(Block inventoryBlock, Player p, double multi, BlockFace face) {
		Chest chest = (Chest) inventoryBlock.getState();
		Inventory inventory = chest.getBlockInventory();
		Inventory inv = Bukkit.createInventory(null, 27);
		if (inventory instanceof DoubleChestInventory) {
	        DoubleChest doubleChest = (DoubleChest) inventory.getHolder();
	        inv = doubleChest.getInventory();
	    } else {
	    	inv = inventory;
	    }
		double sellCurrent = 0D;
		int itemTotal = 0;
		for (ItemStack i : inv.getContents()) {
			if (i == null)
				continue;
			if (i.getType() == null)
				continue;
			if (i.getType() == Material.AIR)
				continue;

			double price = GetPrice.getPrice(i);
			if (price <= 0) {
				continue;
			} else {
				itemTotal = itemTotal + i.getAmount();
				inv.remove(i);
				sellCurrent = sellCurrent + price;
			}

		}
		
		if (itemTotal == 0) {
			p.sendMessage(msg.nothingInChest());
			return;
		}
		sell(p, sellCurrent, multi, itemTotal, face, inventoryBlock);
		removeUse(p);
	}

	public static void sell(Player p, double price, double multi, int items, BlockFace face, Block chest) {
		FileConfiguration config = CalEcoTools.sellwandConfig;
		double amt = price * multi;
		
		// Send Message (x2 one for multi one for original)
		p.sendMessage(color(config.getString("sellMessage").replaceAll("%amount%", NumberFormatter.withLargeIntegers(price))));
		p.sendMessage(color(config.getString("sellMessageBonus").replaceAll("%amount%", NumberFormatter.withLargeIntegers(amt - price) + "")));
		
		// Send Money
		Deposit.pay(p.getUniqueId().toString(), amt);
		
		
		// Spawn in hologram with delayed task to remove it (config on/off)
		SellWandHoloManager.getInstance().createHolo(p, face, chest, amt, items);
	}

	public static void removeUse(Player p) {
		ItemStack item = p.getItemInHand();
		int uses = Integer.valueOf(NBT.getNBT(item, "uses"));
		double multi = Double.valueOf(NBT.getNBT(item, "multi"));
		if (uses > 0) {
			if (uses == 1) {
				p.getInventory().remove(item);
			} else {
				p.setItemInHand(SellWandItem.getSellWand(multi, uses - 1));
			}
		}
	}

	public static String color(String i) {
		return ChatColor.translateAlternateColorCodes('&', i);
	}
}
