package pl.blackwater.rpg.npcs.quests.questline;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.enums.RPGClass;
import pl.blackwater.rpg.items.ItemType;
import pl.blackwater.rpg.npcs.quests.GatheringQuest;
import pl.blackwater.rpg.npcs.quests.rewards.ItemReward;
import pl.blackwater.rpg.npcs.quests.rewards.Reward;
import pl.blackwater.rpg.npcs.quests.rewards.StatReward;
import pl.blackwater.rpg.storage.RPGItem;
import pl.blackwater.rpg.storage.enums.Stats;

public class ArmorCollectorQuest_2 extends GatheringQuest {
    /*
            final RPGItem helmet = new RPGItem(Material.LEATHER_HELMET,"&3HELM MROCZNEGO LOWCY", RPGClass.HUNTER.name(),5,10,0,5,0,6,0,2.5f,0,5.0f,1.0f,1.0f,5,15,0,8,true, ItemType.EQUIPMENT,null);
        final RPGItem chestplate = new RPGItem(Material.LEATHER_CHESTPLATE,"&3ZBROJA MROCZNEGO LOWCY",RPGClass.HUNTER.name(),7,4,4,0,0,6,0,2.0f,0,0f,0f,0f,4,15,0,15,true, ItemType.EQUIPMENT,null);
        final RPGItem leggings = new RPGItem(Material.LEATHER_LEGGINGS,"&3SPODNIE MROCZNEGO LOWCY",RPGClass.HUNTER.name(),4,4,0,0,0,1,0,0,0,0f,0f,0f,0,10,0,20,true, ItemType.EQUIPMENT,null);
        new RPGItem(Material.LEATHER_BOOTS,"&3BUTY MROCZNEGO LOWCY",RPGClass.HUNTER.name(),6,2,0,0,0,1,0,0,0,0f,0f,0f,0,10,0,16,true, ItemType.EQUIPMENT,null);
        new RPGItem(Material.BOW,"&aLUK MROCZNEGO LOWCY",RPGClass.HUNTER.name(),7,0,18,6,0,4,0,5.0f,0,2.0f,4.5f,5.0f,0,35,0,15,true, ItemType.EQUIPMENT,null);

     */
    public ArmorCollectorQuest_2() {
        super("KOLEKCJA LOWCOW", "MISTRZ CZELADNIK",new ItemStack[]{
                        new RPGItem(Material.LEATHER_HELMET,"&3HELM MROCZNEGO LOWCY", RPGClass.HUNTER.name(),5,10,0,5,0,6,0,2.5f,0,5.0f,1.0f,1.0f,5,15,0,8,true, ItemType.EQUIPMENT,null).build(),
                        new RPGItem(Material.LEATHER_CHESTPLATE,"&3ZBROJA MROCZNEGO LOWCY",RPGClass.HUNTER.name(),7,4,4,0,0,6,0,2.0f,0,0f,0f,0f,4,15,0,15,true, ItemType.EQUIPMENT,null).build(),
                        new RPGItem(Material.LEATHER_LEGGINGS,"&3SPODNIE MROCZNEGO LOWCY",RPGClass.HUNTER.name(),4,4,0,0,0,1,0,0,0,0f,0f,0f,0,10,0,20,true, ItemType.EQUIPMENT,null).build(),
                        new RPGItem(Material.LEATHER_BOOTS,"&3BUTY MROCZNEGO LOWCY",RPGClass.HUNTER.name(),6,2,0,0,0,1,0,0,0,0f,0f,0f,0,10,0,16,true, ItemType.EQUIPMENT,null).build(),
                        new RPGItem(Material.BOW,"&aLUK MROCZNEGO LOWCY",RPGClass.HUNTER.name(),7,0,18,6,0,4,0,5.0f,0,2.0f,4.5f,5.0f,0,35,0,15,true, ItemType.EQUIPMENT,null).build()
                }
                , new Reward[]{
                        new ItemReward(new ItemStack[]{
                                RPG.getPlugin().getSpecialItems().getSpecialItemWithAmount("ACD I",24),
                                RPG.getPlugin().getSpecialItems().getSpecialItemWithAmount("ACD II",12),
                                RPG.getPlugin().getSpecialItems().getSpecialItemWithAmount("ACD III",8),
                                RPG.getPlugin().getSpecialItems().getSpecialItemWithAmount("ACD IV",6),
                        }),
                        new StatReward(Stats.DEXTERITY,15)
                });
    }
}
