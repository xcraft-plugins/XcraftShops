package de.ardania.jan.ardashops.commands;

import de.ardania.jan.ardashops.entities.Shop;
import de.ardania.jan.ardashops.handler.DatabaseHandler;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.adapters.AbstractItemStack;
import io.lumine.xikage.mythicmobs.adapters.AbstractLocation;
import io.lumine.xikage.mythicmobs.adapters.AbstractWorld;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import io.lumine.xikage.mythicmobs.mobs.MobManager;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.logging.Level;

import static de.ardania.jan.ardashops.Main.LOGGER;
import static de.ardania.jan.ardashops.handler.MessageHandler.MESSAGE;

public class CreateCommand {
    public static void createShop(Player player) {
        try {
            Shop shop = new Shop();
            shop.setOwnerUUID(player.getUniqueId());
            shop.setLocation(player.getLocation());
            DatabaseHandler.insertShop(shop);
            //ActiveMob mob = MythicMobs.inst().getMobManager().spawnMob("Shop", player.getLocation());
            //ItemStack item = new ItemStack(Material.DIAMOND, 1);
            //item.getItemMeta().setDisplayName(player.getUniqueId().toString());
            //mob.getEntity().equipItemHead(BukkitAdapter.adapt(item));
            LOGGER.log(Level.INFO, "Shop created! ShopOwnerUUID: " + shop.getOwnerUUID() + ", Location: " + shop.getLocation().toString());
            player.sendMessage(MESSAGE.getString("PLUGIN_PREFIX") + MESSAGE.getString("SHOP_CREATED"));
        } catch (SQLException e) {
            player.sendMessage(MESSAGE.getString("PLUGIN_PREFIX") + MESSAGE.getString("ERROR_SHOP_CREATED"));
            LOGGER.log(Level.SEVERE, e.toString() + "\nERROR while creating Shop for " + player.getUniqueId() + "(" + player.getName() + ")" + " at " + player.getLocation().toString() + "\n Pretty sure thats because the Shop already exist. Baka >.<");
        }
    }
}
