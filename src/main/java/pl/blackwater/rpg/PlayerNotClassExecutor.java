package pl.blackwater.rpg;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.rpg.data.enums.RPGClass;
import pl.blackwater.spigotplugin.thread.SpigotSingleThread;
import pl.blackwater.spigotplugin.thread.SpigotThread;
import pl.blackwater.spigotplugin.util.ChatUtil;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PlayerNotClassExecutor extends SpigotSingleThread {
    private final RPG plugin;
    public PlayerNotClassExecutor(RPG plugin) {
        super(Executors.newSingleThreadScheduledExecutor());
        this.plugin = plugin;
    }

    @Override
    public SpigotThread runThread() {
        getExecutorService().scheduleAtFixedRate(() -> {
            for(Player player : Bukkit.getOnlinePlayers()){
                final RPGUser rpgUser = plugin.getRpgUserManager().getUser(player.getUniqueId());

                if(rpgUser.getRpgClass().equals(RPGClass.BEGINNER)){
                    ChatUtil.sendMessage(player,"&7Nie wybrales jeszcze klasy postaci, &awybierz &7klase na spawn'ie u odpowiedniego &aNPC");
                    player.sendTitle(ChatUtil.hexColor("&4NIE WYBRANO KLASY"),ChatUtil.hexColor("&7WYBIERZ KLASE U &cODPOWIEDNIEGO NPC"));

                    //TODO check sum
                }
            }
        },5L,5L, TimeUnit.SECONDS);
        return this;
    }
}
