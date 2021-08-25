package com.rcallum.CalEcoTools.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.rcallum.CalEcoTools.Holograms.VoidChestHoloManager;

public class Connections implements Listener{
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		VoidChestHoloManager.getInstance().joinEvent(e.getPlayer());
	}
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		VoidChestHoloManager.getInstance().leaveEvent(e.getPlayer());
	}

}
