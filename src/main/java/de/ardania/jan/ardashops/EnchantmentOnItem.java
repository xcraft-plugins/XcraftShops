package de.ardania.jan.ardashops;

import org.bukkit.enchantments.Enchantment;

import java.util.HashMap;
import java.util.Map;

public class EnchantmentOnItem {

    Item item;
    Map<Enchantment, Integer> enchantmentsOnItem= new HashMap<Enchantment, Integer>();

    public EnchantmentOnItem(Item item){
        this.item = item;
        enchantmentsOnItem();
    }

    public void enchantmentsOnItem(){
        for (Map.Entry<String, Integer> entrySet :
                item.getEnchantments().entrySet()) {
            enchantmentsOnItem.put(Enchantment.getByName(entrySet.getKey()), entrySet.getValue());
        }
    }

    public Map<Enchantment, Integer> getEnchantmentsOnItem() {
        return enchantmentsOnItem;
    }
}
