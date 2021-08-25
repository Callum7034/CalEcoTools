package com.rcallum.CalEcoTools.ShopHook;

import org.bukkit.inventory.ItemStack;

import net.brcdev.shopgui.ShopGuiPlusApi;

public class GetPrice {
	
	public static double getPrice(ItemStack i) {
		double price = 0;
		
		price = ShopGuiPlusApi.getItemStackPriceSell(i);
		
		return price;
	}
}
