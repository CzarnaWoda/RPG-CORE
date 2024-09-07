package pl.blackwater.rpg.mine;

import org.bukkit.Location;

import java.io.Serializable;
import java.util.UUID;


public class OreLocation implements Serializable {

    private UUID uuid;
    private Location location;

    public OreLocation(){};
    public OreLocation(UUID uuid, Location location){
        this.location = location;
        this.uuid = uuid;
    }

    public Location getLocation() {
        return location;
    }

    public UUID getUuid() {
        return uuid;
    }
}
