package com.rcallum.CalEcoTools.Utils;

import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class NBT {
	public static ItemStack setNBT(ItemStack item, String key, String value) {
		net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound compound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		compound.setString(key, value);
		nmsItem.setTag(compound);
		ItemStack output = CraftItemStack.asBukkitCopy(nmsItem);
		return output;
	}

	public static String getNBT(ItemStack item, String key) {
		net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound compound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		if (!compound.hasKey(key)) {
			return "";
		}
		return compound.getString(key);
	}

	public static boolean hasNBT(ItemStack item, String key) {
		net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		try {
			NBTTagCompound compound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
			if (compound.hasKey(key)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {

		}
		return false;
	}
}
