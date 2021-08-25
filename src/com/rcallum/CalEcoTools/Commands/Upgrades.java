package com.rcallum.CalEcoTools.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rcallum.CalEcoTools.Messages.msg;

public class Upgrades implements CommandExecutor {
	/// voidchest give {player} {multiplier}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("pickupupgrade")) {
			if (sender.hasPermission("cet.giveupgrades")) {
				if (args.length == 2) {
					if (args[0].equalsIgnoreCase("give")) {
						String player = args[1];
						Player target = Bukkit.getPlayer(player);
						if (target == null) {
							sender.sendMessage(msg.noPlayer());
							return false;
						}
						target.getInventory().addItem(com.rcallum.CalEcoTools.Items.Upgrades.getVCAutoPickup());
						// MESSAGES
						sender.sendMessage(msg.sentUpgrade());
						target.sendMessage(msg.recievedUpgrade());
						return true;
					}
				}
			} else {
				sender.sendMessage(msg.noPerm());
				return false;
			}
			sender.sendMessage(color("&b/pickupupgrade give {player}"));
		}
		
		if (command.getName().equalsIgnoreCase("vcupgrade")) {
			if (sender.hasPermission("cet.giveupgrades")) {
				if (args.length == 3) {
					if (args[0].equalsIgnoreCase("give")) {
						String player = args[1];
						double multi = Double.valueOf(args[2]);
						Player target = Bukkit.getPlayer(player);
						if (target == null) {
							sender.sendMessage(msg.noPlayer());
							return false;
						}
						target.getInventory().addItem(com.rcallum.CalEcoTools.Items.Upgrades.getVCUpgrade(multi));
						// MESSAGES
						sender.sendMessage(msg.sentUpgrade());
						target.sendMessage(msg.recievedUpgrade());
						return true;
					}
				}
			} else {
				sender.sendMessage(msg.noPerm());
				return false;
			}
			sender.sendMessage(color("&b/vcupgrade give {player} {multiplier}"));
		}
		return false;
	}

	public String color(String i) {
		return ChatColor.translateAlternateColorCodes('&', i);
	}
}
