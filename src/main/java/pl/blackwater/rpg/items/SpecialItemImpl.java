package pl.blackwater.rpg.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.rpg.storage.enums.AdditionalModifiers;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.ItemBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SpecialItemImpl {


    private final HashMap<String, SpecialItem> specialItemHashMap;
    private final List<SpecialItem> specialItemStacks;

    private final List<ItemStack> upgradeMaterials;
    public SpecialItemImpl(){
        this.specialItemHashMap = new HashMap<>();

        final ItemBuilder acd1 = new ItemBuilder(Material.PAPER).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ACD I #F2FF6C★"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZMIENIA STATYSTYKE SREDNIE OBRAZENIA NA #2ED8A3STWORZENIA"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZAKRES ZWOJU: #2ED8A30-5#89CE63%"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #517ff5TEN ZWOJ MOZESZ UZYC ZAWSZE!"))
                .addLore("")
                .addLore("")
                .addFlag(ItemFlag.HIDE_ATTRIBUTES);
        final ItemBuilder acd2 = new ItemBuilder(Material.PAPER).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ACD II #F2FF6C★"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZMIENIA STATYSTYKE SREDNIE OBRAZENIA NA #2ED8A3STWORZENIA"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZAKRES ZWOJU: #2ED8A30-15#89CE63%"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #517ff5WYMAGANIA ABY UZYC ZWOJU:"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753PRZEDMIOT MUSI MIEC CONAJMNIEJ #517ff53%"))
                .addLore("")
                .addLore("")
                .addFlag(ItemFlag.HIDE_ATTRIBUTES);
        final ItemBuilder acd3 = new ItemBuilder(Material.PAPER).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ACD III #F2FF6C★"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZMIENIA STATYSTYKE SREDNIE OBRAZENIA NA #2ED8A3STWORZENIA"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZAKRES ZWOJU: #2ED8A35-22#89CE63%"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #517ff5WYMAGANIA ABY UZYC ZWOJU:"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753PRZEDMIOT MUSI MIEC CONAJMNIEJ #517ff510%"))
                .addLore("")
                .addLore("")
                .addFlag(ItemFlag.HIDE_ATTRIBUTES);
        final ItemBuilder acd4 = new ItemBuilder(Material.PAPER).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ACD IV #F2FF6C★"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZMIENIA STATYSTYKE SREDNIE OBRAZENIA NA #2ED8A3STWORZENIA"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZAKRES ZWOJU: #2ED8A38-35#89CE63%"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #517ff5WYMAGANIA ABY UZYC ZWOJU:"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753PRZEDMIOT MUSI MIEC CONAJMNIEJ #517ff515%"))
                .addLore("")
                .addLore("")
                .addFlag(ItemFlag.HIDE_ATTRIBUTES);
        final ItemBuilder acd5 = new ItemBuilder(Material.PAPER).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ACD V #F2FF6C★"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZMIENIA STATYSTYKE SREDNIE OBRAZENIA NA #2ED8A3STWORZENIA"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZAKRES ZWOJU: #2ED8A310-50#89CE63%"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #517ff5WYMAGANIA ABY UZYC ZWOJU:"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753PRZEDMIOT MUSI MIEC CONAJMNIEJ #517ff520%"))
                .addLore("")
                .addLore("")
                .addFlag(ItemFlag.HIDE_ATTRIBUTES);

        final ItemBuilder ahd1 = new ItemBuilder(Material.PAPER).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ AHD I #F2FF6C★"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZMIENIA STATYSTYKE SREDNIE OBRAZENIA NA #2ED8A3LUDZI"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZAKRES ZWOJU: #2ED8A30-5#89CE63%"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #517ff5TEN ZWOJ MOZESZ UZYC ZAWSZE!"))
                .addLore("")
                .addLore("")
                .addFlag(ItemFlag.HIDE_ATTRIBUTES);
        final ItemBuilder ahd2 = new ItemBuilder(Material.PAPER).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ AHD II #F2FF6C★"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZMIENIA STATYSTYKE SREDNIE OBRAZENIA NA #2ED8A3LUDZI"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZAKRES ZWOJU: #2ED8A30-15#89CE63%"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #517ff5WYMAGANIA ABY UZYC ZWOJU:"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753PRZEDMIOT MUSI MIEC CONAJMNIEJ #517ff53%"))
                .addLore("")
                .addLore("")
                .addFlag(ItemFlag.HIDE_ATTRIBUTES);
        final ItemBuilder ahd3 = new ItemBuilder(Material.PAPER).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ AHD III #F2FF6C★"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZMIENIA STATYSTYKE SREDNIE OBRAZENIA NA #2ED8A3LUDZI"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZAKRES ZWOJU: #2ED8A35-22#89CE63%"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #517ff5WYMAGANIA ABY UZYC ZWOJU:"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753PRZEDMIOT MUSI MIEC CONAJMNIEJ #517ff510%"))
                .addLore("")
                .addLore("")
                .addFlag(ItemFlag.HIDE_ATTRIBUTES);
        final ItemBuilder ahd4 = new ItemBuilder(Material.PAPER).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ AHD IV #F2FF6C★"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZMIENIA STATYSTYKE SREDNIE OBRAZENIA NA #2ED8A3LUDZI"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZAKRES ZWOJU: #2ED8A38-35#89CE63%"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #517ff5WYMAGANIA ABY UZYC ZWOJU:"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753PRZEDMIOT MUSI MIEC CONAJMNIEJ #517ff515%"))
                .addLore("")
                .addLore("")
                .addFlag(ItemFlag.HIDE_ATTRIBUTES);
        final ItemBuilder ahd5 = new ItemBuilder(Material.PAPER).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ AHD V #F2FF6C★"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZMIENIA STATYSTYKE SREDNIE OBRAZENIA NA #2ED8A3LUDZI"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZAKRES ZWOJU: #2ED8A310-50#89CE63%"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #517ff5WYMAGANIA ABY UZYC ZWOJU:"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753PRZEDMIOT MUSI MIEC CONAJMNIEJ #517ff520%"))
                .addLore("")
                .addLore("")
                .addFlag(ItemFlag.HIDE_ATTRIBUTES);

        final ItemBuilder amd1 = new ItemBuilder(Material.PAPER).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ AMD I #F2FF6C★"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZMIENIA STATYSTYKE SREDNIE OBRAZENIA NA #2ED8A3POTWORY"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZAKRES ZWOJU: #2ED8A30-5#89CE63%"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #517ff5TEN ZWOJ MOZESZ UZYC ZAWSZE!"))
                .addLore("")
                .addLore("")
                .addFlag(ItemFlag.HIDE_ATTRIBUTES);
        final ItemBuilder amd2 = new ItemBuilder(Material.PAPER).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ AMD II #F2FF6C★"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZMIENIA STATYSTYKE SREDNIE OBRAZENIA NA #2ED8A3POTWORY"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZAKRES ZWOJU: #2ED8A30-15#89CE63%"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #517ff5WYMAGANIA ABY UZYC ZWOJU:"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753PRZEDMIOT MUSI MIEC CONAJMNIEJ #517ff53%"))
                .addLore("")
                .addLore("")
                .addFlag(ItemFlag.HIDE_ATTRIBUTES);
        final ItemBuilder amd3 = new ItemBuilder(Material.PAPER).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ AMD III #F2FF6C★"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZMIENIA STATYSTYKE SREDNIE OBRAZENIA NA #2ED8A3POTWORY"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZAKRES ZWOJU: #2ED8A35-22#89CE63%"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #517ff5WYMAGANIA ABY UZYC ZWOJU:"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753PRZEDMIOT MUSI MIEC CONAJMNIEJ #517ff510%"))
                .addLore("")
                .addLore("")
                .addFlag(ItemFlag.HIDE_ATTRIBUTES);
        final ItemBuilder amd4 = new ItemBuilder(Material.PAPER).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ AMD IV #F2FF6C★"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZMIENIA STATYSTYKE SREDNIE OBRAZENIA NA #2ED8A3POTWORY"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZAKRES ZWOJU: #2ED8A38-35#89CE63%"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #517ff5WYMAGANIA ABY UZYC ZWOJU:"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753PRZEDMIOT MUSI MIEC CONAJMNIEJ #517ff515%"))
                .addLore("")
                .addLore("")
                .addFlag(ItemFlag.HIDE_ATTRIBUTES);
        final ItemBuilder amd5 = new ItemBuilder(Material.PAPER).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ AMD V #F2FF6C★"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZMIENIA STATYSTYKE SREDNIE OBRAZENIA NA #2ED8A3POTWORY"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZAKRES ZWOJU: #2ED8A310-50#89CE63%"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #517ff5WYMAGANIA ABY UZYC ZWOJU:"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753PRZEDMIOT MUSI MIEC CONAJMNIEJ #517ff520%"))
                .addLore("")
                .addLore("")
                .addFlag(ItemFlag.HIDE_ATTRIBUTES);

        final ItemBuilder ach1 = new ItemBuilder(Material.PAPER).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ACH I #F2FF6C★"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZMIENIA STATYSTYKE SREDNIA SZANSE NA #2ED8A3CIOS KRYTYCZNY"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZAKRES ZWOJU: #2ED8A30-5#89CE63%"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #517ff5TEN ZWOJ MOZESZ UZYC ZAWSZE!"))
                .addLore("")
                .addLore("")
                .addFlag(ItemFlag.HIDE_ATTRIBUTES);
        final ItemBuilder ach2 = new ItemBuilder(Material.PAPER).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ACH II #F2FF6C★"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZMIENIA STATYSTYKE SREDNIA SZANSE NA #2ED8A3CIOS KRYTYCZNY"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZAKRES ZWOJU: #2ED8A30-15#89CE63%"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #517ff5WYMAGANIA ABY UZYC ZWOJU:"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753PRZEDMIOT MUSI MIEC CONAJMNIEJ #517ff53%"))
                .addLore("")
                .addLore("")
                .addFlag(ItemFlag.HIDE_ATTRIBUTES);
        final ItemBuilder ach3 = new ItemBuilder(Material.PAPER).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ACH III #F2FF6C★"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZMIENIA STATYSTYKE SREDNIA SZANSE NA #2ED8A3CIOS KRYTYCZNY"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZAKRES ZWOJU: #2ED8A35-22#89CE63%"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #517ff5WYMAGANIA ABY UZYC ZWOJU:"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753PRZEDMIOT MUSI MIEC CONAJMNIEJ #517ff510%"))
                .addLore("")
                .addLore("")
                .addFlag(ItemFlag.HIDE_ATTRIBUTES);
        final ItemBuilder ach4 = new ItemBuilder(Material.PAPER).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ACH IV #F2FF6C★"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZMIENIA STATYSTYKE SREDNIA SZANSE NA #2ED8A3CIOS KRYTYCZNY"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZAKRES ZWOJU: #2ED8A38-35#89CE63%"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #517ff5WYMAGANIA ABY UZYC ZWOJU:"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753PRZEDMIOT MUSI MIEC CONAJMNIEJ #517ff515%"))
                .addLore("")
                .addLore("")
                .addFlag(ItemFlag.HIDE_ATTRIBUTES);
        final ItemBuilder ach5 = new ItemBuilder(Material.PAPER).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ACH V #F2FF6C★"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZMIENIA STATYSTYKE SREDNIA SZANSE NA #2ED8A3CIOS KRYTYCZNY"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZAKRES ZWOJU: #2ED8A310-50#89CE63%"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #517ff5WYMAGANIA ABY UZYC ZWOJU:"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753PRZEDMIOT MUSI MIEC CONAJMNIEJ #517ff520%"))
                .addLore("")
                .addLore("")
                .addFlag(ItemFlag.HIDE_ATTRIBUTES);

        final ItemBuilder adoch1 = new ItemBuilder(Material.PAPER).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ADOCH I #F2FF6C★"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZMIENIA STATYSTYKE SREDNIE OBRAZENIA OD #2ED8A3CIOSU KRYTYCZNEGO"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZAKRES ZWOJU: #2ED8A30-5#89CE63%"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #517ff5TEN ZWOJ MOZESZ UZYC ZAWSZE!"))
                .addLore("")
                .addLore("")
                .addFlag(ItemFlag.HIDE_ATTRIBUTES);
        final ItemBuilder adoch2 = new ItemBuilder(Material.PAPER).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ADOCH II #F2FF6C★"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZMIENIA STATYSTYKE SREDNIE OBRAZENIA OD #2ED8A3CIOSU KRYTYCZNEGO"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZAKRES ZWOJU: #2ED8A30-15#89CE63%"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #517ff5WYMAGANIA ABY UZYC ZWOJU:"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753PRZEDMIOT MUSI MIEC CONAJMNIEJ #517ff53%"))
                .addLore("")
                .addLore("")
                .addFlag(ItemFlag.HIDE_ATTRIBUTES);
        final ItemBuilder adoch3 = new ItemBuilder(Material.PAPER).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ADOCH III #F2FF6C★"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZMIENIA STATYSTYKE SREDNIE OBRAZENIA OD #2ED8A3CIOSU KRYTYCZNEGO"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZAKRES ZWOJU: #2ED8A35-22#89CE63%"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #517ff5WYMAGANIA ABY UZYC ZWOJU:"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753PRZEDMIOT MUSI MIEC CONAJMNIEJ #517ff510%"))
                .addLore("")
                .addLore("")
                .addFlag(ItemFlag.HIDE_ATTRIBUTES);
        final ItemBuilder adoch4 = new ItemBuilder(Material.PAPER).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ADOCH IV #F2FF6C★"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZMIENIA STATYSTYKE SREDNIE OBRAZENIA OD #2ED8A3CIOSU KRYTYCZNEGO"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZAKRES ZWOJU: #2ED8A38-35#89CE63%"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #517ff5WYMAGANIA ABY UZYC ZWOJU:"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753PRZEDMIOT MUSI MIEC CONAJMNIEJ #517ff515%"))
                .addLore("")
                .addLore("")
                .addFlag(ItemFlag.HIDE_ATTRIBUTES);
        final ItemBuilder adoch5 = new ItemBuilder(Material.PAPER).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ADOCH V #F2FF6C★"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZMIENIA STATYSTYKE SREDNIE OBRAZENIA OD #2ED8A3CIOSU KRYTYCZNEGO"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753ZAKRES ZWOJU: #2ED8A310-50#89CE63%"))
                .addLore("")
                .addLore(ChatUtil.hexColor("#89CE63♯ #517ff5WYMAGANIA ABY UZYC ZWOJU:"))
                .addLore(ChatUtil.hexColor("#89CE63♯ #0cf753PRZEDMIOT MUSI MIEC CONAJMNIEJ #517ff520%"))
                .addLore("")
                .addLore("")
                .addFlag(ItemFlag.HIDE_ATTRIBUTES);
        specialItemStacks = new ArrayList<>();

        addSpecialItem(acd1,new SpecialItem(acd1.build(),0.0f,AdditionalModifiers.ACD,0,5));
        addSpecialItem(acd2, new SpecialItem(acd2.build(),3.0f,AdditionalModifiers.ACD,0,15));
        addSpecialItem(acd3,new SpecialItem(acd3.build(),10.f,AdditionalModifiers.ACD,5,22));
        addSpecialItem(acd4,new SpecialItem(acd4.build(),15.0f,AdditionalModifiers.ACD,8,35));
        addSpecialItem(acd5,new SpecialItem(acd5.build(),20.0f,AdditionalModifiers.ACD,10,50));

        addSpecialItem(amd1,new SpecialItem(amd1.build(),0.0f,AdditionalModifiers.AMD,0,5));
        addSpecialItem(amd2, new SpecialItem(amd2.build(),3.0f,AdditionalModifiers.AMD,0,15));
        addSpecialItem(amd3,new SpecialItem(amd3.build(),10.f,AdditionalModifiers.AMD,5,22));
        addSpecialItem(amd4,new SpecialItem(amd4.build(),15.0f,AdditionalModifiers.AMD,8,35));
        addSpecialItem(amd5,new SpecialItem(amd5.build(),20.0f,AdditionalModifiers.AMD,10,50));

        addSpecialItem(ahd1,new SpecialItem(ahd1.build(),0.0f,AdditionalModifiers.AHD,0,5));
        addSpecialItem(ahd2, new SpecialItem(ahd2.build(),3.0f,AdditionalModifiers.AHD,0,15));
        addSpecialItem(ahd3,new SpecialItem(ahd3.build(),10.f,AdditionalModifiers.AHD,5,22));
        addSpecialItem(ahd4,new SpecialItem(ahd4.build(),15.0f,AdditionalModifiers.AHD,8,35));
        addSpecialItem(ahd5,new SpecialItem(ahd5.build(),20.0f,AdditionalModifiers.AHD,10,50));

        addSpecialItem(ach1,new SpecialItem(ach1.build(),0.0f,AdditionalModifiers.ACH,0,5));
        addSpecialItem(ach2, new SpecialItem(ach2.build(),3.0f,AdditionalModifiers.ACH,0,15));
        addSpecialItem(ach3,new SpecialItem(ach3.build(),10.f,AdditionalModifiers.ACH,5,22));
        addSpecialItem(ach4,new SpecialItem(ach4.build(),15.0f,AdditionalModifiers.ACH,8,35));
        addSpecialItem(ach5,new SpecialItem(ach5.build(),20.0f,AdditionalModifiers.ACH,10,50));

        addSpecialItem(adoch1,new SpecialItem(adoch1.build(),0.0f,AdditionalModifiers.ADOCH,0,5));
        addSpecialItem(adoch2, new SpecialItem(adoch2.build(),3.0f,AdditionalModifiers.ADOCH,0,15));
        addSpecialItem(adoch3,new SpecialItem(adoch3.build(),10.f,AdditionalModifiers.ADOCH,5,22));
        addSpecialItem(adoch4,new SpecialItem(adoch4.build(),15.0f,AdditionalModifiers.ADOCH,8,35));
        addSpecialItem(adoch5,new SpecialItem(adoch5.build(),20.0f,AdditionalModifiers.ADOCH,10,50));

        this.upgradeMaterials = new ArrayList<>();

        upgradeMaterials.add(new ItemBuilder(Material.BOOK).setTitle(ChatUtil.hexColor("#F2FF6C★ #F3F9B3ZWOJ ULEPSZENIA +1 #F2FF6C★")).build());

    }

    public HashMap<String, SpecialItem> getSpecialItemHashMap() {
        return specialItemHashMap;
    }

    public List<SpecialItem> getSpecialItemStacks() {
        return specialItemStacks;
    }

    private void addSpecialItem(ItemBuilder item,SpecialItem specialItem){
        specialItemHashMap.put(item.getTitle(),specialItem);
        specialItemStacks.add(specialItem);
    }
    public ItemStack getSpecialItemWithAmount(String name, int amount){
        for(SpecialItem item : getSpecialItemStacks()){
            if(Objects.requireNonNull(item.getItemStack().getItemMeta()).getDisplayName().contains(name)){
                final ItemStack clone = item.getItemStack().clone();
                clone.setAmount(amount);
                return clone;
            }
        }
        return null;
    }

    public List<ItemStack> getUpgradeMaterials() {
        return upgradeMaterials;
    }
}
