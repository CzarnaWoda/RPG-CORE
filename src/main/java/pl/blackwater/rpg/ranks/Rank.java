package pl.blackwater.rpg.ranks;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.Entry;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.spigotplugin.util.GsonUtil;

import java.util.List;

@Getter
@Setter
public class Rank implements Entry {

    public String name,prefix,suffix;
    public List<String> permissions;

    public Rank(String name, String prefix, String suffix, List<String> permissions){
        this.name = name;
        this.prefix = prefix;
        this.suffix =  suffix;
        this.permissions = permissions;

    }
    @Override
    public void insert() {
        RPG.getPlugin().getRedisStorage().RPG_RANKS.put(name, GsonUtil.toJson(this));
    }

    @Override
    public void update() {
        RPG.getPlugin().getRedisStorage().RPG_RANKS.putAsync(name, GsonUtil.toJson(this));
        System.out.println("RankInfo - Rank update status = " + true + ", rank name: " + name);
    }
    @Override
    public void remove() {
        RPG.getPlugin().getRedisStorage().RPG_RANKS.remove(name);
    }
    public void addPermission(String pex){
        if(permissions.contains(pex)){
            System.out.println("RankERROR - permission already set, cant be add !");
            return;
        }
        permissions.add(pex);
        for(RPGUser user : RPG.getPlugin().getRpgUserManager().getUsers().values()) {
            if (user.getRankName().equalsIgnoreCase(getName())) {
                Player player = Bukkit.getPlayer(user.getUuid());
                if (player != null) {
                    PermissionAttachment pperms = RPG.getPlugin().getRankManager().getPermissions().get(player.getUniqueId());
                    pperms.setPermission(pex, true);
                }
            }
        }
    }
    public void removePermission(String pex){
        if(!permissions.contains(pex)){
            System.out.println("RankERROR - permission not found, cant be remove ! (RANKSYSTEM -> removePermission(String pex))");
            return;
        }
        permissions.remove(pex);
        for(RPGUser user : RPG.getPlugin().getRpgUserManager().getUsers().values()) {
            if (user.getRankName().equalsIgnoreCase(getName())) {
                Player player = Bukkit.getPlayer(user.getUuid());
                if (player != null) {
                    PermissionAttachment pperms = RPG.getPlugin().getRankManager().getPermissions().get(player.getUniqueId());
                    pperms.unsetPermission(pex);
                }
            }
        }
    }
}
