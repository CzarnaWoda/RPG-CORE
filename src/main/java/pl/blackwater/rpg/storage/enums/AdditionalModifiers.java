package pl.blackwater.rpg.storage.enums;

public enum AdditionalModifiers {

    AMD("averageMonsterDamage"),
    AHD("averageHumanDamage"),
    ACD("averageCreatureDamage"),
    ADOCH("additionalDamageOfCriticalChance"),
    ACH("additionalCriticalChance");

    private final String type;
    AdditionalModifiers(String type) {
        this.type = type;
    }

    public static String getType(AdditionalModifiers am){
        return am.type;
    }

    public String getType() {
        return type;
    }
}
