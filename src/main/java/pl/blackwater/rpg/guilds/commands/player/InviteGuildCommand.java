package pl.blackwater.rpg.guilds.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.guilds.Guild;
import pl.blackwater.rpg.guilds.Member;
import pl.blackwater.rpg.guilds.enums.GuildPermission;
import pl.blackwater.spigotplugin.spigot.commands.PlayerCommand;
import pl.blackwater.spigotplugin.util.ChatUtil;

import java.util.Optional;

public class InviteGuildCommand extends PlayerCommand {

    private final RPG plugin;
    public InviteGuildCommand(RPG plugin) {
        super("invite", "Komenda do zapraszania gracza do gildii", "/guild invite [PLAYER]", "rpg.guilds.invite", "zapros");

        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(Player player, String[] strings) {
        if(strings.length == 1){
            final Player other = Bukkit.getPlayer(strings[0]);
            if(other == null){
                return ChatUtil.sendMessage(player,"&4Blad: &cGracz nie jest na serwerze!");
            }
            if(plugin.getGuildManager().getGuild(other).isPresent()){
                return ChatUtil.sendMessage(player,"&4Blad: &cTen gracz ma juz gildie!");
            }
            final Optional<Guild> executorGuild = plugin.getGuildManager().getGuild(player.getUniqueId());
            executorGuild.ifPresent(guild -> {
                if(guild.getMembers().size() >= guild.getPlayerLimit()){
                    ChatUtil.sendMessage(player,"&4Blad: &cTwoja gildia osiagnela maksymalna ilosc czlonkow");
                }
                if(guild.getInvites().getIfPresent(other.getUniqueId()) == null) {
                    final Optional<Member> executorMember = plugin.getGuildManager().getMember(player.getUniqueId());
                    executorMember.ifPresent(member -> {
                        if(member.getGuildPermissions().get(GuildPermission.INVITE)){
                            guild.getInvites().put(other.getUniqueId(),System.currentTimeMillis());

                            ChatUtil.sendMessage(other,ChatUtil.hexColor("#591f9cGratulacje! #9f69f0Otrzymales zaproszenie do gildii #539fe6[" + guild.getTag() + "#539fe6]"));
                            ChatUtil.sendMessage(other, ChatUtil.hexColor("#9f69f0Aby przyjac zaproszenie wpisz komende #9f69f0/gildia dolacz " +  guild.getTag()));
                            ChatUtil.sendMessage(player,ChatUtil.hexColor("#9f69f0Zaprosiles #9f69f0" +  other.getDisplayName() + " #9f69f0do swojej gildii"));
                        }else{
                            ChatUtil.sendMessage(player,"&4Blad: &cNie posiadasz uprawnien do zapraszania graczy do gildii!");
                        }
                    });
                }else{
                    ChatUtil.sendMessage(player,"&4Blad: &cTen gracz ma juz zaproszenie do gildii!");
                }
            });

        }else{
            return ChatUtil.sendMessage(player,"&4Poprawne uzycie: &c" + getUsage());
        }
        return false;
    }
}
