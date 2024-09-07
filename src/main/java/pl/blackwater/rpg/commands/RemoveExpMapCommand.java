package pl.blackwater.rpg.commands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.maps.ExpMap;
import pl.blackwater.spigotplugin.spigot.commands.PlayerCommand;
import pl.blackwater.spigotplugin.spigot.inventory.actions.InventoryGUI;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.ItemBuilder;

public class RemoveExpMapCommand extends PlayerCommand {
    public RemoveExpMapCommand() {
        super("removeexpmap", "Komenda do usuwania expowiska z bazy danych", "/removeexpmap", "rpg.removeexpmap", "rem");
    }

    @Override
    public boolean onCommand(Player player, String[] strings) {

        final InventoryGUI mapGui = new InventoryGUI(ChatUtil.hexColor("&6USUWANIE MAP"),5);

        for(ExpMap expMap : RPG.getPlugin().getMapStorage().getExpMaps()){
            final ItemBuilder map = new ItemBuilder(Material.PAPER).setTitle(ChatUtil.hexColor(expMap.getMapName()));

            mapGui.addItem(map.build(),(player1, inventory, i, itemStack, clickType) -> {
                mapGui.getInventory().remove(itemStack);

                RPG.getPlugin().getMapStorage().removeExpMap(expMap);

                ChatUtil.sendMessage(player1,ChatUtil.hexColor("&aPomyslnie usunales mape &e" + expMap.getMapName()));
            });
        }
        mapGui.openInventory(player);

        return false;
    }
}
