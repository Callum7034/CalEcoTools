package com.rcallum.CalEcoTools.GUI.VoidChest;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.rcallum.CalEcoTools.CalEcoTools;
import com.rcallum.CalEcoTools.Data.VoidChests;
import com.rcallum.CalEcoTools.Manager.VoidChest.VCManager;
import com.rcallum.CalEcoTools.Manager.VoidChest.VoidChest;
import com.rcallum.CalEcoTools.Utils.NBT;
import com.rcallum.CalEcoTools.Utils.NumberFormatter;

public class VoidChestGUI implements Listener {
	private static VoidChestGUI instance;

	public VoidChestGUI() {
		instance = this;
	}

	public static VoidChestGUI getInstance() {
		if (instance == null) {
			instance = new VoidChestGUI();
		}
		return instance;
	}

	public void openGUI(Player p, VoidChest vc) {
		ConfigurationSection guiConfig = CalEcoTools.voidConfig.getConfigurationSection("GUI");

		String name = c(guiConfig.getString("name"));
		Inventory GUI = Bukkit.createInventory(null, 27, name);

		String FillerItem = guiConfig.getString("fillerItem");
		int FillerItemDamage = guiConfig.getInt("fillerItemDamage");
		ItemStack filler = new ItemStack(Material.getMaterial(FillerItem), 1, (short) FillerItemDamage);
		ItemMeta a = filler.getItemMeta();
		a.setDisplayName(" ");
		filler.setItemMeta(a);
		for (int i = 0; i < GUI.getSize(); i++) {
			GUI.setItem(i, filler);
		}

		ItemStack info = new ItemStack(Material.ENCHANTED_BOOK);
		ItemMeta im = info.getItemMeta();
		im.setDisplayName(c("&6&lStats"));

		String id = vc.getID();
		boolean pickup = vc.getConfig().getBoolean("autopickup");
		String Pickup = "";
		if (pickup) {
			Pickup = "Enabled";
		} else {
			Pickup = "Disabled";
		}
		double sellprice = VoidChests.getSellAmount(id);
		double selltotal = VoidChests.getSellTotal(id);
		List<String> lore = new ArrayList<String>();
		lore.add(c("&9Auto-Pickup: &e"+Pickup));
		lore.add(c("&8---------------------------"));
		lore.add(c("&9Current Items: &e" + VoidChests.getItemAmount(id)));
		if (sellprice > 10000D) {
			lore.add(c("&9Current Sell Amount: &e" + NumberFormatter.formatNumber(sellprice)));
		} else {
			lore.add(c("&9Current Sell Amount: &e$" + sellprice));
		}

		lore.add(c("&8---------------------------"));
		lore.add(c("&9Total Items: &e" + VoidChests.getItemTotal(id)));
		if (selltotal > 10000D) {
			lore.add(c("&9Total Sell Amount: &e" + NumberFormatter.formatNumber(selltotal)));
		} else {
			lore.add(c("&9Total Sell Amount: &e$" + selltotal));
		}

		lore.add(c("&8---------------------------"));
		lore.add(c("&9Times Sold: &e" + VoidChests.getTimesSold(id)));
		lore.add(c("&9Multiplier: &e" + VoidChests.getMulti(id) + "x"));
		lore.add(c("&8---------------------------"));

		im.setLore(lore);
		info.setItemMeta(im);
		GUI.setItem(10, info);

		ItemStack sell = new ItemStack(Material.PAPER);
		sell = NBT.setNBT(sell, "id", id);
		im = sell.getItemMeta();
		im.setDisplayName(c("&a&lSell Contents"));
		lore = new ArrayList<String>();
		lore.add(c("&8---------------------------"));
		lore.add(c("&9Items: &e" + VoidChests.getItemAmount(id)));
		lore.add(c("&9Sell Amount: &e" + NumberFormatter.formatNumber(VoidChests.getSellAmount(id))));
		lore.add(c("&8---------------------------"));
		im.setLore(lore);
		sell.setItemMeta(im);
		GUI.setItem(12, sell);

		ItemStack inven = new ItemStack(Material.CHEST);
		inven = NBT.setNBT(inven, "id", id);
		im = inven.getItemMeta();
		im.setDisplayName(c("&b&lInventory"));
		lore = new ArrayList<String>();
		lore.add(c("&8---------------------------"));
		lore.add(c("&9Items: &e" + VoidChests.getItemAmount(id)));
		lore.add(c("&9Click to check actual inventory"));
		lore.add(c("&8---------------------------"));
		im.setLore(lore);
		inven.setItemMeta(im);
		GUI.setItem(14, inven);

		ItemStack remove = new ItemStack(Material.BARRIER);
		remove = NBT.setNBT(remove, "id", id);
		im = remove.getItemMeta();
		im.setDisplayName(c("&c&lRemove Void Chest"));
		lore = new ArrayList<String>();
		lore.add(c("&7Click to remove the void chest"));
		lore.add(c("&7Make sure you have room in your inventory"));
		im.setLore(lore);
		remove.setItemMeta(im);
		GUI.setItem(16, remove);
		p.openInventory(GUI);
	}

	public String c(String i) {
		return ChatColor.translateAlternateColorCodes('&', i);
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		String n = c(CalEcoTools.voidConfig.getString("GUI.name"));
		if (e.getView().getTitle().equalsIgnoreCase(n)) {
			e.setCancelled(true);
			if (e.getSlot() == 12) {
				VoidChest vc = VCManager.getInstance().getVoidChest(NBT.getNBT(e.getCurrentItem(), "id"));
				vc.withdraw((Player) e.getWhoClicked());
				e.getWhoClicked().closeInventory();
			}
			if (e.getSlot() == 14) {
				VoidChest vc = VCManager.getInstance().getVoidChest(NBT.getNBT(e.getCurrentItem(), "id"));
				e.getWhoClicked().openInventory(vc.getInv());
			}
			if (e.getSlot() == 16) {
				VoidChests.removeChest((Player) e.getWhoClicked(), NBT.getNBT(e.getCurrentItem(), "id"));
				// remove vc
			}
		}
	}
}
