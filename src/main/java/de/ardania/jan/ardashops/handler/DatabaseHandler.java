package de.ardania.jan.ardashops.handler;

import de.ardania.jan.ardashops.entities.Item;
import de.ardania.jan.ardashops.entities.Shop;
import de.ardania.jan.ardashops.util.SerializersAndDeserializers;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;

import static de.ardania.jan.ardashops.Main.DB;
import static de.ardania.jan.ardashops.Main.LOGGER;
import static de.ardania.jan.ardashops.handler.MessageHandler.MESSAGE;

public class DatabaseHandler {

    private List<Item> getItems(@NotNull ResultSet resultSet) {
        Item item = null;
        List<Item> items = new ArrayList<>();
        while (true) {
            try {
                if (!resultSet.next()) break;
                item = new Item();
                item.setSlotInInv(resultSet.getInt("i_slotInInv"));
                item.setPriceToSell(resultSet.getInt("i_priceToSell"));
                item.setAmountToSell(resultSet.getInt("i_amountToSell"));
                item.setAmountInStorage(resultSet.getInt("i_amountInStorage"));
                item.setPageInInv(resultSet.getInt("i_pageInInv"));
                item.setShopID(resultSet.getInt("i_s_shopID"));
                item.setItem(getItemStackFromSQL(resultSet));
                items.add(item);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, e.toString());
            }
        }
        return items;
    }

    private ItemStack getItemStackFromSQL(@NotNull ResultSet resultSet) throws SQLException {
        ItemStack itemStack = SerializersAndDeserializers.deserializeByteArrayToObject(resultSet.getBytes("i_itemStack"), ItemStack.class);
        return itemStack;
    }

    public Shop getShop(int shopID) {
        ResultSet resultSet = null;
        Shop shop = null;
        try {
            resultSet = DB.query("SELECT * FROM item i INNER JOIN shop s ON s.s_shopID=i.i_s_shopID WHERE  = " + shopID + ";");
            shop = new Shop();
            shop.setOwnerUUID(SerializersAndDeserializers.deserializeByteArrayToObject(resultSet.getBytes("s_o_ownerID"), UUID.class));
            shop.setLocation(SerializersAndDeserializers.deserializeByteArrayToObject(resultSet.getBytes("s_shopLocation"), Location.class));
            shop.setItems(getItems(resultSet));
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString());
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, e.toString());
            }
        }
        return shop;
    }

    public void insertOwner(@NotNull UUID ownerID) {
        PreparedStatement preparedStatement = null;
        int rows = 0;
        try {
            preparedStatement = DB.getConnection().prepareStatement("INSERT INTO owner(o_ownerID) VALUES(?)");
            preparedStatement.setBytes(1, SerializersAndDeserializers.serializeObjectToByteArray(ownerID));
            rows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, MESSAGE.getString("ERROR_OWNER_INSERTION"));
            LOGGER.log(Level.SEVERE, e.toString());
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, MESSAGE.getString("ERROR_STATEMENT_CLOSING"));
                LOGGER.log(Level.SEVERE, e.toString());
            }
        }
        if (rows == 0) LOGGER.info("Insert into owner failed!");
    }

    public void insertShop(@NotNull Shop shop) {
        PreparedStatement preparedStatement = null;
        int rows = 0;
        try {
            preparedStatement = DB.getConnection().prepareStatement("INSERT INTO shop(s_shopLocation, s_o_ownerID) VALUES(?, ?)");
            preparedStatement.setBytes(1, SerializersAndDeserializers.serializeObjectToByteArray(shop.getLocation()));
            preparedStatement.setBytes(2, SerializersAndDeserializers.serializeObjectToByteArray(shop.getOwnerUUID()));
            rows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, MESSAGE.getString("ERROR_SHOP_INSERTION"));
            LOGGER.log(Level.SEVERE, e.toString());
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, MESSAGE.getString("ERROR_STATEMENT_CLOSING"));
                LOGGER.log(Level.SEVERE, e.toString());
            }
        }
        if (rows == 0) LOGGER.info("Insert into shop failed!");
    }

    public void insertItem(@NotNull Item item) {
        PreparedStatement preparedStatement = null;
        int rows = 0;
        try {
            preparedStatement = DB.getConnection().prepareStatement("INSERT INTO item(i_amountToSell, i_amountInStorage, i_priceToSell, i_slotInInv, i_pageInInv, i_itemStack, i_s_shopID) VALUES(?, ?, ?, ?, ?, ?, ?);");
            preparedStatement.setInt(1, item.getAmountToSell());
            preparedStatement.setInt(2, item.getAmountInStorage());
            preparedStatement.setInt(3, item.getPriceToSell());
            preparedStatement.setInt(4, item.getSlotInInv());
            preparedStatement.setInt(5, item.getPageInInv());
            preparedStatement.setBytes(6, SerializersAndDeserializers.serializeObjectToByteArray(item.getItem()));
            preparedStatement.setInt(7, item.getShopID());
            rows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, MESSAGE.getString("ERROR_ITEM_INSERTION"));
            LOGGER.log(Level.SEVERE, e.toString());
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, MESSAGE.getString("ERROR_STATEMENT_CLOSING"));
                LOGGER.log(Level.SEVERE, e.toString());
            }
        }
        if (rows == 0) LOGGER.info("Insert into item failed!");
    }
}
