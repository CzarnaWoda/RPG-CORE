package pl.blackwater.rpg.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.interfaces.Colors;
import pl.blackwater.rpg.util.PlayerInventoryUtil;
import pl.blackwater.spigotplugin.spigot.commands.PlayerCommand;
import pl.blackwater.spigotplugin.util.ChatUtil;

public class ClearCommand extends PlayerCommand implements Colors
{
    private final RPG plugin;
    public ClearCommand(RPG plugin) {
        super("clearinv", "Czyszczenie ekwipunku graczy", "/clearinv [gracz]", "core.clearinv", "clear", "clearinventory", "ci");
        this.plugin = plugin;
    }
    
    public boolean onCommand(Player sender, String[] args) {
        if (args.length != 1) {
        	PlayerInventoryUtil.ClearPlayerInventory(sender);
            return ChatUtil.sendMessage(sender, MainColor + "Twoj ekwipunek zostal " + ImportantColor + "wyczyszczony" + MainColor + "!");
        }
        if (!sender.hasPermission("core.clearinv.others")) {
            return ChatUtil.sendMessage(sender, WarningColor + "Blad" + SpecialSigns + ": " + WarningColor_2 + "Nie posiadasz uprawnien do tej komendy " + SpecialSigns + "(" +  WarningColor + "core.clearinv.others" + SpecialSigns + ")");
        }
        final Player o = Bukkit.getPlayer(args[0]);
        if(o == null){
            return ChatUtil.sendMessage(sender, plugin.getMessageConfig().getUnknownPlayerMessage());
        }
        return ChatUtil.sendMessage(sender, MainColor + "Wyczyszczono ekwipunek gracza " + ImportantColor + o.getDisplayName() + MainColor + "&7!");
    }
}
