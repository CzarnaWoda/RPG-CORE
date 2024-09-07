package pl.blackwater.rpg.mysterybox;


import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

public class MysteryBoxDrop implements Serializable {


    private ItemStack itemStack;
    public String FROM_WHAT;
    public double chance;

    public MysteryBoxDrop(ItemStack itemStack, String FROM_WHAT, double chance){
        this.itemStack = itemStack;
        this.FROM_WHAT = FROM_WHAT;
        this.chance = chance;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public String getFROM_WHAT() {
        return FROM_WHAT;
    }

    public double getChance() {
        return chance;
    }
}
