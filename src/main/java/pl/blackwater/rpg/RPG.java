package pl.blackwater.rpg;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.blackwater.api.config.ConfigAPI;
import pl.blackwater.api.config.style.CommentStyle;
import pl.blackwater.api.config.style.NameStyle;
import pl.blackwater.rpg.bossbar.BossBarServiceStorage;
import pl.blackwater.rpg.bossbar.BossBarThread;
import pl.blackwater.rpg.chat.ChatOptionsManager;
import pl.blackwater.rpg.commands.*;
import pl.blackwater.rpg.configs.*;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.rpg.guilds.GuildManager;
import pl.blackwater.rpg.guilds.GuildManagerImpl;
import pl.blackwater.rpg.guilds.commands.GuildCommand;
import pl.blackwater.rpg.items.*;
import pl.blackwater.rpg.listeners.*;
import pl.blackwater.rpg.managers.RPGUserManager;
import pl.blackwater.rpg.managers.impl.RPGUserManagerImpl;
import pl.blackwater.rpg.maps.MapStorage;
import pl.blackwater.rpg.mine.OreStorage;
import pl.blackwater.rpg.mine.OreThread;
import pl.blackwater.rpg.mysterybox.MysteryBoxActionListener;
import pl.blackwater.rpg.mysterybox.MysteryBoxStorage;
import pl.blackwater.rpg.npcs.NpcListener;
import pl.blackwater.rpg.npcs.quests.QuestStorage;
import pl.blackwater.rpg.npcs.tags.TagManager;
import pl.blackwater.rpg.npcs.tags.TagManagerImpl;
import pl.blackwater.rpg.ranks.Rank;
import pl.blackwater.rpg.ranks.RankManager;
import pl.blackwater.rpg.sidebar.BlackScoreboardManager;
import pl.blackwater.rpg.sidebar.SidebarUpdateThread;
import pl.blackwater.rpg.trades.TraderStorage;
import pl.blackwater.rpg.util.SignFactory;
import pl.blackwater.rpg.util.serializers.ChatColorSerializer;
import pl.blackwater.rpg.util.serializers.ItemBuilderSerializer;
import pl.blackwater.spigotplugin.SpigotPlugin;
import pl.blackwater.spigotplugin.thread.SpigotThread;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.ItemBuilder;

public class RPG extends JavaPlugin {

    private static RPG plugin;
    private RedisStorage redisStorage;

    private RPGConfig rpgConfig;
    private MessageConfig messageConfig;

    private DropItemConfig dropItemConfig;

    private GuildConfig guildConfig;
    private TagManager tagManager;

    private CustomItemManager customItemManager;

    private CustomItemStorage customItemStorage;

    private RPGUserManager rpgUserManager;

    private ItemManager dropItemManager;
    private ItemManager boxItemManager;

    private BossBarServiceStorage bossBarServiceStorage;

    private BlackScoreboardManager scoreboardManager;

    private SpigotThread bossBarThread;
    private SpigotThread sidebarThread;
    private final PluginManager pm = Bukkit.getPluginManager();

    private ProtocolManager protocolManager;

    private SignFactory signFactory;

    private MysteryBoxStorage mysteryBoxStorage;


    private SpecialItemImpl specialItems;

    private OreStorage oreStorage;

    private GuildManager guildManager;

    private RankManager rankManager;

    private ChatOptionsManager chatOptionsManager;

    private ChatConfig chatConfig;

    private TraderStorage traderStorage;

    private MapStorage mapStorage;
    @Override
    public void onLoad() {
        protocolManager = ProtocolLibrary.getProtocolManager();
    }

    @Override
    public void onEnable() {
        //load Plugin
        plugin = this;

        //REDIS STORAGE

        redisStorage = new RedisStorage();

        //RPGUsers
        this.rpgUserManager = new RPGUserManagerImpl(this);

        rpgUserManager.setup();


        //CONFIG
        ConfigAPI.registerSerializer(ChatColor.class, new ChatColorSerializer());
        ConfigAPI.registerSerializer(ItemBuilder.class, new ItemBuilderSerializer());

        rpgConfig = ConfigAPI.init(RPGConfig.class,
                NameStyle.UNDERSCORE,
                CommentStyle.INLINE,
                true,
                this);
        messageConfig = ConfigAPI.init(MessageConfig.class,
                NameStyle.UNDERSCORE,
                CommentStyle.INLINE,
                true,
                this);
        dropItemConfig = ConfigAPI.init(DropItemConfig.class,
                NameStyle.UNDERSCORE,
                CommentStyle.INLINE,
                true,
                this);
        customItemStorage = ConfigAPI.init(CustomItemStorage.class,
                NameStyle.UNDERSCORE,
                CommentStyle.INLINE,
                true,
                this);
        guildConfig = ConfigAPI.init(GuildConfig.class,
                NameStyle.HYPHEN,
                CommentStyle.INLINE,
                true,
                this);
        chatConfig = ConfigAPI.init(ChatConfig.class,
                NameStyle.UNDERSCORE,
                CommentStyle.INLINE,
                true,
                this);
        //ITEMS
        this.mysteryBoxStorage  = new MysteryBoxStorage();
        this.dropItemManager = new DropItemManager();
        this.boxItemManager = new BoxItemManager();

        //Listeners
        registerListeners(this,new DisableThingsListener(),new NpcListener(this),new PlayerInteractListener(this),new AsyncPlayerChatListener(this),new PlayerBreakBlockListener(this),new BlockPlaceListener(),new StorageInventoryListener(this),new InventoryOpenListener(),new MysteryBoxActionListener(),new WeatherChangeListener(),new PlayerDeathListener(this),new PickupItemListener(this), new JoinQuitListener(this),new PlayerInventoryListener(this), new MythicMobsListener(this), new PlayerDamageEntityListener(this), new FoodChangeListener());
        SpigotPlugin.registerCommand(new DEBUGCommand());
        SpigotPlugin.registerCommand(new GamemodeCommand(this));
        SpigotPlugin.registerCommand(new EnchantCommand());
        SpigotPlugin.registerCommand(new ClearCommand(this));
        SpigotPlugin.registerCommand(new SetSpawnCommand(this));
        SpigotPlugin.registerCommand(new ItemCrateCommand());
        SpigotPlugin.registerCommand(new SpawnCommand());
        SpigotPlugin.registerCommand(new MysteryBoxCommand(this));
        SpigotPlugin.registerCommand(new ChatControlCommand(this));
        SpigotPlugin.registerCommand(new BroadcastCommand(this));
        SpigotPlugin.registerCommand(new DropCommand(this));
        SpigotPlugin.registerCommand(new StatsCommand(this));
        SpigotPlugin.registerCommand(new SpecialItemsCommand(this));
        SpigotPlugin.registerCommand(new OreManagerCommand(this));
        SpigotPlugin.registerCommand(new ClearEntityCommand());
        SpigotPlugin.registerCommand(new AddStatCommand(this));
        SpigotPlugin.registerCommand(new RankSetCommand(this));
        SpigotPlugin.registerCommand(new RankManageCommand(this));
        SpigotPlugin.registerCommand(new GodCommand(this));
        SpigotPlugin.registerCommand(new FlyCommand(this));
        //GUILDSCMD
        SpigotPlugin.registerCommand(new GuildCommand(this));

        SpigotPlugin.registerCommand(new AddExpMapCommand());
        SpigotPlugin.registerCommand(new RemoveExpMapCommand());

        //NPCs
        this.tagManager = new TagManagerImpl(this);


        bossBarServiceStorage = new BossBarServiceStorage();
        this.scoreboardManager = new BlackScoreboardManager(this);

        //THRREADS
        this.bossBarThread = new BossBarThread().runThread();
        this.sidebarThread = new SidebarUpdateThread().runThread();


        //CUSTOMITEMS
        this.customItemManager  = new CustomItemManagerImpl(this);

        //LOAD ITEMS TODO CHECK DATA TYPE
        try {
            customItemManager.setup();
        }catch (Exception e){
            e.printStackTrace();
        }
        //SIGN
        this.signFactory = new SignFactory(this);
        //MYSTERYBOXES

        //npcs



        specialItems = new SpecialItemImpl();

        //QUESTS
        new QuestStorage();
        SpigotPlugin.registerCommand(new QuestCommand());

        //remove entities
        for(World world : Bukkit.getWorlds()){
            for(Entity entity : world.getEntities()){
                if(entity instanceof Item){
                    final Item item = (Item) entity;
                    item.remove();
                    entity.remove();
                }
            }
        }

        this.oreStorage = new OreStorage(this);
        oreStorage.load();

        new OreThread(this).runTaskTimer(this,200L, 400L);

        //GUILDS
        this.guildManager = new GuildManagerImpl(this);

        //RANKS
        this.rankManager = new RankManager(this);

        rankManager.loadRanks();

        //CHAT
        this.chatOptionsManager = new ChatOptionsManager(this);
        //RPG CLASS EXECUTOR
        new PlayerNotClassExecutor(this).runThread();
        //TODO check sum !

        this.traderStorage = new TraderStorage();

        this.mapStorage = new MapStorage();

        mapStorage.load();
    }

    @Override
    public void onDisable() {
        bossBarThread.stopThread();

        sidebarThread.stopThread();


        Bukkit.getOnlinePlayers().forEach(player -> {
            final RPGUser user = getRpgUserManager().getUser(player.getUniqueId());

            user.update(false);
            getRpgUserManager().leftTheGame(player);

            player.kickPlayer(ChatUtil.fixColor("&8->> &4&l%X% &cSERWER JEST W TRAKCIE WYLACZANIA &4&l%X% &8<<-"));
        });

        for(Rank rank : getRankManager().getRanks().values()){
            rank.update();
        }

    }

    public static RPG getPlugin() {
        return plugin;
    }

    public RedisStorage getRedisStorage() {
        return redisStorage;
    }

    public RPGConfig getRpgConfig() {
        return rpgConfig;
    }

    public ItemManager getDropItemManager() {
        return dropItemManager;
    }

    private void registerListeners(Plugin plugin, Listener... listeners){
        for(Listener listener : listeners){
            pm.registerEvents(listener,plugin);
        }
    }

    public SpecialItemImpl getSpecialItems() {
        return specialItems;
    }


    public BossBarServiceStorage getBossBarServiceStorage() {
        return bossBarServiceStorage;
    }

    public RPGUserManager getRpgUserManager() {
        return rpgUserManager;
    }

    public MessageConfig getMessageConfig() {
        return messageConfig;
    }

    public BlackScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public TraderStorage getTraderStorage() {
        return traderStorage;
    }

    public DropItemConfig getDropItemConfig() {
        return dropItemConfig;
    }

    public CustomItemStorage getCustomItemStorage() {
        return customItemStorage;
    }

    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }

    public CustomItemManager getCustomItemManager() {
        return customItemManager;
    }

    public SignFactory getSignFactory() {
        return signFactory;
    }

    public ItemManager getBoxItemManager() {
        return boxItemManager;
    }


    public TagManager getTagManager() {
        return tagManager;
    }


    public MysteryBoxStorage getMysteryBoxStorage() {
        return mysteryBoxStorage;
    }

    public OreStorage getOreStorage() {
        return oreStorage;
    }

    public GuildManager getGuildManager() {
        return guildManager;
    }

    public GuildConfig getGuildConfig() {
        return guildConfig;
    }

    public RankManager getRankManager() {
        return rankManager;
    }

    public ChatOptionsManager getChatOptionsManager() {
        return chatOptionsManager;
    }

    public ChatConfig getChatConfig() {
        return chatConfig;
    }

    public MapStorage getMapStorage() {
        return mapStorage;
    }
}
