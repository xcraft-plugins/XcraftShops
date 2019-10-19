package de.ardania.jan.ardashops;

import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static de.ardania.jan.ardashops.FileHandler.SHOPS;

public class Command implements CommandExecutor {
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        Player player;
        if (sender instanceof Player) {
            player = (Player) sender;

            player.sendMessage(new EnchantmentOnItem(SHOPS.get(player.getName()).getItems().get(0)).getEnchantmentsOnItem().toString());
            ItemStack itemStack = new ItemStack(Material.DIAMOND_SWORD, 1);
            itemStack.addUnsafeEnchantments(new EnchantmentOnItem(SHOPS.get(player.getName()).getItems().get(0)).getEnchantmentsOnItem());
            player.setItemOnCursor(itemStack);
        }
        return false;
    }
}
