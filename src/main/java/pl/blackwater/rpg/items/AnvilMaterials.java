package pl.blackwater.rpg.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.ItemBuilder;

public enum AnvilMaterials {


    SKORA_LOWCY(new ItemBuilder(Material.LEATHER).setTitle(ChatUtil.hexColor("&eSKORA LOWCY")).addLore(ChatUtil.hexColor("&8->> &6MATERIAL RZEMIESLNICZY DO WYMIANY")).build()),

    GHOUL_HEAD(new ItemBuilder(Material.ZOMBIE_HEAD).setTitle(ChatUtil.hexColor("#77b0d4GLOWA GHOUL'A")).addLore(ChatUtil.hexColor("&8->> &6MATERIAL RZEMIESLNICZY DO WYMIANY")).build()),
    GHOUL_BONE(new ItemBuilder(Material.BONE).setTitle(ChatUtil.hexColor("#b8f2e7KOSC GHOUL'A")).addLore(ChatUtil.hexColor("&8->> &6MATERIAL RZEMIESLNICZY DO WYMIANY")).build()),
    ODLAMEK_LUKU_LOWCY(new ItemBuilder(Material.STICK).setTitle(ChatUtil.hexColor("&eOLDAMEK LUKU LOWCY")).addLore(ChatUtil.hexColor("&8->> &6MATERIAL RZEMIESLNICZY DO WYMIANY")).build());

    private final ItemStack item;
    AnvilMaterials(ItemStack item) {
        this.item= item;
    }

    public ItemStack getItem() {
        return item;
    }

    public ItemStack getItemWithAmount(int amount){
        final ItemStack clone = item.clone();
        clone.setAmount(amount);
        return clone;
    }
}
