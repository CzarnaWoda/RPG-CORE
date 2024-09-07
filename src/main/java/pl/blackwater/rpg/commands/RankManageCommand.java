package pl.blackwater.rpg.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.ranks.Rank;
import pl.blackwater.rpg.util.SignFactory;
import pl.blackwater.spigotplugin.spigot.commands.PlayerCommand;
import pl.blackwater.spigotplugin.spigot.inventory.actions.InventoryGUI;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.ItemBuilder;

import java.util.Arrays;

public class RankManageCommand extends PlayerCommand {

    private final RPG plugin;
    public RankManageCommand(RPG plugin) {
        super("rankmanage", "Komenda do zarzadzania rangami na serwerze", "/rankmanage", "rpg.rankmanage", "ranks","rank");

        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(Player player, String[] strings) {
        final InventoryGUI ranksManageGui = new InventoryGUI(ChatUtil.hexColor("#ad6461★ #eb987cZARZADZANIE RANGAMI #ad6461★"),1);

        for(Rank rank : plugin.getRankManager().getRanks().values()){
            final ItemBuilder rankItem = new ItemBuilder(Material.BOOK).setTitle(ChatUtil.hexColor("#ad6461★ #eb987cRANGA #edd395" + rank.getName()))
                    .addFlag(ItemFlag.HIDE_ATTRIBUTES);

            ranksManageGui.addItem(rankItem.build(),(guiPlayer, inventory, i, itemStack, clickType) -> {
                guiPlayer.closeInventory();

                final InventoryGUI rankGui = new InventoryGUI(ChatUtil.hexColor("#ad6461★ #eb987cZARZADZANIE RANGA #edd395" + rank.getName() +" #ad6461★"),1);

                final ItemBuilder remove = new ItemBuilder(Material.REDSTONE_BLOCK).setTitle(ChatUtil.hexColor("#ad6461★ &cUSUN UPRAWNIENIE #ad6461★"));
                final ItemBuilder addPermission = new ItemBuilder(Material.EMERALD_BLOCK).setTitle(ChatUtil.hexColor("#ad6461★ &aDODAJ UPRAWNIENIE #ad6461★"));
                final ItemBuilder change = new ItemBuilder(Material.GOLD_BLOCK).setTitle(ChatUtil.hexColor("#ad6461★ &eZARZADZAJ RANGA #ad6461★"));

                guiPlayer.closeInventory();
                rankGui.addItem(addPermission.build(),(player1, inventory1, i1, itemStack1, clickType1) -> {
                    final SignFactory.Menu menu = plugin.getSignFactory().newMenu(Arrays.asList("WPISZ","PONIZEJ","PERMISJE","TUTAJ")).reopenIfFail(true).response((player2, strings1) -> {
                        final String permission = strings1[3];
                        if(permission != null){
                            rank.addPermission(permission);
                            ChatUtil.sendMessage(player1, "&aPoprawnie dodano uprawnienie " + permission + " dla rangi  " +  rank.getName());
                            return true;
                        }
                        return false;
                    });
                    menu.open(player1);
                });

                rankGui.addItem(change.build(),(player1, inventory1, i1, itemStack1, clickType1) -> {
                    openManageMenu(rank,player1);
                });
                rankGui.addItem(remove.build(),(player1, inventory1, i1, itemStack1, clickType1) -> {
                    final SignFactory.Menu menu = plugin.getSignFactory().newMenu(Arrays.asList("WPISZ","PONIZEJ","PERMISJE","TUTAJ")).reopenIfFail(true).response((player2, strings1) -> {
                        final String permission = strings1[3];
                        if(permission != null){
                            rank.removePermission(permission);
                            ChatUtil.sendMessage(player1, "&cPoprawnie usunieto uprawnienie " + permission + " dla rangi  " +  rank.getName());
                            return true;
                        }
                        return false;
                    });
                    menu.open(player1);
                });

                rankGui.openInventory(guiPlayer);
            });
        }
        final ItemBuilder createRank = new ItemBuilder(Material.EMERALD_BLOCK).setTitle(ChatUtil.hexColor("#ad6461★ &aSTWORZ RANGE #ad6461★"));
        ranksManageGui.setItem(ranksManageGui.get().getSize() -1 , createRank.build(), (player1, inventory, i, itemStack, clickType) -> {
            player1.closeInventory();

            final SignFactory.Menu menu = plugin.getSignFactory().newMenu(Arrays.asList("WPISZ","NAZWE","RANGI","DEFAULTNAME")).reopenIfFail(true).response((player2, strings1) -> {
                final String rankName = strings1[3];
                if(rankName != null){
                    plugin.getRankManager().createRank(rankName);
                    ChatUtil.sendMessage(player2,"&aStworzyles range o nazwie &e" + rankName);
                    return true;
                }
                return false;
            });
            menu.open(player1);
        });


        ranksManageGui.openInventory(player);
        return false;
    }
    private void openManageMenu(Rank rank, Player player){
        final InventoryGUI manageRankGui = new InventoryGUI(ChatUtil.hexColor("#ad6461★ #eb987cZARZADZANIE RANGA #edd395" + rank.getName() +" #ad6461★"),1);

        final ItemBuilder prefix = new ItemBuilder(Material.ENCHANTED_BOOK).setTitle(ChatUtil.hexColor("#ad6461★ #eb987cPREFIX"))
                        .addLore(ChatUtil.hexColor("#ad6461★ #edd395" + rank.getPrefix()));
        final ItemBuilder suffix = new ItemBuilder(Material.WRITABLE_BOOK).setTitle(ChatUtil.hexColor("#ad6461★ #eb987cSUFFIX"))
                .addLore(ChatUtil.hexColor("#ad6461★ #edd395" + rank.getSuffix()));
        final ItemBuilder permissionList = new ItemBuilder(Material.PAPER).setTitle(ChatUtil.hexColor("#ad6461★ #eb987cPOKAZ UPRAWNIENIA DLA TEJ RANGI"));
            /*
    MAIN: #eb987c
    Important: #edd395
    SpecialSigns: #ad6461
     */
        manageRankGui.addItem(permissionList.build(),(player1, inventory, i, itemStack, clickType) -> {
            player1.closeInventory();

            ChatUtil.sendMessage(player1,ChatUtil.hexColor("#ad6461★ #eb987cUPRAWNIENIA RANGI #edd395" + rank.getName() + " #ad6461★"));
            rank.getPermissions().forEach(s -> ChatUtil.sendMessage(player1, ChatUtil.hexColor("#ad6461★ " + s)));
            ChatUtil.sendMessage(player1,ChatUtil.hexColor("#ad6461★ #eb987cUPRAWNIENIA RANGI #edd395" + rank.getName() + " #ad6461★"));
        });
        manageRankGui.addItem(prefix.build(),(player1, inventory, i, itemStack, clickType) -> {
            player1.closeInventory();

            final SignFactory.Menu menu = plugin.getSignFactory().newMenu(Arrays.asList("WPISZ","PREFIX","PONIZEJ","DEFAULT")).reopenIfFail(true).response((player2, strings) -> {
                final String p = strings[3];

                if(p != null){
                    rank.setPrefix(p);
                    rank.update();

                    Bukkit.getScheduler().runTaskLater(plugin,() -> openManageMenu(rank,player),40L);
                    return true;
                }
                return false;
            });
            menu.open(player1);
        });
        manageRankGui.addItem(suffix.build(),(player1, inventory, i, itemStack, clickType) -> {
            player1.closeInventory();

            final SignFactory.Menu menu = plugin.getSignFactory().newMenu(Arrays.asList("WPISZ","SUFFIX","PONIZEJ","DEFAULT")).reopenIfFail(true).response((player2, strings) -> {
                final String p = strings[3];

                if(p != null){
                    rank.setSuffix(p);
                    rank.update();

                    Bukkit.getScheduler().runTaskLater(plugin,() -> openManageMenu(rank,player),40L);
                    return true;
                }
                return false;
            });
            menu.open(player1);
        });
        manageRankGui.openInventory(player);
    }
}
