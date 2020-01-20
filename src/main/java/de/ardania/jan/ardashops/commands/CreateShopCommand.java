package de.ardania.jan.ardashops.commands;

import de.ardania.jan.ardashops.entities.Shop;
import de.ardania.jan.ardashops.handler.DatabaseHandler;
import lombok.extern.log4j.Log4j2;
import org.bukkit.entity.Player;

import java.util.logging.Level;

import static de.ardania.jan.ardashops.handler.MessageHandler.MESSAGE;

@Log4j2
public class CreateShopCommand extends DatabaseHandler {
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
        log.info("\nShop created for Player: " + player.getName() + "\nShopOwnerUUID: " + shop.getOwnerUUID() + "\n" + shop.getLocation().toString());
        player.sendMessage(MESSAGE.getString("PLUGIN_PREFIX") + MESSAGE.getString("SHOP_CREATED"));
    }
}
