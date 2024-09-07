package pl.blackwater.rpg.npcs.quests.questline;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.enums.RPGClass;
import pl.blackwater.rpg.items.ItemType;
import pl.blackwater.rpg.npcs.quests.GatheringQuest;
import pl.blackwater.rpg.npcs.quests.rewards.ItemReward;
import pl.blackwater.rpg.npcs.quests.rewards.Reward;
import pl.blackwater.rpg.storage.RPGItem;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.ItemBuilder;

public class HunterQuest1 extends GatheringQuest {
    public HunterQuest1() {
        super("ZDOBADZ SKORY", "Lowca",
                new ItemStack[]{RPG.getPlugin().getDropItemConfig().getDropItems().get(0).getItemBuilder().setAmount(64).build()},
                new Reward[]{new ItemReward(new ItemStack[]{
                        new RPGItem(Material.GOLD_NUGGET, ChatUtil.hexColor("#42f595KAMIEN LOWCOW"), RPGClass.HUNTER.name(),2,0,0,0,0,0,0,5.0f,2.5f,5.0f,3.5f,3.0f,0,20,0,10,true, ItemType.JEWELERY,null).build()
                })});
    }
}
