package pl.blackwater.rpg.commands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.ChatUserOptions;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.spigotplugin.spigot.commands.PlayerCommand;
import pl.blackwater.spigotplugin.spigot.inventory.actions.InventoryGUI;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.ItemBuilder;

import java.util.concurrent.atomic.AtomicInteger;

public class ChatControlCommand extends PlayerCommand {
    private final RPG plugin;
    public ChatControlCommand(RPG plugin) {
        super("cc", "ChatControl command", "/cc", "rpg.chatcontrol", "chat");

        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(Player player, String[] strings) {
        openMenu(player);
        return false;
    }

    private void openMenu(Player player){

        final InventoryGUI gui = new InventoryGUI(ChatUtil.fixColor("&a&l✭ &2ZARZADZANIE ALERTAMI &a&l✭"),3);

        final AtomicInteger index = new AtomicInteger(9);
        final RPGUser user = plugin.getRpgUserManager().getUser(player.getUniqueId());
        final ItemBuilder green = new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setTitle(ChatUtil.fixColor("&a&l⭐ &aWLACZONE &a&l⭐"));
        final ItemBuilder red = new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setTitle(ChatUtil.fixColor("&c&l✖ &4WYLACZONE &c&l✖"));
        for(ChatUserOptions options : ChatUserOptions.values()){
            final ItemBuilder builder = new ItemBuilder(options.getGuiMaterial()).setTitle(options.getGuiTitle()).addFlag(ItemFlag.HIDE_ATTRIBUTES).addFlag(ItemFlag.HIDE_UNBREAKABLE);
            final boolean b = ChatUserOptions.getBoolean(options,user.getChatUser());
            gui.setItem(index.get(),builder.build(),(player1, inventory, i, itemStack, clickType) -> {
                boolean status = ChatUserOptions.getBooleanAndToggle(options,user.getChatUser());
                if(status){
                    inventory.setItem(i+9,green.build());
                    inventory.setItem(i-9,green.build());
                }else{
                    inventory.setItem(i+9,red.build());
                    inventory.setItem(i-9,red.build());
                }
            });
            setGlassForOption(gui, green, red, b, index.get());
            index.getAndIncrement();
            if(index.get() == 16){
                final ItemBuilder nextPageBuilder = new ItemBuilder(Material.ARROW).setTitle(ChatUtil.fixColor(ChatUtil.hexColor("#FFDD95") + "↪" + ChatUtil.hexColor("#63BEFF") + "PRZEJDZ NA NASTEPNA STRONE " + ChatUtil.hexColor("#FFDD95") + "↪"));
                final ItemBuilder purple = new ItemBuilder(Material.PURPLE_STAINED_GLASS_PANE).setTitle(ChatUtil.fixColor(ChatUtil.hexColor("#6100AA") + "↯"));

                gui.setItem(26, purple.build(),null);
                gui.setItem(17,nextPageBuilder.build(),(player1, inventory, i, itemStack, clickType) -> {
                    player1.closeInventory();
                    openMenu(player1,8);
                });
                gui.setItem(8, purple.build(),null);
                break;
            }
        }
        gui.openInventory(player);


    }
    private void openMenu(Player player, int start){

        final InventoryGUI gui = new InventoryGUI(ChatUtil.fixColor("&a&l✭ &2ZARZADZANIE ALERTAMI &a&l✭"),3);

        final AtomicInteger index = new AtomicInteger(9);
        final RPGUser user = plugin.getRpgUserManager().getUser(player.getUniqueId());
        final ItemBuilder green = new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setTitle(ChatUtil.fixColor("&a&l⭐ &aWLACZONE &a&l⭐"));
        final ItemBuilder red = new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setTitle(ChatUtil.fixColor("&c&l✖ &4WYLACZONE &c&l✖"));
        for(int j = start ; j < ChatUserOptions.values().length ; j ++){
            final ChatUserOptions options = ChatUserOptions.values()[j];
            final ItemBuilder builder = new ItemBuilder(options.getGuiMaterial()).setTitle(options.getGuiTitle()).addFlag(ItemFlag.HIDE_ATTRIBUTES).addFlag(ItemFlag.HIDE_UNBREAKABLE);
            final boolean b = ChatUserOptions.getBoolean(options,user.getChatUser());
            gui.setItem(index.get(),builder.build(),(player1, inventory, i, itemStack, clickType) -> {
                boolean status = ChatUserOptions.getBooleanAndToggle(options,user.getChatUser());
                if(status){
                    inventory.setItem(i+9,green.build());
                    inventory.setItem(i-9,green.build());
                }else{
                    inventory.setItem(i+9,red.build());
                    inventory.setItem(i-9,red.build());
                }
            });
            setGlassForOption(gui, green, red, b, index.get());
            index.getAndIncrement();
            if(index.get() == 16){
                final ItemBuilder nextPageBuilder = new ItemBuilder(Material.ARROW).setTitle(ChatUtil.fixColor(ChatUtil.hexColor("#FFDD95") + "↪" + ChatUtil.hexColor("#63BEFF") + "PRZEJDZ NA NASTEPNA STRONE " + ChatUtil.hexColor("#FFDD95") + "↪"));
                final ItemBuilder purple = new ItemBuilder(Material.PURPLE_STAINED_GLASS_PANE).setTitle(ChatUtil.fixColor(ChatUtil.hexColor("#6100AA") + "↯"));

                gui.setItem(26, purple.build(),null);
                gui.setItem(17,nextPageBuilder.build(),(player1, inventory, i, itemStack, clickType) -> {
                    player1.closeInventory();
                    openMenu(player1,start + 8);
                });
                gui.setItem(8, purple.build(),null);
                break;
            }
        }
        gui.openInventory(player);


    }

    private void setGlassForOption(InventoryGUI gui, ItemBuilder green, ItemBuilder red, boolean b, int finalIndex) {
        if(b){
            gui.setItem(finalIndex + 9 ,green.build(),null);
            gui.setItem(finalIndex - 9 , green.build(),null);
        }else{
            gui.setItem(finalIndex + 9 ,red.build(),null);
            gui.setItem(finalIndex - 9 , red.build(),null);
        }
    }
}
