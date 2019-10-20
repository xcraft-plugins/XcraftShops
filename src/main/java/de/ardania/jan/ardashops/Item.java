package de.ardania.jan.ardashops;

import java.util.Map;

public class Item {
    private String id;
    private Map<String, Integer> enchantments;
    private int amountToSell;
    private int priceToSell;
    private int amountInStorage;

    public Map<String, Integer> getEnchantments() {
        return enchantments;
    }

    public void setEnchantments(Map<String, Integer> enchantments) {
        this.enchantments = enchantments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
