package de.ardania.jan.ardashops.util;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SerializersAndDeserializers {

    public static byte[] serializeObjectToByteArray(Object obj) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            BukkitObjectOutputStream boos = new BukkitObjectOutputStream(outputStream);
            boos.writeObject(obj);
        } catch (IOException ex) {
            throw new IllegalStateException("unable to save item meta", ex);
        }
        return outputStream.toByteArray();
    }

    public static <T> T deserializeByteArrayToObject(byte[] data, Class<T> cls) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        try {
            BukkitObjectInputStream bois = new BukkitObjectInputStream(inputStream);
            return (T) bois.readObject();
        } catch (IOException ex) {
            throw new IllegalStateException("unable to read item meta", ex);
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException("unable to read item meta", ex);
        }
    }
}
