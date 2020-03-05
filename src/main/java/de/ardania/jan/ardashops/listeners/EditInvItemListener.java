package de.ardania.jan.ardashops.listeners;

import de.ardania.jan.ardashops.entities.Item;
import de.ardania.jan.ardashops.handler.DatabaseHandler;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import static de.ardania.jan.ardashops.Main.activeInvInfoDataMap;

public class EditInvItemListener extends DatabaseHandler implements Listener {
    @EventHandler
    public void onEditInvItemClick(InventoryClickEvent event) {
        //TODO: Functionality to add new Items to Shop and if so open changePriceInv
        //TODO: Check if click was on Item already existing

        if (!activeInvInfoDataMap.containsKey(event.getWhoClicked())) {
            return; //Überprüft ob der Spieler einen Shop offen hat.
        }

        if (!event.getView().getTitle().equals(activeInvInfoDataMap.get(event.getWhoClicked()).getShop().getShopID() + "-Edit")) {
            return; //Überprüft ob der Spieler sich im EditShopInv befindet und sonst in keinem anderen.
        }

        //TODO Ifs in methode mit return boolean... und dann setCancelled und return if true.
        if (event.isShiftClick()) { //Überprüft ob der Spieler ein Item mittels Shift Click bewegen wollte.
            event.setCancelled(true);
            return;
        }

        if (event.getRawSlot() == -1 && event.getCursor() != null) { //Überprüft ob der Spieler mit einem Item am Cursor auf den Rand des Inventars klickt.
            event.setCancelled(true);
            return;
        }

        if (event.getRawSlot() == -999) { //Überprüft ob der Spieler auf den Bereich neben den Inventorys klickt.
            event.setCancelled(true);
            return;
        }

        if (event.getClick().isKeyboardClick()) { //Überprüfung ob der Spieler eine Zahl drückt um somit Items zwischen den Invs zu tauschen.
            event.setCancelled(true);
            return;
        }

        if (event.getClickedInventory().getType().equals(InventoryType.PLAYER)) { //Überprüft ob der Spieler in sein Inventar klickt.
            if (event.getCurrentItem() == null) return;

            if (event.getCursor().getType() == Material.AIR && event.getCurrentItem() == null) {
                return;
            } else if (event.getCursor().getType() == Material.AIR && event.getCurrentItem() != null) {
                activeInvInfoDataMap.get(event.getWhoClicked()).setItemInHand(event.getCurrentItem());
                activeInvInfoDataMap.get(event.getWhoClicked()).setSlotInInv(event.getSlot());
                event.getWhoClicked().sendMessage(activeInvInfoDataMap.get(event.getWhoClicked()).getItemInHand().toString() + "1");
            } else if (event.getCursor().getType() != Material.AIR && event.getCurrentItem() == null) {
                activeInvInfoDataMap.get(event.getWhoClicked()).setItemInHand(null);
                activeInvInfoDataMap.get(event.getWhoClicked()).setSlotInInv(-1);
                event.getWhoClicked().sendMessage(activeInvInfoDataMap.get(event.getWhoClicked()).getItemInHand().toString() + "2");
            } else if (event.getCursor().getType() != Material.AIR && event.getCurrentItem() != null) {
                activeInvInfoDataMap.get(event.getWhoClicked()).setItemInHand(event.getCurrentItem());
                activeInvInfoDataMap.get(event.getWhoClicked()).setSlotInInv(event.getSlot());
                event.getWhoClicked().sendMessage(activeInvInfoDataMap.get(event.getWhoClicked()).getItemInHand().toString() + "3");
            } else if (event.getCursor().getType() != Material.AIR && event.getCurrentItem() != null && event.getCursor().getType() == event.getCurrentItem().getType()) {
                event.setCancelled(true);
                return;
            }


        } else if (event.getClickedInventory().getType().equals(InventoryType.CHEST)) {
            if (event.isLeftClick()) {
                if (event.getCursor().getType() == Material.AIR) {
                    event.setCancelled(true);
                    return;
                } else if (event.getCursor().getType() != Material.AIR && event.getCurrentItem() != null && event.getCursor().getType() != event.getCurrentItem().getType()) {
                    event.setCancelled(true);
                    return;
                } else if (event.getCursor().getType() != Material.AIR && event.getCurrentItem() != null && event.getCursor() == event.getCurrentItem() && event.getCurrentItem().getAmount() != event.getCursor().getAmount()) {

                    ItemStack itemInHand = activeInvInfoDataMap.get(event.getWhoClicked()).getItemInHand();
                    ItemStack itemInInv = event.getCurrentItem();
                    int slotInInv = event.getSlot();

                    activeInvInfoDataMap.get(event.getWhoClicked()).getShopInv().setItem(slotInInv, itemInHand);
                    activeInvInfoDataMap.get(event.getWhoClicked()).setItemInHand(null);
                    event.getWhoClicked().sendMessage(activeInvInfoDataMap.get(event.getWhoClicked()).getItemInHand().toString() + "4");
                    event.getWhoClicked().getInventory().setItem(activeInvInfoDataMap.get(event.getWhoClicked()).getSlotInInv(), itemInInv);
                    activeInvInfoDataMap.get(event.getWhoClicked()).setSlotInInv(-1);

                    activeInvInfoDataMap.get(event.getWhoClicked())
                            .getShop()
                            .getItems()
                            .stream()
                            .filter(item -> item.getSlotInInv() == event.getSlot())
                            .findFirst()
                            .get()
                            .setAmountToSell(event.getCursor().getAmount());
                    updateItemAmount(activeInvInfoDataMap.get(event.getWhoClicked())
                            .getShop()
                            .getItems()
                            .stream()
                            .filter(item -> item.getSlotInInv() == event.getSlot())
                            .findFirst()
                            .get());
                } else if (event.getCursor().getType() != Material.AIR) {
                    if (event.getCurrentItem() == null) {
                        ItemStack itemInHand = activeInvInfoDataMap.get(event.getWhoClicked()).getItemInHand();
                        int slot = activeInvInfoDataMap.get(event.getWhoClicked()).getSlotInInv();
                        activeInvInfoDataMap.get(event.getWhoClicked()).getShopInv().setItem(slot, itemInHand);
                        event.setCursor(null);
                        event.getWhoClicked().sendMessage(activeInvInfoDataMap.get(event.getWhoClicked()).getItemInHand().toString() + "5");
                        activeInvInfoDataMap.get(event.getWhoClicked()).setItemInHand(null);
                        activeInvInfoDataMap.get(event.getWhoClicked()).setSlotInInv(-1);

                        Item newItem = new Item();
                        newItem.setItem(itemInHand);
                        newItem.setPriceToSell(0);
                        newItem.setAmountInStorage(0);
                        newItem.setAmountToSell(itemInHand.getAmount());
                        newItem.setSlotInInv(slot);
                        newItem.setPageInInv(1);
                        newItem.setShopID(activeInvInfoDataMap.get(event.getWhoClicked()).getShop().getShopID());

                        activeInvInfoDataMap.get(event.getWhoClicked()).getShop().getItems().add(newItem);
                        activeInvInfoDataMap.get(event.getWhoClicked()).setItemToChangePrice(newItem);

                        insertItem(newItem);

                        event.getWhoClicked().openInventory(activeInvInfoDataMap.get(event.getWhoClicked()).getChangePriceInv());
                    }
                } else {
                    event.setCancelled(true);
                    return;
                }
            } else {
                event.setCancelled(true);
                return;
            }
        }
    }
}
