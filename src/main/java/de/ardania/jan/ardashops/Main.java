package de.ardania.jan.ardashops;

import de.ardania.jan.ardashops.commands.CommandHandler;
import de.ardania.jan.ardashops.handler.ConfigHandler;
import de.ardania.jan.ardashops.handler.MessageHandler;
import lib.PatPeter.SQLibrary.SQLite;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

    public static JavaPlugin PLUGIN;
    public static Logger LOGGER;

    public static SQLite DB;

    public CommandHandler commandHandler;
    public MessageHandler messageHandler;
    public ConfigHandler configHandler;

    public void onEnable(){
        PLUGIN = this;
        LOGGER = getLogger();
        commandHandler = new CommandHandler();
        messageHandler = new MessageHandler();
        configHandler = new ConfigHandler();
        LOGGER.info("\n"+"                  _        _____ _                     \n" +
                "    /\\           | |      / ____| |                    \n" +
                "   /  \\   _ __ __| | __ _| (___ | |__   ___  _ __  ___ \n" +
                "  / /\\ \\ | '__/ _` |/ _` |\\___ \\| '_ \\ / _ \\| '_ \\/ __|\n" +
                " / ____ \\| | | (_| | (_| |____) | | | | (_) | |_) \\__ \\\n" +
                "/_/    \\_\\_|  \\__,_|\\__,_|_____/|_| |_|\\___/| .__/|___/\n" +
                "                                            | |        \n" +
                "                                            |_|        " + "\n" + " ______             _     _          _ \n" +
                "|  ____|           | |   | |        | |\n" +
                "| |__   _ __   __ _| |__ | | ___  __| |\n" +
                "|  __| | '_ \\ / _` | '_ \\| |/ _ \\/ _` |\n" +
                "| |____| | | | (_| | |_) | |  __/ (_| |\n" +
                "|______|_| |_|\\__,_|_.__/|_|\\___|\\__,_|");
        getCommand("as").setExecutor(commandHandler);
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
            LOGGER.info("Table already exists! Skip creation!");
            return;
        } else {
            DB.query("CREATE TABLE IF NOT EXISTS shop (\n" +
                    "  ownerUUID BLOB PRIMARY KEY NOT NULL,\n" +
                    "  location BLOB NULL DEFAULT NULL\n" +
                    "  );");

            LOGGER.info("Table shop has been created!");

            DB.query("CREATE TABLE IF NOT EXISTS item (\n" +
                    "  amountToSell INT NULL DEFAULT NULL,\n" +
                    "  priceToSell INT NULL DEFAULT NULL,\n" +
                    "  amountInStorage INT NULL DEFAULT NULL,\n" +
                    "  slotInInv INT NULL DEFAULT NULL,\n" +
                    "  itemStack BLOB NULL DEFAULT NULL,\n" +
                    "  itemDisplayName BLOB NULL DEFAULT NULL,\n" +
                    "  itemLore BLOB NULL DEFAULT NULL,\n" +
                    "  ownerUUID BLOB NULL DEFAULT NULL,\n" +
                    "  FOREIGN KEY (ownerUUID) REFERENCES shop(ownerUUID)\n" +
                    "    );");

            LOGGER.info("Table item has been created!");
        }
    }

    public void sqlConnection(){
        DB = new SQLite(LOGGER, "ArdaShops", getDataFolder().getAbsolutePath(), "shops", ".db");
        try {
            DB.open();
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            getPluginLoader().disablePlugin(PLUGIN);
        }
    }
}
