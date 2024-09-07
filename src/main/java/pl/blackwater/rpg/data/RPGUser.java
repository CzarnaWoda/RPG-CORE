package pl.blackwater.rpg.data;


import io.netty.util.internal.ConcurrentSet;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.enums.RPGClass;
import pl.blackwater.rpg.mine.enums.OreType;
import pl.blackwater.rpg.npcs.quests.Quest;
import pl.blackwater.rpg.npcs.quests.QuestData;
import pl.blackwater.rpg.npcs.quests.enums.QuestType;
import pl.blackwater.rpg.ranks.Rank;
import pl.blackwater.rpg.storage.enums.Stats;
import pl.blackwater.rpg.util.RPGUtil;
import pl.blackwater.spigotplugin.util.GsonUtil;
import pl.blackwater.spigotplugin.util.MathUtil;

import java.util.*;

@Setter
@Getter
public class RPGUser {

    private UUID uuid;
    private String lastName;
    private int money;
    private int level;
    private double exp;

    private GameMode gameMode;
    private boolean fly;
    private boolean god;

    private RPGClass rpgClass;

    //NUMBERS
    private int kills;
    private int deaths;
    private UUID lastKiller;
    private UUID lastVictim;

    //STATS

    private int STRENGTH = 0;
    private int DEXTERITY = 0;
    private int INTELLIGENCE = 0;
    private int STAMINA = 0;

    private float averageMonsterDamage = 0.0F;
    private float averageHumanDamage = 0.0F;
    private float averageCreatureDamage = 0.0F;
    private float additionalDamageOfCriticalChance = 0.0F;
    private float additionalCriticalChance = 0.0F;

    //JEWELERY
    private final ItemStack[] jewelery;

    private ChatUser chatUser = new ChatUser();

    //QUESTS
    private Set<QuestData> questDataSet = new ConcurrentSet<>();
    private List<ItemStack[]> storages = new ArrayList<>();

    private Map<OreType,Integer> mineOres;

    private long lastLeftTime;

    private String rankName = "GRACZ";

    private int storageLevel = 0;

    private List<UUID> discoveredNpc = new ArrayList<>();


    public RPGUser(UUID uuid, String lastName){
        this.uuid = uuid;
        this.lastName = lastName;
        this.money = 0;
        this.level = 1;
        this.exp = 0;
        this.rpgClass = RPGClass.BEGINNER;
        this.jewelery = new ItemStack[9];

        this.questDataSet.add(new QuestData("DEFAULT", QuestType.GATHERING,1,1));

        mineOres = new HashMap<>();
        mineOres.put(OreType.DIAMENTU,0);
        mineOres.put(OreType.SZMARAGDU,0);
        mineOres.put(OreType.MIEDZI,0);
        mineOres.put(OreType.METALU,0);
        mineOres.put(OreType.WEGLA,0);
        mineOres.put(OreType.AMETYSTU,0);
        mineOres.put(OreType.RUBINU,0);
        mineOres.put(OreType.KWARCU,0);
        mineOres.put(OreType.CZARNEGO_METALU,0);

        storages.addAll(Arrays.asList(new ItemStack[53],new ItemStack[53],new ItemStack[53],new ItemStack[53],new ItemStack[53],new ItemStack[53],new ItemStack[53],new ItemStack[53],new ItemStack[53]));

        this.lastLeftTime = System.currentTimeMillis();

        this.discoveredNpc.add(UUID.randomUUID());

    }

    public void insert(boolean async){
        if(async){
            RPG.getPlugin().getRedisStorage().RPG_USERS.putAsync(uuid,GsonUtil.toJson(this));
        }else {
            RPG.getPlugin().getRedisStorage().RPG_USERS.put(uuid, GsonUtil.toJson(this));
        }
    }

    public void update(boolean async){
        if(async){
            RPG.getPlugin().getRedisStorage().RPG_USERS.putAsync(uuid,GsonUtil.toJson(this));
        }else {
            RPG.getPlugin().getRedisStorage().RPG_USERS.put(uuid, GsonUtil.toJson(this));
        }
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public double getExp() {
        return MathUtil.round(exp,2);
    }

    public int getLevel() {
        return level;
    }

    public int getMoney() {
        return money;
    }

    public int getStorageLevel() {
        return storageLevel;
    }
    public void addStorageLevel(int add){
        this.storageLevel+=add;
        update(true);
    }

    public String getLastName() {
        return lastName;
    }

    public UUID getUuid() {
        return uuid;
    }
    public void addExp(double exp){
        if(level != RPGUtil.getMaxLevel()) {
            this.exp += exp;
        }
        if(this.exp >= this.getExpToLevel()){
            removeExp(this.getExpToLevel());
            addLevel(1);
        }
    }
    public void addMoney(int money){
        this.money += money;
    }
    public void removeExp(double exp){
        this.exp -= exp;
    }
    public void addLevel(int level){
        if(level != RPGUtil.getMaxLevel()) {
            this.level += level;
        }
    }

    public RPGClass getRpgClass() {
        return rpgClass;
    }

    public void setRpgClass(RPGClass rpgClass) {
        this.rpgClass = rpgClass;
    }

    public double getExpToLevel(){
        if(this.level < RPGUtil.MAX_LEVEL) {
            return RPGUtil.LEVEL_SCALE.get(level);
        }else{
            return Double.MAX_VALUE;
        }
    }

    public void addQuestData(QuestData data){
        getQuestDataSet().add(data);
    }
    public boolean hasQuestData(Quest quest){
        for(QuestData questData : getQuestDataSet()){
            if(questData.getQuestName().equals(quest.getName())){
                return true;
            }
        }
        return false;
    }
    public void addDeaths(int deaths){
        this.deaths += deaths;
    }
    public void addKills(int kills){
        this.kills += kills;
    }

    public List<ItemStack[]> getStorages() {
        return storages;
    }
    public ItemStack[] getStorage(int i){
        return getStorage(i);
    }

    public void addMinedOre(OreType type,int amount){
        int before = mineOres.get(type);
        before+=amount;
        mineOres.put(type,before);
    }
    public void removeMoney(int money){
        this.money -= money;
    }

    public Rank getRank(){
        return RPG.getPlugin().getRankManager().getRank(getRankName());
    }

    public Player getPlayer(){
        return Bukkit.getPlayer(uuid);
    }

    public void addStat(Stats statType, int amount){
        switch (statType){
            case STAMINA:
                this.STAMINA+=amount;
                break;
            case STRENGTH:
                this.STRENGTH += amount;
                break;
            case DEXTERITY:
                this.DEXTERITY += amount;
                break;
            case INTELLIGENCE:
                this.INTELLIGENCE += amount;
                break;
            default:
                throw new IllegalArgumentException("Dont recognize stat type in addStat method");
        }
    }

    public List<UUID> getDiscoveredNpc() {
        return discoveredNpc;
    }
    public void addDiscoveredNpc(UUID npcUUID){
        this.discoveredNpc.add(npcUUID);
    }
}
