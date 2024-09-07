package pl.blackwater.rpg.sidebar;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import pl.blackwater.spigotplugin.util.ChatUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class BlackScoreboard {
    private final Player p;
    public final HashMap<Integer, String> scores = new HashMap<>();
    final private List<String> list = Arrays.asList("1","2","3","4","5","6","7","8","9","a","b","c","e","f","m","n");
    private final BlackScoreboardManager scoreboardManager;
    
    public BlackScoreboard(final BlackScoreboardManager scoreboardManager, final Player p, final String title, final String... values) {
        this.p = p;
        this.scoreboardManager = scoreboardManager;

        final Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        
        final Objective objective = scoreboard.registerNewObjective("test", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(title);

        ArrayUtils.reverse(values);

        for (String value : values) {
            objective.getScore(value).setScore(scores.size());
            scores.put(scores.size(), value);
        }

        p.setScoreboard(scoreboard);
        scoreboardManager.getScoreboards().put(p.getName(), this);
        
    }

    public BlackScoreboard(final BlackScoreboardManager scoreboardManager, final Player p, final String title){
        this.p = p;
        this.scoreboardManager = scoreboardManager;

        final Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        final Objective objective = scoreboard.registerNewObjective("test", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatUtil.fixColor(title));

        p.setScoreboard(scoreboard);
        scoreboardManager.getScoreboards().put(p.getName(), this);
    }

    public void addLine(final String value) {
    	final int size = scores.size();
        scores.put(size, ChatUtil.fixColor(value));
        p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(scores.get(size)).setScore(size);
    }

    public void updateLine(final int slot,final String value) {
        if (scores.get(slot) == null) {
            return;
        }
        p.getScoreboard().resetScores(scores.get(slot));
        p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(ChatUtil.fixColor(value)).setScore(slot);
        scores.put(slot, ChatUtil.fixColor(value));
    }

    public void addBlankLine(){
    	final int size = scores.size();
        scores.put(size, ChatUtil.fixColor("&" + list.get(size)));
        p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(scores.get(size)).setScore(size);
    }

    public void setBlanked(final int slot) {
        if (scores.get(slot) == null) {
            return;
        }

        p.getScoreboard().resetScores(scores.get(slot));
        scores.put(slot, ChatUtil.fixColor("&" +slot));
        p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(scores.get(slot)).setScore(slot);
    }

    public void setTitle(final String value) {
        p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(value);
    }


}