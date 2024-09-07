package pl.blackwater.rpg.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        if(!e.getPlayer().hasPermission("rpg.admin")){
            e.setCancelled(true);
        }
    }
}
