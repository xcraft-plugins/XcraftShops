package de.ardania.jan.ardashops.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import static de.ardania.jan.ardashops.Main.activeInvInfoDataMap;

public class CloseInvListener implements Listener {
    @EventHandler
    public void onInvClose(InventoryCloseEvent event) {
        if (activeInvInfoDataMap.containsKey(event.getPlayer())) {
            activeInvInfoDataMap.remove(event.getPlayer());
        }
    }
}
