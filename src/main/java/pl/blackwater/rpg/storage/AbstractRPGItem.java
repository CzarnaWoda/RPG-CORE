package pl.blackwater.rpg.storage;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import pl.blackwater.rpg.data.enums.RPGClass;
import pl.blackwater.rpg.items.ItemType;

import java.util.HashMap;
import java.util.Map;

@Data
@Setter
public class AbstractRPGItem {

    private  Material material = Material.DIAMOND_SWORD;
    private  String name = "NAME";

    private String rpgClass = RPGClass.WARRIOR.name();

    private  int level = 1;
    private  int armor = 5;
    private  int damage = 0;
    private  int attackSpeed = 0;
    private  int knockBack_Resistance = 0;
    private  int max_health = 0;
    private  int luck = 0;
    private  double averageMonsterDamage = 0;
    private  double averageHumanDamage = 0;
    private  double averageCreatureDamage = 0;
    private  double additionalDamageOfCriticalChance = 0;
    private  double additionalCriticalChance = 0;
    private  int STRENGTH = 0;
    private  int DEXTERITY = 0;
    private  int INTELLIGENCE = 0;
    private  int STAMINA = 0;
    private  boolean unbreakable = false;

    private  ItemType itemType = ItemType.EQUIPMENT;
    private  Map<Enchantment, Integer> enchantments = new HashMap<>();

    public AbstractRPGItem(){}
}
