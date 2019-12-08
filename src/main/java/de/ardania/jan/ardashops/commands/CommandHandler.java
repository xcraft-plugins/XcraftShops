package de.ardania.jan.ardashops.commands;

import de.ardania.jan.ardashops.handler.DatabaseHandler;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.SQLException;
import java.util.Arrays;

public class CommandHandler implements CommandExecutor {

    OpenCommand openCommand;
    CreateCommand createCommand;
    DatabaseHandler databaseHandler;

    public CommandHandler() {
        openCommand = new OpenCommand();
        createCommand = new CreateCommand();
        databaseHandler = new DatabaseHandler();
    }

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        Player player = sender instanceof Player ? (Player) sender : null;

        if (player != null) {
            if (args.length < 1) HelpCommand.PluginInfo(player);
            else if (args[0].equals("create")) {
                createCommand.createShop(player);
            } else if (args[0].equals("open")) {
                player.openInventory(openCommand.openShop(1));
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