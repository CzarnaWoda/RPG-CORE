package pl.blackwater.rpg.commands;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.interfaces.Colors;
import pl.blackwater.rpg.util.EnchantManager;
import pl.blackwater.spigotplugin.spigot.commands.PlayerCommand;
import pl.blackwater.spigotplugin.util.ChatUtil;

public class EnchantCommand extends PlayerCommand implements Colors
{
    public EnchantCommand() {
        super("enchant", "nadawanie zaklec przedmiotom", "/enchant <zaklecie> [poziom]", "core.enchant");
    }
    
    public boolean onCommand(Player p, String[] args)
    {
      if ((args.length >= 1) && (args.length <= 2))
      {
        final ItemStack item = p.getItemInHand();
        final String enchantmentName = args[0];
        final Enchantment enchant = EnchantManager.get(enchantmentName);
        if (enchant == null) {
          return ChatUtil.sendMessage(p, WarningColor + "Blad" + SpecialSigns + ": " + WarningColor_2 + "Nie znaleziono podanego enchantu!");
        }
        int level = enchant.getMaxLevel();
        if (args.length == 2) {
          level = Integer.parseInt(args[1]);
        }
        item.addUnsafeEnchantment(enchant, level);
        return ChatUtil.sendMessage(p, MainColor + "Zaklecie " + ImportantColor + enchant.getName().toLowerCase().replace("_", " ") + MainColor + " zostalo dodane do przedmiotu w Twojej rece!");
      }
      return ChatUtil.sendMessage(p, RPG.getPlugin().getMessageConfig().getCommandUsageMessage().replace("{USAGE}",getUsage()));
    }
}
