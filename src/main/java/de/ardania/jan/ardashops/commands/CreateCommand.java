package de.ardania.jan.ardashops.commands;

import de.ardania.jan.ardashops.entities.Shop;
import de.ardania.jan.ardashops.handler.DatabaseHandler;
import org.bukkit.entity.Player;

import java.util.logging.Level;

import static de.ardania.jan.ardashops.Main.LOGGER;
import static de.ardania.jan.ardashops.handler.MessageHandler.MESSAGE;

public class CreateCommand extends DatabaseHandler {
    public void createShop(Player player) {
        Shop shop = new Shop();
        shop.setOwnerUUID(player.getUniqueId());
        shop.setLocation(player.getLocation());
        if (!ownerExist(player.getUniqueId())) insertOwner(player.getUniqueId());
        insertShop(shop);
        //ActiveMob mob = MythicMobs.inst().getMobManager().spawnMob("Shop", player.getLocation());
        //ItemStack item = new ItemStack(Material.DIAMOND, 1);
        //item.getItemMeta().setDisplayName(player.getUniqueId().toString());
        //mob.getEntity().equipItemHead(BukkitAdapter.adapt(item));
        LOGGER.log(Level.INFO, "\nShop created for Player: " + player.getName() + "\nShopOwnerUUID: " + shop.getOwnerUUID() + "\n" + shop.getLocation().toString());
        player.sendMessage(MESSAGE.getString("PLUGIN_PREFIX") + MESSAGE.getString("SHOP_CREATED"));
    }
}
