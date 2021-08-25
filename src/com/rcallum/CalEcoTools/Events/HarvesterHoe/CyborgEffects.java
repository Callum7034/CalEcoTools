package com.rcallum.CalEcoTools.Events.HarvesterHoe;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.rcallum.CalEcoTools.Manager.HarvesterHoe.UpgradeHoe;
import com.rcallum.CalEcoTools.Manager.HarvesterHoe.UpgradeHoe.Upgrade;
import com.rcallum.CalEcoTools.Utils.NBT;

public class CyborgEffects implements Listener {

	Set<Player> haveHadActive = new HashSet<>();

	@EventHandler
	public void heldItem(PlayerItemHeldEvent e) {
		Player p = e.getPlayer();
		check(p, p.getInventory().getItem(e.getNewSlot()));
	}

	@EventHandler
	public void closeInv(InventoryCloseEvent e) {
		check((Player) e.getPlayer(), e.getPlayer().getItemInHand());
	}
	
	@EventHandler
	public void dropItem(PlayerDropItemEvent e) {
		check((Player) e.getPlayer(), e.getPlayer().getItemInHand());
	}


	public void check(Player p, ItemStack item) {
		if (item == null) {
			checkForEffects(p);
			return;
		}
		if (item.getType() == Material.AIR) {
			checkForEffects(p);
			return;
		}
		if (NBT.hasNBT(item, "HarvesterHoe")) {

			int cyborgLVL = Integer.valueOf(NBT.getNBT(item, UpgradeHoe.getInstance().convertToString(Upgrade.CYBORG)));
			if (cyborgLVL == 0) {
				checkForEffects(p);
			} else {
				haveHadActive.add(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 10000000, cyborgLVL - 1));
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10000000, cyborgLVL - 1));
			}

		} else {
			checkForEffects(p);
		}
	}

	public void checkForEffects(Player p) {
		if (!haveHadActive.contains(p)) {
			return;
		}
		if (p.hasPotionEffect(PotionEffectType.SPEED)) {
			p.removePotionEffect(PotionEffectType.SPEED);
		}
		if (p.hasPotionEffect(PotionEffectType.FAST_DIGGING)) {
			p.removePotionEffect(PotionEffectType.FAST_DIGGING);
		}
		haveHadActive.remove(p);
	}

}
