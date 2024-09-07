package pl.blackwater.rpg.items;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface SpecialItemAction {

    void execute(Player player, ItemStack itemStack);
}
