package pl.blackwater.rpg.commands;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.ChatUser;
import pl.blackwater.rpg.data.ChatUserOptions;
import pl.blackwater.spigotplugin.spigot.commands.PlayerCommand;
import pl.blackwater.spigotplugin.util.ChatUtil;

public class BroadcastCommand extends PlayerCommand {

    private final RPG plugin;
    public BroadcastCommand(RPG plugin) {
        super("broadcast", "Komenda do ogloszen", "/broadcast <message>", "rpg.broadcast", "bc");
        this.plugin = plugin;

    }

    @Override
    public boolean onCommand(Player player, String[] strings) {

        if(strings.length <  1){
            return ChatUtil.sendMessage(player,"&4Poprawne uzycie: &c" + getUsage());
            //bc 3 3 3 3 3 3
        }

        final ChatUser user = plugin.getRpgUserManager().getUser(player.getUniqueId()).getChatUser();
        final String message = StringUtils.join(strings," ");
        for(Player online : Bukkit.getOnlinePlayers()){
            if(user.isBroadcastMessageChat()) {
                ChatUtil.sendMessage(online, message);
            }
        }
        return false;
    }
}
