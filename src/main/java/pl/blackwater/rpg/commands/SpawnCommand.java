package pl.blackwater.rpg.commands;

import org.bukkit.entity.Player;
import pl.blackwater.rpg.RPG;
import pl.blackwater.spigotplugin.spigot.commands.PlayerCommand;
import pl.blackwater.spigotplugin.spigot.timer.TimerUtil;
import pl.blackwater.spigotplugin.util.ChatUtil;

public class SpawnCommand extends PlayerCommand {
    public SpawnCommand() {
        super("spawn", "Spawn Command", "/spawn", "rpg.spawn");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {

        //TODO combat

        TimerUtil.teleport(player, RPG.getPlugin().getRpgConfig().getSpawnLocation(),5);

        return true;
    }
}
