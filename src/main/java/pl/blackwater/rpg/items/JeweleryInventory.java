package pl.blackwater.rpg.items;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.rpg.util.RPGUtil;
import pl.blackwater.spigotplugin.spigot.inventory.actions.InventoryGUI;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.InventoryUtil;
import pl.blackwater.spigotplugin.util.ItemBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class JeweleryInventory {

    public static void open(Player player){

        InventoryGUI gui = new InventoryGUI("&8->> &6BIŻUTERIA &8<<-", 3);
        final RPGUser user = RPG.getPlugin().getRpgUserManager().getUser(player.getUniqueId());

        for(int i=0; i<9; i++){
            if(i < user.getLevel()){
                if(user.getJewelery()[i] != null){
                    final ItemBuilder glassItem = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setTitle(ChatUtil.fixColor("&8&lSLOT ZAJĘTY")).addLore(ChatUtil.fixColor("&7Kliknij na biżuterię, aby ją zdjąć"));
                    gui.setItem(i, glassItem.build(), null);
                    gui.setItem(i+18, glassItem.build(), null);

                    int jeweleryId = i;
                    gui.setItem(i+9, user.getJewelery()[i], (player1, inventory, i1, itemStack, clickType) -> {
                        InventoryUtil.giveItems(player, itemStack);
                        RPGUtil.removeStatsFromUser(user, new NBTItem(itemStack));

                        itemStack.setType(Material.AIR);
                        user.getJewelery()[jeweleryId] = null;

                        open(player);
                    });
                }else {
                    final ItemBuilder glassItem = new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setTitle(ChatUtil.fixColor("&a&lWOLNY SLOT"));
                    gui.setItem(i, glassItem.build(), null);
                    gui.setItem(i+18, glassItem.build(), null);

                    int slotId = i;
                    gui.setItem(i+9, new ItemBuilder(Material.GREEN_TERRACOTTA).setTitle(ChatUtil.fixColor("&a&lKLIKNIJ, ABY WYBRAC BIŻUTERIE")).build(), (player1, inventory, i1, itemStack, clickType) -> {
                        List<ItemStack> userJewelery = getJeweleryFromInventory(user,player.getInventory());
                        if(userJewelery.size() > 0){
                            final InventoryGUI jeweleryPick = new InventoryGUI("&8->> &aWYBIERZ BIŻUTERIE &8<<-", userJewelery.size() < 9 ? 1 : userJewelery.size()/9);
                            AtomicInteger jewSlot = new AtomicInteger(0);
                            userJewelery.forEach(jeweleryItem -> {
                                jeweleryPick.setItem(jewSlot.getAndIncrement(), jeweleryItem, (player2, inventory1, i2, itemStack1, clickType1) -> {
                                    final ItemStack jeweleryClone = jeweleryItem.clone();
                                    jeweleryClone.setAmount(1);

                                    user.getJewelery()[slotId] = jeweleryClone;
                                    InventoryUtil.removeItemFromPlayer(player2, jeweleryItem, 1);
                                    RPGUtil.addUserStatsFromItem(user, new NBTItem(jeweleryClone));
                                    open(player);
                                });
                            });
                            jeweleryPick.openInventory(player);
                        }else {
                            player.sendMessage(ChatUtil.fixColor("&7>> &cNie posiadasz biżuterii w ekwipunku"));
                        }
                    });
                }
            }else {
                final ItemBuilder glassItem = new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setTitle(ChatUtil.fixColor("&c&lSLOT ZABLOKOWANY")).addLore(ChatUtil.fixColor("&7Aby odblokować ten slot osiągnij większy LVL"));
                gui.setItem(i, glassItem.build(), null);
                gui.setItem(i+9, new ItemBuilder(Material.BARRIER).setTitle(ChatUtil.fixColor("&c%X%")).build(), null);
                gui.setItem(i+18, glassItem.build(), null);
            }
        }

        gui.openInventory(player);

    }

    private static List<ItemStack> getJeweleryFromInventory(RPGUser user ,Inventory inventory){
        final List<ItemStack> items = new ArrayList<>();
        for (ItemStack content : inventory.getContents()) {
            if(RPGUtil.isRPGItem(content)){
                final NBTItem nbtItem = new NBTItem(content);
                final ItemType type = ItemType.fromString(nbtItem.getString("ItemType"));
                if(type != null){
                    if(type == ItemType.JEWELERY){
                        if(user.getLevel() >= nbtItem.getInteger("level")) {
                            if(user.getRpgClass().name().equalsIgnoreCase(nbtItem.getString("rpgClass"))) {
                                items.add(content);
                            }
                        }
                    }
                }
            }
        }
        return items;
    }

}
