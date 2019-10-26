package de.ardania.jan.ardashops.handler;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

import static de.ardania.jan.ardashops.Main.PLUGIN;

public class MessageHandler {
    FileConfiguration configuration;

    public MessageHandler() {
        createCustomConfig();
    }

    /**
     * @return the messages.yml file with messages of this plugin
     */
    private File createCustomConfig() {
        File file = new File(PLUGIN.getDataFolder(), "messages.yml");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            PLUGIN.saveResource("messages.yml", false);
        }

        configuration = new YamlConfiguration();
        try {
            configuration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * @return the yml file
     */
    public FileConfiguration getConfiguration() {
        return configuration;
    }
}
