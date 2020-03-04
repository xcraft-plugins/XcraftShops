package de.ardania.jan.ardashops.entities;

import lombok.*;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private int itemID;
    private ItemStack item;
    private int amountToSell;
    private int priceToSell;
    private int amountInStorage;
    private int slotInInv;
    private int pageInInv;
    private int shopID;
}
