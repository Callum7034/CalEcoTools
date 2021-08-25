package com.rcallum.CalEcoTools.Items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.rcallum.CalEcoTools.CalEcoTools;
import com.rcallum.CalEcoTools.Utils.NBT;

public class VoidChestItem {
	
	public static ItemStack getVoidChest(double multi, boolean autopickup) {
		ItemStack VoidChest = new ItemStack(Material.CHEST);
		FileConfiguration config = CalEcoTools.voidConfig;
		String name = config.getString("ItemName");
		String Pickup = "";
		if (autopickup) {
			Pickup = "Enabled";
		} else {
			Pickup = "Disabled";
		}
		List<String> lore = new ArrayList<String>();
		for (String s : config.getStringList("Lore")) {
			String ss = ChatColor.translateAlternateColorCodes('&', s);
			ss = ss.replaceAll("%multiplier%", multi+"");
			ss = ss.replaceAll("%autopickup%", Pickup);
			lore.add(ss);
		}
		ItemMeta im = VoidChest.getItemMeta();
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		im.setLore(lore);
		VoidChest.setItemMeta(im);
		VoidChest = NBT.setNBT(VoidChest, "VoidChest", "true");
		VoidChest = NBT.setNBT(VoidChest, "autopickup", Pickup);
		return NBT.setNBT(VoidChest, "multi", multi+"");
	}

}
