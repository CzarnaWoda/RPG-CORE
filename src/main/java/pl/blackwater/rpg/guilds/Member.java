package pl.blackwater.rpg.guilds;

import lombok.Getter;
import pl.blackwater.rpg.guilds.enums.GuildPermission;
import pl.blackwater.rpg.guilds.enums.MemberType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class Member implements Serializable {

    private UUID uuid;
    private final Map<GuildPermission,Boolean> guildPermissions = new HashMap<>();
    private long joinTime;
    private MemberType memberType;

    public Member(){
    }
    public Member(UUID uuid,MemberType memberType){
        this.uuid = uuid;
        for(GuildPermission guildPermission : GuildPermission.values()){
            guildPermissions.put(guildPermission,memberType.equals(MemberType.OWNER));
        }
        this.joinTime = System.currentTimeMillis();
        this.memberType = memberType;
    }

}
