package pl.blackwater.rpg.commands;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.bossbar.BossBarService;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.rpg.data.enums.RPGClass;
import pl.blackwater.rpg.items.ItemType;
import pl.blackwater.rpg.storage.RPGItem;
import pl.blackwater.rpg.util.Base64Util;
import pl.blackwater.rpg.util.RPGUtil;
import pl.blackwater.spigotplugin.spigot.commands.PlayerCommand;
import pl.blackwater.spigotplugin.util.ChatUtil;

import java.io.IOException;

public class DEBUGCommand extends PlayerCommand {
    public DEBUGCommand() {
        super("debug", "debug", "123", "rpg.debug", "debug");
    }

    @Override
    public boolean onCommand(Player player, String[] strings) {
        RPG.getPlugin().getRpgUserManager().getUser(player.getUniqueId()).setRpgClass(RPGClass.BEGINNER);

        return true;
    }
}
