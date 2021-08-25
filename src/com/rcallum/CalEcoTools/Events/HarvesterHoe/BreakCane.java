package com.rcallum.CalEcoTools.Events.HarvesterHoe;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.rcallum.CalEcoTools.CalEcoTools;
import com.rcallum.CalEcoTools.Data.CaneTokens;
import com.rcallum.CalEcoTools.EconHandler.Deposit;
import com.rcallum.CalEcoTools.Manager.HarvesterHoeManager;
import com.rcallum.CalEcoTools.Manager.HarvesterHoe.UpgradeHoe;
import com.rcallum.CalEcoTools.Manager.HarvesterHoe.UpgradeHoe.Upgrade;
import com.rcallum.CalEcoTools.Statistics.Stats;
import com.rcallum.CalEcoTools.Utils.NBT;

public class BreakCane implements Listener {

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if (e.getBlock() == null)
			return;
		if (e.getBlock().getType() == Material.SUGAR_CANE_BLOCK) {
			if (NBT.hasNBT(e.getPlayer().getItemInHand(), "HarvesterHoe")) {
				if (e.getBlock().getLocation().clone().add(0, -1, 0).getBlock()
						.getType() == Material.SUGAR_CANE_BLOCK) {
					if (e.getBlock().getLocation().clone().add(0, 1, 0).getBlock()
							.getType() == Material.SUGAR_CANE_BLOCK) {
						//CHECK HOW HIGH
						int height = 0;
						Location l = e.getBlock().getLocation();
						List<Block> canes = new ArrayList<>();
						for (int i = 0; i < 255; i++) {
							l.add(0, 1, 0);
							if (l.getBlock().getType() == Material.SUGAR_CANE_BLOCK) {
								canes.add(l.getBlock());
								height++;
							} else {
								break;
							}
						}
						for (int i = canes.size(); i-- > 0; ) {
						    canes.get(i).setType(Material.AIR);
						}
						//IF 2 HIGH:
						e.setCancelled(true);
						breakCaneAddMultis(height+1, e.getPlayer(), e.getPlayer().getItemInHand());
					} else {
						e.setCancelled(true);
						e.getBlock().setType(Material.AIR);
						breakCaneAddMultis(1, e.getPlayer(), e.getPlayer().getItemInHand());
					}
				} else {
					e.setCancelled(true);
				}
			}
		}
	}

	// --------------------------------
	// MULTIPLIERS/ENCHANTS ADDED HERE
	// --------------------------------
	public void breakCaneAddMultis(int CaneBroken, Player p, ItemStack hoe) {
		int dropMulti = UpgradeHoe.getInstance().getLevel(p, Upgrade.DROPS) + 1;
		int tokenMulti = UpgradeHoe.getInstance().getLevel(p, Upgrade.TOKEN) + 1;
		int sellMulti = UpgradeHoe.getInstance().getLevel(p, Upgrade.SELL) + 10;

		CaneTokens.getInstance().addTokens(p, CaneBroken * tokenMulti);
		String autoSellEnabled = NBT.getNBT(hoe, "autosell");
		if (autoSellEnabled.equalsIgnoreCase("true")) {
			// Sell
			double price = CalEcoTools.getInstance().CANEPRICE;
			double sellM = sellMulti / 10;
			price = (price * dropMulti) * sellM;
			Deposit.pay(p.getUniqueId().toString(), price);
			if (CalEcoTools.getInstance().autoSellCane.containsKey(p)) {
				double total = CalEcoTools.getInstance().autoSellCane.get(p);
				CalEcoTools.getInstance().autoSellCane.remove(p);
				CalEcoTools.getInstance().autoSellCane.put(p, total + price);
			} else {
				CalEcoTools.getInstance().autoSellCane.put(p, price);
			}
		} else {
			p.getInventory().addItem(getSugarCaneDrops(CaneBroken, dropMulti));
		}
		mobcoinsAndKeys(p, CaneBroken, hoe);
		HarvesterHoeManager.getInstance().addRawCane(hoe, CaneBroken);
		HarvesterHoeManager.getInstance().addPlayerRawCane(p, CaneBroken);
		HarvesterHoeManager.getInstance().addTotalCane(hoe, CaneBroken * dropMulti);
		Stats.addCaneBreak(CaneBroken);
	}

	public void mobcoinsAndKeys(Player p, int CaneBroken, ItemStack hoe) {
		int mobcoinLVL = UpgradeHoe.getInstance().getLevel(p, Upgrade.MOBCOIN);
		int keyFinderLVL = UpgradeHoe.getInstance().getLevel(p, Upgrade.KEYS);
		Random r = new Random();
		int mobCoinsToGive = 0;
		if (mobcoinLVL > 0) {

			if (mobcoinLVL < 20) {
				int randomNum = r.nextInt(100);
				if (mobcoinLVL > randomNum) {
					mobCoinsToGive++;
				}
			} else {
				//
				int randomNum = r.nextInt(2);
				if (randomNum == 1) {
					mobCoinsToGive++;
				}
				for (int i = 0; i < ((mobcoinLVL - 20) / 2); i++) {
					randomNum = r.nextInt(3);
					if (randomNum == 1) {
						mobCoinsToGive++;
					}
				}
			}
		}
		if (mobCoinsToGive != 0) {
			giveMobCoins(p, mobCoinsToGive);
		}
		if (keyFinderLVL > 0) {
			
			int randomNum = r.nextInt(100);
			
			if (keyFinderLVL < randomNum) {
				giveKey(p);
			}

		}
	}

	public void giveMobCoins(Player p, int amount) {
		String cmd = CalEcoTools.harvesterConfig.getString("mobcoinCommand").replaceAll("%player%",
				p.getName()).replaceAll("%amount%", amount + "");
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd);
	}
	public void giveKey(Player p) {
		String cmd = CalEcoTools.harvesterConfig.getString("keyFinderCommand").replaceAll("%player%",
				p.getName());
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd);
	}

	public ItemStack getSugarCaneDrops(int amount, int dropMulti) {
		ItemStack cane = new ItemStack(Material.SUGAR_CANE);
		cane.setAmount(amount * dropMulti);

		return cane;
	}

}
