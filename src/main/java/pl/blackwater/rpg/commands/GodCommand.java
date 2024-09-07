package pl.blackwater.rpg.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.spigotplugin.spigot.commands.PlayerCommand;
import pl.blackwater.spigotplugin.util.ChatUtil;

public class GodCommand extends PlayerCommand {


    private final RPG plugin;
    public GodCommand(RPG plugin) {
        super("god", "Toggle god on admin", "/god [player]", "rpg.god", "godd");
        this.plugin  = plugin;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if(args.length > 0){
            final RPGUser otherUser = plugin.getRpgUserManager().getUser(args[0]);

            if(otherUser == null){
                return ChatUtil.sendMessage(player,"&4Blad: &cTaki uzytkownik nie istnieje na serwerze!");
            }
            otherUser.setGod(!otherUser.isGod());

            final Player otherPlayer = Bukkit.getPlayer(args[0]);

            ChatUtil.sendMessage(player,"&7Zmieniles status god dla gracza &a" + otherUser.getLastName() + " &7na " + (otherUser.isGod() ? "&2wlaczony" : "&4wylaczony"));
            if(otherPlayer != null){

                return ChatUtil.sendMessage(otherPlayer,"&7Gracz &a" + player.getDisplayName() + "&7 zmienil ci status go na "  + (otherUser.isGod() ? "&2wlaczony" : "&4wylaczony"));
            }
        }else {
            final RPGUser rpgUser = plugin.getRpgUserManager().getUser(player.getUniqueId());

            rpgUser.setGod(!rpgUser.isGod());

            return ChatUtil.sendMessage(player,"&7Zmieniles swoj status god na "  + (rpgUser.isGod() ? "&awlaczony" : "&4wylaczony"));
        }
        return false;
    }
}
