package pl.blackwater.rpg.guilds.enums;

import org.bukkit.Material;

public enum GuildPermission {
    PVP(Material.DIAMOND_SWORD,"Pozwala przelaczac PVP w gildii"),
    KICK(Material.BARRIER,"Pozwala wyrzucac czlonkow z gildii"),
    INVITE(Material.LIME_DYE,"Pozwala zapraszac graczy do gildii"),
    MANAGE(Material.DRAGON_EGG,"Pozwala zarzadzac gildia pod komenda /guild manage");

    private final Material material;
    private final String description;
    GuildPermission(Material material,String description) {
        this.material = material;
        this.description = description;
    }

    public Material getMaterial() {
        return material;
    }

    public String getDescription() {
        return description;
    }
}
