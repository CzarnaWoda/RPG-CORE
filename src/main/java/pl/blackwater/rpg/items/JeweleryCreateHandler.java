package pl.blackwater.rpg.items;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.enums.RPGClass;
import pl.blackwater.rpg.storage.AbstractRPGItem;
import pl.blackwater.rpg.storage.RPGItem;
import pl.blackwater.rpg.util.SignFactory;
import pl.blackwater.spigotplugin.spigot.inventory.actions.IAction;
import pl.blackwater.spigotplugin.spigot.inventory.actions.InventoryGUI;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.ItemBuilder;

import java.util.Collections;

@RequiredArgsConstructor
public class JeweleryCreateHandler implements IAction {

    private final Material material;
    private final AbstractRPGItem abstractRPGItem;

    @Override
    public void execute(Player player, Inventory inventory, int i, ItemStack itemStack, ClickType clickType) {
        abstractRPGItem.setMaterial(material);

        InventoryGUI handlerInv = new InventoryGUI("&8->> &6USTAW STATYSTYKI &8<<-", 5);


        final ItemBuilder levelItem = new ItemBuilder(Material.EXPERIENCE_BOTTLE).setTitle("&8->> &6LEVEL: &a" + abstractRPGItem.getLevel());

        final ItemBuilder avgDmgMonsterItem = new ItemBuilder(Material.ZOMBIE_HEAD).setTitle("&8->> &6OBRAZENIA DLA POTWOROW: &a" + abstractRPGItem.getAverageMonsterDamage());
        final ItemBuilder avgDmgHumanItem = new ItemBuilder(Material.PLAYER_HEAD).setTitle("&8->> &6OBRAZENIA DLA GRACZY: &a" + abstractRPGItem.getAverageHumanDamage());
        final ItemBuilder avgDmgCreatureItem = new ItemBuilder(Material.SKELETON_SKULL).setTitle("&8->> &6OBRAZENIA DLA STWOROW: &a" + abstractRPGItem.getAverageCreatureDamage());
        final ItemBuilder criticalDmgItem = new ItemBuilder(Material.IRON_SWORD).setTitle("&8->> &6DODATKOWE KRYTYCZNE OBRAZENIA: &a" + abstractRPGItem.getAdditionalDamageOfCriticalChance());
        final ItemBuilder criticalChanceItem = new ItemBuilder(Material.IRON_AXE).setTitle("&8->> &6DODATKOWA SZANSA NA CIOS KRYTYCZNY: &a" + abstractRPGItem.getAdditionalCriticalChance());

        final ItemBuilder strengthItem = new ItemBuilder(Material.GOLD_INGOT).setTitle("&8->> &6SILA: &a" + abstractRPGItem.getSTRENGTH());
        final ItemBuilder dexterityItem = new ItemBuilder(Material.FEATHER).setTitle("&8->> &6ZRECZNOSC: &a" + abstractRPGItem.getDEXTERITY());
        final ItemBuilder intelligenceItem = new ItemBuilder(Material.LAPIS_BLOCK).setTitle("&8->> &6INTELIGENCJA: &a" + abstractRPGItem.getINTELLIGENCE());
        final ItemBuilder staminaItem = new ItemBuilder(Material.GOLDEN_APPLE).setTitle("&8->> &6STAMINA: &a" + abstractRPGItem.getSTAMINA());
        final ItemBuilder classItem = new ItemBuilder(Material.EMERALD).setTitle(ChatUtil.hexColor("&8->> &6KLASA:  " + abstractRPGItem.getClass()));

        //LEVEL
        handlerInv.setItem(0,levelItem.build(), (p, inv, index, item, click) -> {
            if(click.equals(ClickType.LEFT)){
                abstractRPGItem.setLevel(abstractRPGItem.getLevel() + 1);

            }
            if(click.equals(ClickType.RIGHT)){
                abstractRPGItem.setLevel(abstractRPGItem.getLevel() - 1);
            }
            updateItemStack(item, ChatUtil.fixColor("&8->> &6LEVEL: &a" + abstractRPGItem.getLevel()));
        });

        //AVG MONSTER DMG
        handlerInv.setItem(1,avgDmgMonsterItem.build(), (p, inv, index, item, click) -> {
            if(click.equals(ClickType.LEFT)){
                abstractRPGItem.setAverageMonsterDamage(abstractRPGItem.getAverageMonsterDamage() + 0.1);

            }
            if(click.equals(ClickType.RIGHT)){
                abstractRPGItem.setAverageMonsterDamage(abstractRPGItem.getAverageMonsterDamage() - 0.1);
            }
            updateItemStack(item,ChatUtil.fixColor("&8->> &6OBRAZENIA DLA POTWOROW: &a" + abstractRPGItem.getAverageMonsterDamage()));
        });

        //AVG HUMAN DMG
        handlerInv.setItem(2,avgDmgHumanItem.build(), (p, inv, index, item, click) -> {
            if(click.equals(ClickType.LEFT)){
                abstractRPGItem.setAverageHumanDamage(abstractRPGItem.getAverageHumanDamage() + 0.1);

            }
            if(click.equals(ClickType.RIGHT)){
                abstractRPGItem.setAverageHumanDamage(abstractRPGItem.getAverageHumanDamage() - 0.1);
            }
            updateItemStack(item,ChatUtil.fixColor("&8->> &6OBRAZENIA DLA GRACZY: &a" + abstractRPGItem.getAverageHumanDamage()));
        });

        //AVG CREATURE DMG
        handlerInv.setItem(3,avgDmgCreatureItem.build(), (p, inv, index, item, click) -> {
            if(click.equals(ClickType.LEFT)){
                abstractRPGItem.setAverageCreatureDamage(abstractRPGItem.getAverageCreatureDamage() + 0.1);

            }
            if(click.equals(ClickType.RIGHT)){
                abstractRPGItem.setAverageCreatureDamage(abstractRPGItem.getAverageCreatureDamage() - 0.1);
            }
            updateItemStack(item,ChatUtil.fixColor("&8->> &6OBRAZENIA DLA STWOROW: &a" + abstractRPGItem.getAverageCreatureDamage()));
        });

        //ADDITIONAL DMG OF CRITICAL
        handlerInv.setItem(4,criticalDmgItem.build(), (p, inv, index, item, click) -> {
            if(click.equals(ClickType.LEFT)){
                abstractRPGItem.setAdditionalDamageOfCriticalChance(abstractRPGItem.getAdditionalDamageOfCriticalChance() + 0.1);

            }
            if(click.equals(ClickType.RIGHT)){
                abstractRPGItem.setAdditionalDamageOfCriticalChance(abstractRPGItem.getAdditionalDamageOfCriticalChance() - 0.1);
            }
            updateItemStack(item,ChatUtil.fixColor("&8->> &6DODATKOWE KRYTYCZNE OBRAZENIA: &a" + abstractRPGItem.getAdditionalDamageOfCriticalChance()));
        });

        //ADDITIONAL CHANCE FOR CRITICAL
        handlerInv.setItem(5,criticalChanceItem.build(), (p, inv, index, item, click) -> {
            if(click.equals(ClickType.LEFT)){
                abstractRPGItem.setAdditionalCriticalChance(abstractRPGItem.getAdditionalCriticalChance() + 0.1);

            }
            if(click.equals(ClickType.RIGHT)){
                abstractRPGItem.setAdditionalCriticalChance(abstractRPGItem.getAdditionalCriticalChance() - 0.1);
            }
            updateItemStack(item,ChatUtil.fixColor("&8->> &6DODATKOWA SZANSA NA CIOS KRYTYCZNY: &a" + abstractRPGItem.getAdditionalCriticalChance()));
        });

        //STRENGTH
        handlerInv.setItem(6,strengthItem.build(), (p, inv, index, item, click) -> {
            if(click.equals(ClickType.LEFT)){
                abstractRPGItem.setSTRENGTH(abstractRPGItem.getSTRENGTH() + 1);

            }
            if(click.equals(ClickType.RIGHT)){
                abstractRPGItem.setSTRENGTH(abstractRPGItem.getSTRENGTH() - 1);
            }
            updateItemStack(item,ChatUtil.fixColor("&8->> &6SILA: &a" + abstractRPGItem.getSTRENGTH()));
        });

        //DEXTERITY
        handlerInv.setItem(7,dexterityItem.build(), (p, inv, index, item, click) -> {
            if(click.equals(ClickType.LEFT)){
                abstractRPGItem.setDEXTERITY(abstractRPGItem.getDEXTERITY() + 1);

            }
            if(click.equals(ClickType.RIGHT)){
                abstractRPGItem.setDEXTERITY(abstractRPGItem.getDEXTERITY() - 1);
            }
            updateItemStack(item,ChatUtil.fixColor("&8->> &6ZRECZNOSC: &a" + abstractRPGItem.getDEXTERITY()));
        });

        //INTELLIGENCE
        handlerInv.setItem(8,intelligenceItem.build(), (p, inv, index, item, click) -> {
            if(click.equals(ClickType.LEFT)){
                abstractRPGItem.setINTELLIGENCE(abstractRPGItem.getINTELLIGENCE() + 1);

            }
            if(click.equals(ClickType.RIGHT)){
                abstractRPGItem.setINTELLIGENCE(abstractRPGItem.getINTELLIGENCE() - 1);
            }
            updateItemStack(item,ChatUtil.fixColor("&8->> &6INTELIGENCJA: &a" + abstractRPGItem.getINTELLIGENCE()));
        });

        //STAMINA
        handlerInv.setItem(9,staminaItem.build(), (p, inv, index, item, click) -> {
            if(click.equals(ClickType.LEFT)){
                abstractRPGItem.setSTAMINA(abstractRPGItem.getSTAMINA() + 1);

            }
            if(click.equals(ClickType.RIGHT)){
                abstractRPGItem.setSTAMINA(abstractRPGItem.getSTAMINA() - 1);
            }
            updateItemStack(item,ChatUtil.fixColor("&8->> &6STAMINA: &a" + abstractRPGItem.getSTAMINA()));
        });

        final ItemBuilder name = new ItemBuilder(Material.WRITABLE_BOOK,1).setTitle(ChatUtil.fixColor("&8->> &7NAZWA: &6" + abstractRPGItem.getName()));

        handlerInv.setItem(10,name.build(),(player1, inventory1, i1, itemStack1, clickType1) -> {
            handlerInv.setFinalInventory(true);
            player1.closeInventory();
            SignFactory.Menu menu = RPG.getPlugin().getSignFactory().newMenu(Collections.singletonList("WPISZ NAZWE!"))
                    .reopenIfFail(true)
                    .response((splayer, strings) -> {
                        String s = StringUtils.join(strings,"",1,4);

                        if(s != null){
                            abstractRPGItem.setName(s);
                            Bukkit.getScheduler().runTaskLater(RPG.getPlugin(),() -> {
                                        handlerInv.openInventory(player);
                                        updateItemStack(itemStack1,"&8->> &7NAZWA: &6" + abstractRPGItem.getName());
                                    }
                                    ,20L);
                            return true;
                        }
                        return false;
                    });
            menu.open(player1);
        });
        handlerInv.setItem(11,classItem.build(),(player1, inventory1, i1, itemStack1, clickType1) -> {
            if(abstractRPGItem.getRpgClass().equals(RPGClass.WARRIOR.name())){
                abstractRPGItem.setRpgClass(RPGClass.HUNTER.name());
            }else if(abstractRPGItem.getRpgClass().equals(RPGClass.HUNTER.name())){
                abstractRPGItem.setRpgClass(RPGClass.WARRIOR.name());
            }
            updateItemStack(itemStack1,"&8->> &6KLASA:  " + abstractRPGItem.getClass());
        });

        final ItemBuilder create = new ItemBuilder(Material.RED_WOOL).setTitle(ChatUtil.fixColor("&c%V% STWORZ BIŻUTERIE %V%"));

        handlerInv.setItem(31,create.build(),(player1, inventory1, i1, itemStack1, clickType1) -> {
            handlerInv.setFinalInventory(false);
            final RPGItem rpgItem = new RPGItem(abstractRPGItem.getMaterial(),
                    abstractRPGItem.getName(),
                    abstractRPGItem.getRpgClass(),
                    abstractRPGItem.getLevel(),
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    (float) abstractRPGItem.getAverageMonsterDamage(),
                    (float) abstractRPGItem.getAverageHumanDamage(),
                    (float) abstractRPGItem.getAverageCreatureDamage(),
                    (float) abstractRPGItem.getAdditionalDamageOfCriticalChance(),
                    (float) abstractRPGItem.getAdditionalCriticalChance(),
                    abstractRPGItem.getSTRENGTH(),
                    abstractRPGItem.getDEXTERITY(),
                    abstractRPGItem.getINTELLIGENCE(),
                    abstractRPGItem.getSTAMINA(),
                    true,
                    ItemType.JEWELERY,
                    null);

            RPG.getPlugin().getCustomItemManager().createItem(rpgItem,StorageType.REDIS);

            player.closeInventory();

        });

        handlerInv.openInventory(player);

    }

    private static ItemStack updateItemStack(ItemStack itemStack,String name){

        final ItemMeta meta = itemStack.getItemMeta();

        meta.setDisplayName(ChatUtil.fixColor(name));

        itemStack.setItemMeta(meta);

        return itemStack;
    }

}
