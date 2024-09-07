package pl.blackwater.rpg.commands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.items.DropItem;
import pl.blackwater.rpg.mysterybox.MysteryBox;
import pl.blackwater.rpg.mysterybox.MysteryBoxItem;
import pl.blackwater.spigotplugin.spigot.commands.PlayerCommand;
import pl.blackwater.spigotplugin.spigot.inventory.actions.InventoryGUI;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.ItemBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class
DropCommand extends PlayerCommand {


    private final HashMap<String,List<DropItem>> dropItems;
    private final RPG plugin;

    public DropCommand(RPG plugin){
        super("drop","Drop Command","/drop","rpg.drop","drops");

        this.plugin = plugin;
        this.dropItems = new HashMap<>();

        for(DropItem dropItem : plugin.getDropItemConfig().getDropItems()){
            System.out.println(dropItem.FROM_WHAT);
        }
        for(String s : plugin.getDropItemManager().getItems().values().stream().map(DropItem::getFROM_WHAT).distinct().collect(Collectors.toList())){
            System.out.println(s);
            dropItems.put(s,plugin.getDropItemManager().getItems().values().stream().filter(dropItem -> dropItem.getFROM_WHAT().equalsIgnoreCase(s)).collect(Collectors.toList()));
        }
    }

    @Override
    public boolean onCommand(Player player, String[] strings) {
        openDropMenu(player);
        return true;
    }
    private void openDropMenu(Player player){
        final InventoryGUI gui = new InventoryGUI(ChatUtil.hexColor("#F6FFAC★ #F2C011DROP PRZEDMIOTOW #F6FFAC★"),2);

        for(String name : dropItems.keySet()){
                String finalName = name;
                finalName = finalName.replace("MOB:","");
                final ItemBuilder a = new ItemBuilder(Material.WITHER_SKELETON_SKULL).setTitle(finalName)
                        .addLore(ChatUtil.hexColor("&8->> #B8F87AILOSC DROPOW: #F5F821" + dropItems.get(name).size()));

                String finalName1 = finalName;
                gui.addItem(a.build(),(player1, inventory, i, itemStack, clickType) -> {
                    final InventoryGUI bottomInv = new InventoryGUI(ChatUtil.hexColor("#F86021" + finalName1.toUpperCase()),4);

                    for(DropItem dropItem : dropItems.get(name)){
                        bottomInv.addItem(dropItem.getItemBuilder().build(),null);
                    }

                    final ItemBuilder back = new ItemBuilder(Material.RED_DYE).setTitle(ChatUtil.hexColor("&4%X% #FF6AABPOWROT &4%X%"));

                    bottomInv.setItem(bottomInv.get().getSize() - 1 , back.build(),(player2, inventory1, i1, itemStack1, clickType1) -> {
                        player2.closeInventory();
                        openDropMenu(player);
                    });
                    bottomInv.openInventory(player1);
                });
        }
        for(MysteryBox mysteryBox : plugin.getMysteryBoxStorage().getMysteryBoxList()){
            gui.addItem(mysteryBox.getBoxItemBuilder().build(),(player1, inventory, i, itemStack, clickType) -> {
                final InventoryGUI bottomInv = new InventoryGUI(ChatUtil.hexColor(mysteryBox.getBoxName()),4);


                for (MysteryBoxItem stack : mysteryBox.getDropList()) {
                    bottomInv.addItem(stack.getItemStack(), null);
                }

                final ItemBuilder back = new ItemBuilder(Material.RED_DYE).setTitle(ChatUtil.hexColor("&4%X% #FF6AABPOWROT &4%X%"));

                bottomInv.setItem(bottomInv.get().getSize() - 1 , back.build(),(player2, inventory1, i1, itemStack1, clickType1) -> {
                    player2.closeInventory();
                    openDropMenu(player);
                });
                bottomInv.openInventory(player1);
            });

        }


        gui.openInventory(player);
    }
}
