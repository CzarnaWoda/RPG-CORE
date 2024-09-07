package pl.blackwater.rpg.npcs.quests.rewards;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.rpg.storage.enums.Stats;

@RequiredArgsConstructor
public class StatReward extends Reward{

    private final Stats statType;
    private final int addAmount;

    @Override
    public boolean giveReward(Player player) {
        final RPGUser user = RPG.getPlugin().getRpgUserManager().getUser(player.getUniqueId());
        if(user == null){
            throw new IllegalArgumentException("User not found in giveReward ! (STATREWARD)");
        }
        user.addStat(statType,addAmount);
        user.update(true);
        return true;
    }
}
