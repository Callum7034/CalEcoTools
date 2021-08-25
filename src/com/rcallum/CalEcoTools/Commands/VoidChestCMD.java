package com.rcallum.CalEcoTools.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rcallum.CalEcoTools.Messages.msg;

public class VoidChestCMD implements CommandExecutor{
///voidchest give {player} {multiplier}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("voidchest")) {
			if (sender.hasPermission("cet.givevoidchest")) {
				if (args.length == 4) {
					if (args[0].equalsIgnoreCase("give")) {
						String player = args[1];
						double multi = Double.valueOf(args[2]);
						Player target = Bukkit.getPlayer(player);
						if (target == null) {
							sender.sendMessage(msg.noPlayer());
							return false;
						}
						boolean pic = false;
						if (args[3].equalsIgnoreCase("true")) {
							pic = true;
						}
						if (args[3].equalsIgnoreCase("false")) {
							pic = false;
						}
						if (args[3].equalsIgnoreCase("t"))pic = true;
						if (args[3].equalsIgnoreCase("y"))pic = true;
						if (args[3].equalsIgnoreCase("yes"))pic = true;
						target.getInventory().addItem(com.rcallum.CalEcoTools.Items.VoidChestItem.getVoidChest(multi, pic));
						sender.sendMessage(msg.sentVoidChest());
						target.sendMessage(msg.recievedVoidChest());
						return true;
					}
				}
			} else {
				sender.sendMessage(msg.noPerm());
				return false;
			}
			sender.sendMessage(color("&b/voidchest give {player} {multiplier} {pickup: true/false}"));
		}
		return false;
	}
	public String color(String i) {
		return ChatColor.translateAlternateColorCodes('&', i);
	}
}
