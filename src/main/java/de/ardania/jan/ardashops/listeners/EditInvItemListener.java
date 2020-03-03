package de.ardania.jan.ardashops.listeners;

import de.ardania.jan.ardashops.util.ListenerUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import static de.ardania.jan.ardashops.Main.activeInvInfoDataMap;

public class EditInvItemListener implements Listener {
    @EventHandler
    public void onEditInvItemClick(InventoryClickEvent event) {
        //TODO: Functionality to add new Items to Shop and if so open changePriceInv
        //TODO: Check if click was on Item already existing

        if (!activeInvInfoDataMap.containsKey(event.getWhoClicked())) {
            return; //Überprüft ob der Spieler einen Shop offen hat.
        }

        if (!event.getView().getTitle().equals(activeInvInfoDataMap.get(event.getWhoClicked()).getShop().getShopID() + "-Edit")) {
            return; //Überprüft ob der Spieler sich im EditShopInv befindet und sonst in keinem anderen.
        }

        //TODO Ifs in methode mit return boolean und dann set und return if true.
        if (event.isShiftClick()) { //Überprüft ob der Spieler ein Item mittels Shift Click bewegen wollte.
            event.setCancelled(true);
            return;
        }

        if (event.getRawSlot() == -1 && event.getCursor() != null) { //Überprüft ob der Spieler mit einem Item am Cursor auf den Rand des Inventars klickt.
            event.setCancelled(true);
            return;
        }

        if (event.getRawSlot() == -999) { //Überprüft ob der Spieler auf den Bereich neben den Inventorys klickt.
            event.setCancelled(true);
            return;
        }

        if (event.getClick().equals(ClickType.NUMBER_KEY)) { //Überprüfung ob der Spieler eine Zahl drückt um somit Items zwischen den Invs zu tauschen.
            event.setCancelled(true);
            return;
        }

        if (event.getClickedInventory().getType().equals(InventoryType.PLAYER)) { //Überprüft ob der Spieler in sein Inventar klickt.
            if (event.getCurrentItem() == null) return;

            if (event.getCursor().getType() == Material.AIR && event.getCurrentItem() == null) {
                return;
            } else if (event.getCursor().getType() == Material.AIR && event.getCurrentItem() != null) {
                activeInvInfoDataMap.get(event.getWhoClicked()).setItemInHand(event.getCurrentItem());
                activeInvInfoDataMap.get(event.getWhoClicked()).setSlotInInv(event.getSlot());
            } else if (event.getCursor().getType() != Material.AIR && event.getCurrentItem() == null) {
                activeInvInfoDataMap.get(event.getWhoClicked()).setItemInHand(null);
                activeInvInfoDataMap.get(event.getWhoClicked()).setSlotInInv(-1);
            } else if (event.getCursor().getType() != Material.AIR && event.getCurrentItem() != null) {
                activeInvInfoDataMap.get(event.getWhoClicked()).setItemInHand(event.getCurrentItem());
                activeInvInfoDataMap.get(event.getWhoClicked()).setSlotInInv(event.getSlot());
            } else if (event.getCursor().getType() != Material.AIR && event.getCurrentItem() != null && event.getCursor().getType() == event.getCurrentItem().getType()) {
                event.setCancelled(true);
                return;
            }


        } else if (event.getClickedInventory().getType().equals(InventoryType.CHEST)) {
            return;
        }
    }
}
