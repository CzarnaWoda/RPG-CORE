package pl.blackwater.rpg.sidebar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.rpg.data.combat.Combat;
import pl.blackwater.rpg.data.combat.CombatManager;
import pl.blackwater.spigotplugin.util.ChatUtil;

import java.util.HashMap;

public class BlackScoreboardManager {
	private final RPG plugin;
    private final HashMap<String, BlackScoreboard> scoreboards = new HashMap<>();
    private final HashMap<Integer, String> lines = new HashMap<>();

    public BlackScoreboardManager(RPG plugin){
    	this.plugin = plugin;
	}
    public BlackScoreboard getScoreboard(final Player player){
        return scoreboards.get(player.getName());
    }
    public void removeScoreBoard(final Player player){
    	if(scoreboards.get(player.getName()) != null){
    	scoreboards.remove(player.getName());
		player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
    	}
    }
    public void createScoreBoard(final Player p){
    	final BlackScoreboard sidebar = new BlackScoreboard(this,p, ChatUtil.fixColor("&4⚔ &c&lSTATYSTYKI &4⚔"));

		final RPGUser user = plugin.getRpgUserManager().getUser(p.getUniqueId());
		sidebar.addBlankLine();
		sidebar.addLine(ChatUtil.fixColor("&a$ &2KASA: &a" + user.getMoney()));
		sidebar.addLine(ChatUtil.fixColor("&e⚔ &6EXP: &e" + user.getExp() + "/" + user.getExpToLevel()));//4
		sidebar.addLine(ChatUtil.fixColor("&4★ &cLEVEL: &4" + user.getLevel()));//4
		sidebar.addBlankLine();
	}
	public void updateEventBoard(){
		for(Player p : Bukkit.getOnlinePlayers()){
			if(getScoreboard(p) != null) {
				removeScoreBoard(p);
			}
			createScoreBoard(p);

		}
	}
	public void updateScoreBoard(final Player player){
		final BlackScoreboard sc = getScoreboard(player);

		if(sc != null){
			final RPGUser user = plugin.getRpgUserManager().getUser(player.getUniqueId());
			sc.updateLine(1,"&a$ &2KASA: &a" + user.getMoney());
			sc.updateLine(2,"&e⚔ &6EXP: &e" + user.getExp() + "/" + user.getExpToLevel());
			sc.updateLine(3, "&4★ &cLEVEL: &4" + user.getLevel());
		}
	}

	public HashMap<String, BlackScoreboard> getScoreboards() {
		return scoreboards;
	}

	public HashMap<Integer, String> getLines() {
		return lines;
	}

	public RPG getPlugin() {
		return plugin;
	}
}