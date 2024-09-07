package pl.blackwater.rpg.storage;

import de.tr7zw.nbtapi.*;
import de.tr7zw.nbtapi.NBTList;
import eu.decentsoftware.holograms.api.utils.scheduler.S;
import io.lumine.mythic.bukkit.utils.shadows.nbt.NBTTagCompound;
import io.lumine.mythic.core.menus.items.ItemMenuContext;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.blackwater.rpg.data.enums.RPGClass;
import pl.blackwater.rpg.items.ItemType;
import pl.blackwater.rpg.storage.enums.AdditionalModifiers;
import pl.blackwater.rpg.storage.enums.SlotTypes;
import pl.blackwater.rpg.storage.enums.Stats;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.Util;

import java.util.*;
import java.util.jar.Attributes;

@RequiredArgsConstructor
@Data
public class RPGItem {

    private final Material material;
    private final String name;

    private final String rpgClass;

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

    private final ItemType itemType;
    private final Map<Enchantment, Integer> enchantments;

    public final ItemStack build(){

        ItemStack itemStack = new ItemStack(material);

        NBTItem nbtItem = new NBTItem(itemStack);


        nbtItem.setFloat(AdditionalModifiers.AMD.getType(), this.averageMonsterDamage);
        nbtItem.setFloat(AdditionalModifiers.AHD.getType(),this.averageHumanDamage);
        nbtItem.setFloat(AdditionalModifiers.ACD.getType(),this.averageCreatureDamage);
        nbtItem.setFloat(AdditionalModifiers.ADOCH.getType(),this.additionalDamageOfCriticalChance);
        nbtItem.setFloat(AdditionalModifiers.ACH.getType(),this.additionalCriticalChance);

        nbtItem.setString("ItemType", this.itemType.name());

        nbtItem.setInteger("level",this.level);

        nbtItem.setInteger(Stats.STAMINA.getType(),this.STAMINA);
        nbtItem.setInteger(Stats.DEXTERITY.getType(),this.DEXTERITY);
        nbtItem.setInteger(Stats.INTELLIGENCE.getType(),this.INTELLIGENCE);
        nbtItem.setInteger(Stats.STRENGTH.getType(),this.STRENGTH);
        nbtItem.setString("rpgClass", this.rpgClass);


        /* TODO CHANGE if(damage > 0) {
            NBTCompound damage = nbtCompoundList.
            damage.setString("AttributeName","generic.attackDamage");
            damage.setString("Name", "generic.attackDamage");
            damage.setString("Amount", this.damage);
            damage.setString("Slot", "mainhand");
            nbtCompound.addCompound(damage);

        }
        if(armor > 0){
            NBTTagCompound armor = new NBTTagCompound();
            armor.set("AttributeName", NBTTagString.a("generic.armor"));
            armor.set("Name", NBTTagString.a("generic.armor"));
            armor.set("Amount", NBTTagInt.a(this.armor));
            armor.set("Slot",NBTTagString.a(SlotTypes.getSlotType(this.material)));
            modifiers.add(armor);
        }
        if(attackSpeed > 0){
            NBTTagCompound speed = new NBTTagCompound();
            speed.set("AttributeName", NBTTagString.a("generic.attackSpeed"));
            speed.set("Name", NBTTagString.a("generic.attackSpeed"));
            speed.set("Amount", NBTTagInt.a(this.attackSpeed));
            speed.set("Slot", NBTTagString.a(SlotTypes.getSlotType(this.material)));
            modifiers.add(speed);
        }
        if(knockBack_Resistance > 0){
            NBTTagCompound knockBack = new NBTTagCompound();
            knockBack.set("AttributeName", NBTTagString.a("generic.knockbackResistance"));
            knockBack.set("Name", NBTTagString.a("generic.knockbackResistance"));
            knockBack.set("Amount", NBTTagInt.a(this.knockBack_Resistance));
            knockBack.set("Slot", NBTTagString.a(SlotTypes.getSlotType(this.material)));
            modifiers.add(knockBack);
        }
        if(max_health > 0){
            NBTTagCompound maxhealth = new NBTTagCompound();
            maxhealth.set("AttributeName", NBTTagString.a("generic.maxHealth"));
            maxhealth.set("Name", NBTTagString.a("generic.maxHealth"));
            maxhealth.set("Amount", NBTTagInt.a(this.max_health));
            maxhealth.set("Slot", NBTTagString.a(SlotTypes.getSlotType(this.material)));
            modifiers.add(maxhealth);
        }
        if(luck > 0){
            NBTTagCompound luck = new NBTTagCompound();
            luck.set("AttributeName", NBTTagString.a("generic.luck"));
            luck.set("Name", NBTTagString.a("generic.luck"));
            luck.set("Amount", NBTTagInt.a(this.luck));
            luck.set("Slot", NBTTagString.a(SlotTypes.getSlotType(this.material)));
            modifiers.add(luck);
        }
        compound.set("AttributeModifiers", modifiers);



        if(unbreakable) {
            compound.set("Unbreakable", NBTTagByte.a((byte) 1));
        }



        nmsItemStack.setTag(compound);


        itemStack = CraftItemStack.asBukkitCopy(nmsItemStack);*/




        nbtItem.setInteger("damage",damage);
        nbtItem.setInteger("attack_speed",attackSpeed);
        nbtItem.setInteger("knockback_resistance",knockBack_Resistance);
        nbtItem.setInteger("max_health",max_health);
        nbtItem.setInteger("luck",luck);
        nbtItem.setInteger("armor",armor);

        itemStack = nbtItem.getItem();


        final ItemMeta meta = itemStack.getItemMeta();

        meta.setDisplayName(ChatUtil.fixColor(this.name));

        if(this.enchantments != null) {
            for (Enchantment enchantment : this.enchantments.keySet()) {
                itemStack.addEnchantment(enchantment, this.enchantments.get(enchantment));
            }
        }

        if(damage > 0) {
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(),"generic.attack.damage", this.damage, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.valueOf(SlotTypes.getSlotType(material))));
        }
        if(attackSpeed > 0){
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED,new AttributeModifier(UUID.randomUUID(),"generic.attack.speed",this.attackSpeed, AttributeModifier.Operation.ADD_NUMBER,EquipmentSlot.valueOf(SlotTypes.getSlotType(material))));
        }
        if(knockBack_Resistance > 0){
            meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE,new AttributeModifier(UUID.randomUUID(),"generic.knockback.resistance",this.knockBack_Resistance, AttributeModifier.Operation.ADD_NUMBER,EquipmentSlot.valueOf(SlotTypes.getSlotType(material))));
        }
        if(max_health > 0){
            meta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH,new AttributeModifier(UUID.randomUUID(),"generic.max.health",this.max_health, AttributeModifier.Operation.ADD_NUMBER,EquipmentSlot.valueOf(SlotTypes.getSlotType(material))));
        }
        if(luck > 0){
            meta.addAttributeModifier(Attribute.GENERIC_LUCK,new AttributeModifier(UUID.randomUUID(),"generic.luck",this.luck, AttributeModifier.Operation.ADD_NUMBER,EquipmentSlot.valueOf(SlotTypes.getSlotType(material))));
        }
        if(armor > 0){
            meta.addAttributeModifier(Attribute.GENERIC_ARMOR,new AttributeModifier(UUID.randomUUID(),"generic.armor",this.armor, AttributeModifier.Operation.ADD_NUMBER,EquipmentSlot.valueOf(SlotTypes.getSlotType(material))));
        }

        /*meta.setLore(Arrays.asList(
                ChatUtil.fixColor("&8->> &7-------------------- &8<<-"),
                "",
                ChatUtil.fixColor("  &8* &6STATYSTYKI:"),
                ChatUtil.fixColor("    &8->> &7Wymagany poziom: " + this.level),
                ChatUtil.fixColor(damage == 0 ? "    &8->> &7Obrazenia: &6" + this.damage : null),
                ChatUtil.fixColor(armor == 0 ? "    &8->> &7Pancerz: &6" + this.armor : null),
                ChatUtil.fixColor(attackSpeed == 0 ? "    &8->> &7Predkosc ataku: &6" + this.attackSpeed + "%" : null),
                ChatUtil.fixColor(knockBack_Resistance == 0 ? "    &8->> &7Odpornosc na odepchniecie: &6" + this.knockBack_Resistance : null),
                ChatUtil.fixColor(max_health == 0 ? "    &8->> &7Maksymalne zycie &8(+&8)&7: &6" + this.max_health : null),
                ChatUtil.fixColor(luck == 0 ? "    &8->> &7Szczescie: &6" + this.luck : null),
                "",
                ChatUtil.fixColor((averageMonsterDamage != 0 || averageCreatureDamage != 0 || averageHumanDamage != 0 || additionalDamageOfCriticalChance != 0 || additionalCriticalChance != 0 ? "  &8* &cBONUSY:" : null)),
                "",
                ChatUtil.fixColor(averageMonsterDamage == 0 ? "    &8->> &4Dodatkowe obrazenia przeciwko potworom: &c" + this.averageMonsterDamage + "%" : null),
                ChatUtil.fixColor(averageCreatureDamage == 0 ? "    &8->> &4Dodatkowe obrazenia przeciwko istotom: &c" + this.averageCreatureDamage+ "%" : null),
                ChatUtil.fixColor(averageHumanDamage == 0 ? "    &8->> &4Dodatkowe obrazenia przeciwko ludziom: &c" + this.averageHumanDamage+ "%" : null),
                ChatUtil.fixColor(additionalDamageOfCriticalChance == 0 ? "    &8->> &4Dodatkowe obrazenia dla trafien krytycznych: &c" + this.additionalDamageOfCriticalChance+ "%" : null),
                ChatUtil.fixColor(additionalCriticalChance == 0 ? "    &8->> &4Dodatkowa szansa na trafienie krytyczne: &c" + this.additionalCriticalChance+ "%" : null),
                "",
                ChatUtil.fixColor(STAMINA != 0 || DEXTERITY != 0 || INTELLIGENCE != 0 || STRENGTH != 0 ? "&8  * &bDODATKOWE ATRYBUTY" : null),
                ChatUtil.fixColor(STAMINA == 0 ? "    &8->> &eStamina: &b" + this.STAMINA : null),
                ChatUtil.fixColor(DEXTERITY == 0 ? "    &8->> &eZrecznosc: &b" + this.DEXTERITY : null),
                ChatUtil.fixColor(INTELLIGENCE == 0 ? "    &8->> &eInteligencja: &b" + this.INTELLIGENCE : null),
                ChatUtil.fixColor(STRENGTH == 0 ? "    &8->> &eSila: &b" + this.STRENGTH : null),

                ChatUtil.fixColor("&8->> &7-------------------- &8<<-")));*/
        final List<String> lore = new ArrayList<>();
        lore.add(ChatUtil.hexColor("#68a8f7--[#6df2d3--------------------#68a8f7]--"));
        lore.add("");
        lore.add(ChatUtil.hexColor("  #68a8f7♯ #8968f7STATYSTYKI:"));
        lore.add("");
        lore.add(ChatUtil.hexColor("    #7356f5⚒ #8b56f5Wymagany poziom: #7356f5" + this.level));
        if(damage != 0){
            lore.add(ChatUtil.hexColor("    #f5315f⚔ #f03a98Obrazenia: #f5315f" + this.damage));
        }
        if(armor != 0){
            lore.add(ChatUtil.hexColor("    #fadb41⛨ #f4fa41Pancerz: #fadb41" + this.armor));
        }
        lore.add(ChatUtil.hexColor("    #b79deb☤ #6462d1Wymagana klasa: #824dd1" + (this.rpgClass.equals(RPGClass.BEGINNER.name()) ? "Wszystkie" : rpgClass.toUpperCase())));
        if(attackSpeed != 0){
            lore.add(ChatUtil.hexColor("    #55deed★ #55bfedPredkosc ataku: #55deed" + this.attackSpeed));
        }
        if(knockBack_Resistance != 0){
            lore.add(ChatUtil.hexColor("    #b169f0☬ #c869f0Odpornosc na odepchniecie: #b169f0" + this.knockBack_Resistance));
        }
        if(max_health != 0){
            lore.add(ChatUtil.hexColor("    #f261a2♥ #f589b9Maksymalne zycie #f261a2(#f589b9+#f261a2)#f589b9: #f261a2" + this.max_health));
        }
        if(luck != 0){
            lore.add(ChatUtil.hexColor("    #67eb95♣ #67ebbdSzczescie: #67eb95" + this.luck));
        }
        if(averageMonsterDamage != 0 || averageCreatureDamage != 0 || averageHumanDamage != 0 || additionalDamageOfCriticalChance != 0 || additionalCriticalChance != 0 ){
            lore.add("");
            lore.add(ChatUtil.hexColor("  #68a8f7♯ #681db3BONUSY:"));
            lore.add("");
        }
        if(averageMonsterDamage != 0){
            lore.add(ChatUtil.hexColor("    #f07b73⚲ #ed4237Dodatkowe obrazenia przeciwko potworom: #f07b73" + this.averageMonsterDamage + "%"));
        }
        if(averageCreatureDamage != 0){
            lore.add(ChatUtil.hexColor("    #ffe699⚳ #b09f6bDodatkowe obrazenia przeciwko istotom: #ffe699" + this.averageCreatureDamage+ "%"));
        }
        if(averageHumanDamage != 0){
            lore.add(ChatUtil.hexColor("    #b4e08b⚴ #92e08bDodatkowe obrazenia przeciwko ludziom: #b4e08b" + this.averageHumanDamage+ "%"));
        }
        if(additionalDamageOfCriticalChance != 0){
            lore.add(ChatUtil.hexColor("    #9aeff5⚵ #9adaf5Dodatkowe obrazenia dla trafien krytycznych: #9aeff5" + this.additionalDamageOfCriticalChance+ "%"));
        }
        if(additionalCriticalChance != 0){
            lore.add(ChatUtil.hexColor("    #628cf5⚶ #627df5Dodatkowa szansa na trafienie krytyczne: #628cf5" + this.additionalCriticalChance+ "%"));
        }
        if(STAMINA != 0 || DEXTERITY != 0 || INTELLIGENCE != 0 || STRENGTH != 0){
            lore.add("");
            lore.add(ChatUtil.hexColor("#68a8f7  ♯ #d146e0DODATKOWE ATRYBUTY:"));
            lore.add("");

            if(STAMINA != 0){
                lore.add(ChatUtil.hexColor("    #f3fa37☊ #d3fa37Stamina: #f3fa37" + this.STAMINA));
            }
            if(DEXTERITY != 0){
                lore.add(ChatUtil.hexColor("    #93dbc8☋ #93dbd7Zrecznosc: #93dbc8" + this.DEXTERITY));
            }
            if(INTELLIGENCE != 0){
                lore.add(ChatUtil.hexColor("    #284b70☌ #284470Inteligencja: #284b70" + this.INTELLIGENCE));
            }
            if(STRENGTH != 0){
                lore.add(ChatUtil.hexColor("    #70284a☍ #702828Sila: #70284a" + this.STRENGTH));
            }
        }
        lore.add("");
        lore.add(ChatUtil.hexColor("#68a8f7--[#6df2d3--------------------#68a8f7]--"));
        meta.setLore(lore);
        if(unbreakable){
            meta.setUnbreakable(true);
        }
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        //TODO name and lore

        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public static ItemStack rebuildItem(ItemStack itemStack){
        final ItemMeta meta = itemStack.getItemMeta();

        NBTItem item = new NBTItem(itemStack);


        final int damage = item.getInteger("damage");
        final int armor = item.getInteger("armor");
        final int attackSpeed = item.getInteger("attack_speed");
        final int knockBack_Resistance = item.getInteger("knockback_resistance");
        final int max_health = item.getInteger("max_health");
        final int level = item.getInteger("level");
        final int luck = item.getInteger("luck");
        final int STAMINA = item.getInteger(Stats.STAMINA.getType());
        final int DEXTERITY = item.getInteger(Stats.DEXTERITY.getType());
        final int INTELLIGENCE = item.getInteger(Stats.INTELLIGENCE.getType());
        final int STRENGTH = item.getInteger(Stats.STRENGTH.getType());
        final float averageMonsterDamage = item.getFloat(AdditionalModifiers.AMD.getType());
        final float averageCreatureDamage =  item.getFloat(AdditionalModifiers.ACD.getType());
        final float averageHumanDamage =  item.getFloat(AdditionalModifiers.AHD.getType());
        final float additionalDamageOfCriticalChance =  item.getFloat(AdditionalModifiers.ADOCH.getType());
        final float additionalCriticalChance =  item.getFloat(AdditionalModifiers.ACH.getType());
        List<String> lore = new ArrayList<>();

        lore.add(ChatUtil.hexColor("#68a8f7--[#6df2d3--------------------#68a8f7]--"));
        lore.add("");
        lore.add(ChatUtil.hexColor("  #68a8f7♯ #8968f7STATYSTYKI:"));
        lore.add("");
        lore.add(ChatUtil.hexColor("    #7356f5⚒ #8b56f5Wymagany poziom: #7356f5" + level));
        if(item.getInteger("upgradeLevel") != 0){
            lore.add(ChatUtil.hexColor("    #7496f2★ #ed9791ULEPSZENIE: #f5c35f+" + item.getInteger("upgradeLevel")));
        }
        if(damage != 0){
            lore.add(ChatUtil.hexColor("    #f5315f⚔ #f03a98Obrazenia: #f5315f" + damage));
        }
        if(armor != 0){
            lore.add(ChatUtil.hexColor("    #fadb41⛨ #f4fa41Pancerz: #fadb41" + armor));
        }
        if(attackSpeed != 0){
            lore.add(ChatUtil.hexColor("    #55deed★ #55bfedPredkosc ataku: #55deed" + attackSpeed));
        }
        if(knockBack_Resistance != 0){
            lore.add(ChatUtil.hexColor("    #b169f0☬ #c869f0Odpornosc na odepchniecie: #b169f0" + knockBack_Resistance));
        }
        if(max_health != 0){
            lore.add(ChatUtil.hexColor("    #f261a2♥ #f589b9Maksymalne zycie #f261a2(#f589b9+#f261a2)#f589b9: #f261a2" + max_health));
        }
        if(luck != 0){
            lore.add(ChatUtil.hexColor("    #67eb95♣ #67ebbdSzczescie: #67eb95" + luck));
        }
        if(averageMonsterDamage != 0 || averageCreatureDamage != 0 || averageHumanDamage != 0 || additionalDamageOfCriticalChance != 0 || additionalCriticalChance != 0 ){
            lore.add("");
            lore.add(ChatUtil.hexColor("  #68a8f7♯ #681db3BONUSY:"));
            lore.add("");
        }
        if(averageMonsterDamage != 0){
            lore.add(ChatUtil.hexColor("    #f07b73⚲ #ed4237Dodatkowe obrazenia przeciwko potworom: #f07b73" + averageMonsterDamage + "%"));
        }
        if(averageCreatureDamage != 0){
            lore.add(ChatUtil.hexColor("    #ffe699⚳ #b09f6bDodatkowe obrazenia przeciwko istotom: #ffe699" + averageCreatureDamage+ "%"));
        }
        if(averageHumanDamage != 0){
            lore.add(ChatUtil.hexColor("    #b4e08b⚴ #92e08bDodatkowe obrazenia przeciwko ludziom: #b4e08b" + averageHumanDamage+ "%"));
        }
        if(additionalDamageOfCriticalChance != 0){
            lore.add(ChatUtil.hexColor("    #9aeff5⚵ #9adaf5Dodatkowe obrazenia dla trafien krytycznych: #9aeff5" + additionalDamageOfCriticalChance+ "%"));
        }
        if(additionalCriticalChance != 0){
            lore.add(ChatUtil.hexColor("    #628cf5⚶ #627df5Dodatkowa szansa na trafienie krytyczne: #628cf5" + additionalCriticalChance+ "%"));
        }
        if(STAMINA != 0 || DEXTERITY != 0 || INTELLIGENCE != 0 || STRENGTH != 0){
            lore.add("");
            lore.add(ChatUtil.hexColor("#68a8f7  ♯ #d146e0DODATKOWE ATRYBUTY:"));
            lore.add("");

            if(STAMINA != 0){
                lore.add(ChatUtil.hexColor("    #f3fa37☊ #d3fa37Stamina: #f3fa37" + STAMINA));
            }
            if(DEXTERITY != 0){
                lore.add(ChatUtil.hexColor("    #93dbc8☋ #93dbd7Zrecznosc: #93dbc8" + DEXTERITY));
            }
            if(INTELLIGENCE != 0){
                lore.add(ChatUtil.hexColor("    #284b70☌ #284470Inteligencja: #284b70" + INTELLIGENCE));
            }
            if(STRENGTH != 0){
                lore.add(ChatUtil.hexColor("    #70284a☍ #702828Sila: #70284a" + STRENGTH));
            }
        }
        lore.add("");
        lore.add(ChatUtil.hexColor("#68a8f7--[#6df2d3--------------------#68a8f7]--"));

        meta.setLore(lore);

        itemStack.setItemMeta(meta);

        return itemStack;



    }
}
