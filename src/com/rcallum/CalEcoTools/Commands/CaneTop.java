package com.rcallum.CalEcoTools.Commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.rcallum.CalEcoTools.CalEcoTools;
import com.rcallum.CalEcoTools.Utils.NumberFormatter;

public class CaneTop implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("canetop")) {
			// Send CaneTop Message
			sendCaneTopMessage(sender);
		}
		return false;
	}

	public void sendCaneTopMessage(CommandSender s) {
		Map<String, Double> sorted = sortByValue(getAllCaneData(), false);
		List<String> message = new ArrayList<>();
		message.add(c("&9&m-----------------------------------------------"));
		message.add(c("                  &b&lCaneTop"));
		for (int i = 0; i < 10; i++) {
			String a = "&3%place%)  &b %player%&f: %cane% cane";
			if (i == 9) {
				a = "&3%place%) &b %player%&f: %cane% cane";
			}
			int abas = i;
			a = a.replaceAll("%place%", "" + (abas + 1));
			Object[] ss = sorted.keySet().toArray();
			if (ss.length < i+1) {
				continue;
			}
			a = a.replaceAll("%player%", ss[i].toString());
			a = a.replaceAll("%cane%", NumberFormatter.withLargeIntegers(sorted.get(ss[i].toString())));
			message.add(c(a));
		}
		if (s instanceof Player) {
			Player p = (Player) s;
			String a = "&3%place%) &b %player%&f: %cane% cane";
			Object[] ss = sorted.keySet().toArray();
			int place = 0;
			for (Object st : ss) {
				place++;
				String so = st.toString();
				if (so.equalsIgnoreCase(p.getName())) {
					break;
				}
			}
			a = a.replaceAll("%place%", "" + place);
			a = a.replaceAll("%player%", p.getName());
			a = a.replaceAll("%cane%", NumberFormatter.withLargeIntegers(sorted.get(p.getName())));
			if (place > 10) {
				message.add(c(a));
			}
			
		}
		message.add(c("&9&m-----------------------------------------------"));

		for (String msg : message) {
			s.sendMessage(msg);
		}
	}

	public String c(String input) {
		return ChatColor.translateAlternateColorCodes('&', input);
	}

	public Map<String, Double> getAllCaneData() {
		FileConfiguration c = CalEcoTools.hoeData;
		if (!c.contains("PlayerData")) {
			return null;
		}
		ConfigurationSection sec = c.getConfigurationSection("PlayerData");
		Map<String, Double> unSorted = new HashMap<>();
		for (String uuid : sec.getKeys(false)) {
			unSorted.put(sec.getString(uuid + ".IGN"), sec.getDouble(uuid + ".RawCane"));
		}
		return unSorted;
	}

	public static Map<String, Double> sortByValue(Map<String, Double> unsortMap, final boolean order) {
		List<Entry<String, Double>> list = new LinkedList<>(unsortMap.entrySet());

		// Sorting the list based on values
		list.sort((o1, o2) -> order
				? o1.getValue().compareTo(o2.getValue()) == 0 ? o1.getKey().compareTo(o2.getKey())
						: o1.getValue().compareTo(o2.getValue())
				: o2.getValue().compareTo(o1.getValue()) == 0 ? o2.getKey().compareTo(o1.getKey())
						: o2.getValue().compareTo(o1.getValue()));
		return list.stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> b, LinkedHashMap::new));

	}
}
