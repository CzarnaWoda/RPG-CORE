package pl.blackwater.rpg.items;

public enum ItemType {

    DROPS,
    EQUIPMENT,
    JEWELERY;

    //Method to avoid getting exception when there is no value with this name
    public static ItemType fromString(String name){
        for (ItemType value : values()) {
            if(value.name().equals(name)){
                return value;
            }
        }
        return null;
    }

}
