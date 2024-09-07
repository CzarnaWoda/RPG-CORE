package pl.blackwater.rpg.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.rpg.interfaces.Colors;
import pl.blackwater.rpg.util.Util;
import pl.blackwater.spigotplugin.spigot.commands.Command;
import pl.blackwater.spigotplugin.util.ChatUtil;

public class GamemodeCommand extends Command implements Colors
{
    private final RPG rpg;
    public GamemodeCommand(RPG rpg) {
        super("gamemode", "Zmiana trybu gry graczy", "/gamemode [gracz] <tryb>", "core.gamemode", "gm", "gmode");
        
        this.rpg = rpg;
    }
    
    private void setGamemode(Player p, GameMode mode, CommandSender changer) {
        RPGUser u = rpg.getRpgUserManager().getUser(p.getUniqueId());
        if (u == null) {
            return;
        }
        p.setGameMode(mode);
        p.setAllowFlight(mode.equals(GameMode.CREATIVE));
        u.setGameMode(mode);
        u.setFly(mode.equals(GameMode.CREATIVE));
        u.setGod(mode.equals(GameMode.CREATIVE));
        if (changer == null) {
            ChatUtil.sendMessage(p, MainColor + "Twoj tryb gry zostal zmieniony na " + ImportantColor + mode.toString().toLowerCase() + MainColor + "!");
        }
        else {
            String c = changer.getName().equalsIgnoreCase("CONSOLE") ? "konsole" : changer.getName();
            ChatUtil.sendMessage(p, MainColor + "Twoj tryb gry zostal zmieniony na " + ImportantColor + mode.toString().toLowerCase() + MainColor + " przez " + ImportantColor + c + MainColor + "!");
            ChatUtil.sendMessage(changer, MainColor + "Tryb gry gracza " + ImportantColor + p.getName() + MainColor + " zostal zmieniony na " + ImportantColor + mode.toString().toLowerCase() + MainColor + "!");
        }
    }
    
    public boolean onExecute(CommandSender sender, String[] args) {
        String[] survival = { "0", "s", "survival" };
        String[] creative = { "1", "c", "creative" };
        String[] adventure = { "2", "a", "adventure" };
        String [] spectator = { "3" ,"s" ,"spectator"};
        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (args.length == 1) {
                String mode = args[0];
                if (Util.containsIgnoreCase(survival, mode)) {
                    setGamemode(p, GameMode.SURVIVAL, null);
                }
                else if (Util.containsIgnoreCase(creative, mode)) {
                    setGamemode(p, GameMode.CREATIVE, null);
                }
                else if (Util.containsIgnoreCase(adventure, mode)) {
                    setGamemode(p, GameMode.ADVENTURE, null);
                }
                else if (Util.containsIgnoreCase(spectator, mode)){
                	setGamemode(p, GameMode.SPECTATOR, null);
                }
                else {
                    ChatUtil.sendMessage(sender, WarningColor + "Blad" + SpecialSigns + ": " + WarningColor_2 + "Nie poprawny tryb gamemode");
                }
            }
            else {
                if (args.length != 2) {
                    return ChatUtil.sendMessage(sender, rpg.getMessageConfig().getUnknownPlayerMessage());
                }
                if (!sender.hasPermission("core.gamemode.others")) {
                    return ChatUtil.sendMessage(sender, WarningColor + "Blad" + SpecialSigns + ":" + WarningColor_2 + " Nie masz praw do tej komendy! " + SpecialSigns + "(" + WarningColor + "core.gamemode.others" +  SpecialSigns + ")");
                }
                Player op = Bukkit.getPlayer(args[0]);
                if (op == null) {
                    return ChatUtil.sendMessage(sender, rpg.getMessageConfig().getUnknownPlayerMessage());
                }
                String mode2 = args[1];
                if (Util.containsIgnoreCase(survival, mode2)) {
                    setGamemode(op, GameMode.SURVIVAL, p);
                }
                else if (Util.containsIgnoreCase(creative, mode2)) {
                    setGamemode(op, GameMode.CREATIVE, p);
                }
                else if (Util.containsIgnoreCase(adventure, mode2)) {
                    setGamemode(op, GameMode.ADVENTURE, p);
                }
                else if (Util.containsIgnoreCase(spectator, mode2)){
                	setGamemode(op, GameMode.SPECTATOR, p);
                }
                else {
                    ChatUtil.sendMessage(sender, WarningColor + "Blad " +  SpecialSigns + ": " + WarningColor_2 + "Nie poprawny tryb gamemode");
                }
            }
            return true;
        }
        if (args.length != 2) {
            return ChatUtil.sendMessage(sender, rpg.getMessageConfig().getUnknownPlayerMessage());
        }
        Player op2 = Bukkit.getPlayer(args[0]);
        if (op2 == null) {
            return ChatUtil.sendMessage(sender, rpg.getMessageConfig().getUnknownPlayerMessage());
        }
        String mode = args[1];
        if (Util.containsIgnoreCase(survival, mode)) {
            setGamemode(op2, GameMode.SURVIVAL, sender);
        }
        else if (Util.containsIgnoreCase(creative, mode)) {
            setGamemode(op2, GameMode.CREATIVE, sender);
        }
        else if (Util.containsIgnoreCase(adventure, mode)) {
            setGamemode(op2, GameMode.ADVENTURE, sender);
        }
        else if (Util.containsIgnoreCase(spectator, mode)){
        	setGamemode(op2, GameMode.SPECTATOR, sender);
        }
        else {
            ChatUtil.sendMessage(sender, WarningColor + "Blad " +  SpecialSigns + ": " + WarningColor_2 + "Nie poprawny tryb gamemode");
        }
        return true;
    }
}
