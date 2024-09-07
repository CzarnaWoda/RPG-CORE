package pl.blackwater.rpg.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.mine.OreLocation;
import pl.blackwater.spigotplugin.spigot.commands.PlayerCommand;
import pl.blackwater.spigotplugin.spigot.inventory.actions.InventoryGUI;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.ItemBuilder;

import java.util.ArrayList;
import java.util.List;


public class OreManagerCommand extends PlayerCommand {

    private static final List<Player> interactPlayers = new ArrayList<>();
    private final RPG plugin;
    public OreManagerCommand(RPG plugin) {
        super("oremanage", "Zarzadzanie rudami", "/oremanage", "rpg.oremanage", "om");

        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(Player player, String[] strings) {
        
        final InventoryGUI gui = new InventoryGUI(ChatUtil.hexColor("#6b3debZARZADZANIE RUDAMI"),1);

        final ItemBuilder createLocation = new ItemBuilder(Material.GREEN_DYE).setTitle(ChatUtil.hexColor("#8e57faSTWORZ LOKALIZACJE RUDY"));

        gui.addItem(createLocation.build(),(player1, inventory, i, itemStack, clickType) -> {
            interactPlayers.add(player1);

            ChatUtil.sendMessage(player1,"&8->> &7Kliknij na blok aby stworzyc &2LOKALIZACJE RUDY");

            player1.closeInventory();
        });

        final ItemBuilder listLocation = new ItemBuilder(Material.BOOK).setTitle(ChatUtil.hexColor("#8e57faLOKALIZACJE RUD"));

        gui.addItem(listLocation.build(),(player1, inventory, i, itemStack, clickType) -> {
            final InventoryGUI bottomGui = new InventoryGUI(ChatUtil.hexColor("#8e57faLOKALIZACJE RUD"),5);

            for(OreLocation ore : plugin.getOreStorage().getOreLocationList()){
                final ItemBuilder a = new ItemBuilder(Material.PAPER).setTitle(ChatUtil.hexColor("#421599LOKALIZACJA RUDY"))
                        .setTitle(ChatUtil.hexColor("#773bebLOKALIZACJA: " + ore.getLocation().getX() + ", " + ore.getLocation().getY() + ", " + ore.getLocation().getZ()));
                bottomGui.addItem(a.build(),(player2, inventory1, i1, itemStack1, clickType1) -> {
                    inventory1.remove(itemStack1);
                    plugin.getOreStorage().removeOre(ore);
                });
            }
            bottomGui.openInventory(player1);
        });
        gui.openInventory(player);
        return false;
    }

    public static List<Player> getInteractPlayers() {
        return interactPlayers;
    }
}
