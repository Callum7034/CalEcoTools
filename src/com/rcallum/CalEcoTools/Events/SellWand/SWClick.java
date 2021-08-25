package com.rcallum.CalEcoTools.Events.SellWand;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.rcallum.CalEcoTools.Manager.SellWand.SWManager;
import com.rcallum.CalEcoTools.Statistics.Stats;
import com.rcallum.CalEcoTools.Utils.NBT;

public class SWClick implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void clickChest(PlayerInteractEvent e) {
		if ((e.getClickedBlock().getType() == Material.CHEST)
				|| (e.getClickedBlock().getType() == Material.TRAPPED_CHEST)) {
			if (NBT.hasNBT(e.getPlayer().getItemInHand(), "SellWand")) {
				e.setCancelled(true);
				double multi = Double.valueOf(NBT.getNBT(e.getPlayer().getItemInHand(), "multi"));
				SWManager.SellWand(e.getClickedBlock(), e.getPlayer(), multi, e.getBlockFace());
				Stats.addSellWand(1);
			}
		}
		
	}

}
