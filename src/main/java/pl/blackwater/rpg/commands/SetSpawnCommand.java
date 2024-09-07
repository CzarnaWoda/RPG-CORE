package pl.blackwater.rpg.commands;

import io.lumine.mythic.bukkit.utils.lib.jooq.Require;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.blackwater.api.config.ConfigAPI;
import pl.blackwater.rpg.RPG;
import pl.blackwater.spigotplugin.spigot.commands.PlayerCommand;
import pl.blackwater.spigotplugin.util.ChatUtil;

public class SetSpawnCommand extends PlayerCommand {
    private final RPG plugin;
    public SetSpawnCommand(RPG plugin) {
        super("setspawn","SetSpawn Command", "/setspawn", "rpg.setspawn","ss");
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        final Location loc = player.getLocation();

        plugin.getRpgConfig().setSpawnLocation(loc);


        player.getWorld().setSpawnLocation(loc);
        return ChatUtil.sendMessage(player,"&7Ustawiles lokalizacje &6spawn");
    }
}
