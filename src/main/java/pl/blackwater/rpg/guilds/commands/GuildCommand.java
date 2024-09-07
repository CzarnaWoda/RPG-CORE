package pl.blackwater.rpg.guilds.commands;

import lombok.Getter;
import org.bukkit.entity.Player;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.guilds.commands.player.*;
import pl.blackwater.spigotplugin.spigot.commands.PlayerCommand;
import pl.blackwater.spigotplugin.util.ChatUtil;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;

public class GuildCommand extends PlayerCommand{

    @Getter private final LinkedHashSet<PlayerCommand> subCommands = new LinkedHashSet<>();

    public GuildCommand(RPG plugin){
        super("guild","Glowna komenda systemu gildii","/guild <subkomenda>" ,"rpg.guilds","g","gildia","gildie","klan");

        getSubCommands().add(new CreateGuildCommand(plugin));
        getSubCommands().add(new JoinGuildCommand(plugin));
        getSubCommands().add(new LeaveGuildCommand(plugin));
        getSubCommands().add(new KickGuildCommand(plugin));
        getSubCommands().add(new InviteGuildCommand(plugin));
        getSubCommands().add(new DeleteGuildCommand(plugin));
        getSubCommands().add(new CheckGuildCommand(plugin));
        getSubCommands().add(new ManageGuildCommand(plugin));
        getSubCommands().add(new PvPGuildCommand(plugin));

    }


    @Override
    public boolean onCommand(Player player, String[] strings) {
        if(strings.length == 0){
            return sendHelp(player);
        }
        String subCommand = strings[0];
        PlayerCommand command = getSubCommand(subCommand);
        return (command == null) ? sendHelp(player) : (player.hasPermission(Objects.requireNonNull(command.getPermission())) ? command.onCommand(player, Arrays.copyOfRange(strings, 1, strings.length)) : ChatUtil.sendMessage(player,  "&4Blad: " +  "&cNie posiadasz uprawnien do tej komendy!"));
    }

    private boolean sendHelp(Player player){
        ChatUtil.sendMessage(player,ChatUtil.hexColor("#539fe6&l★ #9f69f0KOMENDY SYSTEMU GILDII #539fe6&l★"));
        ChatUtil.sendMessage(player,"");
        for (PlayerCommand command : getSubCommands()){
            ChatUtil.sendMessage(player,ChatUtil.hexColor("  #539fe6♯ #591f9c" + command.getUsage() + " #539fe6&l★ #9f69f0" + command.getDescription()));
        }
        ChatUtil.sendMessage(player,"");
        ChatUtil.sendMessage(player,ChatUtil.hexColor("#539fe6&l★ #9f69f0KOMENDY SYSTEMU GILDII #539fe6&l★"));

        return true;
    }
    private PlayerCommand getSubCommand(String subCommand){
        for(PlayerCommand command : getSubCommands()){
            if(command.getName().equalsIgnoreCase(subCommand) || command.getAliases().contains(subCommand)){
                return command;
            }
        }
        return null;
    }
}
