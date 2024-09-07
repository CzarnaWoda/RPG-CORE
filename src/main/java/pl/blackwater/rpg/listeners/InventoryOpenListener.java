package pl.blackwater.rpg.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.blackwater.rpg.anvil.AnvilInventory;

public class InventoryOpenListener implements Listener {


    @EventHandler
    public void onAnvilInventoryOpen(InventoryOpenEvent e){
        if(e.getInventory().getType().equals(InventoryType.ANVIL)){
            e.setCancelled(true);
            AnvilInventory.openAnvil((Player) e.getPlayer());
        }
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if(e.getClickedBlock().getType().equals(Material.ANVIL)){
                AnvilInventory.openAnvil(e.getPlayer());
            }
        }
    }
}
