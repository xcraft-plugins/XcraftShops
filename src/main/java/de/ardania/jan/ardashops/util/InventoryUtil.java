package de.ardania.jan.ardashops.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class InventoryUtil {
    /**
     * Initialise the ({@link Inventory}) to set the price for a specific item.
     * ({@link Material#EMERALD_BLOCK}) on slot 11 for confirmation of price.
     * ({@link Material#BARRIER}) on slot 15 for canceling the change of the price.
     * ({@link Material#IRON_BLOCK}) on slot 38 for adding 1 to the price.
     * ({@link Material#GOLD_BLOCK}) on slot 40 for adding 5 to the price.
     * ({@link Material#DIAMOND_BLOCK}) on slot 42 for adding 10 to the price.
     *
     * @param shopID - the ID of the current shop
     * @return the ({@link Inventory}) that represent the menu to set the price.
     */
    public static Inventory initPriceInv(int shopID) {
        //Initialise empty Inventory
        Inventory inv = Bukkit.createInventory(null, 6 * 9, Integer.toString(shopID) + "-Edit-Price");
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

    /**
     * Initialise the ({@link Inventory}) to buy or sell items.
     * {@link Material#EMERALD_BLOCK} on slot 20 to buy items from the shop.
     * {@link Material#GOLD_BLOCK} on slot 24 to sell items to the shop.
     * {@link Material#BARRIER} on slot 53 to go back to the shop.
     *
     * @param shopID - the ID of the current shop
     * @return the ({@link Inventory}) that represent the menu to buy or sell items.
     */
    public static Inventory initBuyAndSellInv(int shopID) {
        //Initialise empty Inventory
        Inventory inv = Bukkit.createInventory(null, 6 * 9, Integer.toString(shopID) + "-BuyOrSell");
        //Sell current Item to Shop
        ItemStack sell = new ItemStack(Material.EMERALD_BLOCK);
        sell.getItemMeta().setDisplayName("Click to Buy!");
        sell.getItemMeta().setLore(Arrays.asList("Klicke um von diesem Shop zu kaufen!"));
        inv.setItem(20, sell);
        //Buy current Item from Shop
        ItemStack buy = new ItemStack(Material.GOLD_BLOCK);
        buy.getItemMeta().setDisplayName("Click to Sell!");
        buy.getItemMeta().setLore(Arrays.asList("Klicke um an diesen Shop zu verkaufen!"));
        inv.setItem(24, buy);
        //Abort Buy or Sell
        ItemStack exit = new ItemStack(Material.BARRIER);
        exit.getItemMeta().setDisplayName("Click to return");
        inv.setItem(53, exit);

        return inv;
    }
}
