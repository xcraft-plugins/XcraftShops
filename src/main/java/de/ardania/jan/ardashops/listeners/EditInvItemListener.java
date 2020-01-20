package de.ardania.jan.ardashops.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class EditInvItemListener implements Listener {
    @EventHandler
    public void onEditInvItemClick(InventoryClickEvent event) {
        //TODO: Check if Player is in activeShopInvData
        //TODO: Functionality to add new Items to Shop and open new changePriceInv
        //TODO: Check if click was on Item already existing
    }
}
