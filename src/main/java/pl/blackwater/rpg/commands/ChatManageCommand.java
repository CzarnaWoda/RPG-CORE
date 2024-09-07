package pl.blackwater.rpg.commands;

import org.bukkit.entity.Player;
import pl.blackwater.rpg.RPG;
import pl.blackwater.spigotplugin.spigot.commands.PlayerCommand;
import pl.blackwater.spigotplugin.util.ChatUtil;

public class ChatManageCommand extends PlayerCommand {
    private final RPG plugin;
    public ChatManageCommand(RPG plugin) {
        super("chatmanage", "Komenda do zarzadzania chatem", "/chatmanage [clear/off/on/premiumon/premiumoff]", "rpg.chatmanage", "cm");
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if(args.length != 1){
            return ChatUtil.sendMessage(player,"&4Poprawne uzycie: &c" + getUsage());
        }

        return false;
    }
}
