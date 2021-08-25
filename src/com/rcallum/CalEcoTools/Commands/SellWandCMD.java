package com.rcallum.CalEcoTools.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rcallum.CalEcoTools.Items.SellWandItem;
import com.rcallum.CalEcoTools.Messages.msg;

public class SellWandCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("sellwand")) {
			if (sender.hasPermission("cet.givesellwand")) {
				if (args.length == 4) {
					if (args[0].equalsIgnoreCase("give")) {
						String player = args[1];
						double multi = Double.valueOf(args[2]);
						int uses = Integer.valueOf(args[3]);
						Player target = Bukkit.getPlayer(player);
						if (target == null) {
							sender.sendMessage(msg.noPlayer());
							return false;
						}
						target.getInventory().addItem(SellWandItem.getSellWand(multi, uses));
						sender.sendMessage(msg.sentSellWand());
						target.sendMessage(msg.recievedSellWand());
						return true;
					}
				}
			} else {
				sender.sendMessage(msg.noPerm());
				return false;
			}
			sender.sendMessage(color("&b/sellwand give {player} {multiplier} {uses}"));
		}
		return false;
	}

	public String color(String i) {
		return ChatColor.translateAlternateColorCodes('&', i);
	}
}
