package pl.blackwater.rpg.guilds.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.rpg.guilds.Guild;
import pl.blackwater.rpg.guilds.Member;
import pl.blackwater.rpg.guilds.enums.GuildPermission;
import pl.blackwater.rpg.npcs.tags.ScoreboardAction;
import pl.blackwater.spigotplugin.spigot.commands.PlayerCommand;
import pl.blackwater.spigotplugin.util.ChatUtil;

import java.util.Optional;

public class KickGuildCommand extends PlayerCommand {

    private final RPG plugin;
    public KickGuildCommand(RPG plugin) {
        super("kick", "Komenda do wyrzucania ludzi z gildii", "/guild kick [MEMBER]", "rpg.guilds.kick", "wyrzuc");

        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(Player player, String[] strings) {
        if(strings.length == 1) {
            final Optional<Guild> executorGuild = plugin.getGuildManager().getGuild(player);
            if (executorGuild.isPresent()) {
                final RPGUser otherUser = plugin.getRpgUserManager().getUser(strings[0]);
                if (otherUser == null) {
                    return ChatUtil.sendMessage(player, "&4Blad: &cNie ma takiego uzytkownika w bazie danych!");
                }
                final Optional<Guild> userGuild = plugin.getGuildManager().getGuild(otherUser);
                userGuild.ifPresentOrElse(guild -> {
                    if(executorGuild.get().equals(guild)) {
                        if (guild.getOwner().equals(otherUser.getUuid())) {
                            ChatUtil.sendMessage(player, "&4Blad: &cNie mozesz wyrzucic wlasciciela!");
                        } else if (otherUser.getUuid().equals(player.getUniqueId())) {
                            ChatUtil.sendMessage(player, "&4Blad: &cNie mozesz wyrzucic sam siebie");
                        }
                        final Optional<Member> otherUserMember = plugin.getGuildManager().getMember(otherUser.getUuid());
                        final Optional<Member> userMember = plugin.getGuildManager().getMember(player.getUniqueId());
                        userMember.ifPresent(value -> otherUserMember.ifPresent(member -> {
                            if (value.getGuildPermissions().get(GuildPermission.KICK)) {
                                guild.getMembers().remove(member);

                                guild.update();

                                final String message = plugin.getGuildConfig().getMESSAGE_KICKPLAYER().replace("{PLAYER}",otherUser.getLastName()).replace("{TAG}",guild.getTag()).replace("{NAME}",guild.getName()).replace("{EXECUTOR}",player.getDisplayName());
                                for(Player online : Bukkit.getOnlinePlayers()){
                                    if(plugin.getRpgUserManager().getUser(online.getUniqueId()).getChatUser().isGuildMessageChat()){
                                        ChatUtil.sendMessage(online,ChatUtil.hexColor(message));
                                    }
                                }
                                final Player otherPlayer = Bukkit.getPlayer(otherUser.getUuid());
                                if(otherPlayer != null) {
                                    plugin.getTagManager().updateTagForPlayer(otherPlayer, ScoreboardAction.UPDATE);
                                }
                            } else {
                                ChatUtil.sendMessage(player, "&4Blad: &cNie masz uprawnien do wyrzucenia gracza z gildii!");
                            }
                        }));
                    }else{
                        ChatUtil.sendMessage(player, "&4Blad: &cTen gracz nie znajduje sie w twojej gildii!");
                    }
                },() -> {});
            }else{
                ChatUtil.sendMessage(player,"&4Blad: &cNie posiadasz gildii!");
            }
        }
        return false;
    }
}
