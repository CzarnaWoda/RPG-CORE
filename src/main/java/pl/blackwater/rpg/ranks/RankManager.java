package pl.blackwater.rpg.ranks;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.jetbrains.annotations.NotNull;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.spigotplugin.util.GsonUtil;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class RankManager {

    @Getter
    public HashMap<UUID, PermissionAttachment> permissions = new HashMap<>();
    @Getter
    private HashMap<String, Rank> ranks = new HashMap<>();

    private RPG plugin;

    public RankManager(RPG plugin){
        this.plugin = plugin;
    }

    public void joinPlayer(@NotNull Player player){
        if(permissions.get(player.getUniqueId()) == null) {
            PermissionAttachment attachment = player.addAttachment(plugin);
            getPermissions().put(player.getUniqueId(), attachment);
        }else{
            permissions.remove(player.getUniqueId());
            PermissionAttachment attachment = player.addAttachment(plugin);
            getPermissions().put(player.getUniqueId(), attachment);
        }
    }
    public Rank getRank(String name){
        for(Rank r : getRanks().values()){
            if(r.getName().equalsIgnoreCase(name)){
                return r;
            }
        }
        return null;
    }
    public void implementPermissions(@NotNull RPGUser u){
        Rank rank = u.getRank();
        if(u.getPlayer() != null){
            PermissionAttachment permissionAttachment = getPermissions().get(u.getPlayer().getUniqueId());
            for(String permissions : rank.getPermissions()){
                permissionAttachment.setPermission(permissions, true);
            }
        }
    }
    public void unImplementPermissions(@NotNull RPGUser u){
        Rank rank = u.getRank();
        if(u.getPlayer() != null){
            PermissionAttachment permissionAttachment = getPermissions().get(u.getPlayer().getUniqueId());
            if(permissionAttachment != null) {
                for (String permissions : rank.getPermissions()) {
                    if (permissionAttachment.getPermissions().containsKey(permissions)) {
                        permissionAttachment.unsetPermission(permissions);
                    }
                }
            }
        }
    }
    public Rank createRank(String rank){
        Rank r = new Rank(rank, "CHANGE","CHANGE", Collections.singletonList("rpg.defaultPermission"));
        ranks.put(rank, r);
        r.insert();
        return r;
    }
    public void loadRanks(){
        final AtomicInteger ai = new AtomicInteger(0);
        plugin.getRedisStorage().RPG_RANKS.forEach((s, s2) -> {
            ranks.put(s, GsonUtil.fromJson(s2,Rank.class));
            ai.getAndIncrement();
        });
        System.out.println("Loaded " +  ai.get() + " ranks!");
    }
}
