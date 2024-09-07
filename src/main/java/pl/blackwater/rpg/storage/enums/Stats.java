package pl.blackwater.rpg.storage.enums;

public enum Stats {



    STRENGTH("STRENGTH"),
    DEXTERITY("DEXTERITY"),
    INTELLIGENCE("INTELLIGENCE"),
    STAMINA("STAMINA");

    private final String type;
    Stats(String type) {
        this.type = type;
    }

    public static String getType(Stats stats){
        return stats.type;
    }

    public String getType() {
        return type;
    }
}
