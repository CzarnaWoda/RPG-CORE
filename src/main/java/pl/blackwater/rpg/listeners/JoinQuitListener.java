package pl.blackwater.rpg.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.rpg.services.LevelActionEnum;
import pl.blackwater.rpg.services.LevelService;

import java.util.List;

public class JoinQuitListener extends LevelService implements Listener {

    private final RPG plugin;
    public JoinQuitListener(RPG plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        plugin.getRpgUserManager().joinToTheGame(e.getPlayer());



    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        plugin.getRpgUserManager().leftTheGame(e.getPlayer());


    }


}
