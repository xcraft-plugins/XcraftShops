package de.ardania.jan.ardashops.util;

import de.ardania.jan.ardashops.entities.Shop;
import lombok.Builder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

@Getter
@Builder
public class ActiveInvInfoData {
    private Shop shop;
    private Inventory shopInv;
    @Builder.Default
    private Inventory priceInv = initPriceInv();
    private Player player;

    /**
     * Initialise the ({@link Inventory}) to set the price for a specific item.
     * ({@link Material#EMERALD_BLOCK}) on slot 11 for confirmation of price.
     * ({@link Material#BARRIER}) on slot 15 for canceling the change of the price.
     * ({@link Material#IRON_BLOCK}) on slot 38 for adding 1 to the price.
     * ({@link Material#GOLD_BLOCK}) on slot 40 for adding 5 to the price.
     * ({@link Material#DIAMOND_BLOCK}) on slot 42 for adding 10 to the price.
     *
     * @return the ({@link Inventory}) that represent the menu to set the price.
     */
    private Inventory initPriceInv() {
        //Initialise empty Inventory
        Inventory inv = Bukkit.createInventory(null, 6 * 9, Integer.toString(shop.getShopID()));
        //Confirm Price and return
        ItemStack confirm = new ItemStack(Material.EMERALD_BLOCK);
        confirm.getItemMeta().setDisplayName("Click to Confirm your Price!");
        confirm.getItemMeta().setLore(Arrays.asList("Aktueller Preis beträgt 0 Euronen."));
        inv.setItem(11, confirm);
        //Abort Price and return
        ItemStack exit = new ItemStack(Material.BARRIER);
        exit.getItemMeta().setDisplayName("Click to return");
        inv.setItem(15, exit);
        //Raise Price by 1
        ItemStack raisePriceOne = new ItemStack(Material.IRON_BLOCK);
        raisePriceOne.getItemMeta().setLore(Arrays.asList("Increase price by 1"));
        inv.setItem(38, raisePriceOne);
        //Raise Price by 5
        ItemStack raisePriceFive = new ItemStack(Material.GOLD_BLOCK);
        raisePriceFive.getItemMeta().setLore(Arrays.asList("Increase price by 5"));
        inv.setItem(40, raisePriceFive);
        //Raise Price by 10
        ItemStack raisePriceTen = new ItemStack(Material.DIAMOND_BLOCK);
        raisePriceTen.getItemMeta().setLore(Arrays.asList("Increase price by 10"));
        inv.setItem(42, raisePriceTen);

        return inv;
    }
}
