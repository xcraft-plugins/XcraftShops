package de.ardania.jan.ardashops.listeners;

import de.ardania.jan.ardashops.util.ListenerUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import static de.ardania.jan.ardashops.Main.activeInvInfoDataMap;

public class BuyAndSellInvListener implements Listener {
    @EventHandler
    public void onItemBuyOrSellClickEvent(InventoryClickEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase(activeInvInfoDataMap.get(event.getWhoClicked()).getShop().getShopID() + "-BuyOrSell")) {
            ListenerUtil.basicInventoryClickEventChecks(event);
        }

    }
}
