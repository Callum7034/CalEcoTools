package com.rcallum.CalEcoTools.Events.VoidChest;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.rcallum.CalEcoTools.CalEcoTools;
import com.rcallum.CalEcoTools.Data.VoidChests;
import com.rcallum.CalEcoTools.GUI.VoidChest.VoidChestGUI;
import com.rcallum.CalEcoTools.Manager.VoidChest.VCManager;
import com.rcallum.CalEcoTools.Manager.VoidChest.VoidChest;
import com.rcallum.CalEcoTools.Messages.msg;
import com.rcallum.CalEcoTools.Utils.NBT;

public class VCClick implements Listener {

	@EventHandler
	public void clickChest(PlayerInteractEvent e) {
		if (e.getPlayer().isSneaking()) {
			return;
		}
		if (e.getClickedBlock() == null)
			return;
		if (e.getClickedBlock().getType() == Material.CHEST) {
			if (VoidChests.isVoidChest(e.getClickedBlock().getLocation())) {
				e.setCancelled(true);
				VoidChest vc = VCManager.getInstance()
						.getVoidChest(VoidChests.locToString(e.getClickedBlock().getLocation()));
				if (vc.getOwner().toString().equalsIgnoreCase(e.getPlayer().getUniqueId().toString())) {
					// CHECK if holding upgrade
					if (NBT.hasNBT(e.getPlayer().getItemInHand(), "upgradeMulti")) {
						double itemMulti = Double.valueOf(NBT.getNBT(e.getPlayer().getItemInHand(), "multi"));
						double chestMulti = vc.getMulti();
						if (itemMulti <= chestMulti) {
							e.getPlayer().sendMessage(msg.AlreadyUpgrade());
							return;
						} else {
							ItemStack is = e.getPlayer().getItemInHand();
							if (is.getAmount() > 1) {
								is.setAmount(is.getAmount() - 1);
							} else {
								e.getPlayer().getInventory().remove(is);
							}
							vc.setMulti(itemMulti);
							e.getPlayer().sendMessage(msg.successfulUpgrade());
							return;
						}
					}
					if (NBT.hasNBT(e.getPlayer().getItemInHand(), "upgradeAutoPickup")) {
						if (vc.getConfig().getBoolean("autopickup")) {
							e.getPlayer().sendMessage(msg.AlreadyUpgrade());
							return;
						} else {
							ItemStack is = e.getPlayer().getItemInHand();
							if (is.getAmount() > 1) {
								is.setAmount(is.getAmount() - 1);
							} else {
								e.getPlayer().getInventory().remove(is);
							}
							vc.getConfig().set("autopickup", true);
							CalEcoTools.getInstance().dataFile.saveData();
							
							e.getPlayer().sendMessage(msg.successfulUpgrade());
							return;
						}
					}
					VoidChestGUI.getInstance().openGUI(e.getPlayer(), vc);
				} else {
					e.getPlayer().sendMessage(msg.notYourVoidChest());
					return;
				}
			}
		}
	}

}
