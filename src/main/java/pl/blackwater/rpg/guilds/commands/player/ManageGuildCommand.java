package pl.blackwater.rpg.guilds.commands.player;

import eu.decentsoftware.holograms.api.utils.items.SkullUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.rpg.guilds.Member;
import pl.blackwater.rpg.guilds.enums.GuildPermission;
import pl.blackwater.rpg.guilds.enums.MemberType;
import pl.blackwater.rpg.guilds.enums.UpgradeType;
import pl.blackwater.spigotplugin.spigot.commands.PlayerCommand;
import pl.blackwater.spigotplugin.spigot.inventory.actions.InventoryGUI;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.ItemBuilder;

import java.util.List;
import java.util.Objects;

public class ManageGuildCommand extends PlayerCommand {
    private final RPG plugin;
    public ManageGuildCommand(RPG plugin) {
        super("manage", "Komenda do zarzadzania gildia", "/guild manage", "rpg.guilds.manage", "panel","zarzadzaj");

        this.plugin = plugin;
    }
    /*
    COLORS:
    MAIN: #9f69f0
    IMPORTANT: #591f9c
    FOR UNIQUE SIGNS: #539fe6
     */
    @Override
    public boolean onCommand(Player player, String[] strings) {


        plugin.getGuildManager().getGuild(player).ifPresentOrElse(guild -> plugin.getGuildManager().getMember(player.getUniqueId()).ifPresent(member -> {
            if(member.getGuildPermissions().get(GuildPermission.MANAGE) || member.getMemberType().equals(MemberType.OWNER)){
                final InventoryGUI manageGui = new InventoryGUI(ChatUtil.hexColor("#9f69f0ZARZADZANIE GILDIA"),1);

                final ItemBuilder manageMembers = new ItemBuilder(Material.PLAYER_HEAD).setTitle(ChatUtil.hexColor("#9f69f0%X% #591f9cZARZADZANIE CZLONKAMI #9f69f0%X%"))
                        .addLore(ChatUtil.hexColor("#539fe6%V% #9f69f0ILOSC CZLONKOW: #591f9c" + guild.getMembers().size()));

                final ItemBuilder managePoints = new ItemBuilder(Material.DIAMOND_SWORD).setTitle(ChatUtil.hexColor("#539fe6%V% #9f69f0ZARZADZANIE PUNKTAMI #539fe6%V%"));

                manageGui.setItem(2,managePoints.build(),(guiPlayer, inventory, i, itemStack, clickType) -> {
                    guiPlayer.closeInventory();
                    final InventoryGUI pointsManageGui = new InventoryGUI(ChatUtil.hexColor("#9f69f0ZARZADZANIE PUNKTAMI"),3);

                    final ItemBuilder pktInfo = new ItemBuilder(Material.PAPER).setTitle(ChatUtil.hexColor("#539fe6♦ #9f69f0PUNKTY - #591f9c" +  guild.getGuildPoints() + " #539fe6♦"));
                    pointsManageGui.setItem(4,pktInfo.build(),null);
                    int index = 18;
                    for(UpgradeType upgradeType : UpgradeType.values()){
                        final ItemBuilder upgrade = new ItemBuilder(upgradeType.getMaterial()).setTitle(ChatUtil.hexColor("#539fe6♦ #9f69f0ULEPSZENIE #591f9c" + upgradeType.name().toUpperCase()))
                                .addLore(ChatUtil.hexColor("539fe6♦ #9f69fSTATUS: #591f9c" + guild.getUpgradeTypes().get(upgradeType) + "/" + upgradeType.getMax()))
                                .addLore(ChatUtil.hexColor("539fe6♦ #9f69f0CENA (KASA): #591f9c" + (upgradeType.getMoney() * guild.getUpgradeTypes().get(upgradeType)) + "$"))
                                .addLore(ChatUtil.hexColor("539fe6♦ #9f69f0CENA (PUNKTY): #591f9c1"));
                        pointsManageGui.setItem(index, upgrade.build(),(managePlayer, inventory1, i1, itemStack1, clickType1) -> {
                            final RPGUser rpgUser = plugin.getRpgUserManager().getUser(managePlayer.getUniqueId());

                            if(rpgUser.getMoney() >= (upgradeType.getMoney() * guild.getUpgradeTypes().get(upgradeType))){
                                if(guild.getGuildPoints() >= 1){
                                    if(upgradeType.getMax() < guild.getUpgradeTypes().get(upgradeType)){
                                        rpgUser.removeMoney((upgradeType.getMoney() * guild.getUpgradeTypes().get(upgradeType)));
                                        guild.removePoint(1);
                                        guild.getUpgradeTypes().put(upgradeType,guild.getUpgradeTypes().get(upgradeType) + 1);
                                        final ItemMeta meta = itemStack1.getItemMeta();
                                        assert meta != null;
                                        final List<String> lore = meta.getLore();
                                        assert lore != null;
                                        lore.set(0,ChatUtil.hexColor("539fe6♦ #9f69fSTATUS: #591f9c" + guild.getUpgradeTypes().get(upgradeType) + "/" + upgradeType.getMax()));
                                        meta.setLore(lore);
                                        itemStack1.setItemMeta(meta);
                                    }else{
                                        ChatUtil.sendMessage(player,"&4Blad: &cTwoja gildia ma juz maksymalne ulepszenie!");
                                    }
                                }else{
                                    ChatUtil.sendMessage(player,"&4Blad: &cTwoja gildia nie posiada tyle punktow");
                                }
                            }else{
                                ChatUtil.sendMessage(player,"&4Blad: &cNie posiadasz tyle pieniedzy");
                            }
                        });
                        pointsManageGui.openInventory(player);



                    }
                });

                manageGui.setItem(1,manageMembers.build(),(guiPlayer, inventory, i, itemStack, clickType) -> {
                    guiPlayer.closeInventory();
                    final InventoryGUI memberManageGui = new InventoryGUI(ChatUtil.hexColor("#9f69f0ZARZADZANIE CZLONKAMI"),2);

                    for(Member guildMember : guild.getMembers()){
                        if(guildMember.getMemberType().equals(MemberType.OWNER)){
                            continue;
                        }
                        final RPGUser rpgUser = plugin.getRpgUserManager().getUser(guildMember.getUuid());
                        final ItemStack memberItem = new ItemBuilder(Material.PLAYER_HEAD).setTitle(ChatUtil.hexColor("#539fe6♦ CZLONEK GILDII #539fe6♦"))
                                .addLore(ChatUtil.hexColor("  #539fe6♦ NAZWA: " + rpgUser.getLastName()))
                                .addLore(ChatUtil.hexColor("  #539fe6♦ TYP: " + guildMember.getMemberType().name().toUpperCase())).build();

                        SkullUtils.setSkullOwner(memberItem,rpgUser.getLastName());

                        memberManageGui.addItem(memberItem,(manageMemberPlayer, inventory1, i1, itemStack1, clickType1) -> {
                            manageMemberPlayer.closeInventory();

                            final InventoryGUI memberGui = new InventoryGUI(ChatUtil.hexColor("#9f69f0ZARZADZANIE CZLONKIEM " + rpgUser.getLastName()),2);

                            for(GuildPermission guildPermission : guildMember.getGuildPermissions().keySet()){
                                final ItemBuilder permission = new ItemBuilder(guildPermission.getMaterial()).setTitle(ChatUtil.hexColor("#539fe6♦ #9f69f0UPRAWNIENIE #591f9c" + guildPermission.name().toUpperCase() + " #539fe6♦"))
                                        .addLore("#539fe6♦ #9f69f0STATUS: " + (guildMember.getGuildPermissions().get(guildPermission) ? "&a%V%" : "&c%X%"))
                                        .addLore("")
                                        .addLore("#539fe6♦ #9f69f0" + guildPermission.getDescription());

                                memberGui.addItem(permission.build(),(player1, inventory2, i2, itemStack2, clickType2) -> {
                                    guildMember.getGuildPermissions().put(guildPermission, !guildMember.getGuildPermissions().get(guildPermission));

                                    final ItemMeta meta = itemStack2.getItemMeta();
                                    assert meta != null;
                                    Objects.requireNonNull(meta.getLore()).set(0,ChatUtil.hexColor("#539fe6♦ #9f69f0STATUS: " + (guildMember.getGuildPermissions().get(guildPermission) ? "&a%V%" : "&c%X%")));
                                    itemStack2.setItemMeta(meta);
                                });
                            }

                            memberGui.openInventory(manageMemberPlayer);
                        });

                    }
                    if(memberManageGui.get().isEmpty()){
                        guiPlayer.closeInventory();
                        ChatUtil.sendMessage(player,"&4Blad: &cNie masz kim zarzadzac!");
                    }else {
                        memberManageGui.openInventory(guiPlayer);
                    }
                });
                manageGui.openInventory(player);
            }else{
                ChatUtil.sendMessage(player,"&4Blad: &cNie posiadasz uprawnien do zarzadzania gildia");
            }
        }),() -> ChatUtil.sendMessage(player,"&4Blad: &cNie posiadasz gildii"));
        return false;
    }
}
