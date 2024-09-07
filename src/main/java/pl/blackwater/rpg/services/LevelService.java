package pl.blackwater.rpg.services;

import pl.blackwater.rpg.data.RPGUser;

public abstract class LevelService {

    public void doLevelAction(RPGUser rpgUser, double exp, LevelActionEnum actionEnum){
        switch (actionEnum){
            case ADD_EXP:
                rpgUser.addExp(exp);
                break;
            case REMOVE_EXP:
                rpgUser.removeExp(exp);
                break;
            case ADD_LEVEL:
                rpgUser.addLevel(1);
                break;
            case RESET_EXP:
                rpgUser.setExp(0.0);
                break;
        }
    }
}
