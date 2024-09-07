package pl.blackwater.rpg.guilds.commands.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.rpg.guilds.Guild;
import pl.blackwater.rpg.npcs.tags.ScoreboardAction;
import pl.blackwater.rpg.util.StringUtil;
import pl.blackwater.spigotplugin.spigot.commands.PlayerCommand;
import pl.blackwater.spigotplugin.util.ChatUtil;

import java.util.Optional;

public class CreateGuildCommand extends PlayerCommand {
    private final RPG plugin;
    public CreateGuildCommand(RPG plugin) {
        super("create", "Komenda ktora zaklada nowa gildie na serwerze", "/guild create [TAG] [NAZWA]", "rpg.guild.create", "zaloz");
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if(args.length != 2){
            return ChatUtil.sendMessage(player,"&4Poprawne uzycie: &c" +  getUsage());
        }
        final String TAG = args[0];
        final String NAME = args[1];

        if(TAG.length() < 2 || TAG.length() > 4 || NAME.length() < 8 || NAME.length() > 32){
            return ChatUtil.sendMessage(player,"&cBlad: &4Tag musi miec od 2 do 4 znakow, nazwa od 8 do 32 znakow");
        }
        if(!StringUtil.isAlphanumeric(NAME) || !StringUtil.isAlphanumericBigLetters(TAG)){
            return ChatUtil.sendMessage(player,"&cBlad: &4Tag moze zawierac tylko duze litery oraz tag i nazwa nie moga zawierac znakow specjalnych!");
        }
        final Optional<Guild> guild = plugin.getGuildManager().getGuild(TAG);
        guild.ifPresentOrElse(guild1 -> ChatUtil.sendMessage(player,"&4Blad: &cGildia o takim TAGU już istnieje"),() -> plugin.getGuildManager().getGuild(NAME).ifPresentOrElse(guild1 -> ChatUtil.sendMessage(player,"&4Blad: &cGildia o takiej NAZWIE juz istnieje"),() -> {
            final RPGUser user = plugin.getRpgUserManager().getUser(player.getUniqueId());

            if(user.getMoney() >= plugin.getGuildConfig().getCreateGuildMoney()){
                user.removeMoney(plugin.getGuildConfig().getCreateGuildMoney());

                plugin.getGuildManager().createGuild(player.getUniqueId(),TAG,NAME);

                for(Player online : Bukkit.getOnlinePlayers()){
                    if(plugin.getRpgUserManager().getUser(online.getUniqueId()).getChatUser().isGuildMessageChat()) {
                        ChatUtil.sendMessage(online, ChatUtil.hexColor(plugin.getGuildConfig().getMESSAGE_GUILDCREATE().replace("{PLAYER}",player.getDisplayName()).replace("{TAG}",TAG).replace("{NAME}",NAME)));
                    }
                }
                plugin.getTagManager().updateTagForPlayer(player, ScoreboardAction.UPDATE);
            }else{
                ChatUtil.sendMessage(player,"&4Blad: &cNie posiadasz wystarczajacej ilosci pieniedzy aby zalozyc gildie");
            }
        }));

        return false;
    }
}
