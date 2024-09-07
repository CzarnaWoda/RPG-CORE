package pl.blackwater.rpg.commands;

import org.bukkit.entity.Player;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.items.AddItemHandler;
import pl.blackwater.rpg.items.AnvilMaterials;
import pl.blackwater.rpg.items.SpecialItem;
import pl.blackwater.rpg.items.UpgradeMaterials;
import pl.blackwater.rpg.mine.enums.OreType;
import pl.blackwater.spigotplugin.spigot.commands.PlayerCommand;
import pl.blackwater.spigotplugin.spigot.inventory.actions.InventoryGUI;
import pl.blackwater.spigotplugin.util.ChatUtil;

public class SpecialItemsCommand extends PlayerCommand {
    private final RPG plugin;
    public SpecialItemsCommand(RPG plugin) {
        super("specialitem", "specialitems", "/specialitem", "rpg.specialitem", "specialitems","si");

        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(Player player, String[] strings) {
        final InventoryGUI gui = new InventoryGUI(ChatUtil.hexColor("#3ABFF9⚔ #3AF9F2SPECIAL ITEMS #3ABFF9⚔"),6);

        for(SpecialItem specialItem : plugin.getSpecialItems().getSpecialItemStacks()){
            gui.addItem(specialItem.getItemStack(),new AddItemHandler(specialItem.getItemStack()));
        }
        for(OreType oreType : OreType.values()){
            gui.addItem(oreType.getItemStack(),new AddItemHandler(oreType.getItemStack()));
        }
        for(UpgradeMaterials upgradeMaterials : UpgradeMaterials.values()){
            gui.addItem(upgradeMaterials.getItem(),new AddItemHandler(upgradeMaterials.getItem()));
        }
        for(AnvilMaterials anvilMaterials : AnvilMaterials.values()){
            gui.addItem(anvilMaterials.getItem(),new AddItemHandler(anvilMaterials.getItem()));
        }
        gui.openInventory(player);
        return false;
    }
}
