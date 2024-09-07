package pl.blackwater.rpg.npcs.tags;

public enum ScoreboardAction {

    CREATE(0),
    RESET(1),
    UPDATE(2);

    private final int value;

    ScoreboardAction(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }


}
