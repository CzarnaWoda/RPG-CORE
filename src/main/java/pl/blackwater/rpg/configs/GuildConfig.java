package pl.blackwater.rpg.configs;

import org.bukkit.inventory.ItemStack;
import pl.blackwater.api.config.Config;
import pl.blackwater.api.config.annotation.ConfigName;

@ConfigName("guilds.yml")
public interface GuildConfig extends Config {

    default int getCreateGuildMoney(){
        return 1000;
    }

    default int getGuildJoinMoney(){return 500;}
    /*MESSAGES*/
    /*
    COLORS:
    MAIN: #9f69f0
    IMPORTANT: #591f9c
    FOR UNIQUE SIGNS: #539fe6
     */
    default String getMESSAGE_GUILDCREATE(){
        return "#9f69f0Gracz #591f9c{PLAYER} #9f69f0zalozyl gildie #539fe6[#591f9c{TAG}#539fe6] #591f9c{NAME}";
    }
    default String getMESSAGE_GUILDJOIN(){
        return "#9f69f0Gracz #591f9c{PLAYER} #9f69f0dolaczyl do gildii #539fe6[#591f9c{TAG}#539fe6] #591f9c{NAME}";
    }
    default String getMESSAGE_GUILDLEAVE(){
        return "#9f69f0Gracz #591f9c{PLAYER} #9f69f0opuscil gildie #539fe6[#591f9c{TAG}#539fe6] #591f9c{NAME}";
    }
    default String getMESSAGE_KICKPLAYER(){
        return "#9f69f0Gracz #591f9c{PLAYER} #9f69f0zostal wyrzucony z gildii #539fe6[#591f9c{TAG}#539fe6] #591f9c{NAME} przez {EXECUTOR}";
    }
    default String getMESSAGE_DELETEGUILD(){
        return "#9f69f0Gracz #591f9c{PLAYER} #9f69f0usunal gildie #539fe6[#591f9c{TAG}#539fe6] #591f9c{NAME}";
    }

}
