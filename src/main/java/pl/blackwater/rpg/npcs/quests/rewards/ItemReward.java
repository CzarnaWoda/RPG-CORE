package pl.blackwater.rpg.npcs.quests.rewards;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.spigotplugin.util.InventoryUtil;

public class ItemReward extends Reward {

    private final ItemStack[] itemStack;


    public ItemReward(ItemStack[] itemStack){
        this.itemStack = itemStack;
    }


    @Override
    public boolean giveReward(Player player) {
        InventoryUtil.giveItems(player,itemStack);
        return true;
    }
}
