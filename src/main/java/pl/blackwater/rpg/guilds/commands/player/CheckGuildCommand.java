package pl.blackwater.rpg.guilds.commands.player;

import eu.decentsoftware.holograms.api.utils.items.SkullUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.rpg.guilds.Member;
import pl.blackwater.rpg.guilds.enums.MemberType;
import pl.blackwater.spigotplugin.spigot.commands.PlayerCommand;
import pl.blackwater.spigotplugin.spigot.inventory.actions.InventoryGUI;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.ItemBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckGuildCommand extends PlayerCommand {

    private final RPG plugin;
    public CheckGuildCommand(RPG plugin) {
        super("info", "Komenda do sprawdzenia informacji na temat gildii", "/guild info [TAG]", "rpg.guilds.check", "check","ginfo","inf");
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(Player player, String[] strings) {
        if(strings.length == 1){
            final String TAG = strings[0];
            plugin.getGuildManager().getGuild(TAG).ifPresentOrElse(guild -> {
                final InventoryGUI gui = new InventoryGUI(ChatUtil.hexColor("#9f69f0GILDIA #539fe6[#591f9c" +  guild.getTag() + "#539fe6] #591f9c " + guild.getName()),6);

                final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");

                final RPGUser owner = plugin.getRpgUserManager().getUser(guild.getOwner());
                final ItemStack skull = new ItemBuilder(Material.PLAYER_HEAD).setTitle(ChatUtil.hexColor("#1a5bc4⚶ WLASCICIEL #1a5bc4⚶"))
                                .addLore(ChatUtil.hexColor("#9c46f2♯ " + owner.getLastName() + " #9c46f2♯")).build();
                SkullUtils.setSkullOwner(skull,owner.getLastName());

                gui.setItem(4,skull,null);

                final ItemBuilder barrier = new ItemBuilder(Material.BARRIER).setTitle(ChatUtil.hexColor("#f551a0♦ #f55151WOLNE MIEJSCE #f551a0♦"));

                int[] membersIndex = {4,22,29,30,31,32,33,39,40,41};
                for(int i  = 0 ; i < membersIndex.length ; i ++){
                    if(guild.getMembers().size() >= i +1){
                        final Member member = guild.getMembers().get(i);
                        if(!member.getMemberType().equals(MemberType.OWNER)) {
                            final Player memberPlayer = Bukkit.getPlayer(member.getUuid());
                            final RPGUser memberUser = plugin.getRpgUserManager().getUser(member.getUuid());
                            final ItemStack memberSkull = new ItemBuilder(Material.PLAYER_HEAD).setTitle(ChatUtil.hexColor("#e2f086♦ " + memberUser.getLastName() + " #e2f086♦"))
                                    .addLore(ChatUtil.hexColor("#6cf0c8♯ #6cf0c8" + (memberPlayer == null ? "Ostatnio online: #6cf0c8" + simpleDateFormat.format(new Date(memberUser.getLastLeftTime())) : "#6cf0c8ONLINE")))
                                    .addLore(ChatUtil.hexColor("#6cf0c8♯ #6cf0c8Pozycja w gildii: #6cf0c8♯" + member.getMemberType().name().toUpperCase())).build();
                            SkullUtils.setSkullOwner(memberSkull, memberUser.getLastName());
                            gui.setItem(membersIndex[i], memberSkull, null);
                        }
                    }else{
                        gui.setItem(membersIndex[i], barrier.build(), null);
                    }
                }

                final ItemBuilder basicInfo = new ItemBuilder(Material.ENCHANTED_BOOK).setTitle(ChatUtil.hexColor("#7488ed%V% #b574edINFORMACJE OGOLNE #7488ed%V%"))
                        .addLore(ChatUtil.hexColor("#7488ed♯ #b574edData zalozenia: #7488ed" + simpleDateFormat.format(guild.getCreateTime())))
                        .addLore(ChatUtil.hexColor("#7488ed♯ #b574edWygasa: #7488ed" +  simpleDateFormat.format(guild.getExpireTime())))
                        .addLore(ChatUtil.hexColor("#7488ed♯ #b574edIlosc czlonkow: #7488ed" + guild.getMembers().size())).addFlag(ItemFlag.HIDE_ATTRIBUTES);

                final ItemBuilder progressInfo = new ItemBuilder(Material.DRAGON_EGG).setTitle(ChatUtil.hexColor("#93d9af★ #93d6d9INFORMACJE O PROGRESSIE #93d9af★"))
                                .addLore(ChatUtil.hexColor("#93d9af♯ #93d6d9POZIOM: #60a7e6" + guild.getGuildLevel())).addLore(ChatUtil.hexColor("#93d9af♯ #93d6d9EXP: #60a7e6"+ guild.getGuildExp() + "/" + guild.getExpToLevel())).addFlag(ItemFlag.HIDE_ATTRIBUTES);

                final ItemBuilder glass = new ItemBuilder(Material.CYAN_STAINED_GLASS_PANE).setTitle(ChatUtil.hexColor("#55e5f2♯"));

                final int[] glassints = {0,1,2,3,5,6,7,8,9,11,12,13,14,15,17,18,19,20,21,23,24,25,26,27,28,34,35,36,37,38,42,43,44,45,46,47,48,49,50,51,52,53};
                for (int glassint : glassints) {
                    gui.setItem(glassint,glass.build(),null);
                }

                gui.setItem(10,basicInfo.build(),null);
                gui.setItem(16,progressInfo.build(),null);
                gui.openInventory(player);
            },() -> ChatUtil.sendMessage(player,"&4Blad: &cTaka gildia nie istnieje!"));
        }else{
            return ChatUtil.sendMessage(player,"&4Poprawne uzycie: &c"+getUsage());
        }
        return false;
    }
}
