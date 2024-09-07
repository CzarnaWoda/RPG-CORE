package pl.blackwater.rpg.configs;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import pl.blackwater.api.config.Config;
import pl.blackwater.api.config.annotation.ConfigName;
import pl.blackwater.rpg.items.DropItem;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.ItemBuilder;

import java.util.Arrays;
import java.util.List;

@ConfigName("drop.yml")
public interface DropItemConfig extends Config {

    default List<DropItem> getDropItems(){
        return Arrays.asList((new DropItem(new ItemBuilder(Material.LEATHER).setTitle(ChatUtil.hexColor("&eSKORA LOWCY")).addLore(ChatUtil.hexColor("&8->> &6MATERIAL RZEMIESLNICZY DO WYMIANY")), "MROCZNY LOWCA", 2.0)),
                new DropItem(new ItemBuilder(Material.BONE).setTitle(ChatUtil.hexColor("#b8f2e7KOSC GHOUL'A")).addLore(ChatUtil.hexColor("&8->> &6MATERIAL RZEMIESLNICZY DO WYMIANY")), "GHOUL", 2.0));
    }

}
