package de.ardania.jan.ardashops.listeners;

import de.ardania.jan.ardashops.util.ListenerUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

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

        ListenerUtil.basicInventoryClickEventChecks(event);
    }
}
