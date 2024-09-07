package pl.blackwater.rpg.npcs.quests;


import lombok.Data;
import pl.blackwater.rpg.npcs.quests.enums.QuestType;

import java.io.Serializable;

@Data
public class QuestData implements Serializable {

    private String questName;
    private int progress;
    private int amount;

    private boolean status;
    private QuestType questType;

    public QuestData(){}

    public QuestData(String questName,QuestType type, int progress, int amount){
        this.questName = questName;
        this.questType = type;
        this.progress = progress;
        this.amount = amount;
        this.status = false;
    }

    public void addQuestProgress(int i){
        this.progress += i;
    }
}
