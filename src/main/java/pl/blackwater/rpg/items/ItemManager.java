package pl.blackwater.rpg.items;

import java.util.Map;
import java.util.Set;

public interface ItemManager {


    Map<String,DropItem> getItems();

    DropItem getItemByName(String name);

    DropItem[] getItemsAsArray();

    void setup();

}
