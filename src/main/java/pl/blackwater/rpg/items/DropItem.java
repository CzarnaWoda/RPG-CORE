package pl.blackwater.rpg.items;

import org.bukkit.inventory.ItemStack;
import pl.blackwater.spigotplugin.util.ItemBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DropItem implements Serializable {

    public ItemBuilder itemBuilder;

    public transient ItemStack itemStack;

    public String FROM_WHAT;

    public double chance;


    public DropItem(){}
    public DropItem(ItemBuilder itemBuilder,String FROM_WHAT, double chance){
        this.itemBuilder = itemBuilder;


        this.itemStack = itemBuilder.build();

        this.chance = chance;
        this.FROM_WHAT = FROM_WHAT;
    }

    public ItemBuilder getItemBuilder() {
        return itemBuilder;
    }


    public String getFROM_WHAT() {
        return FROM_WHAT;
    }

    public void setFROM_WHAT(String FROM_WHAT) {
        this.FROM_WHAT = FROM_WHAT;
    }

    public double getChance() {
        return chance;
    }
}
