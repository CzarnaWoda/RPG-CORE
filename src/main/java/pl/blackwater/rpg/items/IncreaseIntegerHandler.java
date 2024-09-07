package pl.blackwater.rpg.items;

import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.blackwater.spigotplugin.spigot.inventory.actions.IAction;
import pl.blackwater.spigotplugin.spigot.inventory.actions.InventoryGUI;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.ItemBuilder;

import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class IncreaseIntegerHandler implements IAction {

    private int i;

    public IncreaseIntegerHandler(int i){
        this.i = i;
    }
    @Override
    public void execute(Player player, Inventory inventory, int i, ItemStack itemStack, ClickType clickType) {

        final InventoryGUI handlerInv = new InventoryGUI(ChatUtil.fixColor("&6ADD AND DECREASE"),1);

        final ItemBuilder a = new ItemBuilder(Material.BOOK).setTitle("&aAKTUALNA WARTOSC: " + i)
                .addLore(ChatUtil.fixColor("&8->> &7LEFT +1 / RIGHT -1"));
        final AtomicInteger integer = new AtomicInteger(i);
        handlerInv.setItem(4,a.build(),(player1, inventory1, i1, itemStack1, cType) -> {
            if(cType.equals(ClickType.LEFT)){
                integer.getAndIncrement();

            }
            if(cType.equals(ClickType.RIGHT)){
                integer.decrementAndGet();
            }
            updateItemStack(itemStack1,integer.getAndIncrement());
            //TODO update
        });

        final ItemBuilder save = new ItemBuilder(Material.ARROW).setTitle(ChatUtil.fixColor("&aZAPISZ"));

        handlerInv.setItem(6,save.build(),(player1, inventory1, i1, itemStack1, clickType1) -> {
            setI(integer.get());
        });

    }
    private static ItemStack updateItemStack(ItemStack itemStack, int i){

        final ItemMeta meta = itemStack.getItemMeta();

        meta.setDisplayName(ChatUtil.fixColor("&aAKTUALNA WARTOSC: " + i));

        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public void setI(int i) {
        this.i = i;
    }
}
