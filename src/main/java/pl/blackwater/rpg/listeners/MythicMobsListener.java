package pl.blackwater.rpg.listeners;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import io.lumine.mythic.core.drops.droppables.ItemDrop;
import io.lumine.mythic.core.mobs.ActiveMob;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.bossbar.BossBarService;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.rpg.items.DropItem;
import pl.blackwater.rpg.npcs.quests.MobHuntQuest;
import pl.blackwater.rpg.npcs.quests.QuestData;
import pl.blackwater.rpg.npcs.quests.QuestStorage;
import pl.blackwater.rpg.npcs.quests.enums.QuestType;
import pl.blackwater.rpg.services.LevelActionEnum;
import pl.blackwater.rpg.services.LevelService;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.InventoryUtil;
import pl.blackwater.spigotplugin.util.MathUtil;
import pl.blackwater.spigotplugin.util.RandomUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MythicMobsListener extends LevelService implements Listener {

    private final RPG plugin;

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e){
    }

    @EventHandler
    public void test(MythicMobDeathEvent e){
        if(e.getKiller() instanceof Player){
            final Player player = (Player) e.getKiller();
            final RPGUser rpgUser = plugin.getRpgUserManager().getUser(player.getUniqueId());
            final ActiveMob mob = e.getMob();
            final AbstractEntity ae = mob.getEntity();
            plugin.getBossBarServiceStorage().getBossBar(player).setTitle("&8->> &4☠ &a" + ae.getName().toUpperCase() + " &4☠ &8<<-").setProgress(0.0).setMob(null).show();

            double expCalc = ((mob.getDamage() *0.6) + (mob.getArmor() * 1.8)) * mob.getLevel();
            int moneyCalc = (int) ((((mob.getDamage() * 0.2) + mob.getArmor() * 0.5) * mob.getLevel())/2);

            doLevelAction(rpgUser,expCalc, LevelActionEnum.ADD_EXP);
            rpgUser.addMoney(moneyCalc);


            //LEVEL ON EXP BAR
            player.setLevel(rpgUser.getLevel());
            final double progress = rpgUser.getExp() / rpgUser.getExpToLevel();
            player.setExp((float) progress);

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatUtil.fixColor("&8->> &7EXP: &a+&2" + MathUtil.round(expCalc,2) + " &8( &4%X% " + MathUtil.round((progress*100D),2) + "&c% &4%X% &8)" + "&7, KASA: &6+&e" + moneyCalc + "&6$&8 <<-")));

            for(DropItem dropItem : plugin.getDropItemManager().getItems().values()) {
                if (ae.getName().toUpperCase().contains(dropItem.getFROM_WHAT().toUpperCase())){
                    if(RandomUtil.getChance(dropItem.getChance())){
                        final ItemStack itemStack = dropItem.getItemBuilder().setAmount(1).build();
                        InventoryUtil.giveItems(player,itemStack);
                        if(rpgUser.getChatUser().isDropFromMobMessageChat()) {
                            ChatUtil.sendMessage(player, "&3☠ &7Zabijajac &6" + mob.getDisplayName().toUpperCase() + " &7otrzymales &6" + dropItem.getItemBuilder().getTitle() + " &8(&7+&4" + itemStack.getAmount() + "&8) &3☠");
                        }
                    }
                }
            }
            final List<QuestData> dataList = rpgUser.getQuestDataSet().stream().filter(data -> data.getQuestType().equals(QuestType.MOB_HUNT)).collect(Collectors.toList());
            for(QuestData data : dataList){
                final Optional<MobHuntQuest> quest = QuestStorage.getMobHuntQuests().stream().filter(mobHuntQuest -> mobHuntQuest.getName().equalsIgnoreCase(data.getQuestName())).findAny();
                quest.ifPresent(mobHuntQuest -> {
                    if(!data.isStatus()) {
                        if (ae.getName().toUpperCase().contains(mobHuntQuest.getMobName())) {
                            data.addQuestProgress(1);
                        }
                    }
                });
            }
        }

    }
}
