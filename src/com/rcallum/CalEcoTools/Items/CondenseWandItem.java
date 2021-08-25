package com.rcallum.CalEcoTools.Items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.rcallum.CalEcoTools.CalEcoTools;
import com.rcallum.CalEcoTools.Utils.NBT;

public class CondenseWandItem {
	public static ItemStack getCondenseWand(int uses) {
		FileConfiguration config = CalEcoTools.condenseConfig;
		String name = config.getString("ItemName");
		ItemStack CondenseWand = new ItemStack(Material.getMaterial(config.getString("ItemType")));
		if (config.getBoolean("Glow")) CondenseWand.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		String use = uses+"";
		if (uses <= 0) {
			use = config.getString("infiniteUses");
		}
		List<String> lore = new ArrayList<String>();
		for (String s : config.getStringList("Lore")) {
			String ss = ChatColor.translateAlternateColorCodes('&', s);
			ss = ss.replaceAll("%uses%", use);
			lore.add(ss);
		}
		ItemMeta im = CondenseWand.getItemMeta();
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		im.setLore(lore);
		CondenseWand.setItemMeta(im);
		CondenseWand = NBT.setNBT(CondenseWand, "CondenseWand", "true");
		CondenseWand = NBT.setNBT(CondenseWand, "uses", uses+"");
		return CondenseWand;
	}
}
