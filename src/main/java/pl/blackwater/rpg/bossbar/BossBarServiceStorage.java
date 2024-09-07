package pl.blackwater.rpg.bossbar;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BossBarServiceStorage {

    public HashMap<Player, BossBarService> bossBarServiceList;
    public HashMap<Player,BossBarService> antiLogoutBossBarList;

    public BossBarServiceStorage(){
        bossBarServiceList = new HashMap<>();
        antiLogoutBossBarList = new HashMap<>();
    }

    public HashMap<Player, BossBarService> getBossBarServiceList() {
        return bossBarServiceList;
    }

    public HashMap<Player, BossBarService> getAntiLogoutBossBarList() {
        return antiLogoutBossBarList;
    }

    public BossBarService getBossBar(Player player){
        return createBossBarService(player);
    }

    public BossBarService getAntiLogoutBossBar(Player player){
        return createAntiLogoutBossBarService(player);
    }

    public BossBarService createBossBarService(Player player){
        if(bossBarServiceList.get(player) == null){
            final BossBarService service = new BossBarService(player);

            bossBarServiceList.put(player,service);

            return service;
        }
        return getBossBarServiceList().get(player);
    }
    public BossBarService createAntiLogoutBossBarService(Player player){
        if(antiLogoutBossBarList.get(player) == null){
            final BossBarService service = new BossBarService(player);

            service.setColor(BarColor.BLUE);
            service.setStyle(BarStyle.SEGMENTED_20);
            service.hide();

            antiLogoutBossBarList.put(player,service);

            return service;
        }
        return getAntiLogoutBossBarList().get(player);
    }
}
