package de.ardania.jan.ardashops;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class FileHandler {

    public static Map<String, Shop> SHOPS = new HashMap<String, Shop>();
    File filepath = new File(Main.PLUGIN.getDataFolder() + File.separator + "shops");

    public FileHandler(){
        createDir();
        getShops();
    }

    public void getShops(){
        double step = 100.0/filepath.listFiles().length;
        double prog = 0.0;
        DecimalFormat df = new DecimalFormat("#.##");
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            for (File f :
                    filepath.listFiles()) {
                Shop shop = mapper.readValue(f, Shop.class);
                SHOPS.put(shop.getShopOwner(), shop);
                System.out.print("\r");
                System.out.print("Loading Shops... " + df.format(prog += step) + "%");
            }
            System.out.println();
            System.out.println("Loading Shops done!");
            System.out.println("Shops Loaded: " + SHOPS.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createDir(){
        filepath.mkdirs();
    }
}
