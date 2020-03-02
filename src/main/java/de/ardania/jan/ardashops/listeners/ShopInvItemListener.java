package de.ardania.jan.ardashops.listeners;

import de.ardania.jan.ardashops.util.ListenerUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import static de.ardania.jan.ardashops.Main.activeInvInfoDataMap;

public class ShopInvItemListener implements Listener {
    @EventHandler
    public void onShopInvItemClick(InventoryClickEvent event) {
        if (!activeInvInfoDataMap.containsKey(event.getWhoClicked())) {
            return; //Überprüft ob der Spieler einen Shop offen hat.
        }

        if (!event.getView().getTitle().equals(activeInvInfoDataMap.get(event.getWhoClicked()).getShop().getShopID())) {
            return; //Überprüft ob der Spieler sich im ShopInv befindet und sonst in keinem anderen.
        }

        ListenerUtil.basicInventoryClickEventChecks(event);


    }
}
