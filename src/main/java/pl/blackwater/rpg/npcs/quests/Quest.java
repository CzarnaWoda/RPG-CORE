package pl.blackwater.rpg.npcs.quests;

import org.bukkit.entity.Player;
import pl.blackwater.rpg.npcs.quests.enums.QuestType;
import pl.blackwater.rpg.npcs.quests.rewards.Reward;

public abstract class Quest{
    private final String name;
    private final QuestType type;
    private final String npc;
    private final Reward[] rewards;


    public Quest(String name, QuestType questType, String npc, Reward[] rewards){
        this.name = name;
        this.type = questType;
        this.npc = npc;
        this.rewards = rewards;

    }

    public abstract boolean checkQuestStatus(Player player);

    public abstract void doQuestAction(Player player);

    public String getName() {
        return name;
    }

    public QuestType getType() {
        return type;
    }

    public Reward[] getRewards() {
        return rewards;
    }

    public String getNpc() {
        return npc;
    }
}
