package pl.blackwater.rpg.util.serializers;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import pl.blackwater.api.config.BukkitConfiguration;
import pl.blackwater.api.config.serializer.Serializer;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.ItemBuilder;

public class ItemBuilderSerializer extends Serializer<ItemBuilder> {
    @Override
    protected void saveObject(String path, ItemBuilder itemBuilder, BukkitConfiguration config) {
        config.set(path + ".material",itemBuilder.build().getType().name());
        config.set(path + ".data", itemBuilder.build().getDurability());
        config.set(path + ".title", itemBuilder.getTitle());
        if(itemBuilder.getLore() != null && !itemBuilder.getLore().isEmpty()) {
            config.set(path + ".lore", itemBuilder.getLore());
        }
        for(Enchantment e : itemBuilder.build().getEnchantments().keySet()){
            config.set(path + ".enchantments." + itemBuilder.build().getEnchantments().get(e), e.getName());
        }
    }

    @Override
    public ItemBuilder deserialize(String path, BukkitConfiguration config) {
        final ItemBuilder itemBuilder = new ItemBuilder(Material.valueOf(config.getString(path + ".material")) , 1 , (short)config.getInt(path + ".data"))
                .setTitle(ChatUtil.fixColor(config.getString(path + ".title")))
                .addLores(config.getStringList(path + ".lore"));
        if(config.getConfigurationSection(path + ".enchantments") != null) {
            for (String level : config.getConfigurationSection(path + ".enchantments").getKeys(false)) {
                final int enchantLevel = Integer.parseInt(level);
                final Enchantment enchantment = Enchantment.getByName(config.getString(path + ".enchantments." + enchantLevel));

                itemBuilder.addEnchantment(enchantment, enchantLevel);
            }
        }

        return itemBuilder;
    }
}
