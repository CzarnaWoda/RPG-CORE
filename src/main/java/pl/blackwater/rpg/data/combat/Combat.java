package pl.blackwater.rpg.data.combat;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter
@Setter
public class Combat {
    private Player player;
    private long lastAttackTime;
    private Player lastAttackPlayer;

    public Combat(Player player) {
        this.player = player;
        lastAttackTime = 0L;
        lastAttackPlayer = null;
    }


    public boolean hasFight() {
        return this.getLastAttackTime() > System.currentTimeMillis();
    }

    public boolean wasFight() {
        return this.getLastAttackPlayer() != null;
    }
}
