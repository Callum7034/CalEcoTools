package com.rcallum.CalEcoTools.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rcallum.CalEcoTools.Items.HarvesterHoeItem;
import com.rcallum.CalEcoTools.Messages.msg;

public class HarvesterHoeCMD implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("harvesterhoe")) {
			if (sender.hasPermission("cet.harvesterhoe")) {
				if (args.length == 2) {
					if (args[0].equalsIgnoreCase("give")) {
						String player = args[1];
						Player target = Bukkit.getPlayer(player);
						if (target == null) {
							sender.sendMessage(msg.noPlayer());
							return false;
						}
						target.getInventory().addItem(HarvesterHoeItem.getHarvesterHoe());
						sender.sendMessage(msg.sentHarvesterHoe());
						target.sendMessage(msg.recievedHarvesterHoe());
						return true;
					}
				}
			} else {
				sender.sendMessage(msg.noPerm());
				return false;
			}
			sender.sendMessage(color("&b/harvesterhoe give {player}"));
		}
		return false;
	}

	public String color(String i) {
		return ChatColor.translateAlternateColorCodes('&', i);
	}

}
