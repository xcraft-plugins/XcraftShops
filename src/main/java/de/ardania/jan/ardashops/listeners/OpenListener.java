package de.ardania.jan.ardashops.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class OpenListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getClickedInventory().getType() == InventoryType.PLAYER) {
            if (event.getCurrentItem() != null) {
                player.sendMessage("IN");
            }
        }

        if (!event.getView().getTitle().equalsIgnoreCase(player.getUniqueId().toString())) {
            return;
        } else {
            if (event.getCurrentItem() == null) {
                return;
            }
            if (event.getClickedInventory().getType() == InventoryType.CHEST) {
                event.setCancelled(true);
            }
        }
        if (event.getClick().equals(ClickType.NUMBER_KEY)) {
            event.setCancelled(true);


        }
    }
}