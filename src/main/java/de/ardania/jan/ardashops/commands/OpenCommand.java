package de.ardania.jan.ardashops.commands;

import de.ardania.jan.ardashops.entities.Item;
import de.ardania.jan.ardashops.entities.Shop;
import de.ardania.jan.ardashops.handler.DatabaseHandler;
import de.ardania.jan.ardashops.util.SerializersAndDeserializers;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class OpenCommand extends DatabaseHandler implements Listener {

    private static Inventory currentInv;
    private static Shop currentShop;

    public Inventory openShop(int shopID) {
        currentShop = getShopWithItems(shopID);
        //ItemStack i = new ItemStack(Material.DIAMOND, 1);
        //Item item = Item.builder().shopID(1).item(i).amountInStorage(120).amountToSell(10).pageInInv(1).priceToSell(30).slotInInv(5).build();
        //insertItem(item);

        currentInv = Bukkit.createInventory(null, 6 * 9, String.valueOf(shopID));
        //currentInv.setItem(4, new ItemStack(Material.CHEST));

        for (Item item :
                currentShop.getItems()) {
            currentInv.setItem(item.getSlotInInv(), item.getItem());
        }

        return currentInv;
    }

    public Item getItemFromShop(int slot) {
        for (Item i :
                currentShop.getItems()) {
            if (i.getSlotInInv() == slot) {
                return i;
            }
        }
        return null;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        //Überprüfung ob das offene Inv der Shop ist.
        if (!event.getView().getTitle().equals(Integer.toString(currentShop.getShopID()))) {
            return;
        }
        //Überprüft ob der Spieler neben dem bereich klickt.
        if (event.getRawSlot() == -999) {
            event.setCancelled(true);
        }
        //Überprüft ob Spieler auf das Shop inv geklickt hat.
        if (event.getClickedInventory().equals(event.getView().getTopInventory())) {
            event.setCancelled(true);
            player.sendMessage("Top INV");
            if (event.getCurrentItem() != null) {
                player.sendMessage("Dir wurden " + getItemFromShop(event.getSlot()).getPriceToSell() + " abgezogen.");
            }
        }
        //Überprüft ob Spieler auf das eigene inv geklickt hat.
        if (event.getClickedInventory().equals(event.getView().getBottomInventory())) {
            player.sendMessage("Bot INV");
        }
        //Überprüfung ob der Spieler eine Zahl drückt um somit Items zwischen den Invs zu tauschen.
        if (event.getClick().equals(ClickType.NUMBER_KEY)) {
            event.setCancelled(true);
            player.sendMessage("Number pressed");
        }
    }
}
