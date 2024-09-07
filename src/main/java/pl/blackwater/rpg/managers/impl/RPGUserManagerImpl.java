package pl.blackwater.rpg.managers.impl;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.rpg.data.combat.Combat;
import pl.blackwater.rpg.data.combat.CombatManager;
import pl.blackwater.rpg.managers.RPGUserManager;
import pl.blackwater.rpg.npcs.tags.ScoreboardAction;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.GsonUtil;
import pl.blackwater.spigotplugin.util.ItemBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

public class RPGUserManagerImpl implements RPGUserManager {

    private final HashMap<UUID, RPGUser> users;
    private final RPG plugin;

    private final Map<Player, BukkitTask> tasks;

    private static ItemStack[] contents = {
            new ItemBuilder(Material.EMERALD_BLOCK).setTitle(ChatUtil.fixColor("&8->> &a&nSPAWN&8 <<-")).build(),
            new ItemBuilder(Material.NAUTILUS_SHELL).setTitle(ChatUtil.fixColor("&8->> &a&nBIŻUTERIA&8 <<-")).build(),
            new ItemBuilder(Material.CHEST).setTitle(ChatUtil.hexColor("&8->> #ce64f5MAGAZYNY &8<<-")).build(),
            new ItemBuilder(Material.SKELETON_SKULL).setTitle(ChatUtil.hexColor("&8->> &eODKRYTE NPC &8<<-")).build(),
            null
    };

    public RPGUserManagerImpl(RPG plugin){
        this.users = new HashMap<>();

        this.plugin = plugin;

        this.tasks = new HashMap<>();
    }
    @Override
    public RPGUser getUser(UUID uuid) {
        return users.get(uuid);
    }

    @Override
    public Optional<RPGUser> getOptionalUser(UUID uuid) {
        return Optional.of(users.get(uuid));
    }

    @Override
    public RPGUser getUser(String lastName) {
        for(RPGUser rpgUser : getUsers().values()){
            if(rpgUser.getLastName().equals(lastName)){
                return rpgUser;
            }
        }
        return null;
    }

    @Override
    public Map<UUID, RPGUser> getUsers() {
        return users;
    }

    @Override
    public void joinToTheGame(Player player) {
        RPGUser rpgUser = users.get(player.getUniqueId());
        if(rpgUser == null) {
            rpgUser = createUser(player);
        }

        final Combat c = CombatManager.getCombat(player);
        if(c == null){
            CombatManager.CreateCombat(player);

        }else{
            c.setLastAttackTime(0L);
            c.setLastAttackPlayer(null);
        }
        if(!rpgUser.getLastName().equals(player.getDisplayName())){
            rpgUser.setLastName(player.getDisplayName());
        }
        player.setFoodLevel(20);
        player.setHealth(player.getMaxHealth());
        plugin.getBossBarServiceStorage().createBossBarService(player);
        plugin.getBossBarServiceStorage().createAntiLogoutBossBarService(player);
        plugin.getScoreboardManager().createScoreBoard(player);

        //CREATE TASK
        BukkitTask task = Bukkit.getScheduler().runTaskTimer(plugin,() -> {
            InventoryView view = player.getOpenInventory();

            if(view.getTopInventory().getSize() == 5){
                if(!player.getGameMode().equals(GameMode.CREATIVE)) {
                    Inventory craftingInv = view.getTopInventory();

                    craftingInv.setItem(1, contents[0]);
                    craftingInv.setItem(2, contents[1]);
                    craftingInv.setItem(3, contents[2]);
                    craftingInv.setItem(4,contents[3]);
                }
            }
        },0L,20L);
        this.tasks.put(player,task);

        //scoreboard
        plugin.getTagManager().updateTagForPlayer(player, ScoreboardAction.CREATE);

        //RANKS
        plugin.getRankManager().joinPlayer(player);
        plugin.getRankManager().implementPermissions(rpgUser);






    }

    @Override
    public void leftTheGame(Player player) {
        RPGUser rpgUser = users.get(player.getUniqueId());
        if(rpgUser == null) {
            plugin.getLogger().log(Level.WARNING,"Dane uzytkownika " + player.getDisplayName() + " zostaly utracone poniewaz nie istnieje on w globalnej mapie!");
            return;
        }else{
            rpgUser.setLastLeftTime(System.currentTimeMillis());
            rpgUser.update(false);
        }
        BukkitTask task = this.tasks.remove(player);

        if(task != null){
            task.cancel();
        }

        CombatManager.getCombatAsOption(player).ifPresent(combat -> CombatManager.removeCombat(player));
        tasks.remove(player);
        InventoryView view = player.getOpenInventory();

        if(view.getTopInventory().getSize() == 5) {
            Inventory craftingInv = view.getTopInventory();

            craftingInv.setItem(1, null);
            craftingInv.setItem(2,null);
            craftingInv.setItem(3,null);
            player.updateInventory();
        }

        plugin.getRankManager().unImplementPermissions(rpgUser);
    }

    @Override
    public RPGUser createUser(Player player) {
        final RPGUser rpgUser = new RPGUser(player.getUniqueId(),player.getDisplayName());
        this.users.put(rpgUser.getUuid(),rpgUser);

        rpgUser.insert(false);

        return rpgUser;
    }

    @Override
    public void setup() {
        final AtomicInteger ai = new AtomicInteger();

        plugin.getRedisStorage().RPG_USERS.forEach((uuid, s) -> {
            RPGUser rpgUser = GsonUtil.fromJson(s, RPGUser.class);

            this.users.put(uuid,rpgUser);

            ai.getAndIncrement();
        });

        plugin.getLogger().log(Level.INFO,"Loaded " + ai.get() + " RPGUser's");
    }

    @Override
    public void saveUsers() {
        users.values().forEach(rpgUser -> rpgUser.insert(true));
    }

    @Override
    public Map<Player, BukkitTask> getTasks() {
        return tasks;
    }

    public static ItemStack[] getContents() {
        return contents;
    }

}
