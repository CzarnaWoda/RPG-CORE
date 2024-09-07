package pl.blackwater.rpg.guilds;

import org.bukkit.entity.Player;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.spigotplugin.util.GsonUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

public class GuildManagerImpl implements GuildManager{

    private final Map<String,Guild> guilds;
    private final RPG plugin;
    public GuildManagerImpl(RPG plugin){
        this.plugin = plugin;

        this.guilds = new HashMap<>();
        loadGuild();
    }
    @Override
    public Guild createGuild(UUID owner, String tag, String name) {
        final Guild guild = new Guild(tag,name,owner);

        this.guilds.put(tag,guild);
        guild.insert();
        return null;
    }
    @Override
    public Guild getSimpleGuild(Player player){
        for(Guild guild : guilds.values()){
            for(Member member : guild.getMembers()){
                if(member.getUuid().equals(player.getUniqueId())){
                    return guild;
                }
            }
        }
        return null;
    }

    @Override
    public void deleteGuild(String optionalNameTag) {
        getGuild(optionalNameTag).ifPresent(guild -> {
            getGuilds().remove(guild.getTag());
            plugin.getRedisStorage().RPG_GUILDS.remove(guild.getTag());
        });
    }

    @Override
    public void deleteGuild(Guild guild) {
        getGuilds().remove(guild.getTag());
        plugin.getRedisStorage().RPG_GUILDS.remove(guild.getTag());
    }

    @Override
    public Optional<Guild> getGuild(String optionalNameTag) {
        if(getGuilds().get(optionalNameTag) != null){
            return Optional.of(getGuilds().get(optionalNameTag));
        }
        return getGuilds().values().stream().filter(guild -> guild.getName().equalsIgnoreCase(optionalNameTag)).findAny();
    }

    @Override
    public Optional<Guild> getGuild(UUID owner) {
        return getGuilds().values().stream().filter(guild -> guild.getOwner().equals(owner)).findAny();
    }

    @Override
    public Member getSimpleMember(UUID uuid) {
        for(Guild guild : guilds.values()){
            for(Member member : guild.getMembers()){
                if(member.getUuid().equals(uuid)){
                    return member;
                }
            }
        }
        return null;
    }

    @Override
    public Guild getSimpleGuild(UUID player){
        for(Guild guild : guilds.values()){
            for(Member member : guild.getMembers()){
                if(member.getUuid().equals(player)){
                    return guild;
                }
            }
        }
        return null;
    }

    @Override
    public Optional<Guild> getGuild(Member member) {
        return getGuilds().values().stream().filter(guild -> guild.getMembers().contains(member)).findAny();
    }

    @Override
    public Optional<Guild> getGuild(RPGUser rpgUser) {
        return getGuilds().values().stream().filter(guild -> guild.getMembers().stream().anyMatch(member -> member.getUuid().equals(rpgUser.getUuid()))).findAny();
    }

    @Override
    public Optional<Guild> getGuild(Player player) {
        return getGuilds().values().stream().filter(guild -> guild.getMembers().stream().anyMatch(member -> member.getUuid().equals(player.getUniqueId()))).findAny();
    }

    @Override
    public Optional<Member> getMember(UUID uuid) {
        for(Guild guild : getGuilds().values()){
            for (Member member : guild.getMembers()){
                if(member.getUuid().equals(uuid)){
                    return Optional.of(member);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Member> getMember(Player player) {
        for(Guild guild : getGuilds().values()){
            for (Member member : guild.getMembers()){
                if(member.getUuid().equals(player.getUniqueId())){
                    return Optional.of(member);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Map<String, Guild> getGuilds() {
        return guilds;
    }

    @Override
    public void loadGuild() {
        final AtomicInteger ai = new AtomicInteger(0);
        plugin.getRedisStorage().RPG_GUILDS.forEach((s, s2) -> {
            getGuilds().put(s, GsonUtil.fromJson(s2,Guild.class));
            ai.incrementAndGet();
        });
        plugin.getLogger().log(Level.INFO,"Loaded " + ai.get() + " guilds");
    }
}
