package pl.blackwater.rpg.maps;


import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.Entry;
import pl.blackwater.spigotplugin.util.GsonUtil;

import java.io.Serializable;
import java.util.UUID;

public class ExpMap extends Map implements Entry {
    private final int requireLevel;


    public ExpMap(Location location,String mapName, int requireLevel) {
        super(location,mapName, UUID.randomUUID());

        this.requireLevel = requireLevel;
    }

    @Override
    public void insert() {
        RPG.getPlugin().getRedisStorage().RPG_MAPS.put(getUuid(), GsonUtil.toJson(this));
    }

    @Override
    public void update() {
        RPG.getPlugin().getRedisStorage().RPG_MAPS.putAsync(getUuid(), GsonUtil.toJson(this));
    }

    @Override
    public void remove() {
        RPG.getPlugin().getRedisStorage().RPG_MAPS.remove(getUuid());
    }

    public int getRequireLevel() {
        return requireLevel;
    }
}
