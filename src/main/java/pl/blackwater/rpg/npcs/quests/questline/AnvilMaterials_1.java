package pl.blackwater.rpg.npcs.quests.questline;

import org.bukkit.inventory.ItemStack;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.items.AnvilMaterials;
import pl.blackwater.rpg.npcs.quests.GatheringQuest;
import pl.blackwater.rpg.npcs.quests.rewards.ItemReward;
import pl.blackwater.rpg.npcs.quests.rewards.Reward;
import pl.blackwater.rpg.npcs.quests.rewards.StatReward;
import pl.blackwater.rpg.storage.enums.Stats;

public class AnvilMaterials_1 extends GatheringQuest {
    public AnvilMaterials_1() {
        super("MATERIALY RZEMIESLNICZE 1", "KOLEKCJONER",
                new ItemStack[]{AnvilMaterials.ODLAMEK_LUKU_LOWCY.getItemWithAmount(32),AnvilMaterials.SKORA_LOWCY.getItemWithAmount(64)},
                new Reward[]{
                        new ItemReward(new ItemStack[]{
                        RPG.getPlugin().getSpecialItems().getSpecialItemWithAmount("AMD I",32),
                        RPG.getPlugin().getSpecialItems().getSpecialItemWithAmount("AMD II",16),
                        RPG.getPlugin().getSpecialItems().getSpecialItemWithAmount("AMD III",8),
                        RPG.getPlugin().getSpecialItems().getSpecialItemWithAmount("AMD IV",8),
                }),
                new StatReward(Stats.INTELLIGENCE,10)});
    }
}
