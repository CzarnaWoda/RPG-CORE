package pl.blackwater.rpg.bossbar;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.ChatUser;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.rpg.data.combat.Combat;
import pl.blackwater.rpg.data.combat.CombatManager;
import pl.blackwater.spigotplugin.thread.SpigotSingleThread;
import pl.blackwater.spigotplugin.thread.SpigotThread;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.MathUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BossBarThread extends SpigotSingleThread {
    public BossBarThread() {
        super(Executors.newSingleThreadScheduledExecutor());
    }

    @Override
    public SpigotThread runThread() {
        getExecutorService().scheduleAtFixedRate(()->{
            try {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    //MYTHIC MOBS BOSS BAR
                    final BossBarService service = RPG.getPlugin().getBossBarServiceStorage().getBossBar(player);
                    if (System.currentTimeMillis() - service.getLastUpdate() > 6500L) {
                        service.hide();
                    }
                    if (service.getActiveMob() != null) {
                        final ActiveMob mythicMob = service.getActiveMob();
                        final AbstractEntity abstractEntity = mythicMob.getEntity();
                        final double progress = abstractEntity.getHealth() / abstractEntity.getMaxHealth();
                        if (progress != service.getAbstractProgress()) {
                            service.setAbstractProgress(progress).setTitle("&8->> &4★ &c" + abstractEntity.getName().toUpperCase() + " &8(&4" + MathUtil.round(abstractEntity.getHealth(), 2) + "&7/&4" + abstractEntity.getMaxHealth() + "&8) &4★&8 <<-")
                                    .setProgress(progress).show();
                        }
                    }
                    //END MYTHIC MOBS BOSSBAR

                    final BossBarService antiLogoutService = RPG.getPlugin().getBossBarServiceStorage().getAntiLogoutBossBar(player);

                    final Combat c = CombatManager.getCombat(player);

                    final RPGUser rpgUser = RPG.getPlugin().getRpgUserManager().getUser(player.getUniqueId());
                    if (rpgUser != null) {
                        final boolean bossbar = rpgUser.getChatUser().isFightBossBossBar();
                        final boolean chat = rpgUser.getChatUser().isFightMessageOnChat();
                        if (c != null) {
                            if (c.hasFight()) {
                                if (bossbar) {
                                    final long secondsLeft = (c.getLastAttackTime() / 1000) - (System.currentTimeMillis() / 1000);
                                    if (secondsLeft >= 0) {
                                        antiLogoutService.setProgress(secondsLeft / 20f);
                                        antiLogoutService.setTitle("&c&l⚔ &4ANTY-LOGOUT &8(&4" + secondsLeft + "&8) &c&l⚔");
                                        antiLogoutService.show();
                                    }
                                }
                            } else if (c.wasFight() && !c.hasFight()) {
                                c.setLastAttackPlayer(null);
                                if (bossbar) {
                                    antiLogoutService.setTitle("&2&l⭐ &aMOZESZ SIE WYLOGOWAC &2&l⭐");
                                    antiLogoutService.setProgress(1.0);
                                    antiLogoutService.setStyle(BarStyle.SOLID);
                                    antiLogoutService.setColor(BarColor.GREEN);
                                    Bukkit.getScheduler().runTaskLater(RPG.getPlugin(), () -> {
                                        antiLogoutService.hide();
                                        antiLogoutService.setColor(BarColor.BLUE);
                                        antiLogoutService.setStyle(BarStyle.SEGMENTED_20);
                                    }, 100L);
                                }
                                if (chat) {
                                    ChatUtil.sendMessage(player, "&2⚔ &aWalka zostala zakonczona, mozesz sie bezpiecznie wylogowac! &2⚔");
                                }


                            }
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        },500L,500L, TimeUnit.MILLISECONDS);
        return this;
    }
}
