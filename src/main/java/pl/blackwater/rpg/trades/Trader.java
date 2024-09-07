package pl.blackwater.rpg.trades;

import pl.blackwater.spigotplugin.util.TradeBuilder;

public class Trader {

    public transient final String npc;
    private final TradeBuilder merchant;

    public Trader(String npc, TradeBuilder merchant) {
        this.npc = npc;
        this.merchant = merchant;
    }

    public TradeBuilder getTradeBuilder(){
        return this.merchant;
    }

    public String getNpc() {
        return npc;
    }

    public TradeBuilder getMerchant() {
        return merchant;
    }
}