package com.rcallum.CalEcoTools.Events.VoidChest;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import com.rcallum.CalEcoTools.Data.VoidChests;
import com.rcallum.CalEcoTools.Messages.msg;
import com.rcallum.CalEcoTools.Utils.NBT;

public class VCPlaceEvent implements Listener {

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void placeEvent(BlockPlaceEvent e) {
		ItemStack i = e.getItemInHand();
		if (NBT.hasNBT(i, "VoidChest")) {
			// Check Adjacent
			if ((e.getBlock().getRelative(BlockFace.NORTH).getType() == Material.CHEST)
					|| (e.getBlock().getRelative(BlockFace.SOUTH).getType() == Material.CHEST)
					|| (e.getBlock().getRelative(BlockFace.EAST).getType() == Material.CHEST)
					|| (e.getBlock().getRelative(BlockFace.WEST).getType() == Material.CHEST)) {
				
				e.getPlayer().sendMessage(msg.noPlaceVoidChest());
				e.setCancelled(true);
				return;
			}
			double multi = Double.valueOf(NBT.getNBT(i, "multi"));
			String uuid = e.getPlayer().getUniqueId().toString();
			Location loc = e.getBlockPlaced().getLocation();
			String autopickup = NBT.getNBT(i, "autopickup");
			boolean autopick = false;
			if (autopickup.equalsIgnoreCase("Enabled")) autopick = true;

			VoidChests.placeChest(uuid, loc, multi, autopick);
			e.getPlayer().sendMessage(msg.placedVoidChest());
		} else {
			return;
		}
	}

}
