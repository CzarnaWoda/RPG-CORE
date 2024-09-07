package pl.blackwater.rpg.chat;

import org.bukkit.entity.Player;
import pl.blackwater.rpg.RPG;
import pl.blackwater.spigotplugin.util.TimeUtil;

import java.util.HashMap;
import java.util.Map;

public class ChatOptionsManager {

    private final RPG plugin;

    private final Map<Player,Long> times;
    public ChatOptionsManager(RPG plugin){
        this.plugin = plugin;

        plugin.getRedisStorage().RPG_CHATOPTIONS.fastPutIfAbsent(ChatOptions.PREMIUM.name(),false);
        plugin.getRedisStorage().RPG_CHATOPTIONS.fastPutIfAbsent(ChatOptions.STATUS.name(), true);

        times = new HashMap<>();
    }

    public boolean getStatus(){
        return plugin.getRedisStorage().RPG_CHATOPTIONS.get(ChatOptions.STATUS.name());
    }
    public boolean getPremiumStatus(){
        return plugin.getRedisStorage().RPG_CHATOPTIONS.get(ChatOptions.PREMIUM.name());
    }
    public boolean canSendMessage(final Player p) {
        if (p.hasPermission("rpg.chat.slow.bypass")) {
            return true;
        }
        Long time = times.get(p);
        return time == null || System.currentTimeMillis() - time >= TimeUtil.SECOND.getTime(5);
    }

    public Map<Player, Long> getTimes() {
        return times;
    }

    public boolean chatOn(){
        plugin.getRedisStorage().RPG_CHATOPTIONS.put(ChatOptions.STATUS.name(),true);
        return true;
    }
    public boolean chatOff(){
        plugin.getRedisStorage().RPG_CHATOPTIONS.put(ChatOptions.STATUS.name(),false);
        return true;
    }
}
