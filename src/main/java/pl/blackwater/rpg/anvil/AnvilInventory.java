package pl.blackwater.rpg.anvil;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.items.SpecialItem;
import pl.blackwater.rpg.items.UpgradeMaterials;
import pl.blackwater.rpg.storage.RPGItem;
import pl.blackwater.rpg.storage.enums.Stats;
import pl.blackwater.rpg.util.RPGUtil;
import pl.blackwater.spigotplugin.spigot.inventory.actions.InventoryGUI;
import pl.blackwater.spigotplugin.util.*;

public class AnvilInventory {
    
    public static void openAnvil(Player player){
        final InventoryGUI gui = new InventoryGUI(ChatUtil.hexColor("#084cfbM#1458fbA#2064fbG#2b70fbI#377cfcC#4388fcZ#4f94fcN#5ba0fcE #66abfcK#72b7fcO#7ec3fcW#8acffdA#95dbfdD#a1e7fdL#adf3fdO"),3);

        final int[] blueglass = {1,19,9,11,25,7,17,15};
        final int[] yellowglass = {0,2,3,4,5,6,8,12,13,14,18,18,20,21,22,23,24,26};

        final ItemBuilder blue = new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setTitle(ChatUtil.hexColor("#3a8de0#"));
        final ItemBuilder yellow = new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).setTitle(ChatUtil.hexColor("#daeb44#"));
        for (int i : blueglass) {
            gui.setItem(i,blue.build(),null);
        }
        for (int i : yellowglass) {
            gui.setItem(i,yellow.build(),null);
        }
        final ItemBuilder changeBonus = new ItemBuilder(Material.ANVIL).setTitle(ChatUtil.hexColor("#5693fbZ#6784e2M#7776c9I#8867b0E#995897N #aa4a7eB#ba3b64O#cb2c4bN#dc1d32U#ec0f19S#fd0000Y"));
        final ItemBuilder upgradeItem = new ItemBuilder(Material.ANVIL).setTitle(ChatUtil.hexColor("#4ffb84U#9fadd2L#c77ae4E#be67acP#c23facS#b029b7Z #6c3dc7P#3f82d6R#3fb6ddZ#c684caE#b8a3d7D#6ae5e2M#76f35eI#77ce4aO#718090T"));
        gui.addItem(changeBonus.build(),(p, inventory, i, itemStack, clickType) -> {
            p.closeInventory();
            openChangeBonus(p);
        });
        gui.addItem(upgradeItem.build(),(player1, inventory, i, itemStack, clickType) -> {
            player1.closeInventory();
            openUpgradeItem(player1);
        });
        gui.openInventory(player);


    }
    /*
    MAIN COLOR: #ed9791
    IMPORTANT: #f5c35f
    SPECIAL SIGNS: #7496f2
    SIGNS: ★ ☍
     */
    
    public static void openUpgradeItem(Player player){
        final InventoryGUI upgradeGui = new InventoryGUI(ChatUtil.hexColor("#ed9791ULEPSZENIE #f5c35fPRZEDMIOTU"),1);


        for(ItemStack itemStack : player.getInventory().getContents()){
            if(itemStack == null || itemStack.getType().equals(Material.AIR)){
                continue;
            }
            if(RPGUtil.isRPGItem(itemStack)){
                final NBTItem nbtItem = new NBTItem(itemStack);
                final int upgradeLevel = nbtItem.getInteger("upgradeLevel");
                System.out.println(upgradeLevel);
                if(upgradeLevel < 20 && upgradeLevel >= 0 || nbtItem.getInteger("upgradeLevel") == null){
                    upgradeGui.addItem(itemStack,(player1, inventory, i, itemStack1, clickType) -> {
                        player1.closeInventory();

                        final InventoryGUI choseGui = new InventoryGUI(ChatUtil.hexColor("#ed9791ULEPSZENIE #f5c35fPRZEDMIOTU"),3);

                        final ItemBuilder red = new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setTitle(ChatUtil.hexColor("&4#"));
                        final ItemBuilder purple = new ItemBuilder(Material.PURPLE_STAINED_GLASS_PANE).setTitle(ChatUtil.hexColor("#673091#"));
                        int[] reds = {1,7,9,11,15,17,19,25};
                        int[] purples = {0,2,3,4,5,6,8,12,13,14,18,20,21,22,23,24,26};
                        for(int j : reds){
                            choseGui.setItem(j,red.build(),null);
                        }
                        for(int j : purples){
                            choseGui.setItem(j,purple.build(),null);
                        }

                        final ItemBuilder barrier = new ItemBuilder(Material.BARRIER).setTitle(ChatUtil.hexColor("#7496f2★ #ed9791WYBIERZ ZWOJ #f5c35fULEPSZENIA #7496f2★"));

                        choseGui.setItem(10,itemStack1,(player2, inventory1, i1, itemStack2, clickType1) -> {
                            player2.closeInventory();
                            openUpgradeItem(player2);
                        });

                        choseGui.setItem(16,barrier.build(),(player2, inventory1, i1, itemStack2, clickType1) -> {
                            final ItemStack upgradeMaterial = UpgradeMaterials.getByUpgradeLevel(upgradeLevel).getItem();
                            final InventoryGUI choseUpgradeMaterial = new InventoryGUI(ChatUtil.hexColor("#ed9791WYBIERZ #f5c35fULEPSZACZ"),3);

                            for(ItemStack playerItems : player2.getInventory().getContents()){
                                if(playerItems == null || playerItems.getType().equals(Material.AIR)){
                                    continue;
                                }
                                if(playerItems.isSimilar(upgradeMaterial)){
                                    choseUpgradeMaterial.addItem(playerItems,(player3, inventory2, i2, itemStack3, clickType2) -> {
                                        choseGui.setItem(16,playerItems,null);
                                        player3.closeInventory();
                                        choseGui.openInventory(player3);

                                        final ItemBuilder accept = new ItemBuilder(Material.EMERALD_BLOCK).setTitle(ChatUtil.hexColor("#7496f2★ #ed9791ZAAKCEPTUJ #f5c35fULEPSZENIE #7496f2★"));
                                        choseGui.setItem(22,accept.build(),(player4, inventory3, i3, itemStack4, clickType3) -> {
                                            choseGui.setFinalInventory(false);
                                            InventoryUtil.removeItemFromPlayer(player4,itemStack,1);
                                            InventoryUtil.removeItemFromPlayer(player4,upgradeMaterial,1);
                                            for(Stats stats : Stats.values()) {
                                                final int stat = nbtItem.getInteger(stats.getType());
                                                if(stat != 0) {
                                                    nbtItem.setInteger(stats.getType(), (int) (stat + (stat * (0.05 * upgradeLevel))));
                                                }
                                            }
                                            nbtItem.setInteger("upgradeLevel",upgradeLevel+1);
                                            InventoryUtil.giveItems(player4,RPGItem.rebuildItem(nbtItem.getItem()));

                                            final ItemBuilder reUpgrade = new ItemBuilder(Material.GOLD_BLOCK).setTitle(ChatUtil.hexColor("#7496f2★ #ed9791ULEPSZ #f5c35fPONOWNIE #7496f2★"));
                                            choseGui.setItem(22,reUpgrade.build(),(player5, inventory4, i4, itemStack5, clickType4) -> {
                                                choseGui.setFinalInventory(false);
                                                openUpgradeItem(player5);
                                            });

                                        });
                                    });
                                }
                            }
                            if(choseUpgradeMaterial.get().isEmpty()){
                                ChatUtil.sendMessage(player2,"&4Blad: &cNie masz zadnego zwoju do ulepszenia!");
                                return;
                            }
                            choseGui.setFinalInventory(true);
                            player2.closeInventory();
                            choseUpgradeMaterial.openInventory(player2);
                        });

                        choseGui.openInventory(player1);
                    });
                }
            }
        }
        if(upgradeGui.get().isEmpty()){
            ChatUtil.sendMessage(player,"&4Blad: &cNie masz zadnego przedmiotu do ulepszenia!");
        }else {
            upgradeGui.openInventory(player);
        }
    }

    public static void openChangeBonus(Player player){
        final InventoryGUI bottomGui = new InventoryGUI(ChatUtil.hexColor("#fb24c5U#fb2cc2L#fb33bfE#fb3bbcP#fc43b8S#fc4ab5Z #fc52b2P#fc5aafR#fc61acZ#fc69a9E#fc70a6D#fd78a2M#fd809fI#fd879cO#fd8f99T"),3);

        for(ItemStack item : player.getInventory().getContents()){
            if(RPGUtil.isRPGItem(item)){
                bottomGui.addItem(item,(bottomPlayer, inventory1, i1, itemStack1, clickType1) -> {
                    final NBTItem nbtItem = new NBTItem(itemStack1);


                    final InventoryGUI bottombottomGui = new InventoryGUI(ChatUtil.hexColor("#97EFFFULEPSZ PRZEDMIOT"),2);
                    bottombottomGui.setFinalInventory(true);
                    //0 1 2 3 4 5 6 7 8
                    //9 10 11 12 13 14 15 16 17

                    final ItemBuilder glass = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setTitle(ChatUtil.hexColor("#0F272C#"));

                    int[] glassints = {0,1,2,4,7,6,8,9,10,11,12,14,15,16,17};
                    for(int glassi : glassints){
                        bottombottomGui.setItem(glassi,glass.build(),null);
                    }


                    bottombottomGui.setItem(3,item,null);

                    final ItemBuilder barrier = new ItemBuilder(Material.BARRIER).setTitle(ChatUtil.hexColor("#9C5BE8WYBIERZ ZWOJ"));

                    bottombottomGui.setItem(5,barrier.build(),(player1, inventory2, i2, itemStack2, clickType2) -> {
                        player1.closeInventory();

                        final InventoryGUI choseInventory = new InventoryGUI(ChatUtil.hexColor("#9C5BE8WYBIERZ ZWOJ"),2);
                        for(ItemStack content : player1.getInventory().getContents()){
                            if(content != null && content.getItemMeta() != null) {
                                final SpecialItem specialItem = RPG.getPlugin().getSpecialItems().getSpecialItemHashMap().get(content.getItemMeta().getDisplayName());
                                if(specialItem != null) {
                                    if (specialItem.getItemStack().isSimilar(content)) {
                                        if (nbtItem.getFloat(specialItem.getAdditionalModifiers().getType()) >= specialItem.getHasTo()) {
                                            choseInventory.addItem(content, (player2, inventory3, i3, itemStack3, clickType3) -> {
                                                player2.closeInventory();

                                                final ItemStack cloneContent = content.clone();
                                                cloneContent.setAmount(1);
                                                bottombottomGui.setItem(5, cloneContent, null);
                                                final ItemBuilder accept = new ItemBuilder(Material.GREEN_STAINED_GLASS).setTitle(ChatUtil.hexColor("#6CD677%V% ZMIEN BONUS %V%"));
                                                bottombottomGui.setItem(13, accept.build(), (player3, inventory4, i4, itemStack4, clickType4) -> {
                                                    InventoryUtil.removeItemFromPlayer(player3, content, 1);
                                                    InventoryUtil.removeItemFromPlayer(player3, itemStack1, 1);

                                                    //TODO change
                                                    nbtItem.setFloat(specialItem.getAdditionalModifiers().getType(), (float) MathUtil.round((double) RandomUtil.getRandFloat(specialItem.getMin(), specialItem.getMax()), 1));

                                                    final ItemStack finalitem = RPGItem.rebuildItem(nbtItem.getItem());

                                                    InventoryUtil.giveItems(player3, finalitem);

                                                    bottombottomGui.setItem(13, new ItemBuilder(Material.PURPLE_STAINED_GLASS, 1).setTitle(ChatUtil.hexColor("#c65ce0%X% #e03aafZMIEN BONUS PONOWNIE #c65ce0%X%")).build(), (player4, inventory5, i5, itemStack5, clickType5) -> {
                                                        bottombottomGui.setFinalInventory(false);
                                                        player4.closeInventory();
                                                        openChangeBonus(player4);
                                                    });
                                                });
                                                bottombottomGui.openInventory(player2);
                                            });
                                        }
                                    }
                                }
                            }
                        }
                        choseInventory.openInventory(player1);
                    });
                    bottombottomGui.openInventory(bottomPlayer);


                });
            }
        }

        bottomGui.openInventory(player);
    }
}
