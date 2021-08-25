package com.rcallum.CalEcoTools.Placeholder;

import org.bukkit.entity.Player;

import com.rcallum.CalEcoTools.CalEcoTools;
import com.rcallum.CalEcoTools.Data.CaneTokens;
import com.rcallum.CalEcoTools.Manager.HarvesterHoeManager;
import com.rcallum.CalEcoTools.Utils.NumberFormatter;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PapiHook extends PlaceholderExpansion {

	private CalEcoTools plugin;

	/**
	 * Since we register the expansion inside our own plugin, we can simply use this
	 * method here to get an instance of our plugin.
	 *
	 * @param plugin The instance of our plugin.
	 */
	public PapiHook(CalEcoTools plugin) {
		this.plugin = plugin;
	}

	/**
	 * Because this is an internal class, you must override this method to let
	 * PlaceholderAPI know to not unregister your expansion class when
	 * PlaceholderAPI is reloaded
	 *
	 * @return true to persist through reloads
	 */
	@Override
	public boolean persist() {
		return true;
	}

	/**
	 * Because this is a internal class, this check is not needed and we can simply
	 * return {@code true}
	 *
	 * @return Always true since it's an internal class.
	 */
	@Override
	public boolean canRegister() {
		return true;
	}

	/**
	 * 
	 * @return The name of the author as a String.
	 */
	@Override
	public String getAuthor() {
		return plugin.getDescription().getAuthors().toString();
	}

	/**
	 * 
	 * @return The identifier in {@code %<identifier>_<value>%} as String.
	 */
	@Override
	public String getIdentifier() {
		return "cet";
	}

	/**
	 * @return The version as a String.
	 */
	@Override
	public String getVersion() {
		return plugin.getDescription().getVersion();
	}

	/**
	 * @param player     A {@link org.bukkit.Player Player}.
	 * @param identifier A String containing the identifier/value.
	 *
	 * @return possibly-null String of the requested identifier.
	 */
	@Override
	public String onPlaceholderRequest(Player player, String identifier) {

		if (player == null) {
			return "";
		}

		// %cet_canetokens%
		if (identifier.equals("canetokens")) {
			return CaneTokens.getInstance().getTokens(player) + "";
		}

		// %cet_canetokens_formatted%
		if (identifier.equals("canetokens_formatted")) {
			return NumberFormatter.caneNumberFormatter(CaneTokens.getInstance().getTokens(player));
		}

		// %cet_canebroken%
		if (identifier.equals("canebroken")) {
			return HarvesterHoeManager.getInstance().getPlayerRawCane(player) + "";
		}
		return null;
	}
}