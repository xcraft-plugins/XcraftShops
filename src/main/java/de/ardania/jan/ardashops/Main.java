package de.ardania.jan.ardashops;

import de.ardania.jan.ardashops.commands.Command;
import lib.PatPeter.SQLibrary.SQLite;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

    public static JavaPlugin PLUGIN;
    public static Logger LOGGER;

    public static SQLite DB;

    public Command command;

    public void onEnable(){
        PLUGIN = this;
        LOGGER = getLogger();
        command = new Command();
        getCommand("as").setExecutor(command);

        sqlConnection();
        try {
            sqlTableCheck();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void sqlTableCheck() throws SQLException {
        Location l = null;
        if (DB.checkTable("shop") && DB.checkTable("item")) {
            return;
        } else {
            DB.query("CREATE TABLE IF NOT EXISTS shop (\n" +
                    "  ownerUUID BLOB PRIMARY KEY NOT NULL,\n" +
                    "  location BLOB NULL DEFAULT NULL\n" +
                    "  );");

            LOGGER.info("Table shop has been created!");

            DB.query("CREATE TABLE IF NOT EXISTS item (\n" +
                    "  itemStack BLOB NULL DEFAULT NULL,\n" +
                    "  itemDisplayName TEXT NULL DEFAULT NULL,\n" +
                    "  itemLore TEXT NULL DEFAULT NULL,\n" +
                    "  amountToSell INT NULL DEFAULT NULL,\n" +
                    "  priceToSell INT NULL DEFAULT NULL,\n" +
                    "  amountInStorage INT NULL DEFAULT NULL,\n" +
                    "  slotInInv INT NULL DEFAULT NULL,\n" +
                    "  ownerUUID BLOB NULL DEFAULT NULL,\n" +
                    "  FOREIGN KEY (ownerUUID) REFERENCES shop(ownerUUID)\n" +
                    "    );");

            LOGGER.info("Table item has been created!");
        }
    }

    public void sqlConnection(){
        DB = new SQLite(LOGGER, "ArdaShops", getDataFolder().getAbsolutePath(), "test", ".db");
        try {
            DB.open();
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            getPluginLoader().disablePlugin(PLUGIN);
        }
    }
}
