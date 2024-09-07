package pl.blackwater.rpg.listeners;

import de.tr7zw.nbtapi.NBTItem;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import lombok.RequiredArgsConstructor;
import net.minecraft.server.v1_16_R3.DamageSource;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftMob;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.bossbar.BossBarService;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.rpg.data.combat.Combat;
import pl.blackwater.rpg.data.combat.CombatManager;
import pl.blackwater.rpg.data.enums.RPGClass;
import pl.blackwater.rpg.guilds.Guild;
import pl.blackwater.rpg.guilds.enums.OptionsType;
import pl.blackwater.rpg.storage.enums.AdditionalModifiers;
import pl.blackwater.rpg.storage.enums.Stats;
import pl.blackwater.rpg.util.RPGUtil;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.RandomUtil;
import pl.blackwater.spigotplugin.util.TimeUtil;
import pl.blackwater.spigotplugin.util.Util;

import java.util.Objects;

@RequiredArgsConstructor
public class PlayerDamageEntityListener implements Listener {

    private final RPG plugin;

    @EventHandler
    public void combat(EntityDamageByEntityEvent e){
        if(Objects.equals(e.getEntity().getLocation().getWorld(), Bukkit.getWorld("world"))){
            e.setCancelled(true);
            return;
        }
        if (e.isCancelled())
            return;
        if (!(e.getEntity() instanceof Player))
            return;
        if (e.getDamage() < 0.0D) {
            return;
        }
        final Player d = Util.getDamager(e);
        if (d == null)
            return;
        final Player p = (Player) e.getEntity();
        final Combat u = CombatManager.getCombat(p);
        if (u == null)
            return;
        final Combat u1 = CombatManager.getCombat(d);
        if(u1 == null)
            return;
        final RPGUser user = plugin.getRpgUserManager().getUser(e.getEntity().getUniqueId());
        final RPGUser user1 = plugin.getRpgUserManager().getUser(d.getUniqueId());
        if (e.getEntity() instanceof Player) {
            if (user.isGod()) {
                e.setCancelled(true);
                return;
            }
        }
        sendAntiLogoutMessage(d, p, u, user);
        sendAntiLogoutMessage(p, d, u1, user1);
    }

    private void sendAntiLogoutMessage(Player d, Player p, Combat u, RPGUser user) {
        if (!u.hasFight() && user.getChatUser().isFightMessageOnChat()) {
            Util.sendMsg(p, plugin.getMessageConfig().getStartCombatMessage().replace("{SECOND}", String.valueOf(plugin.getRpgConfig().getCombatDuration())));
        }
        u.setLastAttackTime(System.currentTimeMillis() + TimeUtil.SECOND.getTime(plugin.getRpgConfig().getCombatDuration()));
        u.setLastAttackPlayer(d);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerHitByMob(EntityDamageByEntityEvent e){
        final Entity damager = getEntityDamager(e);
        if(damager != null){
            if(Objects.equals(damager.getLocation().getWorld(), Bukkit.getWorld("world"))){
                e.setCancelled(true);
                return;
            }
            if(e.getEntity() instanceof Player){
                final Player other = (Player) e.getEntity();
                final RPGUser otherUser = plugin.getRpgUserManager().getUser(other.getUniqueId());

                if(otherUser.isGod()){
                    e.setCancelled(true);
                    return;
                }
                int otherDEXTERITY = 0;
                int otherSTAMINA = 0;
                if(RPGUtil.isRPGItem(other.getInventory().getItemInMainHand())) {
                    final NBTItem otherNBTContainer = new NBTItem(other.getInventory().getItemInMainHand());
                    if (otherNBTContainer.getInteger("level") > otherUser.getLevel()) {
                        other.sendTitle(ChatUtil.hexColor("&cUWAGA!"), ChatUtil.hexColor("&4PRZEDMIOT MA WIEKSZY POZIOM NIZ TY"));
                        e.setDamage(999);
                        return;
                    }

                    otherDEXTERITY += otherNBTContainer.getInteger(Stats.DEXTERITY.getType()) + otherUser.getDEXTERITY();
                    //float otherAMD = otherNBTContainer.getFloat(AdditionalModifiers.AMD.getType()) + otherUser.getAverageMonsterDamage();
                    //float otherAHD = otherNBTContainer.getFloat(AdditionalModifiers.AHD.getType()) + otherUser.getAverageHumanDamage();
                    //float otherACD = otherNBTContainer.getFloat(AdditionalModifiers.ACD.getType()) + otherUser.getAverageCreatureDamage();
                    otherSTAMINA += otherNBTContainer.getInteger(Stats.STAMINA.getType()) + otherUser.getSTAMINA();
                }
                for (ItemStack otherArmor : other.getInventory().getArmorContents()) {
                    if (RPGUtil.isRPGItem(otherArmor)) {
                        final NBTItem armorNbt = new NBTItem(otherArmor);
                        if (armorNbt.getInteger("level") > otherUser.getLevel()) {
                            other.sendTitle(ChatUtil.hexColor("&cUWAGA!"), ChatUtil.hexColor("&4PRZEDMIOT MA WIEKSZY POZIOM NIZ TY"));
                            e.setDamage(999);
                            return;
                        }
                        //otherAMD += armorNbt.getFloat(AdditionalModifiers.AMD.getType());
                        //otherAHD += armorNbt.getFloat(AdditionalModifiers.AHD.getType());
                        //otherACD += armorNbt.getFloat(AdditionalModifiers.ACD.getType());
                        otherDEXTERITY += armorNbt.getInteger(Stats.DEXTERITY.getType());
                        otherSTAMINA += armorNbt.getInteger(Stats.STAMINA.getType());
                    }
                }
                if(RandomUtil.getChance(Math.min(50,otherDEXTERITY*0.08D))){
                    e.setDamage(0);
                }else{
                    double def = Math.min(65,otherSTAMINA*0.08);
                    if((otherSTAMINA *0.06)> e.getDamage()){
                        e.setDamage(e.getDamage()*0.05);
                    }else {
                        e.setDamage(e.getDamage() * ((100 - def) / 100));
                    }
                    if(otherSTAMINA >= 150){
                        if(damager instanceof Mob) {
                            final net.minecraft.server.v1_16_R3.Entity ce = ((CraftMob)e.getDamager()).getHandle();
                            //craftEntity.getHandle().damageEntity(DamageSource.a((net.minecraft.server.v1_16_R3.Entity) e.getEntity()),otherSTAMINA*0.20f);
                            ce.damageEntity(DamageSource.a(((CraftPlayer)other).getHandle()),otherSTAMINA*0.20f);
                        }
                    }
                }
            }
        }
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onMobHit(EntityDamageByEntityEvent e){
        final Player player = Util.getDamager(e);
        if(player != null){
            if(player.getWorld().equals(Bukkit.getWorld("world"))){
                e.setCancelled(true);
                return;
            }
            if(e.getCause().equals(EntityDamageEvent.DamageCause.MAGIC) || e.getCause().equals(EntityDamageEvent.DamageCause.THORNS)){
                return;
            }
            if(player.getInventory().getItemInMainHand().getType() == Material.AIR){
                e.setCancelled(true);
                ChatUtil.sendMessage(player,"&4Blad: &cNie mozesz uderzac bez broni!");
                return;
            }
            final ItemStack itemStack = player.getInventory().getItemInMainHand();
            if(!RPGUtil.isRPGItem(itemStack)){
                player.sendTitle(ChatUtil.fixColor("&4MUSISZ UDERZAC BRONIA"),ChatUtil.hexColor("&cUWAGA"));
                return;
            }

            final NBTItem nbtContainer = new NBTItem(itemStack);
            final RPGUser user = plugin.getRpgUserManager().getUser(player.getUniqueId());
            if(user.getRpgClass().equals(RPGClass.HUNTER)){
                if(e.getEntity().getLocation().distance(player.getLocation()) > 12){
                    e.setCancelled(true);
                    ChatUtil.sendMessage(player,"&7Strzala z twojego luku nie siega az tak daleko!");
                    return;
                }
                if(!(e.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE))){
                    e.setCancelled(true);
                    return;
                }
            }
            if(nbtContainer.getString("rpgClass") != null && !nbtContainer.getString("rpgClass").equals(user.getRpgClass().name())){
                player.sendTitle(ChatUtil.fixColor("&4PRZEDMIOT NIE MOZE ZOSTAC UZYTY"),ChatUtil.hexColor("&cNIEWLASCIWA KLASA POSTACI"));
                e.setCancelled(true);
                return;
            }
            if(nbtContainer.getInteger("level") > user.getLevel()){
                player.sendTitle(ChatUtil.hexColor("&cUWAGA!"),ChatUtil.hexColor("&4PRZEDMIOT MA WIEKSZY POZIOM NIZ TY"));
                e.setCancelled(true);
                return;
            }
            float criticalChance = nbtContainer.getFloat(AdditionalModifiers.ACH.getType()) + user.getAdditionalCriticalChance();


            float criticalDamage = nbtContainer.getFloat(AdditionalModifiers.ADOCH.getType()) + user.getAdditionalDamageOfCriticalChance();


            float AMD = nbtContainer.getFloat(AdditionalModifiers.AMD.getType()) + user.getAverageMonsterDamage();
            float AHD = nbtContainer.getFloat(AdditionalModifiers.AHD.getType()) + user.getAverageHumanDamage();
            float ACD = nbtContainer.getFloat(AdditionalModifiers.ACD.getType()) + user.getAverageCreatureDamage();
            int STRENGTH = nbtContainer.getInteger(Stats.STRENGTH.getType()) + user.getSTRENGTH();
            int DEXTERITY = nbtContainer.getInteger(Stats.DEXTERITY.getType()) + user.getDEXTERITY();

            for (ItemStack armor : player.getInventory().getArmorContents()) {
                if (RPGUtil.isRPGItem(armor)) {
                    final NBTItem armorNbt = new NBTItem(armor);
                    if(armorNbt.getInteger("level") > user.getLevel()){
                        player.sendTitle(ChatUtil.hexColor("&cUWAGA!"),ChatUtil.hexColor("&4PRZEDMIOT MA WIEKSZY POZIOM NIZ TY"));
                        e.setCancelled(true);
                        return;
                    }
                    if(!armorNbt.getString("rpgClass").equalsIgnoreCase(user.getRpgClass().name())){
                        player.sendTitle(ChatUtil.fixColor("&4PRZEDMIOT NIE MOZE ZOSTAC UZYTY"),ChatUtil.hexColor("&cNIEWLASCIWA KLASA POSTACI"));
                        e.setCancelled(true);
                        return;
                    }

                    criticalChance += armorNbt.getFloat(AdditionalModifiers.ACH.getType());
                    criticalDamage += armorNbt.getFloat(AdditionalModifiers.ADOCH.getType());

                    AMD += armorNbt.getFloat(AdditionalModifiers.AMD.getType());
                    AHD += armorNbt.getFloat(AdditionalModifiers.AHD.getType());
                    ACD += armorNbt.getFloat(AdditionalModifiers.ACD.getType());
                    STRENGTH += armorNbt.getInteger(Stats.STRENGTH.getType());
                    DEXTERITY += armorNbt.getInteger(Stats.DEXTERITY.getType());


                }
            }
            if(e.getEntity() instanceof Player){
                final Player other = (Player) e.getEntity();
                final RPGUser otherUser = plugin.getRpgUserManager().getUser(other.getUniqueId());
                if(otherUser.isGod()){
                    e.setCancelled(true);
                    return;
                }
                //GUILDS
                final Guild playerGuild = plugin.getGuildManager().getSimpleGuild(player);
                if(playerGuild != null){
                    final Guild otherPlayerGuild = plugin.getGuildManager().getSimpleGuild(other);
                    if(otherPlayerGuild != null){
                        if(playerGuild.equals(otherPlayerGuild)){
                            if(!playerGuild.getOptions().get(OptionsType.PVP)){
                                e.setDamage(0);
                                e.setCancelled(true);
                                return;
                            }
                        }
                    }
                }
                if(RPGUtil.isRPGItem(other.getInventory().getItemInMainHand())) {

                    final NBTItem otherNBTContainer = new NBTItem(other.getInventory().getItemInMainHand());
                    if(otherNBTContainer.getInteger("level") > otherUser.getLevel()){
                        other.sendTitle(ChatUtil.hexColor("&cUWAGA!"),ChatUtil.hexColor("&4PRZEDMIOT MA WIEKSZY POZIOM NIZ TY"));
                        e.setDamage(999);
                        return;
                    }

                    int otherDEXTERITY = otherNBTContainer.getInteger(Stats.DEXTERITY.getType()) + otherUser.getDEXTERITY();
                    //float otherAMD = otherNBTContainer.getFloat(AdditionalModifiers.AMD.getType()) + otherUser.getAverageMonsterDamage();
                    //float otherAHD = otherNBTContainer.getFloat(AdditionalModifiers.AHD.getType()) + otherUser.getAverageHumanDamage();
                    //float otherACD = otherNBTContainer.getFloat(AdditionalModifiers.ACD.getType()) + otherUser.getAverageCreatureDamage();
                    int otherSTAMINA = otherNBTContainer.getInteger(Stats.STAMINA.getType()) + otherUser.getSTAMINA();

                    for (ItemStack otherArmor : other.getInventory().getArmorContents()) {
                        if (RPGUtil.isRPGItem(otherArmor)) {
                            final NBTItem armorNbt = new NBTItem(otherArmor);
                            if(armorNbt.getInteger("level") > otherUser.getLevel()){
                                other.sendTitle(ChatUtil.hexColor("&cUWAGA!"),ChatUtil.hexColor("&4PRZEDMIOT MA WIEKSZY POZIOM NIZ TY"));
                                e.setDamage(999);
                                return;
                            }
                            //otherAMD += armorNbt.getFloat(AdditionalModifiers.AMD.getType());
                            //otherAHD += armorNbt.getFloat(AdditionalModifiers.AHD.getType());
                            //otherACD += armorNbt.getFloat(AdditionalModifiers.ACD.getType());
                            otherDEXTERITY += armorNbt.getInteger(Stats.DEXTERITY.getType());
                            otherSTAMINA += armorNbt.getInteger(Stats.STAMINA.getType());
                        }
                    }
                    double damage = (STRENGTH*1.2) * (1.0 + (AHD/100));
                    if(user.getRpgClass().equals(RPGClass.HUNTER)) {
                        criticalDamage = (criticalDamage + (DEXTERITY * 0.5f));
                        criticalChance += DEXTERITY*0.1f;
                        damage += (nbtContainer.getInteger("damage") * 20) + (DEXTERITY*1.3);
                    }
                    if(RandomUtil.getChance(criticalChance)){
                        damage = damage + (1.0 + (criticalDamage/100));
                    }
                    if(RandomUtil.getChance(otherDEXTERITY*0.05)){
                        e.setDamage(0);
                    }else{
                        double durability = (otherSTAMINA * 1.8);
                        e.setDamage(Math.max(0.1,e.getDamage() + damage - durability));
                    }
                }
            }else{
                double damage = (STRENGTH*1.2);
                if(user.getRpgClass().equals(RPGClass.HUNTER)) {
                    criticalDamage = (criticalDamage + (DEXTERITY * 0.5f));
                    criticalChance += DEXTERITY*0.1f;
                    damage += nbtContainer.getInteger("damage") * 20 + (DEXTERITY*1.8);
                }
                if(RandomUtil.getChance(criticalChance)){
                    damage *= (2.0 + (criticalDamage/100));
                }
                if(e.getEntity() instanceof Illager){
                    damage *= (1.0 + (ACD/100));
                    e.setDamage(damage);
                }
                if(e.getEntity() instanceof Monster){
                    damage *= (1.0 + (AMD/100));
                    e.setDamage(damage);
                }
                ActiveMob mob = MythicBukkit.inst().getMobManager().getActiveMob(e.getEntity().getUniqueId()).orElse(null);
                if(mob != null) {
                    final BossBarService service = plugin.getBossBarServiceStorage().getBossBar(player);
                    service.setMob(mob);
                }
            }
        }
    }
    public static Entity getEntityDamager(EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();
        if (!(damager instanceof Player)) {
            return damager;
        } else {
            if (damager instanceof Projectile) {
                Projectile p = (Projectile)damager;
                if (!(p.getShooter() instanceof Player)) {
                    return (Entity) p.getShooter();
                }
            }
            return null;
        }
    }
}
