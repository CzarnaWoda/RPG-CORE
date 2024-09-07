package pl.blackwater.rpg.commands;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.maps.ExpMap;
import pl.blackwater.spigotplugin.spigot.commands.PlayerCommand;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.Util;

public class AddExpMapCommand extends PlayerCommand {
    public AddExpMapCommand() {
        super("addexpmap", "Dodaje expowisko do bazy danych", "/addexpmap <NAME> <LEVEL>", "rpg.addexpmap", "aem");

    }

    @Override
    public boolean onCommand(Player player, String[] strings) {
        if(strings.length > 2){
            if(Util.isInteger(strings[0])) {
                final String name = StringUtils.join(strings," ",1,strings.length);
                final ExpMap expMap = new ExpMap(player.getLocation(), name , Integer.parseInt(strings[0]));

                RPG.getPlugin().getMapStorage().addExpMap(expMap);

                return ChatUtil.sendMessage(player,ChatUtil.hexColor("&aDodano pomyslnie nowe expowisko o nazwie:  " + expMap.getMapName()));
            }else{
                return ChatUtil.sendMessage(player,"&4Blad: &cArgument o indexie [1] (2 w komendzie) nie jest liczba!");
            }
        }else{
            return ChatUtil.sendMessage(player,"&4Poprawne uzycie: &c" + getUsage());
        }
    }
}
