package pl.blackwater.rpg.configs;


import org.bukkit.Material;
import pl.blackwater.api.config.Config;
import pl.blackwater.api.config.annotation.ConfigName;
import pl.blackwater.rpg.data.enums.RPGClass;
import pl.blackwater.rpg.items.ItemType;
import pl.blackwater.rpg.storage.RPGItem;
import pl.blackwater.rpg.util.Base64Util;

import java.util.*;

@ConfigName("itemstorage.yml")
public interface CustomItemStorage extends Config {

    default Map<String,String> getCustomItems(){
        final RPGItem rpgItem = new RPGItem(Material.DIAMOND_SWORD,"TESt", RPGClass.WARRIOR.name(),1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,true, ItemType.EQUIPMENT,null);
        final Map<String,String> config = new HashMap<>();
        config.put(UUID.randomUUID().toString(), Base64Util.singleItemStackToBase64(rpgItem.build()));
        return config;
    }
}
