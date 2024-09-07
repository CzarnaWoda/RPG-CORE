package pl.blackwater.rpg.util;


import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.rpg.items.ItemType;
import pl.blackwater.rpg.storage.enums.AdditionalModifiers;
import pl.blackwater.rpg.storage.enums.Stats;

import java.util.HashMap;
import java.util.Objects;

public class RPGUtil {

    public final static HashMap<Integer, Double> LEVEL_SCALE = new HashMap<>();

    public final static HashMap<Integer,Integer> STORAGE_PRICE = new HashMap<>();

    public static int MAX_LEVEL;

    static {
        LEVEL_SCALE.put(1,6500.0);
        LEVEL_SCALE.put(2,10000.0);
        LEVEL_SCALE.put(3,15000.0);
        LEVEL_SCALE.put(4,18000.0);
        LEVEL_SCALE.put(6,20000.0);
        LEVEL_SCALE.put(7,25000.0);
        LEVEL_SCALE.put(8,30000.0);
        LEVEL_SCALE.put(9,40000.0);
        LEVEL_SCALE.put(10,50000.0);
        LEVEL_SCALE.put(11,60000.0);
        LEVEL_SCALE.put(12,70000.0);
        LEVEL_SCALE.put(13,80000.0);
        LEVEL_SCALE.put(14,90000.0);
        LEVEL_SCALE.put(15,120000.0);
        LEVEL_SCALE.put(16,140000.0);
        LEVEL_SCALE.put(17,160000.0);
        LEVEL_SCALE.put(18,200000.0);
        LEVEL_SCALE.put(19,240000.0);
        LEVEL_SCALE.put(20,300000.0);
        LEVEL_SCALE.put(21,340000.0);
        LEVEL_SCALE.put(22,380000.0);
        LEVEL_SCALE.put(23,440000.0);
        LEVEL_SCALE.put(24,500000.0);
        LEVEL_SCALE.put(25,600000.0);
        LEVEL_SCALE.put(26,700000.0);
        LEVEL_SCALE.put(27,800000.0);
        LEVEL_SCALE.put(28,900000.0);
        LEVEL_SCALE.put(29,1000000.0);
        LEVEL_SCALE.put(30,1300000.0);
        LEVEL_SCALE.put(31,1600000.0);

        STORAGE_PRICE.put(1,20000);
        STORAGE_PRICE.put(2,100000);
        STORAGE_PRICE.put(3,250000);
        STORAGE_PRICE.put(4,400000);
        STORAGE_PRICE.put(5,700000);
        STORAGE_PRICE.put(6,850000);
        STORAGE_PRICE.put(7,950000);
        STORAGE_PRICE.put(8,1000000);
        STORAGE_PRICE.put(9,1500000);




        int level =0;
        for(int i : LEVEL_SCALE.keySet()){
            if(level < i){
                level = i;
            }
        }
        MAX_LEVEL =  level;

    }


    public static int getMaxLevel(){
        return MAX_LEVEL;
    }
    public static boolean isRPGItem(ItemStack itemStack){
        if(itemStack == null){
            return false;
        }
        if(itemStack.getType().equals(Material.AIR)){
            return false;
        }
        if(itemStack.getItemMeta() == null){
            return false;
        }
        if(itemStack.getItemMeta().getLore() == null){
            return false;
        }
        final NBTItem nbtItem = new NBTItem(itemStack);
        return Objects.equals(nbtItem.getString("ItemType"), ItemType.EQUIPMENT.name()) || Objects.equals(nbtItem.getString("ItemType"), ItemType.JEWELERY.name());
    }
    public static boolean isRPGItemByItemType(ItemStack itemStack, ItemType itemType){
        if(itemStack == null){
            return false;
        }
        if(itemStack.getType().equals(Material.AIR)){
            return false;
        }
        if(itemStack.getItemMeta() == null){
            return false;
        }
        if(itemStack.getItemMeta().getLore() == null){
            return false;
        }
        final NBTItem nbtItem = new NBTItem(itemStack);
        return Objects.equals(nbtItem.getString("ItemType"), itemType.name());
    }

    public static ItemType getRPGItemType(ItemStack itemStack){
        if(itemStack == null){
            return null;
        }
        if(itemStack.getType().equals(Material.AIR)){
            return null;
        }
        if(itemStack.getItemMeta() == null){
            return null;
        }
        if(itemStack.getItemMeta().getLore() == null){
            return null;
        }
        NBTContainer nbtContainer = NBTItem.convertItemtoNBT(itemStack);
        return ItemType.fromString(nbtContainer.getString("ItemType"));
    }

    public static void addUserStatsFromItem(RPGUser user, NBTItem dressedItem){
        user.setSTRENGTH(user.getSTRENGTH() + dressedItem.getInteger(Stats.STRENGTH.getType()));
        user.setDEXTERITY(user.getDEXTERITY() + dressedItem.getInteger(Stats.DEXTERITY.getType()));
        user.setINTELLIGENCE(user.getINTELLIGENCE() + dressedItem.getInteger(Stats.INTELLIGENCE.getType()));
        user.setSTAMINA(user.getSTAMINA() + dressedItem.getInteger(Stats.STAMINA.getType()));

        user.setAverageMonsterDamage(user.getAverageMonsterDamage() + dressedItem.getFloat(AdditionalModifiers.AMD.getType()));
        user.setAverageHumanDamage(user.getAverageHumanDamage() + dressedItem.getFloat(AdditionalModifiers.AHD.getType()));
        user.setAverageCreatureDamage(user.getAverageCreatureDamage() + dressedItem.getFloat(AdditionalModifiers.ACD.getType()));
        user.setAdditionalDamageOfCriticalChance(user.getAdditionalDamageOfCriticalChance() + dressedItem.getFloat(AdditionalModifiers.ADOCH.getType()));
        user.setAdditionalCriticalChance(user.getAdditionalCriticalChance() + dressedItem.getFloat(AdditionalModifiers.ACH.getType()));
    }

    public static void removeStatsFromUser(RPGUser user, NBTItem takenItem){
        user.setSTRENGTH(user.getSTRENGTH() - takenItem.getInteger(Stats.STRENGTH.getType()));
        user.setDEXTERITY(user.getDEXTERITY() - takenItem.getInteger(Stats.DEXTERITY.getType()));
        user.setINTELLIGENCE(user.getINTELLIGENCE() - takenItem.getInteger(Stats.INTELLIGENCE.getType()));
        user.setSTAMINA(user.getSTAMINA() - takenItem.getInteger(Stats.STAMINA.getType()));

        user.setAverageMonsterDamage(user.getAverageMonsterDamage() - takenItem.getFloat(AdditionalModifiers.AMD.getType()));
        user.setAverageHumanDamage(user.getAverageHumanDamage() - takenItem.getFloat(AdditionalModifiers.AHD.getType()));
        user.setAverageCreatureDamage(user.getAverageCreatureDamage() - takenItem.getFloat(AdditionalModifiers.ACD.getType()));
        user.setAdditionalDamageOfCriticalChance(user.getAdditionalDamageOfCriticalChance() - takenItem.getFloat(AdditionalModifiers.ADOCH.getType()));
        user.setAdditionalCriticalChance(user.getAdditionalCriticalChance() - takenItem.getFloat(AdditionalModifiers.ACH.getType()));

    }

}
