package pl.blackwater.rpg.commands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.rpg.guilds.Guild;
import pl.blackwater.spigotplugin.spigot.commands.PlayerCommand;
import pl.blackwater.spigotplugin.spigot.inventory.actions.InventoryGUI;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.ItemBuilder;
import pl.blackwater.spigotplugin.util.MathUtil;

import java.util.Arrays;
import java.util.List;

public class StatsCommand extends PlayerCommand {
    
    private final RPG plugin;

    private final List<String> hexColors = Arrays.asList("#ff0000"
            ,"#ff3300"
            ,"#ff6600"
            ,"#ff9900"
            ,"#ffcc00"
            ,"#ffff00"
            ,"#99ff00"
            ,"#33ff00"
            ,"#00cc33"
            ,"#006699"
            ,"#0000ff"
            ,"#1e00cd"
            ,"#3c009b"
            ,"#5a0092"
            ,"#7700b3"
            ,"#9400d3");
        
    public StatsCommand(RPG plugin){
        super("statystyki","Statystyki gracza","/statystyki","rpg.stats","stats");
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(Player player, String[] strings) {
        if(strings.length != 1) {
            final RPGUser rpgUser = plugin.getRpgUserManager().getUser(player.getUniqueId());
            openMenu(rpgUser,player);
        }else{
            final String name = strings[0];
            final RPGUser user = plugin.getRpgUserManager().getUser(name);
            if(user == null){
                return ChatUtil.sendMessage(player,"&4Blad: &cGracz nie istnieje w bazie danych!");
            }
            openMenu(user,player);
        }
        
        return true;
    }
    
    public void openMenu(RPGUser user,Player opener){
        final InventoryGUI gui = new InventoryGUI(ChatUtil.hexColor("         #6FFBFF✟ #7d76ff&lS#8a6bff&lT#9760ff&lA#a455ff&lT#b14aff&lY#bf3fff&lS#cc34ff&lT#d929ff&lY#e61eff&lK#f313ff&lI #6FFBFF✟"),4);



        final ItemBuilder skull = new ItemBuilder(Material.BEACON).setTitle(ChatUtil.hexColor("&e⚒ " + colorName(user.getLastName()) + " &e⚒")).addLores(Arrays.asList(ChatUtil.hexColor("#72FF96⚔ #13FA84LEVEL: #13FAE8" + user.getLevel()),
                ChatUtil.hexColor("#72FF96⚔ #13FA84EXP: #13FAE8" + user.getExp() + "#72FF96/" + "#13FAE8" + user.getExpToLevel()))).addFlag(ItemFlag.HIDE_ATTRIBUTES);

        final ItemBuilder[] statitems= {
                new ItemBuilder(Material.GOLD_NUGGET).setTitle(ChatUtil.hexColor("#EBFF0BSTAN KONTA: " + user.getMoney() + "$")),
                new ItemBuilder(Material.WITHER_SKELETON_SKULL).setTitle(ChatUtil.hexColor("#EB96E0ZABOJSTWA: " + user.getKills())),
                new ItemBuilder(Material.SKELETON_SKULL).setTitle(ChatUtil.hexColor("#AB96EBSMIERCI: " + user.getDeaths())),
                new ItemBuilder(Material.DIAMOND_SWORD).setTitle(ChatUtil.hexColor("#B01660SILA: " + user.getSTRENGTH())).addLore("").addLore(ChatUtil.hexColor("#4DF2E7TA STATYSTYKA JEST ZLICZANA TYLKO Z BIZUTERII")).addFlag(ItemFlag.HIDE_ATTRIBUTES),
                new ItemBuilder(Material.FEATHER).setTitle(ChatUtil.hexColor("#CCFCF8ZRECZNOSC: " + user.getDEXTERITY())).addLore("").addLore(ChatUtil.hexColor("#4DF2E7TA STATYSTYKA JEST ZLICZANA TYLKO Z BIZUTERII")),
                new ItemBuilder(Material.DIAMOND).setTitle(ChatUtil.hexColor("#31F083INTELIGENCJA: " + user.getINTELLIGENCE())).addLore("").addLore(ChatUtil.hexColor("#4DF2E7TA STATYSTYKA JEST ZLICZANA TYLKO Z BIZUTERII")),
                new ItemBuilder(Material.DIAMOND_CHESTPLATE).setTitle(ChatUtil.hexColor("#E3BF33WYTRZYMALOSC: " + user.getSTAMINA())).addLore("").addLore(ChatUtil.hexColor("#4DF2E7TA STATYSTYKA JEST ZLICZANA TYLKO Z BIZUTERII")).addFlag(ItemFlag.HIDE_ATTRIBUTES),
                new ItemBuilder(Material.IRON_SWORD).setTitle(ChatUtil.hexColor("#33BFE3DODATKOWE OBRAZENIA NA POTWORY: " + MathUtil.round(user.getAverageMonsterDamage(),2) + "%")).addLore("").addLore(ChatUtil.hexColor("#4DF2E7TA STATYSTYKA JEST ZLICZANA TYLKO Z BIZUTERII")).addFlag(ItemFlag.HIDE_ATTRIBUTES),
                new ItemBuilder(Material.GOLDEN_SWORD).setTitle(ChatUtil.hexColor("#8AFD8FDODATKOWE OBRAZENIA NA ISTOTY: " + MathUtil.round(user.getAverageCreatureDamage(),2) + "%")).addLore("").addLore(ChatUtil.hexColor("#4DF2E7TA STATYSTYKA JEST ZLICZANA TYLKO Z BIZUTERII")).addFlag(ItemFlag.HIDE_ATTRIBUTES),
                new ItemBuilder(Material.WOODEN_SWORD).setTitle(ChatUtil.hexColor("#4295A2DODATKOWE OBRAZENIA NA LUDZI: " + MathUtil.round(user.getAverageHumanDamage(),2) + "%")).addLore("").addLore(ChatUtil.hexColor("#4DF2E7TA STATYSTYKA JEST ZLICZANA TYLKO Z BIZUTERII")).addFlag(ItemFlag.HIDE_ATTRIBUTES),
                new ItemBuilder(Material.BOW).setTitle(ChatUtil.hexColor("#7B42A2DODATKOWA SZANSA NA UDERZENIE KRYTYCZNE: " + MathUtil.round(user.getAdditionalCriticalChance(),2) + "%")).addLore("").addLore(ChatUtil.hexColor("#4DF2E7TA STATYSTYKA JEST ZLICZANA TYLKO Z BIZUTERII")),
                new ItemBuilder(Material.ANVIL).setTitle(ChatUtil.hexColor("#A2426CDODATKOWE OBRAZENIA NA UDERZENIE KRYTYCZNE: " + MathUtil.round(user.getAdditionalDamageOfCriticalChance(),2) + "%")).addLore("").addLore(ChatUtil.hexColor("#4DF2E7TA STATYSTYKA JEST ZLICZANA TYLKO Z BIZUTERII"))
        };
        final int[] glass = {0,1,2,3,5,6,7,8,9,17,18,19,25,26,27,28,29,30,32,33,34,35};
        final int[] stats = {10,11,12,13,14,15,16,20,21,22,23,24};
        final ItemBuilder pane = new ItemBuilder(Material.CYAN_STAINED_GLASS_PANE).setTitle(ChatUtil.hexColor("#20F9FF♯"));
        for (int i : glass) {
            gui.setItem(i,pane.build(),null);
        }
        int index = 0;
        for(int i : stats){
            gui.setItem(i,statitems[index].build(),null);
            index++;
        }
        gui.setItem(4,skull.build(),null);

        final Guild member = plugin.getGuildManager().getSimpleGuild(user.getUuid());
        final ItemBuilder guild = new ItemBuilder(Material.DRAGON_EGG).setTitle(ChatUtil.hexColor("#960dbfGILDIA: #aa60e6" + (member == null ? "BRAK" : member.getTag())));
        gui.setItem(31,guild.build(),null);

        gui.openInventory(opener);
    }
    //31

    public String colorName(String name){
        StringBuilder finalName = new StringBuilder();
        int index = 0;
        for (char c : name.toCharArray()) {
            finalName.append(hexColors.get(index)).append(c);
            index++;
        }
        return finalName.toString();
    }
}
