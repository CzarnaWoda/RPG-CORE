package pl.blackwater.rpg.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.blackwater.spigotplugin.spigot.commands.Command;
import pl.blackwater.spigotplugin.util.ChatUtil;

public class ClearEntityCommand extends Command {
    public ClearEntityCommand() {
        super("clearentity", "Clear entity", "/clearentity", "rpg.admin", "ce");
    }

    @Override
    public boolean onExecute(CommandSender commandSender, String[] strings) {
        for(World world : Bukkit.getWorlds()){
            for(Entity entity : world.getEntities()){
                if(entity instanceof Player){
                    continue;
                }
                entity.remove();
            }
        }
        ChatUtil.sendMessage(commandSender,"&4WYCZYSZCZONO ENTITY NA SWIATACH!");
        return false;
    }
}
