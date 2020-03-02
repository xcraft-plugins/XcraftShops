package de.ardania.jan.ardashops.util;

import de.ardania.jan.ardashops.entities.Shop;
import lombok.Builder;
import lombok.Data;
import org.bukkit.inventory.Inventory;

@Data
@Builder
public class ActiveInvInfoData {
    private Shop shop;
    private Inventory shopInv;
    private Inventory changePriceInv;
    private Inventory buyAndSellInv;
}
