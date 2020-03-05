package de.ardania.jan.ardashops.commands;

import de.ardania.jan.ardashops.entities.Item;
import de.ardania.jan.ardashops.entities.Shop;
import de.ardania.jan.ardashops.handler.DatabaseHandler;
import de.ardania.jan.ardashops.util.ActiveInvInfoData;
import de.ardania.jan.ardashops.util.InventoryUtil;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import static de.ardania.jan.ardashops.Main.PLUGIN;
import static de.ardania.jan.ardashops.Main.activeInvInfoDataMap;

public class EditCommand extends DatabaseHandler {

    public Inventory openEditInventory(int shopID, Player player) {
        ActiveInvInfoData activeInvInfoData = ActiveInvInfoData.builder().build();
        activeInvInfoData.setShop(getShopWithItems(shopID));
        activeInvInfoData.setChangePriceInv(InventoryUtil.initPriceInv(shopID));
        activeInvInfoData.setShopInv(getShopEditInv(activeInvInfoData.getShop()));

        activeInvInfoDataMap.put(player, activeInvInfoData);

        return activeInvInfoData.getShopInv();
    }

    private Inventory getShopEditInv(Shop shop) {
        Inventory shopInv = Bukkit.createInventory(null, 6 * 9, shop.getShopID() + "-Edit");
        for (Item i :
                shop.getItems()) {
            shopInv.setItem(i.getSlotInInv(), i.getItem());
        }
        return shopInv;
    }
}
