package de.ardania.jan.ardashops.util;

import de.ardania.jan.ardashops.entities.Item;
import de.ardania.jan.ardashops.entities.Shop;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.sql.*;
import java.util.*;

import static de.ardania.jan.ardashops.Main.DB;
import static de.ardania.jan.ardashops.Main.LOGGER;

public class DatabaseData {

    private List<Item> getItemList(ResultSet resultSet) throws SQLException {
        Item item = null;
        List<Item> items = new ArrayList<>();
        while (resultSet.next()) {
            item = new Item();
            item.setSlotInInv(resultSet.getInt("slotInInv"));
            item.setPriceToSell(resultSet.getInt("priceToSell"));
            item.setAmountToSell(resultSet.getInt("amountToSell"));
            item.setAmountInStorage(resultSet.getInt("amountInStorage"));
            item.setItem(getItemStackFromSQL(resultSet));
            items.add(item);
        }
        return items;
    }

    private ItemStack getItemStackFromSQL(ResultSet resultSet) throws SQLException {
        ItemStack itemStack = SerializersAndDeserializers.deserializeByteArrayToObject(resultSet.getBytes("itemStack"), ItemStack.class);
        itemStack.getItemMeta().setDisplayName(resultSet.getString("itemDisplayName"));
        itemStack.getItemMeta().setLore(Arrays.asList(resultSet.getString("itemLore")));
        return itemStack;
    }

    public Shop getShopFromUUID(UUID ownerUUID) throws SQLException {
        ResultSet resultSet = DB.query("SELECT i.amountToSell, i.priceToSell, i.amountInStorage, i.slotInInv, s.ownerUUID, i.itemStack, i.itemDisplayName, i.itemLore, s.location FROM item i INNER JOIN shop s ON s.ownerUUID=i.ownerUUID WHERE i.ownerUUID = '" + ownerUUID + "';");
        Shop shop = new Shop();
        shop.setOwnerUUID(SerializersAndDeserializers.deserializeByteArrayToObject(resultSet.getBytes("ownerUUID"), UUID.class));
        shop.setLocation(SerializersAndDeserializers.deserializeByteArrayToObject(resultSet.getBytes("location"), Location.class));
        shop.setItems(getItemList(resultSet));
        return shop;
    }

    public void insertShop (Shop shop) throws SQLException {
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement("INSERT INTO shop(ownerUUID,location) VALUES(?, ?)");
        preparedStatement.setBytes(1, SerializersAndDeserializers.serializeObjectToByteArray(shop.getOwnerUUID()));
        preparedStatement.setBytes(2, SerializersAndDeserializers.serializeObjectToByteArray(shop.getLocation()));
        int rows = preparedStatement.executeUpdate();
        if (rows != 1) LOGGER.info("Insert into shop failed!");
    }

    //TODO Foreach to insert all items from Shop.getItems and Shop.getOwnerUUID
    public void insertItem (Item item, UUID ownerUUID) throws SQLException {
        PreparedStatement preparedStatement = DB.getConnection().prepareStatement("INSERT INTO item (amountToSell, priceToSell, amountInStorage, slotInInv, ownerUUID, itemStack, itemDisplayName, itemLore) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setInt(1, item.getAmountToSell());
        preparedStatement.setInt(2, item.getPriceToSell());
        preparedStatement.setInt(3, item.getAmountInStorage());
        preparedStatement.setInt(4, item.getSlotInInv());
        preparedStatement.setBytes(5, SerializersAndDeserializers.serializeObjectToByteArray(ownerUUID));
        preparedStatement.setBytes(6, SerializersAndDeserializers.serializeObjectToByteArray(item.getItem()));
        preparedStatement.setBytes(7, SerializersAndDeserializers.serializeObjectToByteArray(item.getItem().getItemMeta().getDisplayName()));
        preparedStatement.setBytes(8, SerializersAndDeserializers.serializeObjectToByteArray(item.getItem().getItemMeta().getLore()));
    }
}
