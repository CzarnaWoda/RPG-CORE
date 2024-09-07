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
public class CustomItemCreateHandler implements IAction {

    private final  Material material;
    private final AbstractRPGItem abstractRPGItem;

    /*
        private final int level;
    private final int armor;
    private final int damage;
    private final int attackSpeed;
    private final int knockBack_Resistance;
    private final int max_health;
    private final int luck;
    private final float averageMonsterDamage;
    private final float averageHumanDamage;
    private final float averageCreatureDamage;
    private final float additionalDamageOfCriticalChance;
    private final float additionalCriticalChance;
    private final int STRENGTH;
    private final int DEXTERITY;
    private final int INTELLIGENCE;
    private final int STAMINA;
    private final boolean unbreakable;
     */


    @Override
    public void execute(Player player, Inventory inventory, int i, ItemStack itemStack, ClickType clickType) {
        abstractRPGItem.setMaterial(material);


        final InventoryGUI handlerInv = new InventoryGUI(ChatUtil.fixColor("&8->> &6STATS &8<<-"),4);

        final ItemBuilder levelItem = new ItemBuilder(Material.EXPERIENCE_BOTTLE).setTitle("&8->> &6LEVEL: &a" + abstractRPGItem.getLevel());
        final ItemBuilder armorItem = new ItemBuilder(Material.DIAMOND_CHESTPLATE).setTitle("&8->> &6ARMOR: &a" + abstractRPGItem.getArmor());
        final ItemBuilder damageItem = new ItemBuilder(Material.DIAMOND_SWORD).setTitle("&8->> &6DAMAGE: &a" + abstractRPGItem.getDamage());
        final ItemBuilder attackSpeedItem = new ItemBuilder(Material.SUGAR).setTitle("&8->> &6SZYBKOSC ATAKU: &a" + abstractRPGItem.getAttackSpeed());
        final ItemBuilder knockItem = new ItemBuilder(Material.FEATHER).setTitle("&8->> &6ODPORNOSC NA KNOCKBACK: &a" + abstractRPGItem.getKnockBack_Resistance());
        final ItemBuilder maxHealthItem = new ItemBuilder(Material.HEART_OF_THE_SEA).setTitle("&8->> &6DODATKOWE HP: &a" + abstractRPGItem.getMax_health());
        final ItemBuilder luckItem = new ItemBuilder(Material.GOLD_NUGGET).setTitle("&8->> &6SZCZESCIE: &a" + abstractRPGItem.getLuck());
        final ItemBuilder avgDmgMonsterItem = new ItemBuilder(Material.ZOMBIE_HEAD).setTitle("&8->> &6OBRAZENIA DLA POTWOROW: &a" + abstractRPGItem.getAverageMonsterDamage());
        final ItemBuilder avgDmgHumanItem = new ItemBuilder(Material.PLAYER_HEAD).setTitle("&8->> &6OBRAZENIA DLA GRACZY: &a" + abstractRPGItem.getAverageHumanDamage());
        final ItemBuilder avgDmgCreatureItem = new ItemBuilder(Material.SKELETON_SKULL).setTitle("&8->> &6OBRAZENIA DLA STWOROW: &a" + abstractRPGItem.getAverageCreatureDamage());
        final ItemBuilder criticalDmgItem = new ItemBuilder(Material.IRON_SWORD).setTitle("&8->> &6DODATKOWE KRYTYCZNE OBRAZENIA: &a" + abstractRPGItem.getAdditionalDamageOfCriticalChance());
        final ItemBuilder criticalChanceItem = new ItemBuilder(Material.IRON_AXE).setTitle("&8->> &6DODATKOWA SZANSA NA CIOS KRYTYCZNY: &a" + abstractRPGItem.getAdditionalCriticalChance());
        final ItemBuilder strengthItem = new ItemBuilder(Material.GOLD_INGOT).setTitle("&8->> &6SILA: &a" + abstractRPGItem.getSTRENGTH());
        final ItemBuilder dexterityItem = new ItemBuilder(Material.FEATHER).setTitle("&8->> &6ZRECZNOSC: &a" + abstractRPGItem.getDEXTERITY());
        final ItemBuilder intelligenceItem = new ItemBuilder(Material.LAPIS_BLOCK).setTitle("&8->> &6INTELIGENCJA: &a" + abstractRPGItem.getINTELLIGENCE());
        final ItemBuilder staminaItem = new ItemBuilder(Material.GOLDEN_APPLE).setTitle("&8->> &6STAMINA: &a" + abstractRPGItem.getSTAMINA());
        final ItemBuilder unbreakableItem = new ItemBuilder(Material.ANVIL).setTitle("&8->> &6NIEZNISZCZALNE: " + (abstractRPGItem.isUnbreakable() ? ChatUtil.fixColor("&a%V%") : ChatUtil.fixColor("&c%X%")));
        final ItemBuilder classItem = new ItemBuilder(Material.EMERALD).setTitle("&8->> &6KLASA: &a" + abstractRPGItem.getRpgClass());



        //LEVEL
        handlerInv.setItem(0,levelItem.build(), (p, inv, index, item, click) -> {
            if(click.equals(ClickType.LEFT) || click.equals(ClickType.SHIFT_LEFT)){
                abstractRPGItem.setLevel(abstractRPGItem.getLevel() + (click.isShiftClick() ? 10 : 1));

            }
            if(click.equals(ClickType.RIGHT) || click.equals(ClickType.SHIFT_RIGHT)){
                abstractRPGItem.setLevel(abstractRPGItem.getLevel() - (click.isShiftClick() ? 10 : 1));
            }
            updateItemStack(item,ChatUtil.fixColor("&8->> &6LEVEL: &a" + abstractRPGItem.getLevel()));
        });
        //ARMOR
        handlerInv.setItem(1,armorItem.build(), (p, inv, index, item, click) -> {
            if(click.equals(ClickType.LEFT) || click.equals(ClickType.SHIFT_LEFT)){
                abstractRPGItem.setArmor(abstractRPGItem.getArmor() + (click.isShiftClick() ? 10 : 1));

            }
            if(click.equals(ClickType.RIGHT) || click.equals(ClickType.SHIFT_RIGHT)){
                abstractRPGItem.setArmor(abstractRPGItem.getArmor() - (click.isShiftClick() ? 10 : 1));
            }
            updateItemStack(item,ChatUtil.fixColor("&8->> &6ARMOR: &a" + abstractRPGItem.getArmor()));
        });
        //DAMAGE
        handlerInv.setItem(2,damageItem.build(), (p, inv, index, item, click) -> {
            if(click.equals(ClickType.LEFT) || click.equals(ClickType.SHIFT_LEFT)){
                abstractRPGItem.setDamage(abstractRPGItem.getDamage() + (click.isShiftClick() ? 10 : 1));

            }
            if(click.equals(ClickType.RIGHT) || click.equals(ClickType.SHIFT_RIGHT)){
                abstractRPGItem.setDamage(abstractRPGItem.getDamage() - (click.isShiftClick() ? 10 : 1));
            }
            updateItemStack(item,ChatUtil.fixColor("&8->> &6DAMAGE: &a" + abstractRPGItem.getDamage()));
        });

        //ATTACK SPEED
        handlerInv.setItem(3,attackSpeedItem.build(), (p, inv, index, item, click) -> {
            if(click.equals(ClickType.LEFT) || click.equals(ClickType.SHIFT_LEFT)){
                abstractRPGItem.setAttackSpeed(abstractRPGItem.getAttackSpeed() + (click.isShiftClick() ? 10 : 1));

            }
            if(click.equals(ClickType.RIGHT) || click.equals(ClickType.SHIFT_RIGHT)){
                abstractRPGItem.setAttackSpeed(abstractRPGItem.getAttackSpeed() - (click.isShiftClick() ? 10 : 1));
            }
            updateItemStack(item,ChatUtil.fixColor("&8->> &6SZYBKOSC ATAKU: &a" + abstractRPGItem.getAttackSpeed()));
        });

        //KNOCK RESISTANCE
        handlerInv.setItem(4,knockItem.build(), (p, inv, index, item, click) -> {
            if(click.equals(ClickType.LEFT) || click.equals(ClickType.SHIFT_LEFT)){
                abstractRPGItem.setKnockBack_Resistance(abstractRPGItem.getKnockBack_Resistance() + (click.isShiftClick() ? 10 : 1));

            }
            if(click.equals(ClickType.RIGHT) || click.equals(ClickType.SHIFT_RIGHT)){
                abstractRPGItem.setKnockBack_Resistance(abstractRPGItem.getKnockBack_Resistance() - (click.isShiftClick() ? 10 : 1));
            }
            updateItemStack(item,ChatUtil.fixColor("&8->> &6ODPORNOSC NA KNOCKBACK: &a" + abstractRPGItem.getKnockBack_Resistance()));
        });

        //MAX HEALTH
        handlerInv.setItem(5,maxHealthItem.build(), (p, inv, index, item, click) -> {
            if(click.equals(ClickType.LEFT) || click.equals(ClickType.SHIFT_LEFT)){
                abstractRPGItem.setMax_health(abstractRPGItem.getMax_health() + (click.isShiftClick() ? 10 : 1));

            }
            if(click.equals(ClickType.RIGHT) || click.equals(ClickType.SHIFT_RIGHT)){
                abstractRPGItem.setMax_health(abstractRPGItem.getMax_health() - (click.isShiftClick() ? 10 : 1));
            }
            updateItemStack(item,ChatUtil.fixColor("&8->> &6DODATKOWE HP: &a" + abstractRPGItem.getMax_health()));
        });

        //LUCK
        handlerInv.setItem(6,luckItem.build(), (p, inv, index, item, click) -> {
            if(click.equals(ClickType.LEFT) || click.equals(ClickType.SHIFT_LEFT)){
                abstractRPGItem.setLuck(abstractRPGItem.getLuck() + (click.isShiftClick() ? 10 : 1));

            }
            if(click.equals(ClickType.RIGHT) || click.equals(ClickType.SHIFT_RIGHT)){
                abstractRPGItem.setLuck(abstractRPGItem.getLuck() - (click.isShiftClick() ? 10 : 1));
            }
            updateItemStack(item,ChatUtil.fixColor("&8->> &6SZCZESCIE: &a" + abstractRPGItem.getLuck()));
        });

        //AVG MONSTER DMG
        handlerInv.setItem(7,avgDmgMonsterItem.build(), (p, inv, index, item, click) -> {
            if(click.equals(ClickType.LEFT) || click.equals(ClickType.SHIFT_LEFT)){
                abstractRPGItem.setAverageMonsterDamage(abstractRPGItem.getAverageMonsterDamage() + (click.isShiftClick() ? 1.0 : 0.1));

            }
            if(click.equals(ClickType.RIGHT) || click.equals(ClickType.SHIFT_RIGHT)){
                abstractRPGItem.setAverageMonsterDamage(abstractRPGItem.getAverageMonsterDamage() - (click.isShiftClick() ? 1.0 : 0.1));
            }
            updateItemStack(item,ChatUtil.fixColor("&8->> &6OBRAZENIA DLA POTWOROW: &a" + abstractRPGItem.getAverageMonsterDamage()));
        });

        //AVG HUMAN DMG
        handlerInv.setItem(8,avgDmgHumanItem.build(), (p, inv, index, item, click) -> {
            if(click.equals(ClickType.LEFT) || click.equals(ClickType.SHIFT_LEFT)){
                abstractRPGItem.setAverageHumanDamage(abstractRPGItem.getAverageHumanDamage() + (click.isShiftClick() ? 1.0 : 0.1));

            }
            if(click.equals(ClickType.RIGHT) || click.equals(ClickType.SHIFT_RIGHT)){
                abstractRPGItem.setAverageHumanDamage(abstractRPGItem.getAverageHumanDamage() - (click.isShiftClick() ? 1.0 : 0.1));
            }
            updateItemStack(item,ChatUtil.fixColor("&8->> &6OBRAZENIA DLA GRACZY: &a" + abstractRPGItem.getAverageHumanDamage()));
        });

        //AVG CREATURE DMG
        handlerInv.setItem(9,avgDmgCreatureItem.build(), (p, inv, index, item, click) -> {
            if(click.equals(ClickType.LEFT) || click.equals(ClickType.SHIFT_LEFT)){
                abstractRPGItem.setAverageCreatureDamage(abstractRPGItem.getAverageCreatureDamage() + (click.isShiftClick() ? 1.0 : 0.1));

            }
            if(click.equals(ClickType.RIGHT) || click.equals(ClickType.SHIFT_RIGHT)){
                abstractRPGItem.setAverageCreatureDamage(abstractRPGItem.getAverageCreatureDamage() - (click.isShiftClick() ? 1.0 : 0.1));
            }
            updateItemStack(item,ChatUtil.fixColor("&8->> &6OBRAZENIA DLA STWOROW: &a" + abstractRPGItem.getAverageCreatureDamage()));
        });

        //ADDITIONAL DMG OF CRITICAL
        handlerInv.setItem(10,criticalDmgItem.build(), (p, inv, index, item, click) -> {
            if(click.equals(ClickType.LEFT) || click.equals(ClickType.SHIFT_LEFT)){
                abstractRPGItem.setAdditionalDamageOfCriticalChance(abstractRPGItem.getAdditionalDamageOfCriticalChance() + (click.isShiftClick() ? 1.0 : 0.1));

            }
            if(click.equals(ClickType.RIGHT) || click.equals(ClickType.SHIFT_RIGHT)){
                abstractRPGItem.setAdditionalDamageOfCriticalChance(abstractRPGItem.getAdditionalDamageOfCriticalChance() - (click.isShiftClick() ? 1.0 : 0.1));
            }
            updateItemStack(item,ChatUtil.fixColor("&8->> &6DODATKOWE KRYTYCZNE OBRAZENIA: &a" + abstractRPGItem.getAdditionalDamageOfCriticalChance()));
        });

        //ADDITIONAL CHANCE FOR CRITICAL
        handlerInv.setItem(11,criticalChanceItem.build(), (p, inv, index, item, click) -> {
            if(click.equals(ClickType.LEFT) || click.equals(ClickType.SHIFT_LEFT)){
                abstractRPGItem.setAdditionalCriticalChance(abstractRPGItem.getAdditionalCriticalChance() + (click.isShiftClick() ? 1.0 : 0.1));

            }
            if(click.equals(ClickType.RIGHT) || click.equals(ClickType.SHIFT_RIGHT)){
                abstractRPGItem.setAdditionalCriticalChance(abstractRPGItem.getAdditionalCriticalChance() - (click.isShiftClick() ? 1.0 : 0.1));
            }
            updateItemStack(item,ChatUtil.fixColor("&8->> &6DODATKOWA SZANSA NA CIOS KRYTYCZNY: &a" + abstractRPGItem.getAdditionalCriticalChance()));
        });

        //STRENGTH
        handlerInv.setItem(12,strengthItem.build(), (p, inv, index, item, click) -> {
            if(click.equals(ClickType.LEFT) || click.equals(ClickType.SHIFT_LEFT)){
                abstractRPGItem.setSTRENGTH(abstractRPGItem.getSTRENGTH() + (click.isShiftClick() ? 10 : 1));

            }
            if(click.equals(ClickType.RIGHT) || click.equals(ClickType.SHIFT_RIGHT)){
                abstractRPGItem.setSTRENGTH(abstractRPGItem.getSTRENGTH() - (click.isShiftClick() ? 10 : 1));
            }
            updateItemStack(item,ChatUtil.fixColor("&8->> &6SILA: &a" + abstractRPGItem.getSTRENGTH()));
        });

        //DEXTERITY
        handlerInv.setItem(13,dexterityItem.build(), (p, inv, index, item, click) -> {
            if(click.equals(ClickType.LEFT) || click.equals(ClickType.SHIFT_LEFT)){
                abstractRPGItem.setDEXTERITY(abstractRPGItem.getDEXTERITY() + (click.isShiftClick() ? 10 : 1));

            }
            if(click.equals(ClickType.RIGHT) || click.equals(ClickType.SHIFT_RIGHT)){
                abstractRPGItem.setDEXTERITY(abstractRPGItem.getDEXTERITY() - (click.isShiftClick() ? 10 : 1));
            }
            updateItemStack(item,ChatUtil.fixColor("&8->> &6ZRECZNOSC: &a" + abstractRPGItem.getDEXTERITY()));
        });

        //INTELLIGENCE
        handlerInv.setItem(14,intelligenceItem.build(), (p, inv, index, item, click) -> {
            if(click.equals(ClickType.LEFT) || click.equals(ClickType.SHIFT_LEFT)){
                abstractRPGItem.setINTELLIGENCE(abstractRPGItem.getINTELLIGENCE() + (click.isShiftClick() ? 10 : 1));

            }
            if(click.equals(ClickType.RIGHT) || click.equals(ClickType.SHIFT_RIGHT)){
                abstractRPGItem.setINTELLIGENCE(abstractRPGItem.getINTELLIGENCE() - (click.isShiftClick() ? 10 : 1));
            }
            updateItemStack(item,ChatUtil.fixColor("&8->> &6INTELIGENCJA: &a" + abstractRPGItem.getINTELLIGENCE()));
        });

        //STAMINA
        handlerInv.setItem(15,staminaItem.build(), (p, inv, index, item, click) -> {
            if(click.equals(ClickType.LEFT) || click.equals(ClickType.SHIFT_LEFT)){
                abstractRPGItem.setSTAMINA(abstractRPGItem.getSTAMINA() + (click.isShiftClick() ? 10 : 1));

            }
            if(click.equals(ClickType.RIGHT) || click.equals(ClickType.SHIFT_RIGHT)){
                abstractRPGItem.setSTAMINA(abstractRPGItem.getSTAMINA() - (click.isShiftClick() ? 10 : 1));
            }
            updateItemStack(item,ChatUtil.fixColor("&8->> &6STAMINA: &a" + abstractRPGItem.getSTAMINA()));
        });

        //UNBREAKABLE
        handlerInv.setItem(16,unbreakableItem.build(), (p, inv, index, item, click) -> {
            if(click.equals(ClickType.LEFT) || click.equals(ClickType.SHIFT_LEFT)){
                abstractRPGItem.setUnbreakable(true);

            }
            if(click.equals(ClickType.RIGHT) || click.equals(ClickType.SHIFT_RIGHT)){
                abstractRPGItem.setUnbreakable(false);
            }
            updateItemStack(item,ChatUtil.fixColor("&8->> &6NIEZNISZCZALNE: " + (abstractRPGItem.isUnbreakable() ? ChatUtil.fixColor("&a%V%") : ChatUtil.fixColor("&c%X%"))));
        });

        final ItemBuilder name = new ItemBuilder(Material.WRITABLE_BOOK,1).setTitle(ChatUtil.fixColor("&8->> &7NAZWA: &6" + abstractRPGItem.getName()));

        handlerInv.setItem(17,name.build(),(player1, inventory1, i1, itemStack1, clickType1) -> {
            handlerInv.setFinalInventory(true);
            player1.closeInventory();
            SignFactory.Menu menu = RPG.getPlugin().getSignFactory().newMenu(Collections.singletonList("WPISZ NAZWE !"))
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
        handlerInv.setItem(18,classItem.build(),(player1, inventory1, i1, itemStack1, clickType1) -> {
            if(abstractRPGItem.getRpgClass().equals(RPGClass.WARRIOR.name())){
                abstractRPGItem.setRpgClass(RPGClass.HUNTER.name());
            }else if(abstractRPGItem.getRpgClass().equals(RPGClass.HUNTER.name())){
                abstractRPGItem.setRpgClass(RPGClass.WARRIOR.name());
            }
            updateItemStack(itemStack1,"&8->> &6KLASA: &a" + abstractRPGItem.getRpgClass());
        });
        final ItemBuilder create = new ItemBuilder(Material.RED_WOOL).setTitle(ChatUtil.fixColor("&c%V% STWORZ PRZEDMIOT %V%"));
//31
        handlerInv.setItem(31,create.build(),(player1, inventory1, i1, itemStack1, clickType1) -> {
            handlerInv.setFinalInventory(false);
            final RPGItem rpgItem = new RPGItem(abstractRPGItem.getMaterial(),
                    abstractRPGItem.getName(),
                    abstractRPGItem.getRpgClass(),
                    abstractRPGItem.getLevel(),
                    abstractRPGItem.getArmor(),
                    abstractRPGItem.getDamage(),
                    abstractRPGItem.getAttackSpeed(),
                    abstractRPGItem.getKnockBack_Resistance(),
                    abstractRPGItem.getMax_health(),
                    abstractRPGItem.getLuck(),
                    (float) abstractRPGItem.getAverageMonsterDamage(),
                    (float) abstractRPGItem.getAverageHumanDamage(),
                    (float) abstractRPGItem.getAverageCreatureDamage(),
                    (float) abstractRPGItem.getAdditionalDamageOfCriticalChance()
                    , (float) abstractRPGItem.getAdditionalCriticalChance(),
                    abstractRPGItem.getSTRENGTH(),
                    abstractRPGItem.getDEXTERITY(),
                    abstractRPGItem.getINTELLIGENCE(),
                    abstractRPGItem.getSTAMINA()
                    ,abstractRPGItem.isUnbreakable(),
                    ItemType.EQUIPMENT,null);

            RPG.getPlugin().getCustomItemManager().createItem(rpgItem,StorageType.REDIS);

            player.closeInventory();


        });

        handlerInv.openInventory(player);


        //TODO test debugged
    }
    private static ItemStack updateItemStack(ItemStack itemStack,String name){

        final ItemMeta meta = itemStack.getItemMeta();

        meta.setDisplayName(ChatUtil.fixColor(name));

        itemStack.setItemMeta(meta);

        return itemStack;
    }
}
