package pl.blackwater.rpg.listeners;

import lombok.RequiredArgsConstructor;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.*;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.rpg.items.JeweleryInventory;
import pl.blackwater.rpg.util.RPGUtil;
import pl.blackwater.spigotplugin.spigot.inventory.actions.InventoryGUI;
import pl.blackwater.spigotplugin.spigot.timer.TimerManager;
import pl.blackwater.spigotplugin.spigot.timer.TimerUtil;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.ItemBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.UUID;

@RequiredArgsConstructor
public class PlayerInventoryListener implements Listener {

    private final RPG plugin;


    private static List<ItemStack> playerItems = new ArrayList<>();

    static {
        playerItems.add(new ItemBuilder(Material.SHIELD).setTitle(ChatUtil.fixColor("&8->> &6&nSPAWN&8 <<-")).build());
    }
    @EventHandler
    public void onCloseInventory(InventoryCloseEvent e){
        Inventory craftingInv = e.getView().getTopInventory();
        if(craftingInv.getSize() == 5) {
            craftingInv.setItem(1, null);
            craftingInv.setItem(2, null);
            craftingInv.setItem(3, null);
            ((Player) e.getPlayer()).updateInventory();
        }
    }
    @EventHandler
    public void onInventoryInteract(InventoryClickEvent e){
        if(e.getView().getTopInventory().getSize() == 5){
            final Inventory inv = e.getView().getTopInventory();
            final Player player = (Player) e.getWhoClicked();
            if(player.getGameMode() != GameMode.CREATIVE){
                if(e.getRawSlot() == 1){
                    e.setCancelled(true);
                    player.closeInventory();
                    TimerUtil.teleport(player,plugin.getRpgConfig().getSpawnLocation(), 4);
                }else if(e.getRawSlot() == 2){
                    e.setCancelled(true);
                    player.closeInventory();
                    JeweleryInventory.open(player);
                }else if(e.getRawSlot() == 3){
                    final RPGUser user = plugin.getRpgUserManager().getUser(player.getUniqueId());
                    e.setCancelled(true);
                    player.closeInventory();

                    final InventoryGUI storageInventory = new InventoryGUI(ChatUtil.hexColor("#872da8MAGAZYNY"),1);

                    int index = 0;
                    for(ItemStack[] items : user.getStorages()){
                        final ItemBuilder chest = new ItemBuilder(Material.ENDER_CHEST).setTitle(ChatUtil.hexColor("#4d1163⛏ #bd08d1MAGAZYN #" + (index + 1) + " #4d1163⛏"))
                                .addLore("")
                                .addLore(ChatUtil.hexColor("#4d1163%V% #bd08d1KLIKNIJ ABY OTWORZYC MAGAZYN"));
                        int finalIndex = index;
                        final int cost = RPGUtil.STORAGE_PRICE.get(finalIndex+1);
                        final ItemBuilder barrier = new ItemBuilder(Material.BARRIER).setTitle(ChatUtil.hexColor("#4d1163⛏ #bd08d1MAGAZYN #" + (index + 1) + " #4d1163⛏"))
                                .addLore("")
                                .addLore(ChatUtil.hexColor("#4d1163%X% #bd08d1MAGAZYN ZABLOKOWANY"))
                                .addLore(ChatUtil.hexColor("#4d1163%X% #bd08d1Koszt odblokowania: #4d1163" + cost));
                        final RPGUser rpgUser = plugin.getRpgUserManager().getUser(player.getUniqueId());
                        if(rpgUser.getStorageLevel() >= (finalIndex+1)) {
                            storageInventory.addItem(chest.build(), (player1, inventory, i, itemStack, clickType) -> {

                                final Inventory storage = Bukkit.createInventory(null, 54, ChatUtil.hexColor("#4d1163⛏ #bd08d1MAGAZYN #" + (finalIndex + 1)));
                                storage.setContents(items);

                                player1.openInventory(storage);
                            });
                        }else{
                            storageInventory.addItem(barrier.build(), (player1, inventory, i, itemStack, clickType) -> {
                                if(user.getMoney() >= cost){
                                    player1.closeInventory();

                                    final InventoryGUI storageBuy = new InventoryGUI(ChatUtil.hexColor("#4d1163⛏ #bd08d1KUPNO MAGAZYNU #" + (finalIndex + 1) + " #4d1163⛏"),1);

                                    final ItemBuilder accept = new ItemBuilder(Material.EMERALD_BLOCK).setTitle(ChatUtil.hexColor("#4d1163⛏ #bd08d1AKCEPTUJ KUPNO #4d1163⛏"));

                                    storageBuy.setItem(4,accept.build(),(player2, inventory1, i1, itemStack1, clickType1) -> {
                                        if(user.getMoney() >= cost) {
                                            player2.closeInventory();
                                            user.removeMoney(cost);
                                            user.addStorageLevel(1);
                                        }else{
                                            ChatUtil.sendMessage(player2,"&4ILLEGAL INTERACTION");
                                        }
                                    });
                                    storageBuy.openInventory(player1);

                                }else{
                                    ChatUtil.sendMessage(player1,"&4Blad: &cNie posiadasz wystarczajacej ilosci kasy!");
                                }
                            });
                        }
                        index++;
                    }
                    storageInventory.openInventory(player);
                }else if(e.getRawSlot() == 4){

                    final RPGUser rpgUser = plugin.getRpgUserManager().getUser(player.getUniqueId());
                    player.closeInventory();
                    
                    final InventoryGUI npcGui = new InventoryGUI(ChatUtil.hexColor("#f3fa91ODKRYTE NPC"),2);

                    for(UUID npcUUID : rpgUser.getDiscoveredNpc()){
                        final NPC npc = CitizensAPI.getNPCRegistry().getByUniqueId(npcUUID);
                        if(npc != null){
                            final ItemBuilder npcItem = new ItemBuilder(Material.EGG).setTitle(ChatUtil.hexColor("#8dd9f2" + npc.getName()))
                                    .addLore(ChatUtil.hexColor("  #7595eb♯ #8975ebKliknij aby sie przeleportowac!")).addEnchantment(Enchantment.DURABILITY,1).addFlag(ItemFlag.HIDE_ENCHANTS).addFlag(ItemFlag.HIDE_ATTRIBUTES);

                            npcGui.addItem(npcItem.build(),(player1, inventory, i, itemStack, clickType) -> {
                                player1.closeInventory();

                                TimerUtil.teleport(player1,npc.getStoredLocation(),5);
                            });
                        }
                    }
                    if(npcGui.get().isEmpty()){
                        ChatUtil.sendMessage(player,"&4Nie masz odkrytych zadnych NPC");
                    }else {
                        npcGui.openInventory(player);
                    }
                }
            }
        }
    }
}
