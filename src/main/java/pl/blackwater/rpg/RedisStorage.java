package pl.blackwater.rpg;

import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.redisson.api.RMap;
import pl.blackwater.redisson.api.RSet;
import pl.blackwater.redisson.client.RedisConnection;
import pl.blackwater.rpg.mine.OreLocation;
import pl.blackwater.spigotplugin.redis.RedisManager;

import java.util.List;
import java.util.UUID;

public class RedisStorage {

    public final RMap<UUID,String> RPG_USERS;
    public final RMap<UUID,String> RPG_ITEMS;
    public final RMap<UUID, String> RPG_NPCS;

    public final RMap<UUID,String> RPG_ORE_LOCATIONS;

    public final RMap<String, String> RPG_MYSTERYBOX_DROP;

    public final RMap<String,String> RPG_GUILDS;

    public final RMap<String,String> RPG_RANKS;

    public final RMap<String,Boolean> RPG_CHATOPTIONS;

    public final RMap<UUID,String> RPG_MAPS;

    public RedisStorage(){
        RPG_USERS = RedisManager.getRedisConnection().getMap("RPG_USERS");


        //TYPE OF SAVING DATA
        RPG_ITEMS = RedisManager.getRedisConnection().getMap("RPG_ITEMS");

        RPG_NPCS = RedisManager.getRedisConnection().getMap("RPG_NPCS");

        RPG_MYSTERYBOX_DROP = RedisManager.getRedisConnection().getMap("RPG_MYSTERYBOX_DROP");

        RPG_ORE_LOCATIONS = RedisManager.getRedisConnection().getMap("RPG_ORE_LOCATIONS");

        RPG_GUILDS = RedisManager.getRedisConnection().getMap("RPG_GUILDS");

        RPG_RANKS = RedisManager.getRedisConnection().getMap("RPG_RANKS");

        RPG_CHATOPTIONS = RedisManager.getRedisConnection().getMap("RPG_CHATOPTIONS");

        RPG_MAPS = RedisManager.getRedisConnection().getMap("RPG_MAPS");
    }



}
