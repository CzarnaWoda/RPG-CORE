package pl.blackwater.rpg.guilds.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.ChatUser;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.rpg.guilds.Guild;
import pl.blackwater.rpg.guilds.Member;
import pl.blackwater.rpg.guilds.enums.MemberType;
import pl.blackwater.rpg.npcs.tags.ScoreboardAction;
import pl.blackwater.rpg.util.StringUtil;
import pl.blackwater.spigotplugin.spigot.commands.PlayerCommand;
import pl.blackwater.spigotplugin.util.ChatUtil;

import java.util.Optional;

public class JoinGuildCommand extends PlayerCommand {

    private final RPG plugin;
    public JoinGuildCommand(RPG plugin) {
        super("join", "Komenda dolaczania do gildii", "/guild join [TAG]", "rpg.guild.join", "dolacz");

        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(Player player, String[] strings) {
        if(strings.length == 1){
            if(plugin.getGuildManager().getGuild(player).isPresent()){
                return ChatUtil.sendMessage(player,"&4Blad: &cPosiadasz juz gildie!");
            }
            final String guildTag = strings[0];
            if(StringUtil.isAlphanumericBigLetters(guildTag)){
                final Optional<Guild> guild = plugin.getGuildManager().getGuild(guildTag);
                guild.ifPresentOrElse(joinGuild -> {
                    if(joinGuild.getMembers().size() < joinGuild.getPlayerLimit()) {
                        if (joinGuild.getInvites().getIfPresent(player.getUniqueId()) != null) {
                            final RPGUser user = plugin.getRpgUserManager().getUser(player.getUniqueId());
                            if (user.getMoney() >= plugin.getGuildConfig().getGuildJoinMoney()) {
                                //JOIN ACTION
                                user.removeMoney(plugin.getGuildConfig().getGuildJoinMoney());
                                joinGuild.getMembers().add(new Member(player.getUniqueId(), MemberType.MEMBER));
                                plugin.getTagManager().updateTagForPlayer(player, ScoreboardAction.UPDATE);

                                joinGuild.update();

                                final String message = plugin.getGuildConfig().getMESSAGE_GUILDJOIN().replace("{PLAYER}", player.getDisplayName()).replace("{TAG}", guildTag).replace("{NAME}", joinGuild.getName());

                                for (Player online : Bukkit.getOnlinePlayers()) {
                                    final ChatUser cu = plugin.getRpgUserManager().getUser(online.getUniqueId()).getChatUser();
                                    if (cu.isGuildMessageChat()) {
                                        ChatUtil.sendMessage(online, ChatUtil.hexColor(message));
                                    }
                                }
                            }
                        }
                    }else{
                        ChatUtil.sendMessage(player,"&4Blad: &cGildia osiagnela maksymalna ilosc czlonkow!");
                    }
                },() -> ChatUtil.sendMessage(player,"&4Blad: &cGildia o takim tagu nie istnieje!"));
            }else{
                return ChatUtil.sendMessage(player,"&4Blad: &cPodales zla nazwe gildii!");
            }
        }else{
            return ChatUtil.sendMessage(player,"&4Poprawne uzycie: &c" +  getUsage());
        }
        return false;
    }
}
