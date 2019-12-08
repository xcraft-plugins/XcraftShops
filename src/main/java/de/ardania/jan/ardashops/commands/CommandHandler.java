package de.ardania.jan.ardashops.commands;

import de.ardania.jan.ardashops.handler.DatabaseHandler;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import java.sql.SQLException;

public class CommandHandler implements CommandExecutor {


    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        Player player = sender instanceof Player ? (Player) sender : null;

        if (player != null) {
            if (args.length < 1) HelpCommand.PluginInfo(player);
            else if (args[0].equals("create")) {
                CreateCommand.createShop(player);
            } else if (args[0].equals("open")) {
                OpenCommand openCommand = new OpenCommand(player);
                player.openInventory(openCommand.getPage());
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