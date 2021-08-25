package com.rcallum.CalEcoTools.Events.HarvesterHoe;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.rcallum.CalEcoTools.GUI.GUIOpener;
import com.rcallum.CalEcoTools.Utils.NBT;

public class OpenHoeGUI implements Listener{
	
	@EventHandler
	public void onShiftRightClick(PlayerInteractEvent e) {
		if (e.getPlayer().isSneaking()) {
			if ((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
				if (NBT.hasNBT(e.getPlayer().getItemInHand(), "HarvesterHoe")) {
					GUIOpener.getInstance().openHarvesterHoeGUI(e.getPlayer().getItemInHand(), e.getPlayer());
				}
			}
		}
	}

}
