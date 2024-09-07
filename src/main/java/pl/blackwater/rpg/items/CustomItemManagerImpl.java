package pl.blackwater.rpg.items;

import org.bukkit.inventory.ItemStack;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.storage.RPGItem;
import pl.blackwater.rpg.util.Base64Util;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;


public class CustomItemManagerImpl implements CustomItemManager{
    private final RPG plugin;
    private final Map<UUID, ItemStack> items;
    private final HashMap<ItemStack, UUID> itemsByItemStack;

    public CustomItemManagerImpl(RPG plugin){
        this.plugin = plugin;
        this.items = new HashMap<>();

        this.itemsByItemStack = new HashMap<>();
    }

    @Override
    public void createItem(RPGItem rpgItem, StorageType type) {
        final UUID randomUUID = UUID.randomUUID();

        final ItemStack itemStack = rpgItem.build();
        final String data = Base64Util.singleItemStackToBase64(itemStack);

        if(type.equals(StorageType.REDIS)){
            //SAVE DATA TO REDIS

            plugin.getRedisStorage().RPG_ITEMS.putAsync(randomUUID,data);


            //TODO remove debug
            System.out.println("SAVED DATA TO REDIS #" + System.currentTimeMillis());
        }
        if (type.equals(StorageType.CONFIG)){
            plugin.getCustomItemStorage().getCustomItems().put(randomUUID.toString(),data);
        }

        items.put(randomUUID,itemStack);
        getItemsByItemStackKey().put(itemStack,randomUUID);
    }

    @Override
    public Map<UUID, ItemStack> getItems() {
        return items;
    }

    @Override
    public Map<ItemStack, UUID> getItemsByItemStackKey() {
        return itemsByItemStack;
    }

    @Override
    public void deleteItem(UUID uuid) {
        final ItemStack itemStack = items.remove(uuid);
        itemsByItemStack.remove(itemStack);

        plugin.getRedisStorage().RPG_ITEMS.remove(uuid);

        //WARNING OF 2 STRING TYPE KEYS <String,String>
        plugin.getCustomItemStorage().getCustomItems().remove(uuid);
    }

    @Override
    public void setup() {
        //REDIS DATA LOAD
        final AtomicInteger i = new AtomicInteger();

        plugin.getRedisStorage().RPG_ITEMS.forEach((uuid, s) -> {
            items.put(uuid, Base64Util.singleItemStackFromBase64(s));
            itemsByItemStack.put(items.get(uuid),uuid);

            i.getAndIncrement();
        });

        //CONFIG DATA LOAD

        plugin.getCustomItemStorage().getCustomItems().forEach((s, s2) -> {
            final UUID u = UUID.fromString(s);

            items.put(u,Base64Util.singleItemStackFromBase64(s2));
            itemsByItemStack.put(items.get(u),u);

            i.getAndIncrement();
        });

        plugin.getLogger().log(Level.INFO,"Loaded (" + i.get() + ") RPG ITEMS");
    }
}
