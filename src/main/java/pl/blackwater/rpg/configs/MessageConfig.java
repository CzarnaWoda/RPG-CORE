package pl.blackwater.rpg.configs;

import org.bukkit.ChatColor;
import pl.blackwater.api.config.Config;
import pl.blackwater.api.config.annotation.ConfigName;


@ConfigName("message.yml")
public interface MessageConfig extends Config {

    default String getUnknownPlayerMessage(){
        return "&4Blad: &cGracz nie jest online!";
    }
    default String getUnknownUserMessage(){
        return "&4Blad: &cTakiego gracza nie bylo na serwerze!";
    }
    default String getCommandUsageMessage(){
        return "&4Blad: &cPoprawne uzycie &8(&4{USAGE}&8)";
    }

    default String getStartCombatMessage(){
        return "&4♰ &cRozpoczeto walke, nie mozesz sie wylogowac przez {SECOND} sekund &4♰";
    }
    default ChatColor getWarningColor(){
        return ChatColor.DARK_RED;
    }
    default ChatColor getWarningColor2(){
        return ChatColor.RED;
    }
    default ChatColor getSpecialSignsColor(){
        return ChatColor.DARK_GRAY;
    }
    default ChatColor getMainColor(){
        return ChatColor.GRAY;
    }
    default ChatColor getImportantColor(){
        return ChatColor.GOLD;
    }
}
