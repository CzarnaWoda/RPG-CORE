package pl.blackwater.rpg.listeners;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.spigotplugin.util.ChatUtil;

@RequiredArgsConstructor
public class StorageInventoryListener implements Listener {

    private final RPG plugin;
    @EventHandler
    public void onCloseInventory(InventoryCloseEvent e){
        if(e.getView().getTitle().contains(ChatUtil.hexColor("#4d1163⛏ #bd08d1MAGAZYN #"))){
            final Player player = (Player) e.getPlayer();
            final RPGUser user = plugin.getRpgUserManager().getUser(e.getPlayer().getUniqueId());

            final int index = Integer.parseInt(e.getView().getTitle().replaceAll(ChatUtil.hexColor("#4d1163⛏ #bd08d1MAGAZYN #"),""));

            user.getStorages().set(index-1,e.getView().getTopInventory().getContents());

            ChatUtil.sendMessage(player,ChatUtil.hexColor("#4d1163⛏ #bd08d1ZAPISANO MAGAZYN &8(" + "#4d1163" + index + "&8) #4d1163⛏"));


        }
    }
}
