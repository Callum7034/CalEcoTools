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
import com.rcallum.CalEcoTools.Manager.HarvesterHoeManager;
import com.rcallum.CalEcoTools.Utils.NBT;

public class HarvesterHoeItem {
	
	public static ItemStack getHarvesterHoe() {
		FileConfiguration config = CalEcoTools.harvesterConfig;
		ItemStack HarvesterHoe = new ItemStack(Material.getMaterial(config.getString("ItemType")));
		if (config.getBoolean("Glow")) HarvesterHoe.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		String name = config.getString("ItemName");
		List<String> lore = new ArrayList<String>();
		for (String s : config.getStringList("Lore")) {
			String ss = ChatColor.translateAlternateColorCodes('&', s);
			lore.add(ss);
		}
		ItemMeta im = HarvesterHoe.getItemMeta();
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		im.setLore(lore);
		HarvesterHoe.setItemMeta(im);
		HarvesterHoe = NBT.setNBT(HarvesterHoe, "HarvesterHoe", "true");
		HarvesterHoe = NBT.setNBT(HarvesterHoe, "autosell", "false");
		HarvesterHoe = NBT.setNBT(HarvesterHoe, "Autosell", "0");
		HarvesterHoe = NBT.setNBT(HarvesterHoe, "Cyborg", "0");
		HarvesterHoe = NBT.setNBT(HarvesterHoe, "DropBooster", "0");
		HarvesterHoe = NBT.setNBT(HarvesterHoe, "KeyFinder", "0");
		HarvesterHoe = NBT.setNBT(HarvesterHoe, "MobcoinFinder", "0");
		HarvesterHoe = NBT.setNBT(HarvesterHoe, "SellMulti", "0");
		HarvesterHoe = NBT.setNBT(HarvesterHoe, "TokenBooster", "0");
		HarvesterHoe = NBT.setNBT(HarvesterHoe, "HoeID", HarvesterHoeManager.getInstance().getNewID()+"");
		
		return HarvesterHoe;
	}

}
