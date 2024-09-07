package pl.blackwater.rpg.maps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import pl.blackwater.rpg.RPG;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.GsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class MapStorage {


    private final HashMap<UUID, Map> mapsStorage;


    public MapStorage(){
        this.mapsStorage = new HashMap<>();

        //final ExpMap expMap = new ExpMap(new Location(Bukkit.getWorld("exp1"),35.428,67,5.596), ChatUtil.hexColor("#eaf2a5☬ #9ae2edEXPOWISKO 1 &8(#9ae2ed0-10LvL&8) #eaf2a5☬"),0);

        //mapsStorage.put(expMap.getMapName(),expMap);
    }

    public void addExpMap(ExpMap map){
        this.mapsStorage.put(map.getUuid(),map);
        map.insert();
    }
    public void removeExpMap(ExpMap map){
        this.mapsStorage.remove(map.getUuid());
        map.remove();
    }

    public List<ExpMap> getExpMaps(){
        final List<ExpMap> expMaps = new ArrayList<>();
        for(Map map : mapsStorage.values()){
            if(map instanceof  ExpMap){
                expMaps.add((ExpMap) map);
            }
        }
        return expMaps;
    }

    public HashMap<UUID, Map> getMapsStorage() {
        return mapsStorage;
    }

    public void load(){
        RPG.getPlugin().getRedisStorage().RPG_MAPS.forEach((uuid, s) -> {
            final ExpMap expMap = GsonUtil.fromJson(s,ExpMap.class);

            mapsStorage.put(uuid,expMap);
        });

        RPG.getPlugin().getLogger().log(Level.INFO,"Loaded exp maps!");
    }


}
