package pl.blackwater.rpg.npcs.quests.questline;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.rpg.data.enums.RPGClass;
import pl.blackwater.rpg.items.ItemType;
import pl.blackwater.rpg.npcs.quests.MobHuntQuest;
import pl.blackwater.rpg.npcs.quests.rewards.ItemReward;
import pl.blackwater.rpg.npcs.quests.rewards.Reward;
import pl.blackwater.rpg.storage.RPGItem;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.ItemBuilder;

public class HuntLowcaQuest extends MobHuntQuest {
    public HuntLowcaQuest() {
        super("UPOLUJ MROCZNYCH LOWCOW!", "Lowca", new Reward[]{new ItemReward(new ItemStack[]{
                new RPGItem(Material.IRON_NUGGET, ChatUtil.hexColor("#42f595KAMIEN WOJOWNIKOW"), RPGClass.WARRIOR.name(),2,0,0,0,0,0,3,5.0f,2.5f,5.0f,3.5f,3.0f,15,0,0,25,true, ItemType.JEWELERY,null).build()
        })}, "MROCZNY LOWCA", 256);
    }
}
