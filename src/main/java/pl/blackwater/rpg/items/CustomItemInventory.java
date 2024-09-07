package pl.blackwater.rpg.items;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.rpg.storage.AbstractRPGItem;
import pl.blackwater.rpg.storage.enums.AdditionalModifiers;
import pl.blackwater.rpg.storage.enums.Stats;
import pl.blackwater.rpg.util.RPGUtil;
import pl.blackwater.spigotplugin.spigot.inventory.actions.InventoryGUI;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.ItemBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomItemInventory {


    private static RPG plugin;

    private static List<Material> materialList = new ArrayList<>();
    static {
        plugin = RPG.getPlugin();


        //TODO maybe manualy write it xd
        for(Material material : Material.values()){
            if(material.name().endsWith("_CHESTPLATE") || material.name().endsWith("_LEGGINGS") || material.name().endsWith("_HELMET") || material.name().endsWith("_BOOTS") || material.name().endsWith("_SWORD") || material.name().endsWith("_AXE")){
                materialList.add(material);
            }
        }
        materialList.add(Material.BOW);
    }
    public static void open(Player player){
        //sword armor
        final InventoryGUI inv = new InventoryGUI(ChatUtil.fixColor("&8->> &6CUSTOM ITEM CREATOR &8<<-"),2);

        int itemsAmount = plugin.getCustomItemManager().getItems().size();
        final ItemBuilder create = new ItemBuilder(Material.EMERALD_BLOCK).setTitle(ChatUtil.fixColor("&8->> &aSTWORZ PRZEDMIOT"));
        final ItemBuilder createJew = new ItemBuilder(Material.NAUTILUS_SHELL).setTitle(ChatUtil.fixColor("&8->> &aSTWORZ BIŻUTERIE"));

        final ItemBuilder list = new ItemBuilder(Material.BOOK).setTitle(ChatUtil.fixColor("&8->> &6LISTA PRZEDMIOTOW"))
                .addLore(ChatUtil.fixColor("&8->> &7Liczba przedmiotow: &6" + itemsAmount));

        final ItemBuilder reBuildItem = new ItemBuilder(Material.REDSTONE_BLOCK).setTitle(ChatUtil.fixColor("&8->> &eSTWORZ PRZEDMIOT Z PRZEDMIOTU W EQ"));

        inv.setItem(0, create.build(), (p, inventory, i, itemStack, clickType) -> {

            final InventoryGUI botInv = new InventoryGUI(ChatUtil.fixColor("&8->> &6WYBIERZ PRZEDMIOT &8<<-"),6);

            final AbstractRPGItem abstractRPGItem = new AbstractRPGItem();

            int absIndex = 0;
            for(Material m : materialList){
                final ItemBuilder a = new ItemBuilder(m,1).setTitle(ChatUtil.fixColor("&aKLIKNIJ ABY WYBRAC"));

                //TODO handler
                botInv.setItem(absIndex, a.build(), new CustomItemCreateHandler(m, abstractRPGItem));

                absIndex++;
            }
            botInv.openInventory(player);
        });
        inv.setItem(1,reBuildItem.build(),(p, inventory, i, itemStack, clickType) -> {
            final InventoryGUI botInv = new InventoryGUI(ChatUtil.fixColor("&8->> &6WYBIERZ PRZEDMIOT &8<<-"),6);

            for(ItemStack item : p.getInventory().getContents()) {
                if (RPGUtil.isRPGItem(item)){
                    final AbstractRPGItem abstractRPGItem = new AbstractRPGItem();
                    final NBTItem nbtItem = new NBTItem(item);

                    abstractRPGItem.setRpgClass(nbtItem.getString("rpgClass"));
                    abstractRPGItem.setName(item.getItemMeta().getDisplayName());
                    abstractRPGItem.setLevel(nbtItem.getInteger("level"));
                    abstractRPGItem.setArmor(nbtItem.getInteger("armor"));
                    abstractRPGItem.setDamage(nbtItem.getInteger("damage"));
                    abstractRPGItem.setAttackSpeed(nbtItem.getInteger("attack_speed"));
                    abstractRPGItem.setKnockBack_Resistance(nbtItem.getInteger("knockback_resistance"));
                    abstractRPGItem.setMax_health(nbtItem.getInteger("max_health"));
                    abstractRPGItem.setLuck(nbtItem.getInteger("luck"));
                    abstractRPGItem.setAverageMonsterDamage(nbtItem.getFloat(AdditionalModifiers.AMD.getType()));
                    abstractRPGItem.setAverageHumanDamage(nbtItem.getFloat(AdditionalModifiers.AHD.getType()));
                    abstractRPGItem.setAverageCreatureDamage(nbtItem.getFloat(AdditionalModifiers.ACD.getType()));
                    abstractRPGItem.setAdditionalDamageOfCriticalChance(nbtItem.getFloat(AdditionalModifiers.ADOCH.getType()));
                    abstractRPGItem.setAdditionalCriticalChance(nbtItem.getFloat(AdditionalModifiers.ACH.getType()));
                    abstractRPGItem.setSTRENGTH(nbtItem.getInteger(Stats.STRENGTH.getType()));
                    abstractRPGItem.setDEXTERITY(nbtItem.getInteger(Stats.DEXTERITY.getType()));
                    abstractRPGItem.setINTELLIGENCE(nbtItem.getInteger(Stats.INTELLIGENCE.getType()));
                    abstractRPGItem.setSTAMINA(nbtItem.getInteger(Stats.STAMINA.getType()));
                    abstractRPGItem.setUnbreakable(true);
                    if(item.getItemMeta().getEnchants().size() >=1) {
                        abstractRPGItem.setEnchantments(item.getItemMeta().getEnchants());
                    }
                    botInv.addItem(item,new CustomItemCreateHandler(item.getType(),abstractRPGItem));

                }
            }
            p.closeInventory();
            botInv.openInventory(p);
        });

        inv.setItem(2, createJew.build(), (player1, inventory, i, itemStack, clickType) -> {
            final InventoryGUI gui = new InventoryGUI("&8->> &6WYBIERZ PRZEDMIOT &8<<-", 6);

            AtomicInteger ai = new AtomicInteger(0);
            for (ItemStack content : player.getInventory().getContents()) {
                if(content != null){
                    final ItemBuilder chooseItem = new ItemBuilder(content.getType()).setTitle(ChatUtil.fixColor("&aKLIKNIJ ABY WYBRAC"));
                    gui.setItem(ai.getAndIncrement(), chooseItem.build(), new JeweleryCreateHandler(content.getType(), new AbstractRPGItem()));
                }
            }

            gui.openInventory(player);
        });

        inv.setItem(3,list.build(),(player1, inventory, i, itemStack, clickType) -> {
            int index  = 0;
            final InventoryGUI botinv = new InventoryGUI(ChatUtil.fixColor("&8->> &aLISTA PRZEDMIOTOW &8<<-"),5);
            for(ItemStack itemStacks : plugin.getCustomItemManager().getItems().values()){
                botinv.setItem(index,itemStacks,(player2, inventory1, i1, itemStack1, clickType1) -> {
                    if(clickType1.equals(ClickType.LEFT)) {
                        player2.getInventory().addItem(itemStacks);
                    } else if (clickType1.equals(ClickType.RIGHT)) {
                        plugin.getCustomItemManager().getItems().forEach((uuid, itemStack2) -> {
                            if(itemStack2.equals(itemStacks)){
                                plugin.getCustomItemManager().getItems().remove(uuid);
                            }
                        });
                    }
                });
                index++;
            }

            player.closeInventory();
            botinv.openInventory(player);

        });
        inv.openInventory(player);
    }
}
