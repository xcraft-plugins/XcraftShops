package de.ardania.jan.ardashops.handler;

import de.ardania.jan.ardashops.listeners.BuyAndSellInvListener;
import de.ardania.jan.ardashops.listeners.CloseInvListener;
import de.ardania.jan.ardashops.listeners.EditInvItemListener;
import de.ardania.jan.ardashops.listeners.ShopInvItemListener;
import org.bukkit.event.Listener;

import static de.ardania.jan.ardashops.Main.PLUGIN;

public class ListenerHandler {

    public ListenerHandler() {
        registerListener(new BuyAndSellInvListener());
        registerListener(new CloseInvListener());
        registerListener(new EditInvItemListener());
        registerListener(new ShopInvItemListener());
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
