package com.rcallum.CalEcoTools.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

import com.rcallum.CalEcoTools.Manager.VoidChest.VCManager;
import com.rcallum.CalEcoTools.Statistics.Stats;
import com.rcallum.CalEcoTools.Utils.NumberFormatter;

public class StatisticsGrabber {
	
	/*
	 * 
	 * STATS HERE
	 * Total void chests
	 * Void chest operations (Total, Last Hour)
	 * HarvesterHoe Cane broken (Total, Last Hour)
	 * Sell Wand uses (Total, Last Hour)
	 * Condense Wand uses (Total, Last Hour)
	 * 
	 */
	public static List<String> getStats(){
		List<String> msg = new ArrayList<>();
		msg.add(c("&9&m-----------------------------------------------"));
		msg.add(c("                &b&lCalEcoTools Statistics"));
		msg.add(" ");
		msg.add(c("> &bTotal Placed Void Chests: &f" + VCManager.getInstance().voidChests.size())); //ADD AMOUNT
		msg.add(c("> &bVoid Chests sell operations: &f" + f(Stats.getVCOpsTotal()) + " &btotal")); //ADD AMOUNT
		msg.add(c("> &bVoid Chests sell operations: &f" + f(Stats.getVCOpsHour()) + " &b(last hour)")); //ADD AMOUNT
		msg.add(" ");
		msg.add(c("> &bCane Broken with HarvesterHoe: &f" + f(Stats.getCaneTotal()) + " &btotal"));
		msg.add(c("> &bCane Broken with HarvesterHoe: &f" + f(Stats.getCaneHour()) + " &b(last hour)"));
		msg.add(" ");
		msg.add(c("> &bSell Wand Uses: &f" + f(Stats.getSellWandTotal()) + " &btotal"));
		msg.add(c("> &bSell Wand Uses: &f" + f(Stats.getSellWandHour()) + " &b(last hour)"));
		msg.add(" ");
		msg.add(c("> &bCondense Wand Uses: &f" + f(Stats.getCondenseWandTotal()) + " &btotal"));
		msg.add(c("> &bCondense Wand Uses: &f" + f(Stats.getCondenseWandHour()) + " &b(last hour)"));
		msg.add(c("&9&m-----------------------------------------------"));
		return msg;
	}
	
	public static String c(String input) {
		return ChatColor.translateAlternateColorCodes('&', input);
	}
	public static String f(double i) {
		return NumberFormatter.withLargeIntegers(i);
	}
}
