package pl.blackwater.rpg.npcs.quests;

import pl.blackwater.rpg.npcs.quests.questline.*;

import java.util.HashSet;
import java.util.Set;

public class QuestStorage {

    private static final Set<GatheringQuest> gatheringQuests =  new HashSet<>();

    private static final Set<MobHuntQuest> mobHuntQuests = new HashSet<>();

    public QuestStorage(){
        new HunterQuest1();
        new HuntLowcaQuest();
        new ArmorCollectorQuest_1();
        new ArmorCollectorQuest_2();
        new AnvilMaterials_1();
    }

    public static Set<GatheringQuest> getGatheringQuests() {
        return gatheringQuests;
    }

    public static Set<MobHuntQuest> getMobHuntQuests() {
        return mobHuntQuests;
    }
}
