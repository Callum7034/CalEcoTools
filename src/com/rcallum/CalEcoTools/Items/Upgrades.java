package com.rcallum.CalEcoTools.Items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.rcallum.CalEcoTools.Utils.NBT;

public class Upgrades {
	public static ItemStack getVCUpgrade(double multi) {
		ItemStack VoidChest = new ItemStack(Material.INK_SACK, 1, (short) 14);
		VoidChest.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		String name = "&6&l[!] &e&lMultiplier Upgrade &9" + multi + "x";
		List<String> lore = new ArrayList<String>();

		String ss = c("&eMultiplier: &3%multiplier%x");
		ss = ss.replaceAll("%multiplier%", multi + "");
		lore.add(ss);
		lore.add(c("  &7-----------------------------------"));
		lore.add(c("    &a- Right click this upgrade on a Void Chest"));
		lore.add(c("              &ato set the new sell multiplier"));
		lore.add(c("  &7-----------------------------------"));
		ItemMeta im = VoidChest.getItemMeta();
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		im.setLore(lore);
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		VoidChest.setItemMeta(im);
		VoidChest = NBT.setNBT(VoidChest, "upgradeMulti", multi+"");
		return NBT.setNBT(VoidChest, "multi", multi + "");
	}
	
	public static ItemStack getVCAutoPickup() {
		ItemStack VoidChest = new ItemStack(Material.PRISMARINE_CRYSTALS);
		VoidChest.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		String name = "&6&l[!] &e&lAuto-Pickup Upgrade";
		List<String> lore = new ArrayList<String>();
		lore.add(c("  &7-----------------------------------"));
		lore.add(c("    &a- Right click this upgrade on a Void Chest"));
		lore.add(c("          &ato allow it to automatically pickup items"));
		lore.add(c("  &7-----------------------------------"));
		ItemMeta im = VoidChest.getItemMeta();
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		im.setLore(lore);
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		VoidChest.setItemMeta(im);
		
		VoidChest = NBT.setNBT(VoidChest, "upgradeAutoPickup", "true");
		return VoidChest;
	}
	
	public static String c(String i) {
		return ChatColor.translateAlternateColorCodes('&', i);
	}

}
