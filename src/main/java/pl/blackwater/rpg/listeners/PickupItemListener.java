package pl.blackwater.rpg.listeners;

import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.managers.impl.RPGUserManagerImpl;

@RequiredArgsConstructor
public class PickupItemListener implements Listener {

    private final RPG plugin;

    @EventHandler
    public void onPickup(EntityPickupItemEvent e){
        if(e.getEntity() instanceof Player){
            if(e.getItem().getItemStack().getType().equals(Material.ARROW)){
                e.getItem().remove();
                e.setCancelled(true);
            }
            if(e.getItem().getItemStack().isSimilar(RPGUserManagerImpl.getContents()[0]) || e.getItem().getItemStack().isSimilar(RPGUserManagerImpl.getContents()[1]) || e.getItem().getItemStack().isSimilar(RPGUserManagerImpl.getContents()[2]) || e.getItem().getItemStack().isSimilar(RPGUserManagerImpl.getContents()[3])){
                e.getItem().remove();
                e.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onItemSpawn(ItemSpawnEvent e){
        final ItemStack item = e.getEntity().getItemStack();

        if(item.getType().equals(Material.ARROW)){
            e.getEntity().remove();
        }

        if(item.isSimilar(RPGUserManagerImpl.getContents()[0]) || item.isSimilar(RPGUserManagerImpl.getContents()[1]) || item.isSimilar(RPGUserManagerImpl.getContents()[2]) || item.isSimilar(RPGUserManagerImpl.getContents()[3])) {
            e.getEntity().remove();
        }
    }
}
