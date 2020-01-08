package de.ardania.jan.ardashops.commands;

import de.ardania.jan.ardashops.handler.DatabaseHandler;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CommandHandler implements CommandExecutor {

    OpenShopCommand openCommand;
    CreateShopCommand createCommand;
    EditingShop editCommand;
    DatabaseHandler databaseHandler;

    public CommandHandler() {
        openCommand = new OpenShopCommand();
        createCommand = new CreateShopCommand();
        editCommand = new EditingShop();
        databaseHandler = new DatabaseHandler();
    }

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        Player player = sender instanceof Player ? (Player) sender : null;

        if (player != null) {
            if (args.length < 1) PluginHelpCommand.PluginInfo(player);
            else if (args[0].equals("create")) {
                createCommand.createShop(player);
            } else if (args[0].equals("open") && !args[1].isEmpty()) {
                Inventory invToOpen = openCommand.openShop(Integer.parseInt(args[1]));
                if (invToOpen != null) {
                    player.openInventory(invToOpen);
                } else {
                    player.sendMessage("Shop nicht vorhanden");
                }

            } else if (args[0].equals("edit") && !args[1].isEmpty()) {
                Inventory invToOpen = editCommand.openEditInventory(Integer.parseInt(args[1]));
                if (invToOpen != null) {
                    player.openInventory(invToOpen);
                } else {
                    player.sendMessage("Shop nicht vorhanden");
                }
            }
        }
        return false;
    }
}



















































































/**
 * player.sendMessage("╔[ArdaShops] by MyNameIsJan\n" +
 * "║\n" +
 * "╠/as create\n" +
 * "║\n" +
 * "╠/as open\n" +
 * "║\n" +
 * "╠/as open\n" +
 * "║\n" +
 * "╠/as help\n" +
 * "╚═════════════════");
 */