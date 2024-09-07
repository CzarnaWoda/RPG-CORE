package pl.blackwater.rpg.npcs.tags;

import net.minecraft.server.v1_16_R3.ChatMessage;
import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_16_R3.Scoreboard;
import net.minecraft.server.v1_16_R3.ScoreboardTeam;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.rpg.guilds.Guild;
import pl.blackwater.rpg.guilds.GuildManager;
import pl.blackwater.rpg.managers.RPGUserManager;
import pl.blackwater.spigotplugin.util.ChatUtil;

import java.util.Optional;


public class TagManagerImpl implements TagManager {

    private final Scoreboard scoreboard;

    private final RPG plugin;

    public TagManagerImpl(RPG plugin){
        this.plugin = plugin;
        this.scoreboard = new Scoreboard();
    }


 
    public void updateTagForPlayer(Player p, ScoreboardAction action){
        ScoreboardTeam team = scoreboard.getPlayerTeam(p.getName());;
        RPGUser user = plugin.getRpgUserManager().getUser(p.getUniqueId());
        //final Rank rank = core.getRankManager().getRank(user.getRank());
        if (team == null) {
            team = scoreboard.createTeam(p.getName());
            scoreboard.addPlayerToTeam(p.getName(), team);
        }

        team.setPrefix(new ChatMessage(getPreparedGuildPrefix(user, user)));
        team.setDisplayName(new ChatMessage(""));
        //if(rank.isPrefixDisplay()){
           // team.setSuffix(new ChatMessage(ChatUtil.fixColor(" " + rank.getLabel())));
       // }

        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam(team, action.getValue());
        (((CraftPlayer) p).getHandle()).playerConnection.sendPacket(packet);

        for(Player target : Bukkit.getOnlinePlayers()){
            if(target != p){
                RPGUser targetUser = plugin.getRpgUserManager().getUser(target.getUniqueId());
                team.setPrefix(new ChatMessage(getPreparedGuildPrefix(targetUser, user)));
                packet = new PacketPlayOutScoreboardTeam(team, action.getValue());
                (((CraftPlayer) target).getHandle()).playerConnection.sendPacket(packet);

                ScoreboardTeam targetTeam = scoreboard.getPlayerTeam(target.getName());
                if(targetTeam != null){
                    targetTeam.setPrefix(new ChatMessage(getPreparedGuildPrefix(user, targetUser)));
                    PacketPlayOutScoreboardTeam inPacket = new PacketPlayOutScoreboardTeam(targetTeam, action.getValue());
                    (((CraftPlayer) p).getHandle()).playerConnection.sendPacket(inPacket);
                }
            }
        }
    }

    public String getPreparedGuildPrefix(RPGUser user, RPGUser target){
        GuildManager guildsManager = plugin.getGuildManager();
        final Optional<Guild> targetGuild = guildsManager.getGuild(target);
        if(!targetGuild.isPresent()) return "";

        final Optional<Guild> userGuild = guildsManager.getGuild(user);
        if(userGuild.isPresent()){
            if(userGuild.get().equals(targetGuild.get()) || user.equals(target)) {
                return ChatUtil.fixColor("&a" + userGuild.get().getTag() + " ");
            }
        }

        return ChatUtil.fixColor("&c" + targetGuild.get().getTag() + " ");
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

}
