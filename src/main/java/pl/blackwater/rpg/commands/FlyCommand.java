package pl.blackwater.rpg.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.spigotplugin.spigot.commands.PlayerCommand;
import pl.blackwater.spigotplugin.util.ChatUtil;

public class FlyCommand extends PlayerCommand {
    private final RPG plugin;
    public FlyCommand(RPG plugin) {
        super("fly", "Toggle fly for player", "/fly [player]", "rpg.fly", "f");
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(Player player, String[] args) {
        if(args.length > 0){
            final RPGUser otherUser = plugin.getRpgUserManager().getUser(args[0]);

            if(otherUser == null){
                return ChatUtil.sendMessage(player,"&4Blad: &cTaki uzytkownik nie istnieje na serwerze!");
            }
            otherUser.setFly(!otherUser.isFly());

            final Player otherPlayer = Bukkit.getPlayer(args[0]);

            ChatUtil.sendMessage(player,"&7Zmieniles status fly dla gracza &a" + otherUser.getLastName() + " &7na " + (otherUser.isGod() ? "&2wlaczony" : "&4wylaczony"));
            if(otherPlayer != null){
                otherPlayer.setFlying(otherUser.isFly());
                otherPlayer.setAllowFlight(otherUser.isFly());
                return ChatUtil.sendMessage(otherPlayer,"&7Gracz &a" + player.getDisplayName() + "&7 zmienil ci status fly na "  + (otherUser.isGod() ? "&2wlaczony" : "&4wylaczony"));
            }
        }else {
            final RPGUser rpgUser = plugin.getRpgUserManager().getUser(player.getUniqueId());

            rpgUser.setFly(!rpgUser.isFly());
            player.setAllowFlight(rpgUser.isFly());
            player.setFlying(rpgUser.isFly());

            return ChatUtil.sendMessage(player,"&7Zmieniles swoj status fly na "  + (rpgUser.isGod() ? "&awlaczony" : "&4wylaczony"));
        }
        return false;
    }
}
