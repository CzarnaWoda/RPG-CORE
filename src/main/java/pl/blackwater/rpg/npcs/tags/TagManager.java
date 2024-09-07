package pl.blackwater.rpg.npcs.tags;

import net.minecraft.server.v1_16_R3.Scoreboard;
import org.bukkit.entity.Player;
import pl.blackwater.rpg.data.RPGUser;

public interface TagManager {

    void updateTagForPlayer(Player p, ScoreboardAction action);

    String getPreparedGuildPrefix(RPGUser user, RPGUser target);

    Scoreboard getScoreboard();

}

