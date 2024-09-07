package pl.blackwater.rpg.items;

import io.lumine.mythic.core.drops.Drop;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.mysterybox.MysteryBox;

import java.util.HashMap;
import java.util.Map;

public class DropItemManager implements ItemManager{

    public Map<String,DropItem> items;

    public DropItemManager(){
        items = new HashMap<>();

        setup();
    }
    @Override
    public Map<String, DropItem> getItems() {
        return items;
    }

    @Override
    public DropItem getItemByName(String name) {
        for(DropItem item : items.values()) {
            if(item.getItemBuilder().getTitle().contains(name)){
                return item;
            }
        }
        return null;
    }

    @Override
    public DropItem[] getItemsAsArray() {
        return (DropItem[]) items.values().toArray();
    }


    @Override
    public void setup() {
        RPG.getPlugin().getDropItemConfig().getDropItems().forEach(dropItem -> {
                dropItem.setFROM_WHAT(dropItem.FROM_WHAT);
                items.put(dropItem.getItemBuilder().getTitle(),dropItem);
        });
        for(MysteryBox mysteryBox : RPG.getPlugin().getMysteryBoxStorage().getMysteryBoxList()){
            if(mysteryBox.getBoxName().equals("#eb7434★ #ebdc34SKRZYNIA LOWCY #eb7434★")){
                final DropItem dropItem = new DropItem(mysteryBox.getBoxItemBuilder(),"MROCZNY LOWCA",2.0);

                items.put(dropItem.getItemBuilder().getTitle(),dropItem);
            }
            if(mysteryBox.getBoxName().equals("#eb7434★ #9450faSKRZYNIA UMARLYCH #eb7434★")){
                final DropItem dropItem = new DropItem(mysteryBox.getBoxItemBuilder(), "GHOUL",2.0);

                items.put(dropItem.getItemBuilder().getTitle(),dropItem);
            }

        }
    }
}
