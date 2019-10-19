package de.ardania.jan.ardashops;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CreateCommand {

    //TODO Add Location where Player created Shop to File with creation and save method would be nice!

    private File customConfigFile;
    private FileConfiguration customConfig;
    private Player player;
    Map<String, Integer> location = new HashMap<String, Integer>();

    public CreateCommand(Player player){
        this.player = player;
        createCustomConfig();
    }

    private void createCustomConfig() {
        customConfigFile = new File(Main.PLUGIN.getDataFolder() + File.separator + "shops", player.getName() + ".yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            try {
                customConfigFile.createNewFile(); //Create new File because there is no file :P

                customConfig = YamlConfiguration.loadConfiguration(customConfigFile);

                customConfig.set("Shopowner", player.getName());
                location.put("x", player.getLocation().getBlockX());
                location.put("y", player.getLocation().getBlockY());
                location.put("z", player.getLocation().getBlockZ());

                customConfig.set("Location", location);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
