package pl.blackwater.rpg.configs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import pl.blackwater.api.config.Config;
import pl.blackwater.api.config.annotation.Comment;
import pl.blackwater.api.config.annotation.ConfigName;

import java.util.HashMap;
import java.util.Map;

@ConfigName("rpg.yml")
public interface RPGConfig extends Config {

    @Comment("Standard exp scale with exp scale")
    default int getStandardExp(){
        return 250;
    }

    @Comment("ANTYLOGOUT DURATION")
    default int getCombatDuration(){
        return 20;
    }

    default Location getSpawnLocation(){
        return new Location(Bukkit.getWorld("world"),0,64,0);
    }

    void setSpawnLocation(Location location);

}
