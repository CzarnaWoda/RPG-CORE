package pl.blackwater.rpg.listeners;

import lombok.RequiredArgsConstructor;
import net.minecraft.server.v1_16_R3.EntityArrow;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.commands.OreManagerCommand;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.rpg.data.enums.RPGClass;
import pl.blackwater.rpg.mine.OreLocation;
import pl.blackwater.rpg.util.RPGUtil;
import pl.blackwater.spigotplugin.util.ChatUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


@RequiredArgsConstructor
public class PlayerInteractListener implements Listener {

    private final RPG plugin;

    private final Map<Player,Long> times = new HashMap<>();
    @EventHandler
    public void onShootArrow(PlayerInteractEvent e){

        final RPGUser user = plugin.getRpgUserManager().getUser(e.getPlayer().getUniqueId());
        //DEBUG TEST

        if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if(e.getItem() != null && e.getItem().getType() == Material.BOW){
                e.setCancelled(true);
                if(user.getRpgClass().equals(RPGClass.HUNTER)){
                    long time = 300L;
                    if(RPGUtil.isRPGItem(e.getItem())) {
                        if (Objects.requireNonNull(e.getItem().getItemMeta()).getAttributeModifiers(Attribute.GENERIC_ATTACK_SPEED) != null) {
                            for (AttributeModifier attributeModifier : Objects.requireNonNull(Objects.requireNonNull(e.getItem().getItemMeta()).getAttributeModifiers(Attribute.GENERIC_ATTACK_SPEED))) {
                                if (attributeModifier.getName().equals("generic.attack.speed")) {
                                    time -= attributeModifier.getAmount() * 15L;
                                }
                            }
                        }
                        if (times.get(e.getPlayer()) != null && System.currentTimeMillis() - times.get(e.getPlayer()) < time) {
                            return;
                        }
                        final Arrow arrow = e.getPlayer().launchProjectile(Arrow.class);
                        arrow.setVelocity(e.getPlayer().getLocation().getDirection().multiply(5));
                        EntityArrow entityArrow = ((CraftArrow) arrow).getHandle();
                        entityArrow.fromPlayer = EntityArrow.PickupStatus.DISALLOWED;
                        entityArrow.despawnCounter = 400;
                        //entityArrow.setShooter(((CraftPlayer)e.getPlayer()).getHandle());
                        entityArrow.attachedToPlayer = false;
                        times.put(e.getPlayer(), System.currentTimeMillis());
                    }
                }
            }
        }
    }
    @EventHandler
    public void onAnvilUser(PlayerInteractEvent e){
        if(e.isCancelled()) return;
        if(e.getClickedBlock() != null && e.getClickedBlock().getType().equals(Material.ANVIL)){
            e.setCancelled(true);

            //TODO ANVIL INV
        }
        if(OreManagerCommand.getInteractPlayers().contains(e.getPlayer())){
            final Player player = e.getPlayer();

            if(e.getAction().equals(Action.LEFT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
                if(e.getClickedBlock() != null) {
                    OreManagerCommand.getInteractPlayers().remove(player);

                    plugin.getOreStorage().addOreLocation(new OreLocation(UUID.randomUUID(),e.getClickedBlock().getLocation()));

                    ChatUtil.sendMessage(player,"&8->> &7Dodano &3NOWA LOKALIZACJE &7rudy!");
                }
            }
        }
    }
}
