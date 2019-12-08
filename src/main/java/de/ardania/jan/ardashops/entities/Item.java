package de.ardania.jan.ardashops.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
@ToString
public class Item {
    private ItemStack item;
    private int amountToSell;
    private int priceToSell;
    private int amountInStorage;
    private int slotInInv;
    private int pageInInv;
    private int shopID;
}
