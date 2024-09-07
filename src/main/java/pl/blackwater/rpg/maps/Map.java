package pl.blackwater.rpg.maps;


import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

@RequiredArgsConstructor
public class Map {
    private final Location location;
    private final String mapName;

    private final UUID uuid;


    public Location getLocation() {
        return location;
    }

    public String getMapName() {
        return mapName;
    }

    public UUID getUuid() {
        return uuid;
    }
}
