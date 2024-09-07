package pl.blackwater.rpg.trades;

import pl.blackwater.rpg.items.AnvilMaterials;
import pl.blackwater.rpg.items.UpgradeMaterials;
import pl.blackwater.rpg.mine.enums.OreType;
import pl.blackwater.spigotplugin.util.TradeBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TraderStorage {

    private final List<Trader> traders;

    public TraderStorage(){
        this.traders = new ArrayList<>();

        /*RPG.getPlugin().getTradersConfig().getTraders().forEach((s, abstractTrade) -> {
            final NPC npc = RPG.getPlugin().getNpcManager().getNpcByName(s);
            if(npc != null){
                final TradeBuilder tradeBuilder = new TradeBuilder(abstractTrade.getTradeLabel());
                abstractTrade.getRecipes().forEach(abstractTradeRecipe -> {
                    //TODO
                    tradeBuilder.addOffer(abstractTradeRecipe.getIngredients(), abstractTradeRecipe.getResult());
                });
                npc.setAction((executor, npcName) -> executor.openMerchant(tradeBuilder.build(), false));
                this.traders.add(new Trader(npc, tradeBuilder));
            }
        });*/
        final Trader kowal = new Trader("KOWAL",new TradeBuilder("KOWAL"));
        kowal.getTradeBuilder().addOffer(Arrays.asList(AnvilMaterials.SKORA_LOWCY.getItemWithAmount(32), OreType.MIEDZI.getItemStackWithAmount(16)), UpgradeMaterials.ZWOJ_PLUS_ONE.getItem());

        this.traders.add(kowal);
    }

    public List<Trader> getTraders() {
        return traders;
    }
}