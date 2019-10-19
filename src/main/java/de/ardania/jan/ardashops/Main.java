package de.ardania.jan.ardashops;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Main extends JavaPlugin {

    public static JavaPlugin PLUGIN;
    public static Logger LOGGER;

    public FileHandler fileHandler;
    public Command command;

    public void onEnable(){
        PLUGIN = this;
        LOGGER = getLogger();
        fileHandler = new FileHandler();
        command = new Command();
        getCommand("as").setExecutor(command);
    }

    public void onDisable(){
        //TODO save Files maybe?
    }
}
