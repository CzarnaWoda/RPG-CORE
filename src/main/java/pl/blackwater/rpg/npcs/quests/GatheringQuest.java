package pl.blackwater.rpg.npcs.quests;


import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.rpg.npcs.quests.enums.QuestType;
import pl.blackwater.rpg.npcs.quests.rewards.Reward;
import pl.blackwater.rpg.util.PlayerInventoryUtil;

import java.util.Optional;


public abstract class GatheringQuest extends Quest {

    private final ItemStack[] questItems;
    private final Reward[] rewards;


    public GatheringQuest(String name, String npc, ItemStack[] questItems, Reward[] rewards) {
        super(name, QuestType.GATHERING, npc, rewards);

        this.questItems = questItems;
        this.rewards = rewards;

        QuestStorage.getGatheringQuests().add(this);
    }

    @Override
    public boolean checkQuestStatus(Player player) {
        boolean status = true;
        for(ItemStack item : questItems){
            if(!PlayerInventoryUtil.checkItems(player,item,item.getAmount())){
                status = false;
            }
        }
        return !status;
    }

    @Override
    public void doQuestAction(Player player) {
        for(ItemStack item : questItems){
            PlayerInventoryUtil.removeItems(player,item,item.getAmount());
        }
        for(Reward reward : rewards){
            reward.giveReward(player);
        }
        final RPGUser user = RPG.getPlugin().getRpgUserManager().getUser(player.getUniqueId());
        final Optional<QuestData> questDataOptional = user.getQuestDataSet().stream().filter(questData -> questData.getQuestName().equalsIgnoreCase(getName())).findFirst();
        questDataOptional.ifPresent(questData -> questData.setStatus(true));
    }

    public ItemStack[] getQuestItems() {
        return questItems;
    }
}
