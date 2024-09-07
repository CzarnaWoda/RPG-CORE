package pl.blackwater.rpg.trades;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.List;

@Getter
public class AbstractTradeRecipe implements Serializable {

    private ItemStack result;
    private List<ItemStack> ingredients;
    private int uses;
    private int maxUses;

    public AbstractTradeRecipe(){}

    public AbstractTradeRecipe(ItemStack result, List<ItemStack> ingredients, int uses, int maxUses) {
        this.result = result;
        this.ingredients = ingredients;
        this.uses = uses;
        this.maxUses = maxUses;
    }

}