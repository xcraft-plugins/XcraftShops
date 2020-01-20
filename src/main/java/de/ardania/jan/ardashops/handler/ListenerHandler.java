package de.ardania.jan.ardashops.handler;

import de.ardania.jan.ardashops.listeners.BuyItemListener;
import de.ardania.jan.ardashops.listeners.SellItemListener;
import org.bukkit.event.Listener;

import static de.ardania.jan.ardashops.Main.PLUGIN;

public class ListenerHandler {

    public ListenerHandler() {
        registerListener(new BuyItemListener());
        registerListener(new SellItemListener());
    }

    /**
     * Helper method to register the {@link Listener}
     *
     * @param listener - the {@link Listener} to register
     */
    public void registerListener(Listener listener) {
        PLUGIN.getServer().getPluginManager().registerEvents(listener, PLUGIN);
    }
}
