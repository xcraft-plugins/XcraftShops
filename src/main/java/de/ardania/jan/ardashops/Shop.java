package de.ardania.jan.ardashops;

import java.util.List;
import java.util.Map;

public class Shop {
    private String shopOwner;
    private Map<String, Integer> location;
    private List<Item> items;

    public String getShopOwner() {
        return shopOwner;
    }

    public void setShopOwner(String shopOwner) {
        this.shopOwner = shopOwner;
    }

    public Map<String, Integer> getLocation() {
        return location;
    }

    public void setLocation(Map<String, Integer> location) {
        this.location = location;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
