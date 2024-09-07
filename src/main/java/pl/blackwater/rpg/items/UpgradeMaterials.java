package pl.blackwater.rpg.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.ItemBuilder;

public enum UpgradeMaterials {

    ZWOJ_PLUS_ONE(new ItemBuilder(Material.BOOK).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ULEPSZENIA +1 #F2FF6C★")).build(),0),
    ZWOJ_PLUS_TWO(new ItemBuilder(Material.BOOK).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ULEPSZENIA +2 #F2FF6C★")).build(),1),
    ZWOJ_PLUS_THREE(new ItemBuilder(Material.BOOK).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ULEPSZENIA +3 #F2FF6C★")).build(),2),
    ZWOJ_PLUS_FOURTH(new ItemBuilder(Material.BOOK).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ULEPSZENIA +4 #F2FF6C★")).build(),3),
    ZWOJ_PLUS_FIVE(new ItemBuilder(Material.BOOK).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ULEPSZENIA +5 #F2FF6C★")).build(),4),
    ZWOJ_PLUS_SIX(new ItemBuilder(Material.BOOK).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ULEPSZENIA +6 #F2FF6C★")).build(),5),
    ZWOJ_PLUS_SEVEN(new ItemBuilder(Material.BOOK).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ULEPSZENIA +7 #F2FF6C★")).build(),6),
    ZWOJ_PLUS_EIGHT(new ItemBuilder(Material.BOOK).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ULEPSZENIA +8 #F2FF6C★")).build(),7),
    ZWOJ_PLUS_NINE(new ItemBuilder(Material.BOOK).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ULEPSZENIA +9 #F2FF6C★")).build(),8),
    ZWOJ_PLUS_TEN(new ItemBuilder(Material.BOOK).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ULEPSZENIA +10 #F2FF6C★")).build(),9),
    ZWOJ_PLUS_ELEVEN(new ItemBuilder(Material.BOOK).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ULEPSZENIA +11 #F2FF6C★")).build(),10),
    ZWOJ_PLUS_TWELVE(new ItemBuilder(Material.BOOK).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ULEPSZENIA +12 #F2FF6C★")).build(),11),
    ZWOJ_PLUS_THIRTY(new ItemBuilder(Material.BOOK).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ULEPSZENIA +13 #F2FF6C★")).build(),12),
    ZWOJ_PLUS_FOURTEEN(new ItemBuilder(Material.BOOK).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ULEPSZENIA +14 #F2FF6C★")).build(),13),
    ZWOJ_PLUS_FIFTEEN(new ItemBuilder(Material.BOOK).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ULEPSZENIA +15 #F2FF6C★")).build(),14),
    ZWOJ_PLUS_SIXTEEN(new ItemBuilder(Material.BOOK).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ULEPSZENIA +16 #F2FF6C★")).build(),15),
    ZWOJ_PLUS_SEVENTEEN(new ItemBuilder(Material.BOOK).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ULEPSZENIA +17 #F2FF6C★")).build(),16),
    ZWOJ_PLUS_EIGHTEEN(new ItemBuilder(Material.BOOK).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ULEPSZENIA +18 #F2FF6C★")).build(),17),
    ZWOJ_PLUS_NINETEEN(new ItemBuilder(Material.BOOK).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ULEPSZENIA +19 #F2FF6C★")).build(),18),
    ZWOJ_PLUS_TWENTY(new ItemBuilder(Material.BOOK).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ULEPSZENIA +20 #F2FF6C★")).build(),19);

    private final ItemStack item;
    private final int upgradeLevel;

    UpgradeMaterials(ItemStack item, int upgradeLevel) {
        this.item=item;
        this.upgradeLevel = upgradeLevel;
    }

    public ItemStack getItem() {
        return item;
    }

    public int getUpgradeLevel() {
        return upgradeLevel;
    }

    public static UpgradeMaterials getByUpgradeLevel(int upgradeLevel){
        for (UpgradeMaterials value : UpgradeMaterials.values()) {
            if(value.getUpgradeLevel() == upgradeLevel){
                return value;
            }
        }
        throw new IllegalArgumentException("Not expect that high/low upgrade level");
    }
}
