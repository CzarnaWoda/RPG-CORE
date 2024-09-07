package pl.blackwater.rpg.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Creature;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class DisableThingsListener implements Listener {

    private Material[] blocked = {Material.BREWING_STAND,Material.CRAFTING_TABLE,Material.CHEST,Material.TRAPPED_CHEST,Material.DISPENSER,Material.DROPPER};

    @EventHandler
    public void onFire(BlockIgniteEvent e){
        e.setCancelled(true);
    }
    @EventHandler
    public void onEntityInteract(EntityInteractEvent event) {
        if (event.getBlock().getType() == Material.FARMLAND && event.getEntity() instanceof Creature)
            event.setCancelled(true);
    }
    @EventHandler
    public void disableInteraction(PlayerInteractEvent e){
        if(e.getClickedBlock() != null) {
            if (!e.getPlayer().hasPermission("rpg.admin")) {
                for (Material m : blocked) {
                    if (e.getClickedBlock().getType().equals(m)) {
                        e.setCancelled(true);
                        break;
                    }
                }
            }
        }
    }
}
