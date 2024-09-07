package pl.blackwater.rpg.mysterybox;

import io.lumine.mythic.bukkit.utils.items.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.rpg.util.PlayerInventoryUtil;
import pl.blackwater.spigotplugin.util.InventoryUtil;

import java.util.HashMap;
import java.util.Map;

public class MysteryBoxActionListener implements Listener {


    private static Map<ItemStack,MysteryBox> boxes = new HashMap<>();


    public static Map<ItemStack, MysteryBox> getBoxes() {
        return boxes;
    }


    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(e.getItem() != null) {
            final ItemStack check = e.getItem().clone();
            check.setAmount(1);
            if (boxes.get(check) != null) {
                if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                    final Player player = e.getPlayer();
                    final MysteryBox box = boxes.get(check);
                    box.getAction().execute(player,box.getDropList(),e);
                    if(player.getInventory().getItemInOffHand().isSimilar(check)){
                        final ItemStack offhand = player.getInventory().getItemInOffHand();
                        if(offhand.getAmount() > 1){
                            offhand.setAmount(offhand.getAmount() - 1);
                            player.updateInventory();
                        }else{
                            player.getInventory().setItemInOffHand(null);
                            player.updateInventory();
                        }
                    }else{
                        PlayerInventoryUtil.removeItems(player,check,1);
                    }
                    e.setCancelled(true);
                }
            }
        }
    }
}
