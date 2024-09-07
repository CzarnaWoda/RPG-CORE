package pl.blackwater.rpg.npcs;

import de.tr7zw.nbtapi.NBTItem;
import lombok.RequiredArgsConstructor;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.rpg.data.enums.RPGClass;
import pl.blackwater.rpg.items.ItemType;
import pl.blackwater.rpg.maps.ExpMap;
import pl.blackwater.rpg.npcs.quests.QuestData;
import pl.blackwater.rpg.npcs.quests.QuestStorage;
import pl.blackwater.rpg.storage.RPGItem;
import pl.blackwater.rpg.storage.enums.AdditionalModifiers;
import pl.blackwater.rpg.storage.enums.Stats;
import pl.blackwater.rpg.trades.Trader;
import pl.blackwater.rpg.util.PlayerInventoryUtil;
import pl.blackwater.rpg.util.RPGUtil;
import pl.blackwater.spigotplugin.spigot.inventory.actions.InventoryGUI;
import pl.blackwater.spigotplugin.spigot.timer.TimerUtil;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.InventoryUtil;
import pl.blackwater.spigotplugin.util.ItemBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@RequiredArgsConstructor
public class NpcListener implements Listener {

    private final RPG plugin;

    private final Material[] material = {Material.BLUE_STAINED_GLASS,Material.ORANGE_STAINED_GLASS,Material.PURPLE_STAINED_GLASS,Material.CYAN_STAINED_GLASS,Material.PINK_STAINED_GLASS,Material.BLACK_STAINED_GLASS};
    private final List<String> excludedNpc = Arrays.asList("WOJOWNIK","HUNTER");
    @EventHandler
    public void onTraderNPC(NPCRightClickEvent e){
        if(e.getNPC() == null){
            return;
        }
        for(Trader trader : plugin.getTraderStorage().getTraders()){
            if(trader.getNpc().equalsIgnoreCase(e.getNPC().getName())){
                e.getClicker().openMerchant(trader.getTradeBuilder().build(), false);
            }
        }
    }
    @EventHandler
    public void discoverNpc(NPCRightClickEvent e){
        if(e.getNPC() != null) {
            if(excludedNpc.contains(e.getNPC().getName())){
                return;
            }
            if (Objects.equals(e.getNPC().getStoredLocation().getWorld(), Bukkit.getWorld("world"))) {
                final RPGUser user = plugin.getRpgUserManager().getUser(e.getClicker().getUniqueId());

                if (!user.getDiscoveredNpc().contains(e.getNPC().getUniqueId())) {
                    user.addDiscoveredNpc(e.getNPC().getUniqueId());
                    ChatUtil.sendMessage(e.getClicker(), "&aOdkryles nowego &eNPC&7, zostal on dodany do twoich &eODKRYTYCH NPC");
                }
            }
        }
    }
    @EventHandler
    public void onNPC(NPCRightClickEvent e){
        if(e.getNPC() == null){
            return;
        }
        final Player executor = e.getClicker();
        if(e.getNPC().getName().equals("WOJOWNIK")){
            final InventoryGUI gui = new InventoryGUI(ChatUtil.hexColor("#42c2f5☬ #4284f5WOJOWNIK #42c2f5☬"),1);

            final ItemBuilder opis = new ItemBuilder(Material.BOOK).setTitle(ChatUtil.hexColor("#b862f5%V% #b862f5OPIS KLASY #b862f5%V%"))
                    .addLore(ChatUtil.hexColor("#b862f5* #80ddedKlasa skupia sie na #b862f5wytrzymalosci#80dded, doskonale skaluje sie z #b862f5stamina#80dded."))
                    .addLore(ChatUtil.hexColor("#b862f5* #80ddedObrazenia skaluja sie razem z #b862f5sila#80dded oraz posiada on zwykle malo #b862f5zrecznosci#80dded."))
                    .addLore(ChatUtil.hexColor("#b862f5* #80ddedKlasa idealna do grania #b862f5solo#80dded oraz do bycia #b862f5tankiem#80dded w druzynie!"));
            final ItemBuilder take = new ItemBuilder(Material.EMERALD_BLOCK).setTitle(ChatUtil.hexColor("#b862f5%V% &aWYBIERZ KLASE #b862f5%V%"));

            gui.setItem(1,opis.build(),null);
            gui.setItem(7,take.build(),(player, inventory, i, itemStack, clickType) -> {
                final RPGUser user = plugin.getRpgUserManager().getUser(player.getUniqueId());
                if(user.getRpgClass().equals(RPGClass.BEGINNER)){
                    user.setRpgClass(RPGClass.WARRIOR);

                    player.closeInventory();
                    final RPGItem helmet = new RPGItem(Material.LEATHER_HELMET,"&3HELM WOJOWNIKA",RPGClass.WARRIOR.name(),1,5,0,0,0,4,0,2.5f,0,2.5f,1.0f,1.0f,5,0,0,8,true, ItemType.EQUIPMENT,null);
                    final RPGItem chestplate = new RPGItem(Material.LEATHER_CHESTPLATE,"&3ZBROJA WOJOWNIKA",RPGClass.WARRIOR.name(),1,8,4,0,0,8,0,2.0f,0,0f,0f,0f,10,0,0,20,true, ItemType.EQUIPMENT,null);
                    final RPGItem leggings = new RPGItem(Material.LEATHER_LEGGINGS,"&3SPODNIE WOJOWNIKA",RPGClass.WARRIOR.name(),1,6,0,0,0,2,0,0,0,0f,0f,0f,5,0,0,10,true, ItemType.EQUIPMENT,null);
                    final RPGItem boots = new RPGItem(Material.LEATHER_BOOTS,"&3BUTY WOJOWNIKA",RPGClass.WARRIOR.name(),1,3,0,0,0,2,0,0,0,0f,0f,0f,4,0,0,5,true, ItemType.EQUIPMENT,null);
                    final RPGItem sword = new RPGItem(Material.WOODEN_SWORD,"&aMIECZ WOJOWNIKA",RPGClass.WARRIOR.name(),1,0,12,2,0,5,0,2.0f,0,2.0f,2.5f,2.0f,10,5,0,5,true, ItemType.EQUIPMENT,null);

                    InventoryUtil.giveItems(player,helmet.build(),chestplate.build(),leggings.build(),boots.build(),sword.build());
                }else{
                    ChatUtil.sendMessage(player,"&7Masz juz wybrana &aklase postaci&7!");
                }
            });
            gui.openInventory(executor);
        }
        if(e.getNPC().getName().equals("HUNTER")){
            final InventoryGUI gui = new InventoryGUI(ChatUtil.hexColor("#42c2f5☬ #4284f5LOWCA #42c2f5☬"),1);

            final ItemBuilder opis = new ItemBuilder(Material.BOOK).setTitle(ChatUtil.hexColor("#b862f5%V% #b862f5OPIS KLASY #b862f5%V%"))
                    .addLore(ChatUtil.hexColor("#b862f5* #80ddedKlasa skupia sie na #b862f5obrazeniach#80dded, doskonale skaluje sie z #b862f5zrecznoscia#80dded."))
                    .addLore(ChatUtil.hexColor("#b862f5* #80ddedObrazenia skaluja sie razem z #b862f5zrecznoscia#80dded oraz posiada on skalowanie obrazen i szansy krytycznej w zaleznosci od #b862f5zrecznosci#80dded."))
                    .addLore(ChatUtil.hexColor("#b862f5* #80ddedKlasa idealna do grania jako #b862f5glowne obrazenia#80dded oraz do bycia #b862f5tylna linia#80dded w druzynie!"));
            final ItemBuilder take = new ItemBuilder(Material.EMERALD_BLOCK).setTitle(ChatUtil.hexColor("#b862f5%V% &aWYBIERZ KLASE #b862f5%V%"));

            gui.setItem(1,opis.build(),null);
            gui.setItem(7,take.build(),(player, inventory, i, itemStack, clickType) -> {
                final RPGUser user = plugin.getRpgUserManager().getUser(player.getUniqueId());
                if(user.getRpgClass().equals(RPGClass.BEGINNER)){
                    user.setRpgClass(RPGClass.HUNTER);

                    player.closeInventory();
                    final RPGItem helmet = new RPGItem(Material.LEATHER_HELMET,"&3HELM LOWCY",RPGClass.HUNTER.name(),1,3,0,0,0,3,0,2.5f,0,3.0f,1.0f,1.0f,0,8,0,6,true, ItemType.EQUIPMENT,null);
                    final RPGItem chestplate = new RPGItem(Material.LEATHER_CHESTPLATE,"&3ZBROJA LOWCY",RPGClass.HUNTER.name(),1,4,4,0,0,6,0,2.0f,0,0f,0f,0f,0,10,0,10,true, ItemType.EQUIPMENT,null);
                    final RPGItem leggings = new RPGItem(Material.LEATHER_LEGGINGS,"&3SPODNIE LOWCY",RPGClass.HUNTER.name(),1,4,0,0,0,1,0,0,0,0f,0f,0f,0,6,0,6,true, ItemType.EQUIPMENT,null);
                    final RPGItem boots = new RPGItem(Material.LEATHER_BOOTS,"&3BUTY LOWCY",RPGClass.HUNTER.name(),1,2,0,0,0,1,0,0,0,0f,0f,0f,0,5,0,2,true, ItemType.EQUIPMENT,null);

                    final RPGItem sword = new RPGItem(Material.BOW,"&aLUK ZWIADOWCY",RPGClass.HUNTER.name(),1,0,15,2,0,0,0,2.0f,0,2.0f,2.5f,2.0f,0,20,0,5,true, ItemType.EQUIPMENT,null);

                    InventoryUtil.giveItems(player,helmet.build(),chestplate.build(),leggings.build(),boots.build(),sword.build());

                }else{
                    ChatUtil.sendMessage(player,"&7Masz juz wybrana &aklase postaci&7!");
                }
            });
            gui.openInventory(executor);
        }
        if(e.getNPC().getName().equalsIgnoreCase("Lowca") || e.getNPC().getName().equalsIgnoreCase("KOLEKCJONER") || e.getNPC().getName().equalsIgnoreCase("MISTRZ CZELADNIK")){
            final String npcName = e.getNPC().getName();
            System.out.println("here");
            final InventoryGUI inventoryGUI = new InventoryGUI(ChatUtil.hexColor("#FCFFB7⚔ #B7FEFF" + npcName + " #FCFFB7⚔"),1);
            final RPGUser user = plugin.getRpgUserManager().getUser(executor.getUniqueId());
            QuestStorage.getGatheringQuests().stream().filter(gatheringQuest -> gatheringQuest.getNpc().equalsIgnoreCase(npcName))
                    .forEach(gatheringQuest -> {
                        AtomicBoolean status = new AtomicBoolean(false);
                        final Optional<QuestData> questData = user.getQuestDataSet().stream().filter(data -> data.getQuestName().equals(gatheringQuest.getName())).findAny();
                        questData.ifPresent(questData1 -> status.set(questData1.isStatus()));
                        final ItemBuilder a = new ItemBuilder(status.get() ? Material.GREEN_STAINED_GLASS : Material.RED_STAINED_GLASS)
                                .setTitle(ChatUtil.fixColor(ChatUtil.hexColor(status.get() ? "&c%X% #DE90FF" + gatheringQuest.getName() + " &c%X%" : "&a%V% #9BC6FF" + gatheringQuest.getName() + " &a%V%")))
                                .addLore(ChatUtil.hexColor("#DAF7A6★ #EC68A1POSTEPY: "));
                        questData.ifPresentOrElse(data -> {
                            if(!data.isStatus()) {
                                for (ItemStack questItem : gatheringQuest.getQuestItems()) {
                                    a.addLore(ChatUtil.hexColor("#DAF7A6★ #EC68A1Postep &8(#DAF7A6" + questItem.getItemMeta().getDisplayName().toUpperCase() + "&8)#EC68A1: #DDEEAF" + InventoryUtil.getItemCount(executor, questItem) + "&7/#DDEEAF" + questItem.getAmount()));
                                }
                            }else{
                                a.addLore(ChatUtil.hexColor("#DAF7A6★ #FF675FJUZ WYKONALES TO ZADANIE #DAF7A6★"));
                            }
                        },() -> a.addLore(ChatUtil.hexColor("#DAF7A6★ #EC68A1KLIKNIJ ABY ODEBRAC QUEST #DAF7A6★")));

                        inventoryGUI.addItem(a.build(),(player, inventory, i, itemStack, clickType) -> questData.ifPresentOrElse(data -> {
                            if(data.isStatus()){
                                ChatUtil.sendMessage(player,ChatUtil.hexColor("#FF675FJuz wykonales to zadanie!"));
                            }else
                            if(gatheringQuest.checkQuestStatus(player)){
                                ChatUtil.sendMessage(player,ChatUtil.hexColor("#FF675FNie wykonales jeszcze tego zadania!"));
                            }else{
                                player.closeInventory();
                                gatheringQuest.doQuestAction(player);
                            }
                        },() -> {
                            user.getQuestDataSet().add(new QuestData(gatheringQuest.getName(),gatheringQuest.getType(), 0, 0));
                            player.closeInventory();
                            ChatUtil.sendMessage(player,ChatUtil.hexColor("#92FF5FOdebrales zadanie!"));
                        }));
                    });

            //MOB HUNT
            QuestStorage.getMobHuntQuests().stream().filter(quest -> quest.getNpc().equalsIgnoreCase(npcName))
                    .forEach(quest -> {
                        System.out.println(quest.getName());
                        final Optional<QuestData> questData = user.getQuestDataSet().stream().filter(data -> data.getQuestName().equalsIgnoreCase(quest.getName())).findAny();
                        AtomicBoolean status = new AtomicBoolean(false);
                        questData.ifPresent(questData1 -> status.set(questData1.isStatus()));
                        final ItemBuilder a = new ItemBuilder(status.get() ? Material.GREEN_STAINED_GLASS : Material.RED_STAINED_GLASS)
                                .setTitle(ChatUtil.fixColor(ChatUtil.hexColor(status.get() ? "&c%X% #DE90FF" + quest.getName() + " &c%X%" : "&a%V% #9BC6FF" + quest.getName() + " &a%V%")))
                                .addLore(ChatUtil.hexColor("#DAF7A6★ #EC68A1POSTEPY: "));
                        questData.ifPresentOrElse(data -> {
                            if(!data.isStatus()) {
                                a.addLore(ChatUtil.hexColor("#DAF7A6★ #EC68A1Postep &8(#DAF7A6" + quest.getMobName() + "&8)#EC68A1: #DDEEAF" + data.getProgress() + "&7/#DDEEAF" + quest.getHuntAmount()));
                            }else{
                                a.addLore(ChatUtil.hexColor("#DAF7A6★ #FF675FJUZ WYKONALES TO ZADANIE #DAF7A6★"));
                            }
                        }, () -> a.addLore(ChatUtil.hexColor("#DAF7A6★ #EC68A1KLIKNIJ ABY ODEBRAC QUEST #DAF7A6★")));

                        inventoryGUI.addItem(a.build(),(player, inventory, i, itemStack, clickType) -> questData.ifPresentOrElse(data -> {
                            if(data.isStatus()){
                                ChatUtil.sendMessage(player,ChatUtil.hexColor("#FF675FJuz wykonales to zadanie!"));
                            }else
                            if(quest.checkQuestStatus(player)){
                                ChatUtil.sendMessage(player,ChatUtil.hexColor("#FF675FNie wykonales jeszcze tego zadania!"));
                            }else{
                                player.closeInventory();
                                quest.doQuestAction(player);
                            }
                        },() -> {
                            user.getQuestDataSet().add(new QuestData(quest.getName(),quest.getType(), 0, quest.getHuntAmount()));
                            player.closeInventory();
                            ChatUtil.sendMessage(player,ChatUtil.hexColor("#92FF5FOdebrales zadanie!"));
                        }));
                    });
            inventoryGUI.openInventory(executor);
        }
        if(e.getNPC().getName().equalsIgnoreCase("EXP")){
            final InventoryGUI mapGui = new InventoryGUI(ChatUtil.hexColor("#9ae2ed⚔ #eb93f5LISTA EXPOWISK #9ae2ed⚔"),3);

            int materialIndex = 0;
            for(ExpMap expMap : plugin.getMapStorage().getExpMaps()){
                final ItemBuilder exp = new ItemBuilder((material[materialIndex] != null ? material[materialIndex] : Material.BLACK_STAINED_GLASS)).setTitle(ChatUtil.hexColor(expMap.getMapName())).addLore(ChatUtil.hexColor("  #eb93f5⚒ #b493f5Wymagany poziom: " + expMap.getRequireLevel()));

                mapGui.addItem(exp.build(),(player, inventory, i, itemStack, clickType) -> {
                    player.closeInventory();

                    final RPGUser rpgUser = plugin.getRpgUserManager().getUser(player.getUniqueId());
                    if(rpgUser.getLevel() >= expMap.getRequireLevel()){
                        TimerUtil.teleport(player,expMap.getLocation(),1);
                    }else{
                        ChatUtil.sendMessage(player,"&7Nie posiadasz wymaganego &epoziomu&7!");
                    }
                });
                materialIndex++;
            }
            mapGui.openInventory(e.getClicker());
        }
        if(e.getNPC().getName().equalsIgnoreCase("KUPIEC")){
            final InventoryGUI merchantInventory = new InventoryGUI(ChatUtil.hexColor("             #e892f0⚶ #f7749eKUPIEC #e892f0⚶             "),6);
            int[] glass = {0,1,2,3,5,6,7,8,9,18,27,36,45,17,26,35,44,53,46,47,48,49,50,51,52};
            int[] itemsIndex = {10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34,37,38,39,40,41,42,43};

            final ItemBuilder green = new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setTitle(ChatUtil.hexColor("#86ebc1♯"));
            final ItemBuilder merchant = new ItemBuilder(Material.EMERALD).setTitle(ChatUtil.hexColor("#e892f0⚶ #f7749eKUPIEC #e892f0⚶"));
            merchantInventory.setItem(4,merchant.build(),null);
            for(int i : glass){
                merchantInventory.setItem(i,green.build(),null);
            }
            int index = 0;
            final RPGUser rpgUser = plugin.getRpgUserManager().getUser(e.getClicker().getUniqueId());
            for(ItemStack item : e.getClicker().getInventory().getStorageContents()){
                if(item == null){
                    continue;
                }
                if(RPGUtil.isRPGItemByItemType(item,ItemType.EQUIPMENT)) {

                    final NBTItem nbtItem = new NBTItem(item);
                    int cost  = 0;
                    for(Stats stats : Stats.values()){
                        cost += nbtItem.getInteger(stats.getType()) * 65;
                    }
                    for(AdditionalModifiers additionalModifiers : AdditionalModifiers.values()){
                        cost += nbtItem.getFloat(additionalModifiers.getType()) * 40;
                    }
                    cost *= (1.0 + (nbtItem.getInteger("level")/10));
                    final ItemStack cloneItem = item.clone();

                    final ItemMeta meta = cloneItem.getItemMeta();
                    assert meta != null;
                    final List<String> lore = meta.getLore();
                    Objects.requireNonNull(lore).add(ChatUtil.hexColor("  #9fa0ed♦ #bc9fedCena: #bc9fed" + cost + "$"));
                    meta.setLore(lore);
                    cloneItem.setItemMeta(meta);
                    int finalCost = cost;
                    merchantInventory.setItem(itemsIndex[index], cloneItem, (player, inventory, i, itemStack, clickType) -> {
                        if(PlayerInventoryUtil.checkItemsInStorage(player,item,1)){
                            inventory.setItem(i,null);
                            PlayerInventoryUtil.removeItems(player,item,1);
                            rpgUser.addMoney(finalCost);
                        }
                    });
                    index++;
                }
            }
            if(index == 0){
                ChatUtil.sendMessage(e.getClicker(),"&4Nie posiadasz zadnych przedmiotow na sprzedaz!");
            }else {
                merchantInventory.openInventory(e.getClicker());
            }
        }
    }
}
