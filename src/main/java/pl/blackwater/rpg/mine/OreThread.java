package pl.blackwater.rpg.mine;

import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.mine.enums.OreType;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.ItemBuilder;
import pl.blackwater.spigotplugin.util.RandomUtil;

import java.util.Optional;


@RequiredArgsConstructor
public class OreThread extends BukkitRunnable {
    private final RPG plugin;
    @Override
    public void run() {
        for(OreLocation oreLocation : plugin.getOreStorage().getOreLocationList()){
            final Optional<Ore> optionalOre = plugin.getOreStorage().getOres().stream().filter(ore -> ore.getLocation().equals(oreLocation.getLocation())).findAny();
            if(!optionalOre.isPresent()){
                final OreType type = OreType.values()[RandomUtil.getRandInt(0,OreType.values().length-1)];
                final Ore ore = new Ore(oreLocation.getLocation(), type,type.getItemStack(),type.getPickaxeLevel(),0,type.getAmount());
                final Block block = ore.getBlock();
                block.setType(type.getBlockType());
                ore.setBlock(block);

                plugin.getOreStorage().getOres().add(ore);

                ore.createHologram(oreLocation.getUuid().toString());
            }
        }
    }
}
