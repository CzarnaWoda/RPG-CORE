package pl.blackwater.rpg.commands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.units.qual.A;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.rpg.npcs.quests.GatheringQuest;
import pl.blackwater.rpg.npcs.quests.MobHuntQuest;
import pl.blackwater.rpg.npcs.quests.QuestData;
import pl.blackwater.rpg.npcs.quests.QuestStorage;
import pl.blackwater.spigotplugin.spigot.commands.PlayerCommand;
import pl.blackwater.spigotplugin.spigot.inventory.actions.InventoryGUI;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.InventoryUtil;
import pl.blackwater.spigotplugin.util.ItemBuilder;
import pl.blackwater.spigotplugin.util.MathUtil;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class QuestCommand extends PlayerCommand {


    public QuestCommand() {
        super("quest", "QuestCommand", "/quest", "rpg.quest", "q","zadania");
    }

    @Override
    public boolean onCommand(Player player, String[] strings) {

        final InventoryGUI inv = new InventoryGUI(ChatUtil.hexColor("#726CFF★ #A9FCFFAKTYWNE QUESTY #726CFF★"),3);

        final RPGUser user = RPG.getPlugin().getRpgUserManager().getUser(player.getUniqueId());
        final AtomicInteger ai = new AtomicInteger(0);
        for(QuestData data : user.getQuestDataSet()){
            if(data.getQuestName().equalsIgnoreCase("DEFAULT")){
                continue;
            }
            if(data.isStatus()){
                continue;
            }
            final Optional<GatheringQuest> optionalGatheringQuest = QuestStorage.getGatheringQuests().stream().findAny().filter(gatheringQuest -> gatheringQuest.getName().equals(data.getQuestName()));
            optionalGatheringQuest.ifPresentOrElse(gatheringQuest -> {
                    int amount = 0;
                    int todo = 0;
                    for (ItemStack questItem : gatheringQuest.getQuestItems()) {
                        amount += InventoryUtil.getItemCount(player, questItem);
                        todo += questItem.getAmount();
                    }
                    final ItemBuilder a = new ItemBuilder(Material.CYAN_STAINED_GLASS).setTitle(ChatUtil.hexColor("#B03EFF⚒ #EF73FF" + data.getQuestName()))
                            .addLore(ChatUtil.hexColor("#FF7ADA♯ #B04B69Postep ogolny: #8DFFBD" + MathUtil.round((double) ((amount / todo)) * 100.0D, 2) + "#B04B69%"))
                            .addLore("");
                    for (ItemStack questItem : gatheringQuest.getQuestItems()) {
                        a.addLore(ChatUtil.hexColor("#DAF7A6★ #EC68A1Postep &8(#DAF7A6" + questItem.getItemMeta().getDisplayName().toUpperCase() + "&8)#EC68A1: #DDEEAF" + InventoryUtil.getItemCount(player, questItem) + "&7/#DDEEAF" + questItem.getAmount()));
                    }
                    inv.setItem(ai.getAndIncrement(), a.build(), null);
            },() -> {
                final ItemBuilder a = new ItemBuilder(Material.CYAN_STAINED_GLASS).setTitle(ChatUtil.hexColor("#B03EFF⚒ #EF73FF" + data.getQuestName()))
                        .addLore(ChatUtil.hexColor("#FF7ADA♯ #B04B69Postep ogolny: #8DFFBD" + MathUtil.round(((double)data.getProgress()/(double)data.getAmount()) *100.0D,2) + "#B04B69%"))
                        .addLore("");
                final Optional<MobHuntQuest> mobHuntQuest = QuestStorage.getMobHuntQuests().stream().filter(mobHuntQuest1 -> mobHuntQuest1.getName().equalsIgnoreCase(data.getQuestName())).findAny();
                mobHuntQuest.ifPresent(mobHuntQuest1 -> a.addLore(ChatUtil.hexColor("#DAF7A6★ #EC68A1Postep &8(#DAF7A6" + mobHuntQuest1.getMobName() + "&8)#EC68A1: #DDEEAF" + data.getProgress() + "&7/#DDEEAF" + mobHuntQuest1.getHuntAmount())));
                inv.setItem(ai.getAndIncrement(),a.build(),null);
                System.out.println(data.getProgress() + "/" + data.getAmount());
            });
        }
        if(!inv.get().isEmpty()) {
            inv.openInventory(player);
        }else{
            ChatUtil.sendMessage(player,"&8->> &4Nie posiadasz zadnych aktywnych questow");
        }

        return false;
    }
}
