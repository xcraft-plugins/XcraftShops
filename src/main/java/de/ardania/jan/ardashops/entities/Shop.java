package de.ardania.jan.ardashops.entities;

import org.bukkit.Location;

import java.util.List;
import java.util.UUID;

public class Shop {

    private UUID ownerUUID;
    private Location location;
    private List<Item> items;

    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    public void setOwnerUUID(UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "ownerUUID=" + ownerUUID +
                ", location=" + location +
                ", items=" + items +
                '}';
    }
}
