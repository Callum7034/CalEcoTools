package com.rcallum.CalEcoTools.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rcallum.CalEcoTools.Items.CondenseWandItem;
import com.rcallum.CalEcoTools.Messages.msg;

public class CondenseWandCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("condensewand")) {
			if (sender.hasPermission("cet.givecondensewand")) {
				if (args.length == 3) {
					if (args[0].equalsIgnoreCase("give")) {
						String player = args[1];
						int uses = Integer.valueOf(args[2]);
						Player target = Bukkit.getPlayer(player);
						if (target == null) {
							sender.sendMessage(msg.noPlayer());
							return false;
						}
						target.getInventory().addItem(CondenseWandItem.getCondenseWand(uses));
						sender.sendMessage(msg.sentCondenseWand());
						target.sendMessage(msg.recievedCondenseWand());
						return true;
					}
				}
			} else {
				sender.sendMessage(msg.noPerm());
				return false;
			}
			sender.sendMessage(color("&b/condensewand give {player} {uses}"));
		}
		return false;
	}

	public String color(String i) {
		return ChatColor.translateAlternateColorCodes('&', i);
	}
}