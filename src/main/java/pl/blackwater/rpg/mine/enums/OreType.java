package pl.blackwater.rpg.mine.enums;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.ItemBuilder;

public enum OreType {


    DIAMENTU(new ItemBuilder(Material.DIAMOND).setTitle(ChatUtil.hexColor("#0DFFEFODLAMEK DIAMENTU")).addEnchantment(Enchantment.DURABILITY,1).addFlag(ItemFlag.HIDE_ATTRIBUTES).addFlag(ItemFlag.HIDE_ENCHANTS).build(),1,550,Material.DIAMOND_ORE),
    SZMARAGDU(new ItemBuilder(Material.EMERALD).setTitle(ChatUtil.hexColor("#8DFF8BODLAMEK SZMARAGDU")).addEnchantment(Enchantment.DURABILITY,1).addFlag(ItemFlag.HIDE_ATTRIBUTES).addFlag(ItemFlag.HIDE_ENCHANTS).build(),1,400,Material.EMERALD_ORE),
    MIEDZI(new ItemBuilder(Material.BROWN_DYE).setTitle(ChatUtil.hexColor("#CC9535ODLAMEK MIEDZI")).addEnchantment(Enchantment.DURABILITY,1).addFlag(ItemFlag.HIDE_ATTRIBUTES).addFlag(ItemFlag.HIDE_ENCHANTS).build(),1,175,Material.GRANITE),

    METALU(new ItemBuilder(Material.IRON_INGOT).setTitle(ChatUtil.hexColor("#DADCFFODLAMEK METALU")).addEnchantment(Enchantment.DURABILITY,1).addFlag(ItemFlag.HIDE_ATTRIBUTES).addFlag(ItemFlag.HIDE_ENCHANTS).build(),1,250,Material.IRON_ORE),
    WEGLA(new ItemBuilder(Material.COAL).setTitle(ChatUtil.hexColor("#131213ODALAMEK WEGLA")).addEnchantment(Enchantment.DURABILITY,1).addFlag(ItemFlag.HIDE_ATTRIBUTES).addFlag(ItemFlag.HIDE_ENCHANTS).build(),1,80,Material.COAL_ORE),
    AMETYSTU(new ItemBuilder(Material.PURPLE_DYE).setTitle(ChatUtil.hexColor("#C273E4ODLAMEK AMETYSTU")).addEnchantment(Enchantment.DURABILITY,1).addFlag(ItemFlag.HIDE_ATTRIBUTES).addFlag(ItemFlag.HIDE_ENCHANTS).build(),1,600,Material.PURPLE_TERRACOTTA),
    RUBINU(new ItemBuilder(Material.RED_DYE).setTitle(ChatUtil.hexColor("#FB4FACODLAMEK RUBINU")).addEnchantment(Enchantment.DURABILITY,1).addFlag(ItemFlag.HIDE_ATTRIBUTES).addFlag(ItemFlag.HIDE_ENCHANTS).build(),1,500,Material.REDSTONE_ORE),
    KWARCU(new ItemBuilder(Material.QUARTZ).setTitle(ChatUtil.hexColor("#A1E8E6ODLAMEK KWARCU")).addEnchantment(Enchantment.DURABILITY,1).addFlag(ItemFlag.HIDE_ATTRIBUTES).addFlag(ItemFlag.HIDE_ENCHANTS).build(),1,400,Material.NETHER_QUARTZ_ORE),

    CZARNEGO_METALU(new ItemBuilder(Material.NETHERITE_INGOT).setTitle(ChatUtil.hexColor("#413430ODLAMEK CZARNEGO METALU")).addEnchantment(Enchantment.DURABILITY,1).addFlag(ItemFlag.HIDE_ATTRIBUTES).addFlag(ItemFlag.HIDE_ENCHANTS).build(),1,800,Material.ANCIENT_DEBRIS);







    private final ItemStack itemStack;
    private final int pickaxeLevel;

    private final int amount;
    private final Material blockType;

    OreType(ItemStack itemStack, int pickaxeLevel, int amount,Material blockType){
        this.itemStack = itemStack;
        this.pickaxeLevel = pickaxeLevel;
        this.amount = amount;
        this.blockType = blockType;
    }

    public int getAmount() {
        return amount;
    }

    public int getPickaxeLevel() {
        return pickaxeLevel;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Material getBlockType() {
        return blockType;
    }

    public ItemStack getItemStackWithAmount(int amount){
        final ItemStack clone = this.itemStack.clone();

        clone.setAmount(amount);
        return clone;
    }
}
