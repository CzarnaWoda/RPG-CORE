package pl.blackwater.rpg.mine;


import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.rpg.mine.enums.OreType;
import pl.blackwater.rpg.util.BlockLocationUtil;

import java.io.Serializable;
import java.util.Locale;

@Getter
public class Ore implements Serializable {

    private Location location;
    private OreType oreType;
    private ItemStack oreItem;
    private int pickaxeLevel;

    private int progress;
    private int amount;

    private Block block;

    private transient Hologram hologram;

    public Ore(Location location, OreType oreType, ItemStack oreItem, int pickaxeLevel, int progress, int amount) {
        this.location = location;
        this.oreType = oreType;
        this.oreItem = oreItem;
        this.pickaxeLevel = pickaxeLevel;
        this.progress = progress;
        this.amount = amount;

        location.getBlock().setType(Material.AIR);

        block = location.getBlock();

    }

    public void createHologram(String hologramName){
        this.hologram = DHAPI.createHologram(hologramName, location.clone().add(0.5,3,0.5),false);
        hologram.setAlwaysFacePlayer(true);

        DHAPI.addHologramLine(hologram,oreItem);
        DHAPI.addHologramLine(hologram,"#dfed91⛏ #f5c52aRUDA " + oreType.name().replaceAll("_"," ") + " #dfed91⛏");
        DHAPI.addHologramLine(hologram,"#dfed91⛏ #d1b96bZACZNIJ KOPAC RUDE #dfed91⛏");
        DHAPI.addHologramLine(hologram,"#dfed91⛏ #f5c52aRUDA " + oreType.name().replaceAll("_"," ") + " #dfed91⛏");
        DHAPI.addHologramLine(hologram,oreItem);
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public void addProgress(int i){
        this.progress += i;
    }

    public void removeHologram(){
        DHAPI.removeHologram(hologram.getName());
    }

}
