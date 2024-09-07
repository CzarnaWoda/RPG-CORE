package pl.blackwater.rpg.storage.enums;

import org.bukkit.Material;

public enum SlotTypes {

    MAIN_HAND("HAND"),
    OFFHAND("OFF_HAND"),
    FEET("FEET"),
    LEGS("LEGS"),
    CHEST("CHEST"),
    HEAD("HEAD");

    private final String slot;
    SlotTypes(String slot) {
        this.slot = slot;
    }

    public String getSlot() {
        return slot;
    }

    public static String getSlotType(Material material){
        if(material.toString().endsWith("_HELMET")){
            return HEAD.getSlot();
        }
        if(material.toString().endsWith("_BOOTS")){
            return FEET.getSlot();
        }
        if(material.toString().endsWith("_SWORD") ||  material.toString().endsWith("_AXE") || material.equals(Material.BOW) || material.equals(Material.CROSSBOW)){
            return MAIN_HAND.getSlot();
        }
        if(material.toString().endsWith("_CHESTPLATE")){
            return CHEST.getSlot();
        }
        if(material.toString().endsWith("_LEGGINGS")){
            return LEGS.getSlot();
        }
        return OFFHAND.getSlot();
    }
}
