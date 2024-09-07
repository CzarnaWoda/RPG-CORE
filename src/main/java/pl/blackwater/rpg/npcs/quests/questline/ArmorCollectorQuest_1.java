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

public class ArmorCollectorQuest_1 extends GatheringQuest {

    /*
            final RPGItem helmet1 = new RPGItem(Material.LEATHER_HELMET,"&3HELM MROCZNEGO CIENIA",RPGClass.WARRIOR.name(),8,12,0,0,0,6,0,4.5f,0,6.5f,1.0f,1.0f,15,0,0,18,true, ItemType.EQUIPMENT,null);
        final RPGItem chestplate1 = new RPGItem(Material.LEATHER_CHESTPLATE,"&3ZBROJA MROCZNEGO CIENIA",RPGClass.WARRIOR.name(),6,12,4,0,0,12,0,2.0f,0,0f,0f,0f,20,0,0,30,true, ItemType.EQUIPMENT,null);
        final RPGItem leggings1 = new RPGItem(Material.LEATHER_LEGGINGS,"&3SPODNIE MROCZNEGO CIENIA",RPGClass.WARRIOR.name(),4,8,0,0,0,5,0,0,0,2.0f,0f,0f,15,0,0,25,true, ItemType.EQUIPMENT,null);
        final RPGItem boots1 = new RPGItem(Material.LEATHER_BOOTS,"&3BUTY MROCZNEGO CIENIA",RPGClass.WARRIOR.name(),5,8,0,0,0,4,0,0,0,2.0f,0f,0f,10,0,0,5,true, ItemType.EQUIPMENT,null);
        final RPGItem sword = new RPGItem(Material.WOODEN_SWORD,"&aMIECZ MROCZNEGO CIENIA",RPGClass.WARRIOR.name(),9,0,20,2,0,8,0,2.0f,0,6.0f,2.5f,2.0f,15,5,0,28,true, ItemType.EQUIPMENT,null);

     */
    public ArmorCollectorQuest_1() {
        super("KOLEKCJA CIENIA", "MISTRZ CZELADNIK",new ItemStack[]{
                        new RPGItem(Material.LEATHER_HELMET,"&3HELM MROCZNEGO CIENIA", RPGClass.WARRIOR.name(),8,12,0,0,0,6,0,4.5f,0,6.5f,1.0f,1.0f,15,0,0,18,true, ItemType.EQUIPMENT,null).build(),
                        new RPGItem(Material.LEATHER_CHESTPLATE,"&3ZBROJA MROCZNEGO CIENIA",RPGClass.WARRIOR.name(),6,12,4,0,0,12,0,2.0f,0,0f,0f,0f,20,0,0,30,true, ItemType.EQUIPMENT,null).build(),
                        new RPGItem(Material.LEATHER_LEGGINGS,"&3SPODNIE MROCZNEGO CIENIA",RPGClass.WARRIOR.name(),4,8,0,0,0,5,0,0,0,2.0f,0f,0f,15,0,0,25,true, ItemType.EQUIPMENT,null).build(),
                        new RPGItem(Material.LEATHER_BOOTS,"&3BUTY MROCZNEGO CIENIA",RPGClass.WARRIOR.name(),5,8,0,0,0,4,0,0,0,2.0f,0f,0f,10,0,0,5,true, ItemType.EQUIPMENT,null).build(),
                        new RPGItem(Material.WOODEN_SWORD,"&aMIECZ MROCZNEGO CIENIA",RPGClass.WARRIOR.name(),9,0,20,2,0,8,0,2.0f,0,6.0f,2.5f,2.0f,15,5,0,28,true, ItemType.EQUIPMENT,null).build()
                }
                , new Reward[]{
                        new ItemReward(new ItemStack[]{
                                RPG.getPlugin().getSpecialItems().getSpecialItemWithAmount("ACD I",24),
                                RPG.getPlugin().getSpecialItems().getSpecialItemWithAmount("ACD II",12),
                                RPG.getPlugin().getSpecialItems().getSpecialItemWithAmount("ACD III",8),
                                RPG.getPlugin().getSpecialItems().getSpecialItemWithAmount("ACD IV",6),
                        }),
                        new StatReward(Stats.STRENGTH,15)
                });
    }
}
