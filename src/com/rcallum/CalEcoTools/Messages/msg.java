package com.rcallum.CalEcoTools.Messages;

import org.bukkit.ChatColor;

import com.rcallum.CalEcoTools.CalEcoTools;

public class msg {
	static String prefix = color(CalEcoTools.msg.getString("prefix"));
	
	public static String noPerm() {
		return prefix + color(CalEcoTools.msg.getString("NoPerm"));
	}
	public static String noPlayer() {
		return prefix + color(CalEcoTools.msg.getString("NoPlayer"));
	}
	//Void chests
	public static String sentVoidChest() {
		return prefix + color(CalEcoTools.msg.getString("VoidChestSent"));
	}
	public static String recievedVoidChest() {
		return prefix + color(CalEcoTools.msg.getString("VoidChestRecieved"));
	}
	public static String placedVoidChest() {
		return prefix + color(CalEcoTools.msg.getString("VoidChestPlaced"));
	}
	public static String noPlaceVoidChest() {
		return prefix + color(CalEcoTools.msg.getString("CannotPlaceVoidChest"));
	}
	public static String noBreakVoidChest() {
		return prefix + color(CalEcoTools.msg.getString("CannotBreakVoidChest"));
	}
	public static String notYourVoidChest() {
		return prefix + color(CalEcoTools.msg.getString("NotYourVoidChest"));
	}
	//Upgrades
	public static String sentUpgrade() {
		return prefix + color(CalEcoTools.msg.getString("UpgradeSent"));
	}
	public static String recievedUpgrade() {
		return prefix + color(CalEcoTools.msg.getString("UpgradeRecieved"));
	}
	public static String successfulUpgrade() {
		return prefix + color(CalEcoTools.msg.getString("SuccessfulUpgrade"));
	}
	public static String AlreadyUpgrade() {
		return prefix + color(CalEcoTools.msg.getString("AlreadyUpgrade"));
	}
	
	//Sell Wands
	public static String sentSellWand() {
		return prefix + color(CalEcoTools.msg.getString("SellWandSent"));
	}
	public static String recievedSellWand() {
		return prefix + color(CalEcoTools.msg.getString("SellWandRecieved"));
	}
	public static String nothingInChest() {
		return prefix + color(CalEcoTools.msg.getString("NothingInChest"));
	}
	
	//Condense Wands
	public static String sentCondenseWand() {
		return prefix + color(CalEcoTools.msg.getString("CondenseWandSent"));
	}
	public static String recievedCondenseWand() {
		return prefix + color(CalEcoTools.msg.getString("CondenseWandRecieved"));
	}
	public static String nothingInChestCondense() {
		return prefix + color(CalEcoTools.msg.getString("NothingInChestCondense"));
	}
	public static String condenseChest() {
		return prefix + color(CalEcoTools.msg.getString("CondensedItems"));
	}
	
	//Harvester Hoe
	public static String sentHarvesterHoe() {
		return prefix + color(CalEcoTools.msg.getString("HarvesterHoeSent"));
	}
	public static String recievedHarvesterHoe() {
		return prefix + color(CalEcoTools.msg.getString("HarvesterHoeRecieved"));
	}
	public static String notEnoughTokens() {
		return prefix + color(CalEcoTools.msg.getString("NotEnoughTokens"));
	}
	
	public static String color(String i) {
		return ChatColor.translateAlternateColorCodes('&', i);
	}
}
