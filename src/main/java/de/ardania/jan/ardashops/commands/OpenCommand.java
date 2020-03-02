package de.ardania.jan.ardashops.commands;

import de.ardania.jan.ardashops.entities.Item;
import de.ardania.jan.ardashops.entities.Shop;
import de.ardania.jan.ardashops.handler.DatabaseHandler;
import de.ardania.jan.ardashops.util.ActiveInvInfoData;
import de.ardania.jan.ardashops.util.InventoryUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import static de.ardania.jan.ardashops.Main.activeInvInfoDataMap;

public class OpenCommand extends DatabaseHandler {

    public Inventory openShopInventory(int shopID, Player player) {
        ActiveInvInfoData activeInvInfoData = ActiveInvInfoData.builder().build();
        activeInvInfoData.setShop(getShopWithItems(shopID));
        activeInvInfoData.setShopInv(getShopInv(activeInvInfoData.getShop()));
        activeInvInfoData.setBuyAndSellInv(InventoryUtil.initBuyAndSellInv(shopID));

        activeInvInfoDataMap.put(player, activeInvInfoData);

        return activeInvInfoData.getShopInv();
    }

    private Inventory getShopInv(Shop shop) {
        Inventory shopInv = Bukkit.createInventory(null, 6 * 9, Integer.toString(shop.getShopID()));
        for (Item i :
                shop.getItems()) {
            shopInv.setItem(i.getSlotInInv(), i.getItem());
        }
        return shopInv;
    }
}
