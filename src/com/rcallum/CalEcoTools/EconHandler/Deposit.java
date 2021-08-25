package com.rcallum.CalEcoTools.EconHandler;

import java.util.UUID;

import org.bukkit.Bukkit;

import com.rcallum.CalEcoTools.CalEcoTools;

import net.milkbowl.vault.economy.Economy;

public class Deposit {
	public static void pay(String uuid, double amount) {
		Economy e = CalEcoTools.getInstance().getEconomy();
		e.depositPlayer(Bukkit.getOfflinePlayer(UUID.fromString(uuid)), amount);
	}
}
