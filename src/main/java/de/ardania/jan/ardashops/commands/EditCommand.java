package de.ardania.jan.ardashops.commands;

import com.google.common.eventbus.EventBus;
import de.ardania.jan.ardashops.entities.Item;
import de.ardania.jan.ardashops.entities.Shop;
import de.ardania.jan.ardashops.handler.DatabaseHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class EditCommand extends DatabaseHandler implements Listener {
    private static Inventory currentEditInv;
    private static Shop currentEditShop;
    private static Inventory insertItemInv;
    private static int slot;
    private static int price;
    private static Item itemToAdd;

    public Inventory openEditInventory(int shopID) {
        currentEditShop = getShopWithItems(shopID);

        if (currentEditShop == null) {
            return null;
        }

        currentEditInv = Bukkit.createInventory(null, 6 * 9, shopID + "-Edit");

        if (!currentEditShop.getItems().isEmpty()) {
            for (Item item :
                    currentEditShop.getItems()) {
                currentEditInv.setItem(item.getSlotInInv(), item.getItem());
            }
        }

        initInsertItemInv();

        return currentEditInv;
    }

    private void initInsertItemInv() {
        insertItemInv = Bukkit.createInventory(null, 6 * 9, currentEditShop.getShopID() + "-Edit Item");

        ItemStack raisePriceOne = new ItemStack(Material.IRON_BLOCK);
        raisePriceOne.getItemMeta().setLore(Arrays.asList("Increase price by 1"));
        insertItemInv.setItem(30, raisePriceOne);
        ItemStack raisePriceFive = new ItemStack(Material.GOLD_BLOCK);
        raisePriceFive.getItemMeta().setLore(Arrays.asList("Increase price by 5"));
        insertItemInv.setItem(32, raisePriceFive);
        ItemStack raisePriceTen = new ItemStack(Material.DIAMOND_BLOCK);
        raisePriceTen.getItemMeta().setLore(Arrays.asList("Increase price by 10"));
        insertItemInv.setItem(34, raisePriceTen);

        ItemStack confirm = new ItemStack(Material.EMERALD_BLOCK);
        confirm.getItemMeta().setDisplayName("Click to Confirm your Price!");
        insertItemInv.setItem(5, confirm);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        //Überprüfung ob das offene Inv der Shop ist.
        if (event.getView().getTitle().equals(currentEditShop.getShopID() + "-Edit")) {
            //Überprüft ob der Spieler neben dem bereich klickt.
            if (event.getRawSlot() == -999) return;

            //Überprüft ob der Spieler mit einem Item am Cursor auf den Rand des Inventars klickt.
            if (event.getRawSlot() == -1 && event.getCursor() != null) return;

            //Überprüft ob Spieler auf das Shop inv geklickt hat.
            if (event.getClickedInventory().equals(event.getView().getTopInventory())) {
                event.setCancelled(true);
                //Überprüft ob der Spieler Rechtsklick ins inventar macht
                if (event.getClick().isRightClick() && event.getCursor() != null) event.setCancelled(true);

                if (event.getClick().isLeftClick() && event.getCurrentItem() == null && event.getCursor() != null) {
                    currentEditInv.setItem(event.getSlot(), event.getCursor());
                    player.getInventory().setItem(slot, event.getCursor());
                    itemToAdd = new Item().builder().amountToSell(player.getItemOnCursor().getAmount()).item(player.getItemOnCursor()).amountInStorage(0).pageInInv(1).slotInInv(event.getSlot()).shopID(currentEditShop.getShopID()).build();
                    player.setItemOnCursor(null);
                    player.openInventory(insertItemInv);
                    //Item item = new Item().builder().slotInInv(event.getSlot()).priceToSell((new Random().nextInt(10 - 1) + 1) + 1).pageInInv(1).amountToSell(player.getItemOnCursor().getAmount()).amountInStorage(0).item(player.getItemOnCursor()).shopID(currentEditShop.getShopID()).build();
                    //currentEditShop.getItems().add(item);
                }
            }
            //Überprüft ob Spieler auf das eigene inv geklickt hat.
            if (event.getClickedInventory().equals(event.getView().getBottomInventory())) {
                //Überprüft ob der Spieler ein Item mittels Shift Linksklick verschieben will
                if (event.getClick().isShiftClick()) event.setCancelled(true);
                if (event.getCurrentItem() != null) {
                    slot = event.getSlot();
                }
            }
            //Überprüfung ob der Spieler eine Zahl drückt um somit Items zwischen den Invs zu tauschen.
            if (event.getClick().equals(ClickType.NUMBER_KEY)) event.setCancelled(true);
        } else if (event.getView().getTitle().equals(currentEditShop.getShopID() + "-Edit Item")) {

        } else {
            return;
        }
    }

    @EventHandler
    public void onInventoryExit(InventoryCloseEvent event) {
        //Überprüfung ob das offene Inv der Shop ist.
        if (!event.getView().getTitle().equals(currentEditShop.getShopID() + "-Edit")) return;
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!event.getView().getTitle().equals(currentEditShop.getShopID() + "-Edit")) {
            return;
        }
        event.setCancelled(true);
    }

}
