package de.ardania.jan.ardashops.handler;

import org.bukkit.configuration.file.FileConfiguration;

import static de.ardania.jan.ardashops.Main.PLUGIN;

public class ConfigHandler {
    //The FileConfiguration for the default config
    FileConfiguration config;

    /**
     * Default constructor
     */
    public ConfigHandler() {
        PLUGIN.saveDefaultConfig();
        config = PLUGIN.getConfig();
    }

    /**
     * @return the default config of this plugin
     */
    public FileConfiguration getConfig() {
        return config;
    }

}
