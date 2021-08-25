package com.rcallum.CalEcoTools.GUI;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.rcallum.CalEcoTools.GUI.HarvesterHoe.HoeGUI;

public class GUIOpener {
	private static GUIOpener instance;
	public GUIOpener() {
		instance = this;
	}
	public static GUIOpener getInstance() {
		if (instance == null) {
			instance = new GUIOpener();
		}
		return instance;
	}
	
	public void openHarvesterHoeGUI(ItemStack i, Player p) {
		HoeGUI.getInstance().openMainGUI(p, i);
	}
}
