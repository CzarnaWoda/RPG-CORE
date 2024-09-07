package pl.blackwater.rpg.listeners;

import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.rpg.data.combat.Combat;
import pl.blackwater.rpg.data.combat.CombatManager;
import pl.blackwater.spigotplugin.util.ChatUtil;

@RequiredArgsConstructor
public class PlayerDeathListener implements Listener {

    private final RPG plugin;
    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        e.setKeepInventory(true);
        e.setKeepLevel(true);
        e.setDeathMessage(null);
        e.getDrops().clear();
        e.setDroppedExp(0);
        //OBJECTS
        final Player victim = e.getEntity();
        final RPGUser victimUser = plugin.getRpgUserManager().getUser(victim.getUniqueId());
        Player killer = victim.getKiller();
        final Combat c = CombatManager.getCombat(victim);
        //NUMBERS
        victimUser.addDeaths(1);
        if(killer == null && c != null){
            final Player last = c.getLastAttackPlayer();
            if(last != null){
                killer = last;
            }
        }
        CombatManager.removeFight(c);
        if(killer != null){
            if(killer.getName().equalsIgnoreCase(victim.getName())){
                ChatUtil.sendMessage(killer,"&4Blad: &cNie mozesz zabic samego siebie!");
                return;
            }
            killPlayer(victim,killer);
        }
        respawn(victim);
    }
    private void killPlayer(final Player victim, final Player killer){
        final RPGUser killerUser = plugin.getRpgUserManager().getUser(killer.getUniqueId());

        killerUser.addKills(1);
    }
    private void respawn(final Player victim) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if(victim != null && victim.isOnline() && victim.isDead()) {
                victim.spigot().respawn();
                victim.teleport(plugin.getRpgConfig().getSpawnLocation());
                //TODO LocationUtil.TeleportToLocationWithDelay(victim,LocationUtil.getRandSpawnLocation(CoreConfig.getSpawnLocations()),1);
            }
        },5L);
    }
}
