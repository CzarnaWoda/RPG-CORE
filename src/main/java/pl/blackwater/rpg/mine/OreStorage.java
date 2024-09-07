package pl.blackwater.rpg.mine;

import com.google.gson.Gson;
import org.bukkit.Material;
import pl.blackwater.rpg.RPG;
import pl.blackwater.spigotplugin.util.GsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OreStorage {

    private final List<OreLocation> oreLocationList;

    private final List<Ore> ores;

    private final RPG plugin;

    public OreStorage(RPG plugin){
        this.ores = new ArrayList<>();
        this.oreLocationList = new ArrayList<>();

        this.plugin = plugin;
    }

    public void addOreLocation(OreLocation oreLocation){
        oreLocationList.add(oreLocation);

        plugin.getRedisStorage().RPG_ORE_LOCATIONS.put(oreLocation.getUuid(), GsonUtil.toJson(oreLocation));
    }

    public List<Ore> getOres() {
        return ores;
    }

    public List<OreLocation> getOreLocationList() {
        return oreLocationList;
    }
    public void removeOre(OreLocation oreLocation){
        final Optional<Ore> optionalOre = ores.stream().filter(ore -> ore.getLocation().equals(oreLocation.getLocation())).findAny();
        optionalOre.ifPresent(ore -> {
            if(ore.getBlock() != null){
                ore.getBlock().setType(Material.AIR);
            }
            ore.removeHologram();

        });
        if(optionalOre.isPresent()){
            ores.remove(optionalOre);
        }
        plugin.getRedisStorage().RPG_ORE_LOCATIONS.remove(oreLocation.getUuid());
        oreLocationList.remove(oreLocation);
    }

    public void load(){
        plugin.getRedisStorage().RPG_ORE_LOCATIONS.forEach((uuid, s) -> {
            final OreLocation oreLocation = GsonUtil.fromJson(s, OreLocation.class);

            getOreLocationList().add(oreLocation);
        });
    }
}
