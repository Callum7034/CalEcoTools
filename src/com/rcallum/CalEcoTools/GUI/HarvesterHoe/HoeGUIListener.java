package com.rcallum.CalEcoTools.GUI.HarvesterHoe;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.rcallum.CalEcoTools.Manager.HarvesterHoeManager;
import com.rcallum.CalEcoTools.Manager.HarvesterHoe.UpgradeHoe;
import com.rcallum.CalEcoTools.Manager.HarvesterHoe.UpgradeHoe.Upgrade;
import com.rcallum.CalEcoTools.Utils.NBT;

public class HoeGUIListener implements Listener {
	private static HoeGUIListener instance;

	public HoeGUIListener() {
		instance = this;
	}

	public static HoeGUIListener getInstance() {
		if (instance == null) {
			instance = new HoeGUIListener();
		}
		return instance;
	}


	@EventHandler
	public void onClick(InventoryClickEvent e) {
		String n = c("&9&lHarvester &9&lHoe");
		if (e.getView().getTitle().equalsIgnoreCase(n)) {
			e.setCancelled(true);
			if (NBT.hasNBT(e.getCurrentItem(), "id")) {
				if (!NBT.hasNBT(e.getWhoClicked().getItemInHand(), "HarvesterHoe")) {
					e.getWhoClicked().sendMessage(c("&cYou must be holding a Harvester Hoe to use this GUI"));
					e.getWhoClicked().closeInventory();
					return;
				}
				e.getWhoClicked().closeInventory();
				if (e.getSlot() == 13) {
					if (HarvesterHoeManager.getInstance()
							.toggleAutoSell((Player) e.getWhoClicked())) {
						e.getWhoClicked().closeInventory();
						e.getWhoClicked().sendMessage(c("&aAuto Sell has been toggled"));
						
					} else {
						e.getWhoClicked().closeInventory();
						e.getWhoClicked().sendMessage(c("&cYou cannot enable autosell as you have not unlocked it yet"));
					}
				}
				if (e.getSlot() == 15) {
					HoeGUI.getInstance().openUpgradeGUI((Player) e.getWhoClicked(), e.getWhoClicked().getItemInHand());
				}
			}
		}
		String nn = c("&9&lHarvester &9&lHoe &9&lUpgrades");
		if (e.getView().getTitle().equalsIgnoreCase(nn)) {
			e.setCancelled(true);
			if (NBT.hasNBT(e.getCurrentItem(), "id")) {
				if (!NBT.hasNBT(e.getWhoClicked().getItemInHand(), "HarvesterHoe")) {
					e.getWhoClicked().sendMessage(c("&cYou must be holding a Harvester Hoe to use this GUI"));
					e.getWhoClicked().closeInventory();
					return;
				}
				int slot = e.getSlot();
				Upgrade up = Upgrade.CYBORG;
				if (slot == 10) {
					up = Upgrade.CYBORG;
				}
				if (slot == 12) {
					up = Upgrade.DROPS;
				}
				if (slot == 14) {
					up = Upgrade.SELL;
				}
				if (slot == 16) {
					up = Upgrade.TOKEN;
				}
				if (slot == 20) {
					up = Upgrade.MOBCOIN;
				}
				if (slot == 22) {
					up = Upgrade.AUTOSELL;
				}
				if (slot == 24) {
					up = Upgrade.KEYS;
				}

				UpgradeHoe.getInstance().upgradeHoe(up, (Player) e.getWhoClicked());
				HoeGUI.getInstance().openUpgradeGUI((Player) e.getWhoClicked(), e.getWhoClicked().getItemInHand());
			}
		}

	}


	public String c(String i) {
		return ChatColor.translateAlternateColorCodes('&', i);
	}
}
