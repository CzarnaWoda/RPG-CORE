package pl.blackwater.rpg.bossbar;

import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import pl.blackwater.spigotplugin.util.ChatUtil;

public class BossBarService {

    private final BossBar bossBar;
    private final Player player;

    private double abstractProgress;

    private ActiveMob activeMob;

    private long lastUpdate = System.currentTimeMillis();
    public BossBarService(Player player){
        bossBar = Bukkit.createBossBar(ChatUtil.fixColor("BOSS BAR SERVICE"), BarColor.BLUE, BarStyle.SOLID);

        this.player = player;

        bossBar.addPlayer(player);

        bossBar.setStyle(BarStyle.SEGMENTED_20);
        bossBar.setColor(BarColor.RED);

        hide();
    }

    public BossBarService setTitle(String title){
        bossBar.setTitle(ChatUtil.fixColor(title));
        lastUpdate = System.currentTimeMillis();
        return this;
    }
    public BossBarService setColor(BarColor color){
        bossBar.setColor(color);
        lastUpdate = System.currentTimeMillis();
        return this;
    }
    public BossBarService setMob(ActiveMob activeMob){
        this.activeMob = activeMob;
        lastUpdate = System.currentTimeMillis();
        return this;
    }
    public BossBarService setAbstractProgress(double progress){
        this.abstractProgress = progress;
        lastUpdate = System.currentTimeMillis();
        return this;
    }
    public BossBarService setStyle(BarStyle style){
        bossBar.setStyle(style);
        lastUpdate = System.currentTimeMillis();
        return this;
    }
    public BossBarService show(){
        bossBar.show();
        bossBar.setVisible(true);

        return this;
    }
    public BossBarService hide(){
        bossBar.hide();
        bossBar.setVisible(false);

        return this;
    }
    public BossBarService setProgress(double progress){
        bossBar.setProgress(progress);
        lastUpdate = System.currentTimeMillis();
        return this;
    }

    public ActiveMob getActiveMob() {
        return activeMob;
    }

    public BossBar getBossBar() {
        return bossBar;
    }

    public double getAbstractProgress() {
        return abstractProgress;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public Player getPlayer() {
        return player;
    }
}
