package pl.blackwater.rpg.items;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.spigotplugin.spigot.inventory.actions.IAction;
import pl.blackwater.spigotplugin.util.InventoryUtil;

@RequiredArgsConstructor
public class AddItemHandler implements IAction {

    private final ItemStack item;
    @Override
    public void execute(Player player, Inventory inventory, int i, ItemStack itemStack, ClickType clickType) {
        if(clickType.isShiftClick()){
            final ItemStack clone = item.clone();
            clone.setAmount(64);
            InventoryUtil.giveItems(player,clone);
        }else {
            InventoryUtil.giveItems(player, item);
        }
    }
}
