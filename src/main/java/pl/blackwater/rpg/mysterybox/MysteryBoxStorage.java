package pl.blackwater.rpg.mysterybox;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.rpg.data.enums.RPGClass;
import pl.blackwater.rpg.items.AnvilMaterials;
import pl.blackwater.rpg.items.ItemType;
import pl.blackwater.rpg.storage.RPGItem;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.InventoryUtil;
import pl.blackwater.spigotplugin.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

public class MysteryBoxStorage {


    private final List<MysteryBox> mysteryBoxList;
    public MysteryBoxStorage(){
        mysteryBoxList = new ArrayList<>();


        final MysteryBox mysteryBox = new MysteryBox("#eb7434★ #ebdc34SKRZYNIA LOWCY #eb7434★").setAction((player, boxDropItems, e) -> {
            if(RandomUtil.getChance(20.0)){
                ChatUtil.sendMessage(player,"&8->> &7Otworzyles skrzynke i okazalo sie ze byla &epusta");
                return;
            }
            ItemStack randomItem = null;
            if(boxDropItems.length == 1){
                randomItem = boxDropItems[0].getItemStack();
            }else {
                MysteryBoxItem randomMysteryBoxItem;
                do {
                    randomMysteryBoxItem = boxDropItems[RandomUtil.getRandInt(0, boxDropItems.length - 1)];
                    if (RandomUtil.getChance(randomMysteryBoxItem.getChance())) {
                        randomItem = randomMysteryBoxItem.getItemStack();
                    }
                }while (randomItem == null);
            }
            InventoryUtil.giveItems(player,randomItem);

            final RPGUser user = RPG.getPlugin().getRpgUserManager().getUser(player.getUniqueId());

            if(user.getChatUser().isMysteryBoxMessageChat()){
                if(randomItem.getItemMeta() != null) {
                    ChatUtil.sendMessage(player, "&8->> &7Otworzyles skrzynke i wylosowales &8(&6" + randomItem.getItemMeta().getDisplayName() + "&8) &7x&2" + randomItem.getAmount());
                }
            }
        });
        mysteryBox.addDrop(new MysteryBoxItem(AnvilMaterials.SKORA_LOWCY.getItem(),10.0));
        mysteryBox.addDrop(new MysteryBoxItem(AnvilMaterials.ODLAMEK_LUKU_LOWCY.getItem(),15.0));
        final RPGItem helmet = new RPGItem(Material.LEATHER_HELMET,"&3HELM MROCZNEGO LOWCY", RPGClass.HUNTER.name(),5,10,0,5,0,6,0,2.5f,0,5.0f,1.0f,1.0f,5,15,0,8,true, ItemType.EQUIPMENT,null);
        final RPGItem chestplate = new RPGItem(Material.LEATHER_CHESTPLATE,"&3ZBROJA MROCZNEGO LOWCY",RPGClass.HUNTER.name(),7,4,4,0,0,6,0,2.0f,0,0f,0f,0f,4,15,0,15,true, ItemType.EQUIPMENT,null);
        final RPGItem leggings = new RPGItem(Material.LEATHER_LEGGINGS,"&3SPODNIE MROCZNEGO LOWCY",RPGClass.HUNTER.name(),4,4,0,0,0,1,0,0,0,0f,0f,0f,0,10,0,20,true, ItemType.EQUIPMENT,null);
        final RPGItem boots = new RPGItem(Material.LEATHER_BOOTS,"&3BUTY MROCZNEGO LOWCY",RPGClass.HUNTER.name(),6,2,0,0,0,1,0,0,0,0f,0f,0f,0,10,0,16,true, ItemType.EQUIPMENT,null);
        final RPGItem bow = new RPGItem(Material.BOW,"&aLUK MROCZNEGO LOWCY",RPGClass.HUNTER.name(),7,0,18,6,0,4,0,5.0f,0,2.0f,4.5f,5.0f,0,35,0,15,true, ItemType.EQUIPMENT,null);

        final RPGItem helmet1 = new RPGItem(Material.LEATHER_HELMET,"&3HELM MROCZNEGO CIENIA",RPGClass.WARRIOR.name(),8,12,0,0,0,6,0,4.5f,0,6.5f,1.0f,1.0f,15,0,0,18,true, ItemType.EQUIPMENT,null);
        final RPGItem chestplate1 = new RPGItem(Material.LEATHER_CHESTPLATE,"&3ZBROJA MROCZNEGO CIENIA",RPGClass.WARRIOR.name(),6,12,4,0,0,12,0,2.0f,0,0f,0f,0f,20,0,0,30,true, ItemType.EQUIPMENT,null);
        final RPGItem leggings1 = new RPGItem(Material.LEATHER_LEGGINGS,"&3SPODNIE MROCZNEGO CIENIA",RPGClass.WARRIOR.name(),4,8,0,0,0,5,0,0,0,2.0f,0f,0f,15,0,0,25,true, ItemType.EQUIPMENT,null);
        final RPGItem boots1 = new RPGItem(Material.LEATHER_BOOTS,"&3BUTY MROCZNEGO CIENIA",RPGClass.WARRIOR.name(),5,8,0,0,0,4,0,0,0,2.0f,0f,0f,10,0,0,5,true, ItemType.EQUIPMENT,null);
        final RPGItem sword = new RPGItem(Material.WOODEN_SWORD,"&aMIECZ MROCZNEGO CIENIA",RPGClass.WARRIOR.name(),9,0,20,2,0,8,0,2.0f,0,6.0f,2.5f,2.0f,15,5,0,28,true, ItemType.EQUIPMENT,null);

        mysteryBox.addDrops(new MysteryBoxItem(helmet.build(),2.0),new MysteryBoxItem(chestplate1.build(),2.0),new MysteryBoxItem(chestplate.build(),2.0),new MysteryBoxItem(leggings1.build(),2.0),new MysteryBoxItem(leggings.build(),2.0),new MysteryBoxItem(helmet1.build(),2.0),new MysteryBoxItem(helmet.build(),2.0),new MysteryBoxItem(boots1.build(),2.0),new MysteryBoxItem(boots.build(),2.0),new MysteryBoxItem(sword.build(),2.0),new MysteryBoxItem(bow.build(),2.0));
        getMysteryBoxList().add(mysteryBox);




        //GHOULS

        final MysteryBox ghoulCase = new MysteryBox("#eb7434★ #9450faSKRZYNIA UMARLYCH #eb7434★").setAction((player, boxDropItems, e) -> {
            if(RandomUtil.getChance(20.0)){
                ChatUtil.sendMessage(player,"&8->> &7Otworzyles skrzynke i okazalo sie ze byla &epusta");
                return;
            }
            ItemStack randomItem = null;
            if(boxDropItems.length == 1){
                randomItem = boxDropItems[0].getItemStack();
            }else {
                MysteryBoxItem randomMysteryBoxItem;
                do {
                    randomMysteryBoxItem = boxDropItems[RandomUtil.getRandInt(0, boxDropItems.length - 1)];
                    if (RandomUtil.getChance(randomMysteryBoxItem.getChance())) {
                        randomItem = randomMysteryBoxItem.getItemStack();
                    }
                }while (randomItem == null);
            }
            InventoryUtil.giveItems(player,randomItem);

            final RPGUser user = RPG.getPlugin().getRpgUserManager().getUser(player.getUniqueId());

            if(user.getChatUser().isMysteryBoxMessageChat()){
                if(randomItem.getItemMeta() != null) {
                    ChatUtil.sendMessage(player, "&8->> &7Otworzyles skrzynke i wylosowales &8(&6" + randomItem.getItemMeta().getDisplayName() + "&8) &7x&2" + randomItem.getAmount());
                }
            }
        });
        ghoulCase.addDrop(new MysteryBoxItem(AnvilMaterials.GHOUL_BONE.getItem(),10.0));
        ghoulCase.addDrop(new MysteryBoxItem(AnvilMaterials.GHOUL_HEAD.getItem(),15.0));
        final RPGItem gh = new RPGItem(Material.IRON_HELMET,ChatUtil.hexColor("#a8fca2⚔ #ddfca2HELM LOWCOW UMARLYCH #a8fca2⚔"), RPGClass.HUNTER.name(),12,12,0,6,0,10,0,9.5f,0,10.0f,5.0f,5.0f,10,25,0,18,true, ItemType.EQUIPMENT,null);
        final RPGItem gc = new RPGItem(Material.IRON_CHESTPLATE,ChatUtil.hexColor("#a8fca2⚔ #ddfca2ZBROJA LOWCOW UMARLYCH #a8fca2⚔"),RPGClass.HUNTER.name(),15,6,4,0,0,8,0,8.0f,0,0f,5.0f,5.0f,15,25,0,25,true, ItemType.EQUIPMENT,null);
        final RPGItem gl = new RPGItem(Material.IRON_LEGGINGS,ChatUtil.hexColor("#a8fca2⚔ #ddfca2SPODNIE LOWCOW UMARLYCH #a8fca2⚔"),RPGClass.HUNTER.name(),15,6,0,0,0,10,0,6.5f,0,0f,10.0f,0f,0,20,0,30,true, ItemType.EQUIPMENT,null);
        final RPGItem gb = new RPGItem(Material.IRON_BOOTS,ChatUtil.hexColor("#a8fca2⚔ #ddfca2BUTY LOWCOW UMARLYCH #a8fca2⚔"),RPGClass.HUNTER.name(),16,6,0,0,0,1,0,0,0,0f,10.0f,0f,0,20,0,25,true, ItemType.EQUIPMENT,null);
        final RPGItem gbow = new RPGItem(Material.BOW,ChatUtil.hexColor("#a8fca2⚔ #ddfca2LUK LOWCOW UMARLYCH #a8fca2⚔"),RPGClass.HUNTER.name(),13,0,40,8,0,8,0,10.0f,0,5.0f,5.0f,5.0f,0,50,0,25,true, ItemType.EQUIPMENT,null);

        final RPGItem gh1 = new RPGItem(Material.IRON_HELMET,ChatUtil.hexColor("#a8fca2⚔ #ed5887HELM UMRALYCH #a8fca2⚔"),RPGClass.WARRIOR.name(),12,15,0,0,0,8,0,5.0f,0,8.5f,4.0f,4.0f,25,5,0,25,true, ItemType.EQUIPMENT,null);
        final RPGItem gc1 = new RPGItem(Material.IRON_CHESTPLATE,ChatUtil.hexColor("#a8fca2⚔ #ed5887ZBROJA UMRALYCH #a8fca2⚔"),RPGClass.WARRIOR.name(),15,15,5,0,0,14,0,5.0f,0,0f,0f,0f,30,5,0,45,true, ItemType.EQUIPMENT,null);
        final RPGItem gl1 = new RPGItem(Material.IRON_LEGGINGS,ChatUtil.hexColor("#a8fca2⚔ #ed5887SPODNIE UMRALYCH #a8fca2⚔"),RPGClass.WARRIOR.name(),16,10,0,0,0,6,0,0,0,4.0f,0f,0f,25,5,0,40,true, ItemType.EQUIPMENT,null);
        final RPGItem gb1 = new RPGItem(Material.IRON_BOOTS,ChatUtil.hexColor("#a8fca2⚔ #ed5887BUTY UMRALYCH #a8fca2⚔"),RPGClass.WARRIOR.name(),12,8,0,0,0,6,0,0,0,4.0f,0f,0f,20,5,0,25,true, ItemType.EQUIPMENT,null);
        final RPGItem gs1 = new RPGItem(Material.IRON_SWORD,ChatUtil.hexColor("#a8fca2⚔ #ed5887MIECZ UMRALYCH #a8fca2⚔"),RPGClass.WARRIOR.name(),13,0,30,2,0,12,0,4.0f,0,8.0f,4.5f,6.0f,25,20,0,30,true, ItemType.EQUIPMENT,null);

        ghoulCase.addDrops(new MysteryBoxItem(gh.build(),2.0),new MysteryBoxItem(gh1.build(),2.0),new MysteryBoxItem(gc.build(),2.0),new MysteryBoxItem(gc1.build(),2.0),new MysteryBoxItem(gl.build(),2.0),new MysteryBoxItem(gl1.build(),2.0),new MysteryBoxItem(gb.build(),2.0),new MysteryBoxItem(gb1.build(),2.0),new MysteryBoxItem(gbow.build(),2.0),new MysteryBoxItem(gs1.build(),2.0));
        getMysteryBoxList().add(ghoulCase);
    }

    public List<MysteryBox> getMysteryBoxList() {
        return mysteryBoxList;
    }
}
