package pl.blackwater.rpg.commands;

import org.bukkit.entity.Player;
import pl.blackwater.rpg.items.CustomItemInventory;
import pl.blackwater.spigotplugin.spigot.commands.PlayerCommand;

public class ItemCrateCommand extends PlayerCommand {
    public ItemCrateCommand() {
        super("itemcreate", "ItemCreator", "/itemcreate", "rpg.itemcreate", "itemc");
    }

    @Override
    public boolean onCommand(Player player, String[] strings) {

        CustomItemInventory.open(player);
        return false;
    }
}
