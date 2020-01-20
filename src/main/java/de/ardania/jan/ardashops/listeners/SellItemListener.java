package de.ardania.jan.ardashops.listeners;

import de.ardania.jan.ardashops.util.ListenerUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import static de.ardania.jan.ardashops.Main.activeInvInfoDataMap;

public class SellItemListener implements Listener {
    @EventHandler
    public void onItemSellEvent(InventoryClickEvent event) {
        ListenerUtil.basicInventoryClickEventChecks(event);
        if (activeInvInfoDataMap.containsKey(event.getWhoClicked())) {
            //TODO: Functionality to Sell Items(maybe open merge Buy and Sell Functionality into one Inv)
        }

    }
}
