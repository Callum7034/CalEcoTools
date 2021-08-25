package com.rcallum.CalEcoTools.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.rcallum.CalEcoTools.Messages.msg;

public class MainCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("CalEcoTools")) {
			if (args.length == 1) {
				if (!sender.hasPermission("cet.admin")) {
					sender.sendMessage(msg.noPerm());
					return false;
				}
				if (args[0].equalsIgnoreCase("stats")) {

					for (String s : StatisticsGrabber.getStats()) {
						sender.sendMessage(s);
					}
					return false;
				}
			}
			if (args.length > 0) {

				if (args.length == 2) {
					if (args[0].equalsIgnoreCase("help")) {
						if (args[1].equalsIgnoreCase("2")) {
							sendHelpTwo(sender);
							return false;
						}
					}
				}
				if (args[0].equalsIgnoreCase("help")) {
					sendHelpOne(sender);
					return false;
				}
			}
			if (sender.hasPermission("cet.admin")) {
				sendHelpOne(sender);

			} else {
				sender.sendMessage(msg.noPerm());
			}

		}
		return false;
	}

	public void sendHelpOne(CommandSender sender) {
		sender.sendMessage(color("&7&m----------&r &a&lCalEcoTools &bCommands &7&m----------"));
		sender.sendMessage(color("  &b/voidchest give {player} {multiplier} {pickup: true/false}"));
		sender.sendMessage(color("        &7- Gives player void chest"));
		sender.sendMessage(color("  &b/sellwand give {player} {multiplier} {uses}"));
		sender.sendMessage(color("        &7- Gives player sell wand"));
		sender.sendMessage(color("  &b/harvesterhoe give {player}"));
		sender.sendMessage(color("        &7- Gives player harvester hoe"));
		sender.sendMessage(color("  &b/condensewand give {player} {uses}"));
		sender.sendMessage(color("        &7- Gives player condense wand"));
		sender.sendMessage(color("  &7&oTo make wands infinite set uses to -1"));
		sender.sendMessage(color(" "));
		sender.sendMessage(color("  &b/autosell"));
		sender.sendMessage(color("        &7- (( Yet to be Added ))"));
		sender.sendMessage(color(" "));
		sender.sendMessage(color("  &b/cet stats"));
		sender.sendMessage(color("        &7- Shows CET stats"));
		sender.sendMessage(color("  &a&o/cet help 2 for more"));
		sender.sendMessage(color("&7&m-------------&r &aMade by CallumR &7&m-------------"));
	}

	public void sendHelpTwo(CommandSender sender) {
		sender.sendMessage(color("&7&m----------&r &a&lCalEcoTools &bCommands &7&m----------"));
		sender.sendMessage(color("  &b/vcupgrade give {player} {multiplier}"));
		sender.sendMessage(color("        &7- Gives player void chest multiplier upgrade"));
		sender.sendMessage(color("  &b/pickupupgrade give {player}"));
		sender.sendMessage(color("        &7- Gives player void chest pickup upgrade"));
		sender.sendMessage(color(" "));
		sender.sendMessage(color("  &b/cet help (page)"));
		sender.sendMessage(color("        &7- Shows this Page "));
		sender.sendMessage(color("&7&m-------------&r &aMade by CallumR &7&m-------------"));
	}

	public String color(String i) {
		return ChatColor.translateAlternateColorCodes('&', i);
	}

}
