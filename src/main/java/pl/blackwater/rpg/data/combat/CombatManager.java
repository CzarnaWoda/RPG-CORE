package pl.blackwater.rpg.data.combat;

import lombok.Getter;
import org.bukkit.entity.Player;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.bossbar.BossBarService;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class CombatManager {

    @Getter
    private static final ConcurrentHashMap<Player, Combat> combats = new ConcurrentHashMap<>();

    public static Combat getCombat(final Player player) {
        return combats.get(player);
    }

    public static Optional<Combat> getCombatAsOption(final Player player){
        return Optional.of(combats.get(player));
    }
    public static void CreateCombat(final Player player) {
        final Combat combat = new Combat(player);
        combats.put(player, combat);
    }
    public static void removeCombat(final Player player) {
        combats.remove(player);
    }

    public static void removeFight(final Combat c) {
        if(c != null) {
            c.setLastAttackPlayer(null);
            c.setLastAttackTime(0L);
            RPG.getPlugin().getBossBarServiceStorage().getAntiLogoutBossBar(c.getPlayer()).hide();
        }
    }
}