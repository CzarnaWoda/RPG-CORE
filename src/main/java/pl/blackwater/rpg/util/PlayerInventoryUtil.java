package pl.blackwater.rpg.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerInventoryUtil {
    public static void ClearPlayerInventory(final Player p)
    {
        final PlayerInventory inv = p.getInventory();
        inv.setArmorContents(new ItemStack[] {null,null,null,null});
        inv.setHeldItemSlot(0);
        inv.clear();
        p.updateInventory();
    }
    public static int remove(ItemStack base, Player player, int amount)
    {
        int actual = 0;
        int remaining = amount;
        ItemStack[] contents;
        int length = (contents = player.getInventory().getContents()).length;
        for (int i = 0; i < length; i++)
        {
            ItemStack itemStack = contents[i];
            if (actual == amount) {
                break;
            }
            if ((itemStack != null) &&
                    (itemStack.getType().equals(base.getType())) &&
                    (itemStack.getDurability() == base.getDurability())) {
                if (remaining == 0)
                {
                    actual += itemStack.getAmount();
                    player.getInventory().remove(itemStack);
                }
                else if (itemStack.getAmount() >= amount)
                {
                    actual += itemStack.getAmount() - amount;
                    itemStack.setAmount(amount);
                    remaining = 0;
                }
                else
                {
                    int add = itemStack.getAmount();
                    remaining -= add;
                    player.getInventory().remove(itemStack);
                    actual += add;
                }
            }
        }
        return actual;
    }
    public static void removeItems(Player player, ItemStack itemStack, int amount) {
        int left = amount;
        for(ItemStack itemStacks : player.getInventory().getContents()){
            if(itemStacks != null && !itemStacks.getType().equals(Material.AIR) && itemStacks.isSimilar(itemStack)){
                if(itemStacks.getAmount() > left){
                    itemStacks.setAmount(itemStacks.getAmount() - left);
                    return;
                }else{
                    left -= itemStacks.getAmount();
                    player.getInventory().removeItem(itemStacks);
                }
            }
        }
        player.updateInventory();
    }
    public static boolean checkItems(Player player, ItemStack itemStack, int amount) {
        int left = 0;
        for(ItemStack itemStacks : player.getInventory().getContents()){
            if(itemStacks != null && !itemStacks.getType().equals(Material.AIR) && itemStacks.isSimilar(itemStack)){
                if(itemStacks.getAmount() >= left){
                    return true;
                }else{
                    left += itemStacks.getAmount();
                    if(left >= amount){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static boolean checkItemsInStorage(Player player, ItemStack itemStack, int amount) {
        int left = 0;
        for(ItemStack itemStacks : player.getInventory().getStorageContents()){
            if(itemStacks != null && !itemStacks.getType().equals(Material.AIR) && itemStacks.isSimilar(itemStack)){
                if(itemStacks.getAmount() >= left){
                    return true;
                }else{
                    left += itemStacks.getAmount();
                    if(left >= amount){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
