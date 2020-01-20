package de.ardania.jan.ardashops.commands;

import de.ardania.jan.ardashops.handler.DatabaseHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {

    OpenCommand openCommand;
    CreateShopCommand createCommand;
    EditCommand editCommand;
    DatabaseHandler databaseHandler;

    public CommandHandler() {
        openCommand = new OpenCommand();
        createCommand = new CreateShopCommand();
        editCommand = new EditCommand();
        databaseHandler = new DatabaseHandler();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = sender instanceof Player ? (Player) sender : null;

        if (player != null) {
            if (args.length < 1) PluginHelpCommand.PluginInfo(player);
            else if (args[0].equals("create")) {
                createCommand.createShop(player);
            } else if (args[0].equals("open") && !args[1].isEmpty()) {
                player.openInventory(openCommand.openShopInventory(Integer.parseInt(args[1]), player));
            } else if (args[0].equals("edit") && !args[1].isEmpty()) {
                player.openInventory(editCommand.openEditInventory(Integer.parseInt(args[1]), player));
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