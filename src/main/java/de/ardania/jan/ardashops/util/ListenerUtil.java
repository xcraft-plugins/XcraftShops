package de.ardania.jan.ardashops.util;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class ListenerUtil {
    /**
     * Check whether the Player press any {@link ClickType#NUMBER_KEY}
     * Click outside the {@link Inventory}
     * Or between the top and bottom {@link Inventory}
     * Or trys to move Items via Shift Click
     * And deny the action
     *
     * @param event - the event that is triggered
     */
    public static void basicInventoryClickEventChecks(InventoryClickEvent event) {
        if (event.getClick().equals(ClickType.NUMBER_KEY))
            event.setCancelled(true); //Überprüfung ob der Spieler eine Zahl drückt um somit Items zwischen den Invs zu tauschen.

        if (event.getRawSlot() == -999)
            return; //Überprüft ob der Spieler auf den Bereich neben den Inventorys klickt.

        if (event.getRawSlot() == -1 && event.getCursor() != null)
            return; //Überprüft ob der Spieler mit einem Item am Cursor auf den Rand des Inventars klickt.

        //if (event.isShiftClick())
        //    event.setCancelled(true);
    }
}
