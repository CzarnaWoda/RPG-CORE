package pl.blackwater.rpg.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import pl.blackwater.rpg.RPG;

public class WeatherChangeListener implements Listener {


    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e){
        e.setCancelled(e.toWeatherState());
    }
}
