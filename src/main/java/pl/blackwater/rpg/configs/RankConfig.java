package pl.blackwater.rpg.configs;

import pl.blackwater.api.config.Config;
import pl.blackwater.api.config.annotation.ConfigName;


@ConfigName("ranksconfig.yml")
public interface RankConfig extends Config {

    default String defaultRank(){
        return "GRACZ";
    }

}
