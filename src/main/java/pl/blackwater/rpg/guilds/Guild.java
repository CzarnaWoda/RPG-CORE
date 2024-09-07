package pl.blackwater.rpg.guilds;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.Getter;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.Entry;
import pl.blackwater.rpg.guilds.enums.MemberType;
import pl.blackwater.rpg.guilds.enums.OptionsType;
import pl.blackwater.rpg.guilds.enums.UpgradeType;
import pl.blackwater.spigotplugin.util.GsonUtil;
import pl.blackwater.spigotplugin.util.MathUtil;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Getter
public class Guild implements Entry {

    private final String tag;
    private final String name;
    private final UUID owner;
    private final List<Member> members;

    private int guildLevel;

    private int guildPoints;
    private double guildExp;
    private final Map<UpgradeType,Integer> upgradeTypes = new HashMap<>();
    private final Map<OptionsType,Boolean> options = new HashMap<>();

    private final long createTime;
    private final long expireTime;
    private final int playerLimit;

    private transient Cache<UUID,Long> invites = CacheBuilder.newBuilder().expireAfterWrite(30, TimeUnit.SECONDS).build();

    public Guild(String tag, String name, UUID owner){
        this.tag = tag;
        this.name = name;
        this.owner = owner;
        this.members = new ArrayList<>();
        members.add(new Member(owner, MemberType.OWNER));
        this.guildLevel = 1;
        this.guildPoints = 0;
        this.guildExp = 0.0;

        for(UpgradeType type : UpgradeType.values()){
            upgradeTypes.put(type,0);
        }
        for(OptionsType type : OptionsType.values()){
            options.put(type,false);
        }
        this.createTime = System.currentTimeMillis();
        this.expireTime = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7);
        this.playerLimit = 10;

    }


    public Cache<UUID, Long> getInvites() {
        if(this.invites == null){
            this.invites = CacheBuilder.newBuilder().expireAfterWrite(30, TimeUnit.SECONDS).build();
        }
        return invites;
    }

    @Override
    public void insert() {
        RPG.getPlugin().getRedisStorage().RPG_GUILDS.put(this.tag, GsonUtil.toJson(this));
    }

    @Override
    public void update() {
        RPG.getPlugin().getRedisStorage().RPG_GUILDS.putAsync(this.tag, GsonUtil.toJson(this));
    }

    @Override
    public void remove() {
        RPG.getPlugin().getRedisStorage().RPG_GUILDS.remove(this.tag);
    }

    public double getExpToLevel(){
        return MathUtil.round((double)(guildLevel * 95000)/(double)(10-guildLevel),2);
    }
    public void addExp(double exp){
        this.guildExp += exp;
        if(guildExp >= getExpToLevel()){
            this.guildExp -= getExpToLevel();
            this.guildLevel += 1;
            this.guildPoints += 1;
        }
    }
    public void removePoint(int points){
        this.guildPoints -= points;
    }
}
