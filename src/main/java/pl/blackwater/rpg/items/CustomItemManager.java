package pl.blackwater.rpg.items;

import org.bukkit.inventory.ItemStack;
import pl.blackwater.rpg.storage.RPGItem;

import java.util.Map;
import java.util.UUID;

public interface CustomItemManager {


    void createItem(RPGItem rpgItem, StorageType type);

    Map<UUID,ItemStack> getItems();

    Map<ItemStack, UUID> getItemsByItemStackKey();

    void deleteItem(UUID uuid);

    void setup();


}
