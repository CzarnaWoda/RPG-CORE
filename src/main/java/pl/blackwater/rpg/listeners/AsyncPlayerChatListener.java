package pl.blackwater.rpg.listeners;

import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.chat.ChatOptionsManager;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.rpg.guilds.Guild;
import pl.blackwater.rpg.ranks.Rank;
import pl.blackwater.spigotplugin.util.ChatUtil;

import java.awt.*;
import java.util.regex.Pattern;

public class AsyncPlayerChatListener implements Listener {

    private final RPG plugin;

    public AsyncPlayerChatListener(RPG plugin){
        this.plugin = plugin;
    }
    private static final Pattern URL_PATTERN = Pattern.compile("((?:(?:https?)://)?[\\w-_.]{2,})\\.([a-zA-Z]{2,3}(?:/\\S+)?)");

    private static final Pattern AD_PATTERN = Pattern.compile("\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b|\\bhttps?://\\S+\\b");
    private static final Pattern IPPATTERN = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    private static final Pattern BANNED_FLAMEWAR = Pattern.compile(".*(kurva|hui|gale|chuj|chuja|chujek|chuju|chujem|chujnia|chujowy|chujowa|chujowe|cipa|cipe|cipie|dojebac|dojebal|dojebala|dojebalem|dojebalam|dojebie|dopieprzac|dopierdalac|dopierdala|dopierdalal|dopierdalala|dopierdolil|dopierdole|dopierdoli|dopierdalajacy|dopierdolic|dupa|dupie|dupcia|dupeczka|dupy|dupe|huj|hujek|hujnia|huja|huje|hujem|huju|jebac|jebak|jebaka|jebal|jebany|jebane|jebanka|jebanko|jebankiem|jebanym|jebanej|jebana|jebani|jebanych|jebanymi|jebcie|jebiacy|jebiaca|jebiacego|jebiacej|jebia|jebie|jebliwy|jebnac|jebnal|jebna|jebnela|jebnie|jebnij|jebut|koorwa|korwa|kurestwo|kurew|kurewskiej|kurewska|kurewsko|kurewstwo|kurwa|kurwaa|kurwe|kurwie|kurwiska|kurwo|kurwy|kurwach|kurwami|kurewski|kurwiarz|kurwi\u0105cy|kurwica|kurwic|kurwido\u0142ek|kurwik|kurwiki|kurwiszcze|kurwiszon|kurwiszona|kurwiszonem|kurwiszony|kutas|kutasa|kutasie|kutasem|kutasy|kutasow|kutasach|kutasami|matkojebcy|matkojebca|matkojebcami|matkojebcach|najebac|najebal|najebane|najebany|najebana|najebie|najebia|naopierdalac|naopierdalal|naopierdalala|napierdalac|napierdalajacy|napierdolic|nawpierdalac|nawpierdalal|nawpierdalala|obsrywac|obsrywajacy|odpieprzac|odpieprzy|odpieprzyl|odpieprzyla|odpierdalac|odpierdol|odpierdolil|odpierdolila|odpierdalajacy|odpierdalajaca|odpierdolic|odpierdoli|opieprzaj\u0105cy|opierdalac|opierdala|opierdalajacy|opierdol|opierdolic|opierdoli|opierdola|piczka|pieprzniety|pieprzony|pierdel|pierdlu|pierdolacy|pierdolaca|pierdol|pierdole|pierdolenie|pierdoleniem|pierdoleniu|pierdolec|pierdola|pierdolicie|pierdolic|pierdolil|pierdolila|pierdoli|pierdolniety|pierdolisz|pierdolnac|pierdolnal|pierdolnela|pierdolnie|pierdolnij|pierdolnik|pierdolona|pierdolone|pierdolony|pierdz\u0105cy|pierdziec|pizda|pizde|pizdzie|pizdnac|pizdu|podpierdalac|podpierdala|podpierdalajacypodpierdolic|podpierdoli|pojeb|pojeba|pojebami|pojebanego|pojebanemu|pojebani|pojebany|pojebanych|pojebanym|pojebanymi|pojebem|pojebac|pojebalo|popierdala|popierdalac|popierdolic|popierdoli|popierdolonego|popierdolonemu|popierdolonym|popierdolone|popierdoleni|popierdolony|porozpierdala|porozpierdalac|poruchac|przejebane|przejebac|przyjebali|przepierdalac|przepierdala|przepierdalajacy|przepierdalajaca|przepierdolic|przyjebac|przyjebie|przyjebala|przyjebal|przypieprzac|przypieprzajacy|przypieprzajaca|przypierdalac|przypierdala|przypierdoli|przypierdalajacy|przypierdolic|qrwa|rozjebac|rozjebie|rozjeba\u0142a|rozpierdalac|rozpierdala|rozpierdolic|rozpierdole|rozpierdoli|rozpierducha|skurwiel|skurwiela|skurwielem|skurwielu|skurwysyn|skurwysynow|skurwysyna|skurwysynem|skurwysynu|skurwysyny|skurwysynski|skurwysynstwo|spieprzac|spieprza|spieprzaj|spieprzajcie|spieprzaja|spieprzajacy|spieprzajaca|spierdalac|spierdala|spierdalal|spierdalalcie|spierdalala|spierdalajacy|spierdolic|spierdoli|spierdol\u0105|spierdola|srac|srajacy|srajac|sraj|sukinsyn|sukinsyny|sukinsynom|sukinsynowi|sukinsynow|ujebac|ujebal|ujebana|ujebany|ujebie|ujeba\u0142a|ujebala|upierdalac|upierdala|upierdolic|upierdoli|upierdola|upierdoleni|wjebac|wjebie|wjebia|wjebiemy|wjebiecie|wkurwiac|wkurwial|wkurwiajacy|wkurwiajaca|wkurwi|wkurwiali|wkurwimy|wkurwicie|wkurwic|wpierdalac|wpierdalajacy|wpierdol|wpierdolic|wpizdu|wyjebali|wyjebac|wyjebia|wyjebiesz|wyjebie|wyjebiecie|wyjebiemy|wypieprzac|wypieprza|wypieprzal|wypieprzala|wypieprzy|wypieprzyla|wypieprzyl|wypierdal|wypierdalac|wypierdala|wypierdalaj|wypierdalal|wypierdalala|wypierdolic|wypierdoli|wypierdolimy|wypierdolicie|wypierdola|wypierdolili|wypierdolil|wypierdolila|zajebac|zajebie|zajebia|zajebial|zajebiala|zajebali|zajebana|zajebani|zajebane|zajebany|zajebanych|zajebanym|zajebanymi|zajebiste|zajebisty|zajebistych|zajebista|zajebistym|zajebistymi|zajebiscie|zapieprzyc|zapieprzy|zapieprzyl|zapieprzyla|zapieprza|zapieprz|zapieprzymy|zapieprzycie|zapieprzysz|zapierdala|zapierdalac|zapierdalaja|zapierdalaj|zapierdalajcie|zapierdalala|zapierdalali|zapierdalajacy|zapierdolic|zapierdoli|zapierdolil|zapierdolila|zapierdola|zapierniczac|zapierniczajacy|zasrac|zasranym|zasrywajacy|zesrywac|zesrywajac|zjebac|zjebal|zjebala|zjebana|zjebia|zjebali|zjeby).*");
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
        if(e.isCancelled())
            return;
        if(e.getMessage().contains("%"))
        {
            e.setCancelled(true);
            return;
        }
        final ChatOptionsManager manager = plugin.getChatOptionsManager();
        Player p = e.getPlayer();
        if(!manager.getStatus() && !p.hasPermission("rpg.chat.lock.bypass")) {
            ChatUtil.sendMessage(p, "&cChat na serwerze jest aktualnie &4wylaczony");
            e.setCancelled(true);
            return;
        }
        if(manager.getPremiumStatus() && !p.hasPermission("core.chat.vip")) {
            ChatUtil.sendMessage(p, "&4Chat na serwerze jest aktualnie dostepny tylko dla rang &ePREMIUM");
            e.setCancelled(true);
            return;
        }
        if(!p.hasPermission("core.chat.bypass") && (URL_PATTERN.matcher(e.getMessage()).find() || IPPATTERN.matcher(e.getMessage()).find()) || AD_PATTERN.matcher(e.getMessage()).find()) {
            ChatUtil.sendMessage(p, "&4Blad: &cTwoja wiadomosc zawiera niedozwolone tresci! &8(&eREKLAMA&8)");
            e.setCancelled(true);
            for(final Player admin : Bukkit.getOnlinePlayers()) {
                if(admin.hasPermission("rpg.admin")) {
                    final TextComponent textComponent = new TextComponent(ChatUtil.hexColor("#f75c5cKLIKNIJ ABY SIE TELEPORTOWAC"));
                    textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/tp" + p.getName()));
                    textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("#f75c9a"+e.getMessage()).create()));
                    final BaseComponent[] baseComponents = new ComponentBuilder(ChatUtil.hexColor("#a353db☙ #f75c9aWYKRYTO REKLAME ")).append(textComponent).create();

                    final TextComponent c = new TextComponent(ChatUtil.hexColor("REKLAME"));
                    c.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/ban" + p.getName() + " REKLAMA"));
                    c.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("#f75c9aBAN ZA REKLAME").create()));
                    final BaseComponent[] baseComponents1 = new ComponentBuilder(ChatUtil.hexColor("#a353db☙ ZBANUJ ZA #f75c5c")).append(c).create();

                    final TextComponent c1 = new TextComponent(ChatUtil.hexColor("REKLAME"));
                    c1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/kick" + p.getName() + " REKLAMA"));
                    c1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("#f75c9aKICK ZA REKLAME").create()));
                    final BaseComponent[] baseComponents2 = new ComponentBuilder(ChatUtil.hexColor("#a353db☙ WYRZUC ZA #f75c5c")).append(c1).create();

                    p.spigot().sendMessage(baseComponents);
                    p.spigot().sendMessage(baseComponents1);
                    p.spigot().sendMessage(baseComponents2);
                }
            }
            return;
        }
        if(!p.hasPermission("core.chat.bypass") && (BANNED_FLAMEWAR.matcher(e.getMessage()).find())) {
            ChatUtil.sendMessage(p, "&4Blad: &cTwoja wiadomosc zawiera niedozwolone tresci! &8(&eOBRAZA&8)");
            e.setCancelled(true);
            for(final Player admin : Bukkit.getOnlinePlayers()) {
                if(admin.hasPermission("rpg.admin")) {
                    final TextComponent textComponent = new TextComponent(ChatUtil.hexColor("#f75c5cKLIKNIJ ABY SIE TELEPORTOWAC"));
                    textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/tp" + p.getName()));
                    textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("#f75c9a"+e.getMessage()).create()));
                    final BaseComponent[] baseComponents = new ComponentBuilder(ChatUtil.hexColor("#a353db☙ #f75c9aWYKRYTO OBRAZE ")).append(textComponent).create();

                    final TextComponent c = new TextComponent(ChatUtil.hexColor("OBRAZE"));
                    c.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/ban" + p.getName() + " OBRAZA"));
                    c.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("#f75c9aBAN ZA OBRAZE").create()));
                    final BaseComponent[] baseComponents1 = new ComponentBuilder(ChatUtil.hexColor("#a353db☙ ZBANUJ ZA #f75c5c")).append(c).create();

                    final TextComponent c1 = new TextComponent(ChatUtil.hexColor("OBRAZE"));
                    c1.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/kick" + p.getName() + " OBRAZA"));
                    c1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("#f75c9aKICK ZA OBRAZE").create()));
                    final BaseComponent[] baseComponents2 = new ComponentBuilder(ChatUtil.hexColor("#a353db☙ WYRZUC ZA #f75c5c")).append(c1).create();

                    p.spigot().sendMessage(baseComponents);
                    p.spigot().sendMessage(baseComponents1);
                    p.spigot().sendMessage(baseComponents2);
                }
            }
            return;
        }
        if(!manager.canSendMessage(p)) {
            final int elapsed = (int) (5 - ((System.currentTimeMillis() - manager.getTimes().get(p)) / 1000L));
            ChatUtil.sendMessage(p, "&4Blad: &cNa chacie bedziesz mogl napisac dopiero za: &a" + elapsed + "&cs");
            e.setCancelled(true);
            return;
        }
        final RPGUser user = plugin.getRpgUserManager().getUser(p.getUniqueId());
        final Guild g = plugin.getGuildManager().getSimpleGuild(p);
        Rank rank = plugin.getRankManager().getRank(user.getRankName());
        String globalFormat = ChatUtil.hexColor(plugin.getChatConfig().getGlobalFormat())
                .replace("{LEVEL}", String.valueOf(user.getLevel()))
                .replace("{TAG}", (g != null ? plugin.getChatConfig().getGuildFormat().replace("{TAG}", g.getTag()) : ""))
                .replace("{PREFIX}", rank.getPrefix())
                .replace("{SUFFIX}", rank.getSuffix())
                .replace("{PLAYER}", p.getDisplayName())
                .replace("{MESSAGE}", e.getMessage().replaceAll("&","").replace("#",""));
        String aglobalFormat = ChatUtil.hexColor(plugin.getChatConfig().getAdminGlobalFormat()
                .replace("{LEVEL}", String.valueOf(user.getLevel()))
                .replace("{TAG}", (g != null ? plugin.getChatConfig().getGuildFormat().replace("{TAG}", g.getTag()) : ""))
                .replace("{PREFIX}", rank.getPrefix())
                .replace("{SUFFIX}", rank.getSuffix())
                .replace("{PLAYER}", p.getDisplayName())
                .replace("{MESSAGE}", e.getMessage().replaceAll("&","")));
        if(p.hasPermission("rpg.chat.admin")) {
            e.setFormat(aglobalFormat);
            for(Player player : Bukkit.getOnlinePlayers()){
                ChatUtil.sendMessage(player,aglobalFormat);
            }
            e.setCancelled(true);
        }
        else {
            e.setFormat(globalFormat);
            e.setFormat(aglobalFormat);
            for(Player player : Bukkit.getOnlinePlayers()){
                ChatUtil.sendMessage(player,globalFormat);
            }
            e.setCancelled(true);
        }
        manager.getTimes().put(p, System.currentTimeMillis());
    }
}
