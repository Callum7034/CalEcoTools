package com.rcallum.CalEcoTools.Holograms;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.rcallum.CalEcoTools.Manager.VoidChest.VoidChest;

public interface HoloAPI {

    /**
     * Shows this hologram to the given player
     * @param p The player who will see this hologram at the location specified by calling the constructor
     * @return true if the action was successful, else false
     */
    boolean display(Player player);
   
    /**
     * Removes this hologram from the players view
     * @param p The target player
     * @return true if the action was successful, else false (including the try to remove a non-existing hologram)
     */
    boolean destroy(Player player);
    
    VoidChest getVC();
   
    /**
     * Create a new hologram
     * Note: The internal cache will be automatically initialized, it may take some millis
     * @param loc The location where this hologram is shown
     * @param lines The text-lines, from top to bottom, farbcodes are possible
     */
    public static HoloAPI newInstance(Location location, VoidChest vc, String... lines) {
        return newInstance(location, Arrays.asList(lines), vc);
    }
   
    /**
     * Create a new hologram
     * Note: The internal cache will be automatically initialized, it may take some millis
     * @param loc The location where this hologram is shown
     * @param lines The text-lines, from top to bottom, farbcodes are possible
     */
    public static HoloAPI newInstance(Location location, List<String> lines, VoidChest vc) {
        return new DefaultHoloAPI(location, lines, vc);
    }
}