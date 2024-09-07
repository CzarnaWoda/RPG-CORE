package pl.blackwater.rpg.mysterybox;


import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class MysteryBoxItem {

    private final ItemStack itemStack;
    private final double chance;

    public ItemStack getItemStack() {
        return itemStack;
    }

    public double getChance() {
        return chance;
    }
}
