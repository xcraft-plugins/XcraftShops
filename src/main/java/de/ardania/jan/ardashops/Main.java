package de.ardania.jan.ardashops;

import de.ardania.jan.ardashops.commands.CommandHandler;
import de.ardania.jan.ardashops.commands.OpenCommand;
import de.ardania.jan.ardashops.handler.ConfigHandler;
import de.ardania.jan.ardashops.handler.ListenerHandler;
import de.ardania.jan.ardashops.handler.MessageHandler;
import de.ardania.jan.ardashops.util.ActiveInvInfoData;
import lib.PatPeter.SQLibrary.SQLite;
import lombok.extern.log4j.Log4j2;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class Main extends JavaPlugin {

    public static JavaPlugin PLUGIN;

    public static SQLite DB;

    public static Map<Player, ActiveInvInfoData> activeInvInfoDataMap;

    public CommandHandler commandHandler;
    public MessageHandler messageHandler;
    public ConfigHandler configHandler;
    public ListenerHandler listenerHandler;

    public void onEnable() {
        //Setter
        PLUGIN = this;
        commandHandler = new CommandHandler();
        messageHandler = new MessageHandler();
        configHandler = new ConfigHandler();
        listenerHandler = new ListenerHandler();
        activeInvInfoDataMap = new HashMap<>();

        //Announcer
        announcer();

        //Commands & Listeners
        //getServer().getPluginManager().registerEvents(new OpenCommand(), this);
        //getServer().getPluginManager().registerEvents(new OpenCommand(), this);
        getCommand("as").setExecutor(commandHandler);
        //DB Stuff
        sqlConnection();
        try {
            sqlTableCreation();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void sqlTableCreation() throws SQLException {
        DB.query("CREATE TABLE IF NOT EXISTS owner (\n" +
                "  o_ownerID blob PRIMARY KEY\n" +
                ");");

        DB.query("CREATE TABLE IF NOT EXISTS shop (\n" +
                "  s_shopID INTEGER PRIMARY KEY,\n" +
                "  s_shopLocation blob not null,\n" +
                "  s_o_ownerID uuid,\n" +
                "  FOREIGN KEY (s_o_ownerID) REFERENCES owner (o_ownerID)\n" +
                ");");

        DB.query("CREATE TABLE IF NOT EXISTS item (\n" +
                "  i_itemID INTEGER PRIMARY KEY,\n" +
                "  i_amountToSell int,\n" +
                "  i_amountInStorage int,\n" +
                "  i_priceToSell int,\n" +
                "  i_slotInInv int,\n" +
                "  i_pageInInv int,\n" +
                "  i_itemStack blob,\n" +
                "  i_s_shopID int,\n" +
                "  FOREIGN KEY (i_s_shopID) REFERENCES shop (s_shopID)\n" +
                ");");
    }

    private void sqlConnection(){
        DB = new SQLite(getLogger(), "ArdaShops", getDataFolder().getAbsolutePath(), "shops", ".db");
        try {
            DB.open();
        } catch (Exception e) {
            log.error(e.getMessage());
            getPluginLoader().disablePlugin(PLUGIN);
        }
    }

    private void announcer(){
        log.info("\n" + "                  _        _____ _                     \n" +
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
    }
}
