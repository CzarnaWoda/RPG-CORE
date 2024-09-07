package pl.blackwater.rpg.mysterybox;


import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.Entry;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.GsonUtil;
import pl.blackwater.spigotplugin.util.ItemBuilder;

public class MysteryBox implements Entry {


    private String boxName;
    private MysteryBoxItem[] dropList;

    private transient ItemStack boxItem;
    private transient MysteryBoxAction action;

    private ItemBuilder boxItemBuilder;

    public MysteryBox(String boxName){
        this.boxName = boxName;
        this.dropList = new MysteryBoxItem[0];

        initBox();
    }

    public void initBox(){
        this.boxItemBuilder = new ItemBuilder(Material.CHEST).setTitle(ChatUtil.hexColor(boxName)).addLore(ChatUtil.fixColor("&8->> &7Aby otworzyc skrzynie kliknij prawy przycisk myszki&8!"))
                .addEnchantment(Enchantment.DURABILITY,1)
                .addFlag(ItemFlag.HIDE_ENCHANTS);
        this.boxItem = boxItemBuilder.build();

        MysteryBoxActionListener.getBoxes().put(boxItem, this);
    }

    public String getBoxName() {
        return boxName;
    }

    public MysteryBox setName(String boxName){
        this.boxName = boxName;
        return this;
    }
    public MysteryBox setAction(MysteryBoxAction action){
        if(action != null) {
            this.action = action;
        }
        return this;
    }

    public MysteryBoxItem[] getDropList() {
        return dropList;
    }

    public void addDrop(MysteryBoxItem item){
        final int len = this.dropList.length+1;
        final MysteryBoxItem[] fixedArray = new MysteryBoxItem[len];

        System.arraycopy(this.dropList, 0, fixedArray, 0, this.dropList.length);
        fixedArray[len-1] = item;
        this.dropList = fixedArray;
    }
    public void addDrop(ItemStack item){
        final int len = this.dropList.length+1;
        final MysteryBoxItem[] fixedArray = new MysteryBoxItem[len];

        System.arraycopy(this.dropList, 0, fixedArray, 0, this.dropList.length);
        fixedArray[len-1] = new MysteryBoxItem(item,2.5);
        this.dropList = fixedArray;
    }
    public void addDrops(MysteryBoxItem... items){
        for(MysteryBoxItem i : items){
            addDrop(i);
        }
    }

    public void removeDrop(ItemStack item){
        final int index = getDropIndex(item);
        if(index != -1){
            this.dropList[index] = null;
        }
        reorganizeIndexes();
    }

    public int getDropIndex(ItemStack item) {
        for (int i = 0; i < this.dropList.length; i++) {
            if (this.dropList[i].getItemStack().isSimilar(item)) {
                return i;
            }
        }
        return -1;
    }

    public ItemStack getBoxItem() {
        return boxItem;
    }

    public MysteryBoxAction getAction() {
        return action;
    }

    private void reorganizeIndexes(){
        MysteryBoxItem[] fixedArray = new MysteryBoxItem[this.dropList.length-1];
        int index = 0;
        for (MysteryBoxItem mysteryBoxItem : this.dropList) {
            if (mysteryBoxItem != null) {
                fixedArray[index] = mysteryBoxItem;
                index++;
            }
        }
        this.dropList = fixedArray;
    }

    @Override
    public void insert() {
        RPG.getPlugin().getRedisStorage().RPG_MYSTERYBOX_DROP.putAsync(getBoxName(), GsonUtil.toJson(this));
    }

    @Override
    public void update() {
        RPG.getPlugin().getRedisStorage().RPG_MYSTERYBOX_DROP.putAsync(getBoxName(), GsonUtil.toJson(this));

    }

    public ItemBuilder getBoxItemBuilder() {
        return boxItemBuilder;
    }

    @Override
    public void remove() {
        RPG.getPlugin().getRedisStorage().RPG_MYSTERYBOX_DROP.removeAsync(getBoxName());
    }
}
