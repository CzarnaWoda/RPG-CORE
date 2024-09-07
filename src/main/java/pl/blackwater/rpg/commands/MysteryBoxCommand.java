package pl.blackwater.rpg.commands;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.rpg.mysterybox.MysteryBox;
import pl.blackwater.rpg.util.SignFactory;
import pl.blackwater.spigotplugin.spigot.commands.PlayerCommand;
import pl.blackwater.spigotplugin.spigot.inventory.actions.InventoryGUI;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.InventoryUtil;
import pl.blackwater.spigotplugin.util.ItemBuilder;
import pl.blackwater.spigotplugin.util.Util;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class MysteryBoxCommand extends PlayerCommand {
    private final RPG plugin;
    public MysteryBoxCommand(RPG plugin) {
        super("mysterybox", "Command to manage mysteryboxes", "/mysterybox", "rpg.mysterybox", "mb");
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(Player player, String[] strings) {
        openMenu(player);
        return true;


    }
    private void openMenu(Player player){
        final InventoryGUI inv = new InventoryGUI(ChatUtil.fixColor("&8->> &6&nMYSTERYBOXES&8 <<-"),2);

        final ItemBuilder mbList = new ItemBuilder(Material.BOOK).setTitle(ChatUtil.fixColor("&8->> &aLISTA SKRZYNEK"));

        final ItemBuilder giveAll = new ItemBuilder(Material.GOLD_BLOCK).setTitle(ChatUtil.fixColor("&8->> &aDAJ SKRZYNKE WSZYSTKIM NA SERWERZE"));

        final ItemBuilder editCase = new ItemBuilder(Material.FEATHER).setTitle(ChatUtil.fixColor("&8->> &aEDYTUJ SKRZYNKE"));

        final ItemBuilder createCase = new ItemBuilder(Material.CHEST).setTitle(ChatUtil.fixColor("&8->> &3&lSTWORZ SKRZYNKE"));

        inv.setItem(3,createCase.build(),(player1, inventory, i, itemStack, clickType) -> {
            final InventoryGUI bottomInv = new InventoryGUI(ChatUtil.hexColor("&8->> &6WYBIERZ PARAMETRY"),1);
            bottomInv.setFinalInventory(true);

            AtomicReference<MysteryBox> mysteryBox = new AtomicReference<>();
            final ItemBuilder name = new ItemBuilder(Material.BOOK).setTitle(ChatUtil.hexColor("&8->> &aWYBIERZ NAZWE"));
            bottomInv.addItem(name.build(),(player2, inventory1, i1, itemStack1, clickType1) -> {
                player2.closeInventory();
                SignFactory.Menu menu = plugin.getSignFactory().newMenu(Collections.singletonList("WPISZ NAZWE PONIZEJ!"))
                        .reopenIfFail(true)
                        .response((menuPlayer, strings) -> {
                            final String s = StringUtils.join(strings,"",1,strings.length-1);

                            mysteryBox.set(new MysteryBox(s));
                            Bukkit.getScheduler().runTaskLater(RPG.getPlugin(),() -> bottomInv.openInventory(menuPlayer),20L);
                            return true;
                        });
                menu.open(player2);
                final ItemBuilder create = new ItemBuilder(Material.ARROW).setTitle(ChatUtil.hexColor("&8->> &6STWORZ"));
                bottomInv.addItem(create.build(),(player3, inventory2, i2, itemStack2, clickType2) -> {
                    plugin.getMysteryBoxStorage().getMysteryBoxList().add(mysteryBox.get());
                    //mysteryBox.get().save();
                    bottomInv.setFinalInventory(false);
                    player3.closeInventory();
                });
            });
            bottomInv.openInventory(player1);
        });

        inv.setItem(2,editCase.build(),(p, actionInv, i, item, clickType) -> {
            p.closeInventory();
            final InventoryGUI bottomInv = new InventoryGUI(ChatUtil.fixColor("&8->> &6WYBIERZ SKRZYNKE &8<<-"),1);

            final AtomicInteger index= new AtomicInteger(-1);
            plugin.getMysteryBoxStorage().getMysteryBoxList().forEach(mysteryBox -> bottomInv.setItem(index.incrementAndGet(), mysteryBox.getBoxItem(),(player1, inventory, i1, itemStack, clickType1) -> openChestEditMenu(player, mysteryBox)));
            bottomInv.openInventory(p);

        });

        inv.setItem(1, giveAll.build(),(p, actionInv, i, item, clickType) -> {
            p.closeInventory();

            final InventoryGUI bottomInv = new InventoryGUI(ChatUtil.fixColor("&8->> &6WSZYSTKIE SKRZYNKI NA SERWERZE &8<<-"),1);

            int index = 0;
            for(MysteryBox mysteryBox : plugin.getMysteryBoxStorage().getMysteryBoxList()) {
                bottomInv.setItem(index++, mysteryBox.getBoxItem(), (player1, inventory, i1, itemStack, clickType1) -> {
                    final ItemStack cloneMysteryBoxItem = mysteryBox.getBoxItem().clone();
                    player1.closeInventory();
                    SignFactory.Menu menu = plugin.getSignFactory().newMenu(Collections.singletonList("WPISZ ILOSC PONIZEJ!"))
                            .reopenIfFail(true)
                            .response((menuPlayer, strings) -> {
                                final String s = strings[1];
                                if (s != null && Util.isInteger(s)) {
                                    final int value = Integer.parseInt(s);
                                    cloneMysteryBoxItem.setAmount(value);
                                    for (Player online : Bukkit.getOnlinePlayers()) {
                                        InventoryUtil.giveItems(online,cloneMysteryBoxItem);

                                        final RPGUser user = plugin.getRpgUserManager().getUser(online.getUniqueId());
                                        if(user.getChatUser().isMysteryBoxGiveAllMessageChat()){
                                            ChatUtil.sendMessage(online,"&8->>         &5⚛ &5&l" + mysteryBox.getBoxName()+ " &5⚛         &8<<-");
                                            ChatUtil.sendMessage(online, "&8->>  &6⚜ &eCALY &6&lSERWER&e OTZYMAL &6&lSKRZYNKE &8(&7x&c" + value + "&8) &6⚜");
                                            ChatUtil.sendMessage(online,"&8->>         &5&l⚛ " + mysteryBox.getBoxName()+ " ⚛         &8<<-");
                                        }
                                        return true;
                                    }
                                } else {
                                    return false;
                                }
                                return false;
                            });
                    menu.open(player1);

                });
            }

            bottomInv.setItem(bottomInv.get().getSize() - 1 , new ItemBuilder(Material.RED_WOOL).setTitle(ChatUtil.fixColor("&8->> &c&nPOWROT&8 <<-")).build(),(player1, inventory, i1, itemStack, clickType1) -> {
                player1.closeInventory();
                openMenu(player1);
            });
            bottomInv.openInventory(p);
        });

        inv.setItem(0, mbList.build(),(p, actionInv, i, item, clickType) -> {
            p.closeInventory();

            final InventoryGUI bottomInv = new InventoryGUI(ChatUtil.fixColor("&8->> &6WSZYSTKIE SKRZYNKI NA SERWERZE &8<<-"),1);

            int index = 0;
            for(MysteryBox mysteryBox : plugin.getMysteryBoxStorage().getMysteryBoxList()){
                bottomInv.setItem(index++,mysteryBox.getBoxItem(),(player1, inventory, i1, itemStack, clickType1) -> {
                    final ItemStack cloneMysteryBoxItem = mysteryBox.getBoxItem().clone();
                    cloneMysteryBoxItem.setAmount(1);
                    InventoryUtil.giveItems(player1,cloneMysteryBoxItem);
                });
            }

            bottomInv.setItem(bottomInv.get().getSize() - 1 , new ItemBuilder(Material.RED_WOOL).setTitle(ChatUtil.fixColor("&8->> &c&nPOWROT&8 <<-")).build(),(player1, inventory, i1, itemStack, clickType1) -> {
                player1.closeInventory();
                openMenu(player1);
                    });
            bottomInv.openInventory(p);
        });
        inv.openInventory(player);
    }

    private void openChestEditMenu(Player player, MysteryBox mysteryBox){
        final InventoryGUI typeChooseInventory = new InventoryGUI(ChatUtil.fixColor("&8->> &6WYBIERZ SPOSOB DODANIA PRZEDMIOTU &8<<-"), 1);
        typeChooseInventory.setFinalInventory(true);

        final ItemBuilder customTypeAdd = new ItemBuilder(Material.HONEYCOMB).setTitle(ChatUtil.fixColor("&8->> &aWYBIERZ CUSTOMOWY ITEM"));
        final ItemBuilder inventoryTypeAdd = new ItemBuilder(Material.CHEST).setTitle(ChatUtil.fixColor("&8->> &aWYBIERZ ITEM Z EKWIPUNKU"));
        final ItemBuilder removeItem = new ItemBuilder(Material.RED_TERRACOTTA).setTitle(ChatUtil.fixColor("&8->> &aUSUN PRZEDMIOT Z DROPU"));
        final ItemBuilder backItem = new ItemBuilder(Material.RED_WOOL).setTitle(ChatUtil.fixColor("&8->> &c&nPOWROT&8 <<-"));

        typeChooseInventory.setItem(3, customTypeAdd.build(), (player2, inventory1, i2, itemStack1, clickType2) -> {
            final InventoryGUI chooseItem = new InventoryGUI(ChatUtil.fixColor("&8->> &6WYBIERZ PRZEDMIOT &8<<-"), 6);
            AtomicInteger invSlot = new AtomicInteger(0);
            chooseItem.setItem(53, backItem.build(), (player1, inventory, i, itemStack, clickType) -> openChestEditMenu(player, mysteryBox));
            plugin.getCustomItemManager().getItems().values().forEach(itemStack2 -> chooseItem.setItem(invSlot.getAndIncrement(), itemStack2, (player3, inventory2, i3, itemStack3, clickType3) -> {
//                    mysteryBox.getDropList().add(itemStack3);
                mysteryBox.addDrop(itemStack3);
                typeChooseInventory.openInventory(player);
                ChatUtil.sendMessage(player, "&7>> &aPomyślnie dodałeś przedmiot do MysteryBoxa");
                mysteryBox.update();
            }));
            chooseItem.openInventory(player);
        });

        typeChooseInventory.setItem(5, inventoryTypeAdd.build(), (player2, inventory1, i2, itemStack1, clickType2) -> {
            final InventoryGUI chooseItem = new InventoryGUI(ChatUtil.fixColor("&8->> &6WYBIERZ PRZEDMIOT &8<<-"), 6);
            AtomicInteger invSlot = new AtomicInteger(0);
            chooseItem.setItem(53, backItem.build(), (player1, inventory, i, itemStack, clickType) -> openChestEditMenu(player, mysteryBox));
            for (ItemStack content : player.getInventory().getContents()) {
                chooseItem.setItem(invSlot.getAndIncrement(), content, (player3, inventory2, i3, itemStack2, clickType3) -> {
//                    mysteryBox.getDropList().add(itemStack2);
                    mysteryBox.addDrop(itemStack2);
                    typeChooseInventory.openInventory(player);
                    ChatUtil.sendMessage(player, "&7>> &aPomyślnie dodałeś przedmiot do MysteryBoxa");
                    mysteryBox.update();
                });
            }
            chooseItem.openInventory(player);
        });

        typeChooseInventory.setItem(4, removeItem.build(), (player2, inventory1, i2, itemStack1, clickType2) -> {
            if(mysteryBox.getDropList().length > 1){
                final InventoryGUI chooseItem = new InventoryGUI(ChatUtil.fixColor("&8->> &6WYBIERZ PRZEDMIOT &8<<-"), 6);
                chooseItem.setItem(53, backItem.build(), (player1, inventory, i, itemStack, clickType) -> openChestEditMenu(player, mysteryBox));
                AtomicInteger invSlot = new AtomicInteger(0);

                for (int i = 0; i < mysteryBox.getDropList().length; i++) {
                    if(mysteryBox.getDropList()[i] != null){
                        int finalI = i;
                        chooseItem.setItem(invSlot.getAndIncrement(), mysteryBox.getDropList()[finalI].getItemStack(), (player3, inventory2, i3, itemStack3, clickType3) -> {
                            mysteryBox.removeDrop(mysteryBox.getDropList()[finalI].getItemStack());
                            typeChooseInventory.openInventory(player);
                            ChatUtil.sendMessage(player, "&7>> &aPomyślnie usunąłeś przedmiot z MysteryBoxa");
                            mysteryBox.remove();
                        });
                    }
                }

                chooseItem.openInventory(player);
            }else {
                ChatUtil.sendMessage(player, "&7>> &cSkrzynka posiada tylko jeden przedmiot (Jeśli chcesz go usunąć, dodaj drugi)");
            }
        });

        typeChooseInventory.setItem(8, backItem.build(), (player1, inventory, i, itemStack, clickType) -> openMenu(player));

        typeChooseInventory.openInventory(player);
    }
}
