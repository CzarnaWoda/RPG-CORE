package pl.blackwater.rpg.guilds.commands.player;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.guilds.Member;
import pl.blackwater.rpg.npcs.tags.ScoreboardAction;
import pl.blackwater.spigotplugin.spigot.commands.PlayerCommand;
import pl.blackwater.spigotplugin.util.ChatUtil;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class DeleteGuildCommand extends PlayerCommand {

    private final RPG plugin;
    private final Cache<UUID,Long> status;
    public DeleteGuildCommand(RPG plugin) {
        super("delete", "Komenda do usuniecia gildii", "/guild delete", "rpg.guilds.delete", "usun");

        this.plugin = plugin;
        this.status = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS).build();
    }

    @Override
    public boolean onCommand(Player player, String[] strings) {
        plugin.getGuildManager().getGuild(player).ifPresentOrElse(guild -> {
            if(guild.getOwner().equals(player.getUniqueId())){
                if(status.getIfPresent(player.getUniqueId()) != null){
                    final List<Member> members = guild.getMembers();

                    plugin.getGuildManager().deleteGuild(guild);

                    members.forEach(member -> {
                        final Player memberPlayer = Bukkit.getPlayer(member.getUuid());
                        if(memberPlayer != null){
                            plugin.getTagManager().updateTagForPlayer(memberPlayer, ScoreboardAction.UPDATE);
                        }
                    });
                    final String message = plugin.getGuildConfig().getMESSAGE_DELETEGUILD().replace("{PLAYER}",player.getDisplayName()).replace("{NAME}",guild.getName()).replace("{TAG}",guild.getTag());

                    for(Player online : Bukkit.getOnlinePlayers()){
                        if(plugin.getRpgUserManager().getUser(online.getUniqueId()).getChatUser().isGuildMessageChat()){
                            ChatUtil.sendMessage(online,ChatUtil.hexColor(message));
                        }
                    }
                }else{
                    status.put(player.getUniqueId(),System.currentTimeMillis());
                    ChatUtil.sendMessage(player,"&4Aby usunac gildie wpisz jeszcze raz &c/guild delete. &4PAMIETAJ! &cGdy usuniesz gildie nie bedzie juz powrotu!");
                }
            }else{
                ChatUtil.sendMessage(player,"&4Blad: &cTylko wlasciciel moze usunac gildie");
            }
        },() -> ChatUtil.sendMessage(player,"&4Blad:&cNie posiadasz gildii"));
        return false;
    }
}
