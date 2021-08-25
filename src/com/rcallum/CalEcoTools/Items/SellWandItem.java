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

public class SellWandItem {
	
	public static ItemStack getSellWand(double multi, int uses) {
		FileConfiguration config = CalEcoTools.sellwandConfig;
		String name = config.getString("ItemName");
		ItemStack SellWand = new ItemStack(Material.getMaterial(config.getString("ItemType")));
		if (config.getBoolean("Glow")) SellWand.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		String use = uses+"";
		if (uses <= 0) {
			use = config.getString("infiniteUses");
		}
		List<String> lore = new ArrayList<String>();
		for (String s : config.getStringList("Lore")) {
			String ss = ChatColor.translateAlternateColorCodes('&', s);
			ss = ss.replaceAll("%multiplier%", multi+"");
			ss = ss.replaceAll("%uses%", use);
			lore.add(ss);
		}
		ItemMeta im = SellWand.getItemMeta();
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		im.setLore(lore);
		SellWand.setItemMeta(im);
		SellWand = NBT.setNBT(SellWand, "SellWand", "true");
		SellWand = NBT.setNBT(SellWand, "uses", uses+"");
		return NBT.setNBT(SellWand, "multi", multi+"");
	}

}
