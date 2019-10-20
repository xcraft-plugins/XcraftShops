package de.ardania.jan.ardashops;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Command implements CommandExecutor {
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        Player player;
        if (sender instanceof Player) {
            player = (Player) sender;
            File f = new File("C:\\Users\\PC\\Desktop\\1.14\\plugins\\ArdaShops\\test.yml");

            ObjectMapper mapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));

            try {
                FileConfiguration fileConfiguration = new YamlConfiguration();
                fileConfiguration.load(f);
                Map<String,Object> items = (Map<String, Object>) fileConfiguration.getMapList("Item").get(0);
                player.setItemOnCursor(ItemStack.deserialize(items));

                fileConfiguration.save(f);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidConfigurationException e) {
                e.printStackTrace();
            }

        }

        /**

        ItemStack s = new ItemStack(Material.DIAMOND_SWORD, 1);
        s.addEnchantment(Enchantment.DURABILITY, 2);
        s.getItemMeta().setLore(Arrays.asList("TestLore"));

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));

        try {
            mapper.writeValue(f, s.serialize());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(s.serialize());*/

        return false;
    }
}
