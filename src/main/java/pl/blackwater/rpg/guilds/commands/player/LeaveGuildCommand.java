package pl.blackwater.rpg.guilds.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.guilds.Guild;
import pl.blackwater.rpg.npcs.tags.ScoreboardAction;
import pl.blackwater.spigotplugin.spigot.commands.PlayerCommand;
import pl.blackwater.spigotplugin.util.ChatUtil;

import java.util.Optional;

public class LeaveGuildCommand extends PlayerCommand {

    private final RPG plugin;
    public LeaveGuildCommand(RPG plugin) {
        super("leave", "Komenda do opuszczania aktualnej gildii", "/guild leave", "rpg.guild.leave", "opusc");

        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(Player player, String[] strings) {
        final Optional<Guild> optionalGuild = plugin.getGuildManager().getGuild(player);
        optionalGuild.ifPresentOrElse(guild -> {
            if(guild.getOwner().equals(player.getUniqueId())){
                ChatUtil.sendMessage(player,"&4Blad: &cNie mozesz opuscic gildii w ktorej jestes wlascicielem!");
            }else{
                guild.getMembers().removeIf(member -> member.getUuid().equals(player.getUniqueId()));
                guild.update();
                plugin.getTagManager().updateTagForPlayer(player, ScoreboardAction.UPDATE);

                final String message = plugin.getGuildConfig().getMESSAGE_GUILDLEAVE().replace("{PLAYER}",player.getDisplayName().replace("{TAG}",guild.getTag()).replace("{NAME}",guild.getName()));
                for(Player online : Bukkit.getOnlinePlayers()){
                    if(plugin.getRpgUserManager().getUser(online.getUniqueId()).getChatUser().isGuildMessageChat()){
                        ChatUtil.sendMessage(online,ChatUtil.hexColor(message));
                    }
                }
            }

        },() -> {

        });
        return false;
    }
}
