package pl.blackwater.rpg.util.serializers;

import org.bukkit.ChatColor;
import pl.blackwater.api.config.BukkitConfiguration;
import pl.blackwater.api.config.exception.InvalidConfigException;
import pl.blackwater.api.config.serializer.Serializer;

public class ChatColorSerializer extends Serializer<ChatColor> {
    @Override
    protected void saveObject(String s, ChatColor chatColor, BukkitConfiguration bukkitConfiguration) {
        bukkitConfiguration.set(s,chatColor.toString());
    }

    @Override
    public ChatColor deserialize(String s, BukkitConfiguration bukkitConfiguration) {
        return ChatColor.getByChar(bukkitConfiguration.getString(s));
    }
}
