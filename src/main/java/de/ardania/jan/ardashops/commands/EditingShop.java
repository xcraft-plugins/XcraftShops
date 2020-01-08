package de.ardania.jan.ardashops.commands;

import de.ardania.jan.ardashops.entities.Item;
import de.ardania.jan.ardashops.entities.Shop;
import de.ardania.jan.ardashops.handler.DatabaseHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class EditingShop extends DatabaseHandler implements Listener {
    private static Inventory currentEditInv;
    private static Shop currentEditShop;
    private static Inventory changePriceInv;
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

        initChangePriceInv();

        return currentEditInv;
    }

    public Item getItemFromShop(int slot) {
        for (Item i :
                currentEditShop.getItems()) {
            if (i.getSlotInInv() == slot) {
                return i;
            }
        }
        return null;
    }

    //Update Inv view
    private void updateInv() {
        if (!currentEditShop.getItems().isEmpty()) {
            for (Item item :
                    currentEditShop.getItems()) {
                currentEditInv.setItem(item.getSlotInInv(), item.getItem());
            }
        }
    }

    private void initChangePriceInv() {
        changePriceInv = Bukkit.createInventory(null, 6 * 9, currentEditShop.getShopID() + "-Edit Item");

        ItemStack raisePriceOne = new ItemStack(Material.IRON_BLOCK);
        raisePriceOne.getItemMeta().setLore(Arrays.asList("Increase price by 1"));
        changePriceInv.setItem(38, raisePriceOne);
        ItemStack raisePriceFive = new ItemStack(Material.GOLD_BLOCK);
        raisePriceFive.getItemMeta().setLore(Arrays.asList("Increase price by 5"));
        changePriceInv.setItem(40, raisePriceFive);
        ItemStack raisePriceTen = new ItemStack(Material.DIAMOND_BLOCK);
        raisePriceTen.getItemMeta().setLore(Arrays.asList("Increase price by 10"));
        changePriceInv.setItem(42, raisePriceTen);

        ItemStack confirm = new ItemStack(Material.EMERALD_BLOCK);
        confirm.getItemMeta().setDisplayName("Click to Confirm your Price!");
        changePriceInv.setItem(11, confirm);

        ItemStack exit = new ItemStack(Material.BARRIER);
        exit.getItemMeta().setDisplayName("Click to return");
        changePriceInv.setItem(15, exit);
    }

    @EventHandler
    public void onEditInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getView().getTitle().equals(currentEditShop.getShopID() + "-Edit")) { //Überprüfung ob das offene Inv der Shop ist.

//            if (basicInvChecksNOK(event)) return;


            if (event.getClickedInventory().equals(event.getView().getTopInventory())) { //Überprüft ob Spieler auf das Shop inv geklickt hat.
                event.setCancelled(true);

                if (event.getClick().isRightClick() && event.getCursor() != null)
                    event.setCancelled(true); //Überprüft ob der Spieler Rechtsklick ins inventar macht

                if (event.getClick().isLeftClick() && event.getCurrentItem() == null && event.getCursor().getType() != Material.AIR) {
                    currentEditInv.setItem(event.getSlot(), event.getCursor());
                    player.getInventory().setItem(slot, event.getCursor());
                    itemToAdd = new Item().builder().amountToSell(player.getItemOnCursor().getAmount()).item(player.getItemOnCursor()).amountInStorage(0).pageInInv(1).slotInInv(event.getSlot()).shopID(currentEditShop.getShopID()).build();
                    player.setItemOnCursor(null);
                    player.openInventory(changePriceInv);
                    //Item item = new Item().builder().slotInInv(event.getSlot()).priceToSell((new Random().nextInt(10 - 1) + 1) + 1).pageInInv(1).amountToSell(player.getItemOnCursor().getAmount()).amountInStorage(0).item(player.getItemOnCursor()).shopID(currentEditShop.getShopID()).build();
                    //currentEditShop.getItems().add(item);
                }
            }

//            bottomInvClick(event);
        } else {
            return;
        }
    }

    @EventHandler
    public void onEditPriceInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(currentEditShop.getShopID() + "-Edit Item")) { //Überprüft ob der Spieler sich im Inventory zum Preis editieren befindet.
//            basicInvChecksNOK(event);
            if (event.getClickedInventory().equals(event.getView().getTopInventory())) { //Überprüft ob Spieler auf das Shop inv geklickt hat.
                event.setCancelled(true);
                //TODO: Preis änderungen vornehmen
                changePriceFromNewItem(event);
            }
        }
    }

    @EventHandler
    public void onInventoryExit(InventoryCloseEvent event) {
        //Überprüfung ob das offene Inv der Shop ist.
        if (!event.getView().getTitle().equals(currentEditShop.getShopID() + "-Edit")) return;

        for (Item i :
                currentEditShop.getItems()) {
            insertItem(i);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!event.getView().getTitle().equals(currentEditShop.getShopID() + "-Edit")) {
            return;
        }
        event.setCancelled(true);
    }

    /**
     * @param event das Event
     */
    private void changePriceFromNewItem(InventoryClickEvent event) {
        if (event.getClick().isLeftClick() && event.getCurrentItem() != null) { //Überprüft ob der Spieler auf ein Item Klickt
            if (!currentEditShop.getItems().contains(itemToAdd)) {
                if (event.getCurrentItem().getType().equals(Material.IRON_BLOCK)) {
                    itemToAdd.setPriceToSell(itemToAdd.getPriceToSell() + 1);
                    changePriceInv.getItem(11).getItemMeta().setLore(Arrays.asList("Aktueller Preis beträgt " + itemToAdd.getPriceToSell() + 1 + " Euronen."));
                } else if (event.getCurrentItem().getType().equals(Material.GOLD_BLOCK)) {
                    itemToAdd.setPriceToSell(itemToAdd.getPriceToSell() + 5);
                    changePriceInv.getItem(11).getItemMeta().setLore(Arrays.asList("Aktueller Preis beträgt " + itemToAdd.getPriceToSell() + 5 + " Euronen."));
                } else if (event.getCurrentItem().getType().equals(Material.DIAMOND_BLOCK)) {
                    itemToAdd.setPriceToSell(itemToAdd.getPriceToSell() + 10);
                    changePriceInv.getItem(11).getItemMeta().setLore(Arrays.asList("Aktueller Preis beträgt " + itemToAdd.getPriceToSell() + 10 + " Euronen."));
                } else if (event.getCurrentItem().getType().equals(Material.EMERALD_BLOCK)) {
                    currentEditShop.getItems().add(itemToAdd);
                    event.getWhoClicked().openInventory(currentEditInv);
                } else if (event.getCurrentItem().getType().equals(Material.BARRIER)) {
                    event.getWhoClicked().openInventory(currentEditInv);
                }
            }
        } else {
            return;
        }
    }

    /**
     * Überprüft ob der Spieler in sein eigenes Inv geklickt hat.
     * Wenn es mit Shift war wird das Event abgebrochen.
     * Hat der Spieler ein Item angeklickt wird der Slot gespeichert.
     *
     * @param event das Event
     */
//    private void bottomInvClick(InventoryClickEvent event) {
//        if (event.getClickedInventory().equals(event.getView().getBottomInventory())) { //Überprüft ob Spieler auf das eigene inv geklickt hat.
//            if (event.getClick().isShiftClick()) //Überprüft ob der Spieler ein Item mittels Shift Linksklick verschieben will
//                event.setCancelled(true);
//            if (event.getCurrentItem() != null) {
//                slot = event.getSlot();
//            }
//        }
//    }

//    private boolean basicInvChecksNOK(InventoryClickEvent event) {
//        if (event.getClick().equals(ClickType.NUMBER_KEY)) //Überprüfung ob der Spieler eine Zahl drückt um somit Items zwischen den Invs zu tauschen.
//            event.setCancelled(true);
//
//        if (event.getRawSlot() == -999) return true; //Überprüft ob der Spieler neben dem bereich klickt.
//
//        if (event.getRawSlot() == -1 && event.getCursor() != null) //Überprüft ob der Spieler mit einem Item am Cursor auf den Rand des Inventars klickt.
//            return true;
//
//        return false;
//    }

}
