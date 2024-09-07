package pl.blackwater.rpg.guilds.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.guilds.Member;
import pl.blackwater.rpg.guilds.enums.GuildPermission;
import pl.blackwater.rpg.guilds.enums.OptionsType;
import pl.blackwater.spigotplugin.spigot.commands.PlayerCommand;
import pl.blackwater.spigotplugin.util.ChatUtil;

public class PvPGuildCommand extends PlayerCommand {

    private final RPG plugin;
    public PvPGuildCommand(RPG plugin) {
        super("pvp", "Komenda do zarzadzania pvp w gildii", "/guild pvp", "rpg.guilds.pvp", "p");

        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(Player player, String[] strings) {
        plugin.getGuildManager().getGuild(player).ifPresentOrElse(guild -> {
            if(guild.getOwner().equals(player.getUniqueId()) || plugin.getGuildManager().getMember(player).get().getGuildPermissions().get(GuildPermission.PVP)){
                guild.getOptions().put(OptionsType.PVP,!guild.getOptions().get(OptionsType.PVP));
                    /*
    COLORS:
    MAIN: #9f69f0
    IMPORTANT: #591f9c
    FOR UNIQUE SIGNS: #539fe6
     */
                ChatUtil.sendMessage(player,ChatUtil.hexColor("#591f9cPvP#9f69f0 w gildii zostalo " + (guild.getOptions().get(OptionsType.PVP) ? "&awlaczone" : "&cwylaczone")));

                for(Member member : guild.getMembers()){
                    final Player memberPlayer = Bukkit.getPlayer(member.getUuid());
                    if(memberPlayer != null){
                        ChatUtil.sendMessage(memberPlayer,ChatUtil.hexColor("#591f9cPvP#9f69f0 w gildii zostalo " + (guild.getOptions().get(OptionsType.PVP) ? "&awlaczone" : "&cwylaczone") + " #9f69f0przez #591f9c" + player.getDisplayName()));
                    }
                }
            }else{
                ChatUtil.sendMessage(player,"&4Blad: &cNie posiadasz uprawnien do zmieniania statusu PVP w gildii");
            }
        },() -> ChatUtil.sendMessage(player,"&4Blad: &cNie posiadasz gildii"));
        return false;
    }
}
