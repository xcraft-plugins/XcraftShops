package de.ardania.jan.ardashops.commands;

import de.ardania.jan.ardashops.entities.Shop;
import de.ardania.jan.ardashops.util.DatabaseData;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.logging.Level;

import static de.ardania.jan.ardashops.Main.LOGGER;

public class CreateCommand {

    public CreateCommand (Player player){
        try {
            createShop(player);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString() + "\n While creating Shop for " + player.getUniqueId() + "(" + player.getName() + ")" + " at Location " + player.getLocation().toString());
        }
    }

    private void createShop(Player player) throws SQLException {
        Shop shop = new Shop();
        DatabaseData databaseData = new DatabaseData();
        shop.setOwnerUUID(player.getUniqueId());
        shop.setLocation(player.getLocation());
        databaseData.insertShop(shop);
        LOGGER.log(Level.INFO, "Shop created! ShopOwnerUUID: " + shop.getOwnerUUID() + ", Location: " + shop.getLocation().toString());
    }
}
