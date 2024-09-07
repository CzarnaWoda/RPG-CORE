package pl.blackwater.rpg.items;

import pl.blackwater.rpg.RPG;

import java.util.HashMap;
import java.util.Map;

public class BoxItemManager implements ItemManager{

    private final Map<String,DropItem> items;

    public BoxItemManager(){
        items = new HashMap<>();

        setup();
    }
    @Override
    public Map<String, DropItem> getItems() {
        return items;
    }

    @Override
    public DropItem getItemByName(String name) {
        return items.get(name);
    }

    @Override
    public DropItem[] getItemsAsArray() {
        return items.values().toArray(new DropItem[0]);
    }

    @Override
    public void setup() {
        RPG.getPlugin().getDropItemConfig().getDropItems().forEach(dropItem ->  {
            if(dropItem.getFROM_WHAT().startsWith("CASE:")){
                dropItem.setFROM_WHAT(dropItem.getFROM_WHAT().replace("CASE:",""));
                items.put(dropItem.getItemBuilder().getTitle(),dropItem);
            }
        });
    }
}
