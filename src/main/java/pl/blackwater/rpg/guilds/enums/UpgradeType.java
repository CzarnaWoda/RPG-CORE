package pl.blackwater.rpg.guilds.enums;

import org.bukkit.Material;

public enum UpgradeType {

    DAMAGE(Material.GOLDEN_SWORD,150000,30);

    private Material material;
    private int money;

    private int max;
    UpgradeType(Material material, int i, int max) {

        this.material = material;
        this.money = i;
        this.max = max;

    }

    public Material getMaterial() {
        return material;
    }

    public int getMoney() {
        return money;
    }

    public int getMax() {
        return max;
    }
}
