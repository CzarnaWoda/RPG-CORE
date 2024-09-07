package pl.blackwater.rpg.guilds;

import org.bukkit.entity.Player;
import pl.blackwater.rpg.data.RPGUser;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface GuildManager {

    Guild createGuild(UUID owner, String tag, String name);

    void deleteGuild(String optionalNameTag);

    void deleteGuild(Guild guild);

    Optional<Guild> getGuild(String optionalNameTag);

    Optional<Guild> getGuild(UUID owner);

    Optional<Guild> getGuild(Member member);

    Optional<Guild> getGuild(RPGUser rpgUser);

    Optional<Guild> getGuild(Player player);

    Optional<Member> getMember(UUID uuid);

    Optional<Member> getMember(Player player);

    Map<String,Guild> getGuilds();

    Guild getSimpleGuild(Player player);
    Guild getSimpleGuild(UUID playerUuid);
    Member getSimpleMember(UUID uuid);

    void loadGuild();

}
