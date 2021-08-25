package com.rcallum.CalEcoTools.Events.CondenseWand;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;

import com.rcallum.CalEcoTools.Manager.CondenseWand.CWManager;
import com.rcallum.CalEcoTools.Statistics.Stats;
import com.rcallum.CalEcoTools.Utils.NBT;

public class CWClick implements Listener{
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void clickChest(PlayerInteractEvent e) {
		if ((e.getClickedBlock().getType() == Material.CHEST)
				|| (e.getClickedBlock().getType() == Material.TRAPPED_CHEST)) {
			if (NBT.hasNBT(e.getPlayer().getItemInHand(), "CondenseWand")) {
				e.setCancelled(true);
				Chest chest = (Chest) e.getClickedBlock().getState();
				Inventory inventory = chest.getBlockInventory();
				Inventory inv = Bukkit.createInventory(null, 27);
				if (inventory instanceof DoubleChestInventory) {
			        DoubleChest doubleChest = (DoubleChest) inventory.getHolder();
			        inv = doubleChest.getInventory();
			    } else {
			    	inv = inventory;
			    }
				//CONDENSE CHEST
				CWManager.convertIngots(e.getPlayer(), inv);
				
				Stats.addCondense(1);
			}
		}
		
	}
}
