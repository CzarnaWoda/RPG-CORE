package pl.blackwater.rpg.listeners;

import io.lumine.mythic.api.drops.IDrop;
import io.lumine.mythic.bukkit.events.MythicDropLoadEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MythicDropLoadListener implements Listener {

    @EventHandler
    public void onMythicDropLoad(MythicDropLoadEvent e){
        final IDrop iDrop = e.getDrop().orElse(null);

    }
}
