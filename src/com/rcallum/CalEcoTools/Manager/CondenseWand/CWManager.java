package com.rcallum.CalEcoTools.Manager.CondenseWand;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;

import com.rcallum.CalEcoTools.Items.CondenseWandItem;
import com.rcallum.CalEcoTools.Messages.msg;
import com.rcallum.CalEcoTools.Utils.NBT;

public class CWManager {
	public static void removeUse(Player p) {
		ItemStack item = p.getItemInHand();
		int uses = Integer.valueOf(NBT.getNBT(item, "uses"));
		if (uses > 0) {
			if (uses == 1) {
				p.getInventory().remove(item);
			} else {
				p.setItemInHand(CondenseWandItem.getCondenseWand(uses - 1));
			}
		}
	}
	public static int convertIngots(Player player, Inventory inv) {
		int itemsChanged = 0;
		try {
			int amountOfDiamonds = 0;
			int amountOfEmeralds = 0;
			int amountOfIron = 0;
			int amountOfGold = 0;
			int amountOfGlowstone = 0;
			int coal = 0;
			int redstone = 0;
			int lapis = 0;
			ItemStack[] var18;
			int emeraldsToTransform = (var18 = inv.getContents()).length;
			for (int diamondOverflow = 0; diamondOverflow < emeraldsToTransform; diamondOverflow++) {
				ItemStack is = var18[diamondOverflow];
				if (is != null) {
					if (is.getType() == Material.DIAMOND) {
						inv.remove(is);
						amountOfDiamonds += is.getAmount();
					}
					if (is.getType() == Material.EMERALD) {
						amountOfEmeralds += is.getAmount();
						inv.remove(is);
					}
					if (is.getType() == Material.IRON_INGOT) {
						inv.remove(is);
						amountOfIron += is.getAmount();
					}
					if (is.getType() == Material.GLOWSTONE_DUST) {
						amountOfGlowstone += is.getAmount();
						inv.remove(is);
					}
					if (is.getType() == Material.GOLD_INGOT) {
						inv.remove(is);
						amountOfGold += is.getAmount();
					}
					if (is.getType() == Material.COAL) {
						inv.remove(is);
						coal += is.getAmount();
					}
					if (is.getType() == Material.REDSTONE) {
						redstone += is.getAmount();
						inv.remove(is);
					}
					if ((is.getType() == Material.INK_SACK) && (((Dye) is.getData()).getColor() == DyeColor.BLUE)) {
						inv.remove(is);
						lapis += is.getAmount();
					}
				}
			}
			itemsChanged = amountOfDiamonds + amountOfEmeralds + amountOfGlowstone + amountOfGold + amountOfIron + coal
					+ redstone + lapis;
			int diamondsToTransform = amountOfDiamonds / 9;
			int diamondOverflow = amountOfDiamonds % 9;
			emeraldsToTransform = amountOfEmeralds / 9;
			int emeraldsOverflow = amountOfEmeralds % 9;
			int ironToTransform = amountOfIron / 9;
			int ironOverflow = amountOfIron % 9;
			int goldToTransform = amountOfGold / 9;
			int goldOverflow = amountOfGold % 9;
			int glowstoneToTransform = amountOfGlowstone / 9;
			int glowstoneOverflow = amountOfGlowstone % 9;
			int rT = redstone / 9;
			int rO = redstone % 9;
			int lT = lapis / 9;
			int lO = lapis % 9;
			int cT = coal / 9;
			int cO = coal % 9;
			if (itemsChanged != 0) {
				player.sendMessage(msg.condenseChest().replaceAll("%ingots%", "" + itemsChanged).replaceAll("%blocks%",
						"" + (itemsChanged / 9)));
			} else {
				player.sendMessage(msg.nothingInChestCondense());
			}
			itemsChanged -= diamondOverflow + emeraldsOverflow + ironOverflow + goldOverflow + glowstoneOverflow + rO
					+ cO + lO;
			inv.addItem(new ItemStack[] {
					new ItemStack(diamondsToTransform > 0 ? Material.DIAMOND_BLOCK : Material.AIR, diamondsToTransform),
					new ItemStack(emeraldsToTransform > 0 ? Material.EMERALD_BLOCK : Material.AIR, emeraldsToTransform),
					new ItemStack(diamondOverflow > 0 ? Material.DIAMOND : Material.AIR, diamondOverflow),
					new ItemStack(emeraldsOverflow > 0 ? Material.EMERALD : Material.AIR, emeraldsOverflow),
					new ItemStack(ironToTransform > 0 ? Material.IRON_BLOCK : Material.AIR, ironToTransform),
					new ItemStack(goldToTransform > 0 ? Material.GOLD_BLOCK : Material.AIR, goldToTransform),
					new ItemStack(glowstoneToTransform > 0 ? Material.GLOWSTONE : Material.AIR, glowstoneToTransform),
					new ItemStack(ironOverflow > 0 ? Material.IRON_INGOT : Material.AIR, ironOverflow),
					new ItemStack(goldOverflow > 0 ? Material.GOLD_INGOT : Material.AIR, goldOverflow),
					new ItemStack(glowstoneOverflow > 0 ? Material.GLOWSTONE_DUST : Material.AIR, glowstoneOverflow) });
			inv.addItem(new ItemStack[] { new ItemStack(rT > 0 ? Material.REDSTONE_BLOCK : Material.AIR, rT) });
			inv.addItem(new ItemStack[] { new ItemStack(lT > 0 ? Material.LAPIS_BLOCK : Material.AIR, lT) });
			inv.addItem(new ItemStack[] { new ItemStack(cT > 0 ? Material.COAL_BLOCK : Material.AIR, cT) });
			inv.addItem(new ItemStack[] { new ItemStack(rO > 0 ? Material.REDSTONE : Material.AIR, rO) });
			inv.addItem(new ItemStack[] { new ItemStack(lO > 0 ? Material.INK_SACK : Material.AIR, lO, (short) 4) });
			inv.addItem(new ItemStack[] { new ItemStack(cO > 0 ? Material.COAL : Material.AIR, cO) });
		} catch (Exception var31) {
			var31.printStackTrace();
		}
		removeUse(player);
		return itemsChanged;
	}
}
