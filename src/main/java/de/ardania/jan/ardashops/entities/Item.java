package de.ardania.jan.ardashops.entities;

import org.bukkit.inventory.ItemStack;

public class Item {
    private ItemStack item;
    private int amountToSell;
    private int priceToSell;
    private int amountInStorage;
    private int slotInInv;

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public int getAmountToSell() {
        return amountToSell;
    }

    public void setAmountToSell(int amountToSell) {
        this.amountToSell = amountToSell;
    }

    public int getPriceToSell() {
        return priceToSell;
    }

    public void setPriceToSell(int priceToSell) {
        this.priceToSell = priceToSell;
    }

    public int getAmountInStorage() {
        return amountInStorage;
    }

    public void setAmountInStorage(int amountInStorage) {
        this.amountInStorage = amountInStorage;
    }

    public int getSlotInInv() {
        return slotInInv;
    }

    public void setSlotInInv(int slotInInv) {
        this.slotInInv = slotInInv;
    }

    @Override
    public String toString() {
        return "Item{" +
                "item=" + item +
                ", amountToSell=" + amountToSell +
                ", priceToSell=" + priceToSell +
                ", amountInStorage=" + amountInStorage +
                ", slotInInv=" + slotInInv +
                '}';
    }
}
