package pl.blackwater.rpg.npcs.quests;

import org.bukkit.entity.Player;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.rpg.npcs.quests.enums.QuestType;
import pl.blackwater.rpg.npcs.quests.rewards.Reward;

import java.util.Optional;

public class MobHuntQuest extends Quest{

    private final String mobName;
    private final int huntAmount;

    public MobHuntQuest(String name, String npc, Reward[] rewards, String mobName, int huntAmount) {
        super(name, QuestType.MOB_HUNT, npc, rewards);

        this.mobName = mobName;
        this.huntAmount = huntAmount;

        QuestStorage.getMobHuntQuests().add(this);
    }

    @Override
    public boolean checkQuestStatus(Player player) {
        final RPGUser user = RPG.getPlugin().getRpgUserManager().getUser(player.getUniqueId());
        final QuestData questData = user.getQuestDataSet().stream().filter(data -> data.getQuestName().equalsIgnoreCase(getName())).findAny().get();
        return questData.getProgress() < getHuntAmount();
    }

    @Override
    public void doQuestAction(Player player) {
        for(Reward reward : getRewards()){
            reward.giveReward(player);
        }
        final RPGUser user = RPG.getPlugin().getRpgUserManager().getUser(player.getUniqueId());
        final Optional<QuestData> quest = user.getQuestDataSet().stream().filter(questData -> questData.getQuestName().equalsIgnoreCase(this.getName())).findFirst();
        quest.ifPresent(questData1 -> questData1.setStatus(true));
    }

    public int getHuntAmount() {
        return huntAmount;
    }

    public String getMobName() {
        return mobName;
    }
}
