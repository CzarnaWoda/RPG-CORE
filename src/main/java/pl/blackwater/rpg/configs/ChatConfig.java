package pl.blackwater.rpg.configs;

import pl.blackwater.api.config.Config;
import pl.blackwater.api.config.annotation.ConfigName;


@ConfigName("chatconfig.yml")
public interface ChatConfig extends Config {

    default String getGlobalFormat(){
        return "&8~&d{LEVEL} {TAG}{PREFIX}{PLAYER}&8: &7{SUFFIX}{MESSAGE}";
    }
    default String getAdminGlobalFormat(){
        return "{TAG}{PREFIX}{PLAYER}&8: {SUFFIX}{MESSAGE}";
    }
    default String getGuildFormat(){
        return "&8[&7{TAG}&8]";
    }
}
