package de.ardania.jan.ardashops.commands;

import de.ardania.jan.ardashops.entities.Item;
import de.ardania.jan.ardashops.entities.Shop;
import de.ardania.jan.ardashops.handler.DatabaseHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.logging.Level;

import static de.ardania.jan.ardashops.Main.LOGGER;

public class OpenCommand extends DatabaseHandler implements Listener {

    private static Inventory currentInv;
    private static Shop currentShop;

    public Inventory openShop(int shopID) {
        currentShop = getShopWithItems(shopID);

        if (currentShop == null) {
            return null;
        }

        currentInv = Bukkit.createInventory(null, 6 * 9, String.valueOf(shopID));

        if (!currentShop.getItems().isEmpty()) {
            for (Item item :
                    currentShop.getItems()) {
                currentInv.setItem(item.getSlotInInv(), item.getItem());
            }
        }


        return currentInv;
    }

    public Item getItemFromShop(int slot) {
        for (Item i :
                currentShop.getItems()) {
            if (i.getSlotInInv() == slot) {
                return i;
            }
        }
        return null;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        //Überprüfung ob das offene Inv der Shop ist.
        if (!event.getView().getTitle().equals(Integer.toString(currentShop.getShopID()))) return;

        //Überprüft ob der Spieler neben dem bereich klickt.
        if (event.getRawSlot() == -999) return;

        //Überprüft ob der Spieler mit einem Item am Cursor auf den Rand des Inventars klickt.
        if (event.getRawSlot() == -1 && event.getCursor() != null) return;

        //Überprüft ob Spieler auf das Shop inv geklickt hat.
        if (event.getClickedInventory().equals(event.getView().getTopInventory())) {
            event.setCancelled(true);
            //Überprüft ob der Spieler Rechtsklick ins inventar macht
            if (event.getClick().isRightClick() && event.getCursor() != null) event.setCancelled(true);

            if (event.getCurrentItem() != null) {
                Item item = getItemFromShop(event.getSlot()); //Das Item auf das geklickt wurde zum kaufen
                //TODO Transactions oder anders gesagt...Wenn der Spieler ein Item zum kaufen anklickt
            }
        }
        //Überprüft ob Spieler auf das eigene inv geklickt hat.
        if (event.getClickedInventory().equals(event.getView().getBottomInventory())) {
            //Überprüft ob der Spieler ein Item mittels Shift Linksklick verschieben will
            if (event.getClick().isShiftClick()) event.setCancelled(true);
            event.setCancelled(true);
        }
        //Überprüfung ob der Spieler eine Zahl drückt um somit Items zwischen den Invs zu tauschen.
        if (event.getClick().equals(ClickType.NUMBER_KEY)) event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!event.getView().getTitle().equals(Integer.toString(currentShop.getShopID()))) {
            return;
        }
        event.setCancelled(true);
    }
}
