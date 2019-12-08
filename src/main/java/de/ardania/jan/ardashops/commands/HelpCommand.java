package de.ardania.jan.ardashops.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static de.ardania.jan.ardashops.Main.PLUGIN;
import static de.ardania.jan.ardashops.handler.MessageHandler.MESSAGE;

public class HelpCommand {
    public static void PluginInfo(Player player) {
        player.sendMessage(MESSAGE.getString("PLUGIN_PREFIX") + ChatColor.GOLD + PLUGIN.getDescription().getVersion() + " by " + PLUGIN.getDescription().getAuthors().get(0));
    }
}
