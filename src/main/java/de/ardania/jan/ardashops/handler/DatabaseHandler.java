package de.ardania.jan.ardashops.handler;

import de.ardania.jan.ardashops.entities.Item;
import de.ardania.jan.ardashops.entities.Shop;
import de.ardania.jan.ardashops.util.SerializersAndDeserializers;
import lombok.extern.log4j.Log4j2;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static de.ardania.jan.ardashops.Main.DB;
import static de.ardania.jan.ardashops.handler.MessageHandler.MESSAGE;

@Log4j2
public class DatabaseHandler {

    @NotNull
    private List<Item> getItems(@NotNull ResultSet resultSet) {
        Item item = null;
        List<Item> items = new ArrayList<>();
        while (true) {
            try {
                if (!resultSet.next()) break;
                item = new Item();
                item.setItemID(resultSet.getInt("i_itemID"));
                item.setSlotInInv(resultSet.getInt("i_slotInInv"));
                item.setPriceToSell(resultSet.getInt("i_priceToSell"));
                item.setAmountToSell(resultSet.getInt("i_amountToSell"));
                item.setAmountInStorage(resultSet.getInt("i_amountInStorage"));
                item.setPageInInv(resultSet.getInt("i_pageInInv"));
                item.setShopID(resultSet.getInt("i_s_shopID"));
                item.setItem(getItemStackFromSQL(resultSet));
                items.add(item);
            } catch (SQLException e) {
                log.error(e.toString());
            }
        }
        return items;
    }

    private ItemStack getItemStackFromSQL(@NotNull ResultSet resultSet) throws SQLException {
        ItemStack itemStack = SerializersAndDeserializers.deserializeByteArrayToObject(resultSet.getBytes("i_itemStack"), ItemStack.class);
        return itemStack;
    }

    public Shop getShopWithItems(int shopID) {
        ResultSet resultSetOfShop = null;
        ResultSet resultSetOfItem = null;
        Shop shop = null;
        try {
            resultSetOfShop = DB.query("SELECT * FROM shop WHERE s_shopID = " + shopID + ";");
            if (!resultSetOfShop.next()) {
                return null;
            }
            shop = new Shop();
            shop.setShopID(resultSetOfShop.getInt("s_shopID"));
            shop.setOwnerUUID(SerializersAndDeserializers.deserializeByteArrayToObject(resultSetOfShop.getBytes("s_o_ownerID"), UUID.class));
            shop.setLocation(SerializersAndDeserializers.deserializeByteArrayToObject(resultSetOfShop.getBytes("s_shopLocation"), Location.class));
        } catch (SQLException e) {
            log.error(e.toString());
        }
        try {
            resultSetOfItem = DB.query("SELECT * FROM item WHERE i_s_shopID = " + shopID + ";");
            shop.setItems(getItems(resultSetOfItem));
        } catch (SQLException e) {
            log.error(e.toString());
        }
        if (resultSetOfShop != null) {
            try {
                resultSetOfShop.close();
            } catch (SQLException e) {
                log.error(e.toString());
            }
        }
        if (resultSetOfItem != null) {
            try {
                resultSetOfShop.close();
            } catch (SQLException e) {
                log.error(e.toString());
            }
        }
        return shop;
    }

    public boolean ownerExist(UUID ownerID) {
        try {
            ResultSet rs = DB.query("SELECT * FROM owner;");
            while (rs.next()) {
                UUID dbOwnerUUID = SerializersAndDeserializers.deserializeByteArrayToObject(rs.getBytes("o_ownerID"), UUID.class);
                if (dbOwnerUUID.equals(ownerID)) {
                    return true;
                }
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void insertOwner(@NotNull UUID ownerID) {
        PreparedStatement preparedStatement = null;
        int rows = 0;
        try {
            preparedStatement = DB.getConnection().prepareStatement("INSERT INTO owner(o_ownerID) VALUES(?)");
            preparedStatement.setBytes(1, SerializersAndDeserializers.serializeObjectToByteArray(ownerID));
            rows = preparedStatement.executeUpdate();
            if (rows != 1) log.info(MESSAGE.getString("ERROR_NO_ROWS_AFFECTED"));
        } catch (SQLException e) {
            log.error(MESSAGE.getString("ERROR_OWNER_INSERTION"));
            log.error(e.toString());
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                log.error(MESSAGE.getString("ERROR_STATEMENT_CLOSING") + "\n" + e.toString());
            }
        }

    }

    public void insertShop(@NotNull Shop shop) {
        PreparedStatement preparedStatement = null;
        int rows = 0;
        try {
            preparedStatement = DB.getConnection().prepareStatement("INSERT INTO shop(s_shopLocation, s_o_ownerID) VALUES(?, ?)");
            preparedStatement.setBytes(1, SerializersAndDeserializers.serializeObjectToByteArray(shop.getLocation()));
            preparedStatement.setBytes(2, SerializersAndDeserializers.serializeObjectToByteArray(shop.getOwnerUUID()));
            rows = preparedStatement.executeUpdate();
            if (rows == 0) if (rows != 1) log.info(MESSAGE.getString("ERROR_NO_ROWS_AFFECTED"));
        } catch (SQLException e) {
            log.error(MESSAGE.getString("ERROR_SHOP_INSERTION"));
            log.error(e.toString());
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                log.error(MESSAGE.getString("ERROR_STATEMENT_CLOSING"));
                log.error(e.toString());
            }
        }

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
            if (rows == 0) if (rows != 1) log.info(MESSAGE.getString("ERROR_NO_ROWS_AFFECTED"));
        } catch (SQLException e) {
            log.error(MESSAGE.getString("ERROR_ITEM_INSERTION"));
            log.error(e.toString());
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                log.error(MESSAGE.getString("ERROR_STATEMENT_CLOSING"));
                log.error(e.toString());
            }
        }

    }

    public void updateItemAmount(@NotNull Item item) {
        PreparedStatement preparedStatement = null;
        int rows = 0;
        try {
            preparedStatement = DB.getConnection().prepareStatement("UPDATE item SET i_amountToSell = ? WHERE i_itemID = ?;");
            preparedStatement.setInt(1, item.getAmountToSell());
            preparedStatement.setInt(2, item.getItemID());
        } catch (SQLException e) {
            log.error(MESSAGE.getString("ERROR_ITEM_UPDATEAMOUNT"));
            log.error(e.toString());
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                log.error(MESSAGE.getString("ERROR_STATEMENT_CLOSING"));
                log.error(e.toString());
            }
        }
    }

    public void updateItemPrice(@NotNull Item item) {
        PreparedStatement preparedStatement = null;
        int rows = 0;
        try {
            preparedStatement = DB.getConnection().prepareStatement("UPDATE item SET i_priceToSell = ? WHERE i_itemID = ?;");
            preparedStatement.setInt(1, item.getPriceToSell());
            preparedStatement.setInt(2, item.getItemID());
        } catch (SQLException e) {
            log.error(MESSAGE.getString("ERROR_ITEM_UPDATEPRICE"));
            log.error(e.toString());
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                log.error(MESSAGE.getString("ERROR_STATEMENT_CLOSING"));
                log.error(e.toString());
            }
        }
    }

    public void updateItemStorage(@NotNull Item item) {
        PreparedStatement preparedStatement = null;
        int rows = 0;
        try {
            preparedStatement = DB.getConnection().prepareStatement("UPDATE item SET i_amountInStorage = ? WHERE i_itemID = ?;");
            preparedStatement.setInt(1, item.getAmountInStorage());
            preparedStatement.setInt(2, item.getItemID());
        } catch (SQLException e) {
            log.error(MESSAGE.getString("ERROR_ITEM_UPDATESTORAGE"));
            log.error(e.toString());
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                log.error(MESSAGE.getString("ERROR_STATEMENT_CLOSING"));
                log.error(e.toString());
            }
        }
    }

    /*public void deleteShop(@NotNull Shop shop) {
        Connection connection = DB.getConnection();
        int deletedRowsFromShop = 0;
        int deletedRowsFromItem = 0;

        //Set auto-commit off for this connection
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Execute deletion on shop table
        PreparedStatement deleteShopStatement = null;
        try {
            deleteShopStatement = connection.prepareStatement("DELETE FROM shop WHERE s_shopID=?");
            deleteShopStatement.setInt(1, shop.getShopID());

            deletedRowsFromShop = deleteShopStatement.executeUpdate();
            if (deletedRowsFromShop != 1) {
                connection.rollback();
                LOGGER.log(Level.INFO, MESSAGE.getString("ERROR_NO_ROWS_AFFECTED"));
                return;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, MESSAGE.getString("ERROR_SHOP_DELETION") + "\n" + e.toString());
        }

        //Execute deletion on item table
        PreparedStatement deleteItemStatement = null;
        try {
            deleteItemStatement = connection.prepareStatement("DELETE FROM item WHERE i_s_shopID=?");
            deleteItemStatement.setInt(1, shop.getShopID());

            deletedRowsFromItem = deleteItemStatement.executeUpdate();
            if (deletedRowsFromItem != 1) {
                connection.rollback();
                LOGGER.log(Level.INFO, MESSAGE.getString("ERROR_NO_ROWS_AFFECTED"));
                return;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, MESSAGE.getString("ERROR_ITEM_DELETION") + "\n" + e.toString());
        }

        //Commit all transaction
        try {
            connection.commit();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, MESSAGE.getString("ERROR_COMMIT") + "\n" + e.toString());
        }
    }

    public void deleteItem(@NotNull Item item) {
        int rowsAffected = 0;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DB.getConnection().prepareStatement("DELETE FROM item WHERE i_s_shopID=?;");
            preparedStatement.setInt(1, item.getShopID());
            rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected != 1) LOGGER.log(Level.INFO, MESSAGE.getString("ERROR_NO_ROWS_AFFECTED"));
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, MESSAGE.getString("ERROR_ITEM_DELETION") + "\n" + e.toString());
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, MESSAGE.getString("ERROR_STATEMENT_CLOSING") + "\n" + e.toString());
            }
        }
    }

    public void updateItem(@NotNull Item item) {
        PreparedStatement preparedStatement = null;
        int rowsAffected = 0;
        try {
            preparedStatement = DB.getConnection().prepareStatement("UPDATE item " +
                    "SET i_amountToSell = ?," +
                    "i_amountInStorage = ?," +
                    "i_priceToSell = ?," +
                    "i_slotInInv = ?," +
                    "i_pageInInv = ?," +
                    "i_itemStack = ?," +
                    "i_s_shopID = ?" +
                    "WHERE i_itemID = ?;");
            preparedStatement.setInt(1, item.getAmountToSell());
            preparedStatement.setInt(2, item.getAmountInStorage());
            preparedStatement.setInt(3, item.getPriceToSell());
            preparedStatement.setInt(4, item.getSlotInInv());
            preparedStatement.setInt(5, item.getPageInInv());
            preparedStatement.setBytes(6, SerializersAndDeserializers.serializeObjectToByteArray(item.getItem()));
            preparedStatement.setInt(7, item.getShopID());

            rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) LOGGER.log(Level.INFO, MESSAGE.getString("ERROR_NO_ROWS_AFFECTED"));
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString());
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, MESSAGE.getString("ERROR_STATEMENT_CLOSING"));
                LOGGER.log(Level.SEVERE, e.toString());
            }
        }
    }*/
}
