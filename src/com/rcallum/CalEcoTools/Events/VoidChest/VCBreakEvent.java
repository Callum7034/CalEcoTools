package com.rcallum.CalEcoTools.Events.VoidChest;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.rcallum.CalEcoTools.Data.VoidChests;
import com.rcallum.CalEcoTools.Messages.msg;

public class VCBreakEvent implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void placeEvent(BlockBreakEvent e) {
		if (e.getBlock().getType() == Material.CHEST) {
			if (VoidChests.isVoidChest(e.getBlock().getLocation())) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(msg.noBreakVoidChest());
			}
		}
		if (e.getBlock().getType() == Material.ANVIL) {
			List<String> lines = new ArrayList<String>();
			lines.add("&aTest Line 1");
			lines.add("&aTest Line 2");
			lines.add("&aTest Line 3");
			
			/*HoloAPI h = HoloAPI.newInstance(e.getBlock().getLocation().add(0, 2, 0), lines);
			h.display(e.getPlayer());
			Bukkit.getScheduler().scheduleSyncDelayedTask(CalEcoTools.getInstance(), new Runnable() {

				@Override
				public void run() {
					h.destroy(e.getPlayer());
					
				}
				
			},20L);
			*/
		}
	}
}
