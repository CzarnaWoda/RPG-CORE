package pl.blackwater.rpg.customenchants;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class RegenerationEnchantment extends Enchantment {
    public RegenerationEnchantment(@NotNull NamespacedKey key) {
        super(key);
    }

    @NotNull
    @Override
    public String getName() {
        return "Regeneracja";
    }

    @Override
    public int getMaxLevel() {
        return 6;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @NotNull
    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.WEARABLE;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(@NotNull Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack itemStack) {
        return itemStack.getType().isItem();
    }
}
