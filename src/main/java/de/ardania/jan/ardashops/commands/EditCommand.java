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
import org.bukkit.inventory.Inventory;

public class EditCommand extends DatabaseHandler implements Listener {
    private static Inventory currentEditInv;
    private static Shop currentEditShop;

    public Inventory openEditInventory(int shopID) {
        currentEditShop = getShopWithItems(shopID);

        currentEditInv = Bukkit.createInventory(null, 6 * 9, shopID + "-Edit");

        if (!currentEditShop.getItems().isEmpty()) {
            for (Item item :
                    currentEditShop.getItems()) {
                currentEditInv.setItem(item.getSlotInInv(), item.getItem());
            }
        }

        return currentEditInv;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        //Überprüfung ob das offene Inv der Shop ist.
        if (!event.getView().getTitle().equals(currentEditShop.getShopID() + "-Edit")) return;

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
                //TODO Transactions oder anders gesagt...Wenn der Spieler ein Item zum kaufen anklickt
            }
        }
        //Überprüft ob Spieler auf das eigene inv geklickt hat.
        if (event.getClickedInventory().equals(event.getView().getBottomInventory())) {
            //Überprüft ob der Spieler ein Item mittels Shift Linksklick verschieben will
            if (event.getClick().isShiftClick()) event.setCancelled(true);
        }
        //Überprüfung ob der Spieler eine Zahl drückt um somit Items zwischen den Invs zu tauschen.
        if (event.getClick().equals(ClickType.NUMBER_KEY)) event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!event.getView().getTitle().equals(currentEditShop.getShopID() + "-Edit")) {
            return;
        }
        event.setCancelled(true);
    }

}
