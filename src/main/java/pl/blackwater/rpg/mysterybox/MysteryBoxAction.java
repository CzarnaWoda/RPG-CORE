package pl.blackwater.rpg.mysterybox;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public interface MysteryBoxAction {

    void execute(Player player, MysteryBoxItem[] boxDropItems, PlayerInteractEvent e);
}
