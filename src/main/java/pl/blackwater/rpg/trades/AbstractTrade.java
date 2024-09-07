package pl.blackwater.rpg.trades;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
public class AbstractTrade implements Serializable {

    private String tradeLabel;
    private List<AbstractTradeRecipe> recipes;

    public AbstractTrade(){}

    public AbstractTrade(String name, List<AbstractTradeRecipe> recipes) {
        this.tradeLabel = name;
        this.recipes = recipes;
    }

}