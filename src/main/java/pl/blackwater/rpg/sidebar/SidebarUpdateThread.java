package pl.blackwater.rpg.sidebar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.blackwater.rpg.RPG;
import pl.blackwater.spigotplugin.thread.SpigotSingleThread;
import pl.blackwater.spigotplugin.thread.SpigotThread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SidebarUpdateThread extends SpigotSingleThread {

    public SidebarUpdateThread(){
        super(Executors.newSingleThreadScheduledExecutor());

    }

    @Override
    public SpigotThread runThread() {
        getExecutorService().scheduleAtFixedRate(() -> {
            for(Player player : Bukkit.getOnlinePlayers()){
                RPG.getPlugin().getScoreboardManager().updateScoreBoard(player);
            }
        },1500L,1500L, TimeUnit.MILLISECONDS);

        return this;
    }
}
