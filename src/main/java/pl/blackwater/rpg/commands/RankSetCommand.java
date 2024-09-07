package pl.blackwater.rpg.commands;

import org.bukkit.entity.Player;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.rpg.ranks.Rank;
import pl.blackwater.spigotplugin.spigot.commands.PlayerCommand;
import pl.blackwater.spigotplugin.util.ChatUtil;

public class RankSetCommand extends PlayerCommand {
    private final RPG plugin;
    public RankSetCommand(RPG plugin) {
        super("rankset", "ustawia range", "/rankset [player] [rank]", "rpg.rankset", "setrank");
        this.plugin = plugin;

    }

    @Override
    public boolean onCommand(Player player, String[] strings) {
        if(strings.length != 2){
            return ChatUtil.sendMessage(player,"&4Poprawne uzycie: &c" +  getUsage());
        }
        final RPGUser rpgUser = plugin.getRpgUserManager().getUser(strings[0]);
        if(rpgUser == null){
            return ChatUtil.sendMessage(player,"&4Blad: &cUzytkownik nie istnieje w bazie danych");
        }
        final Rank rank = plugin.getRankManager().getRank(strings[1]);
        if(rank == null){
            return ChatUtil.sendMessage(player,"&4Blad: &cRanga nie istnieje");
        }
        rpgUser.setRankName(rank.getName());

        plugin.getRankManager().implementPermissions(rpgUser);

        return ChatUtil.sendMessage(player,"&aPomyslnie ustawiles range " + rank.getName() + " dla gracza " +  rpgUser.getLastName());
    }
}
