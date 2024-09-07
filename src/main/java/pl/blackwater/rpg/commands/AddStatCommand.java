package pl.blackwater.rpg.commands;


import org.bukkit.command.CommandSender;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.data.RPGUser;
import pl.blackwater.spigotplugin.spigot.commands.Command;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.Util;

public class AddStatCommand extends Command {

    private final RPG plugin;
    public AddStatCommand(RPG plugin) {
        super("addstat", "Dodawanie statystyk dla user'a", "/addstat [user] [money/level] [value]", "rpg.addstat", "statadd","add");
        this.plugin = plugin;
    }

    @Override
    public boolean onExecute(CommandSender commandSender, String[] strings) {
        if(strings.length != 3){
            return ChatUtil.sendMessage(commandSender,"&4Poprawne uzycie: &c" + getUsage());
        }
        int value = 0;
        if(Util.isInteger(strings[2])){
            value = Integer.parseInt(strings[2]);
        }
        final String valueType = strings[1];
        final RPGUser user =  plugin.getRpgUserManager().getUser(strings[0]);
        if(user == null){
            return ChatUtil.sendMessage(commandSender,"&4Blad: &cUzytkownik nie istnieje w bazie danych!");
        }
        switch (valueType){
            case "money":
                user.addMoney(value);
                return ChatUtil.sendMessage(commandSender,ChatUtil.hexColor("#e86aeb⚒ #eb6abcDodales #e86aeb" +  value + " #eb6abcdla wartosci #e86aeb" + valueType.toUpperCase() + " #eb6abcdla uzytkownika #e86aeb" + user.getLastName()));
            case "level":
                user.addLevel(value);
                return ChatUtil.sendMessage(commandSender,ChatUtil.hexColor("#e86aeb⚒ #eb6abcDodales #e86aeb" +  value + " #eb6abcdla wartosci #e86aeb" + valueType.toUpperCase() + " #eb6abcdla uzytkownika #e86aeb" + user.getLastName()));
            default:
                return ChatUtil.sendMessage(commandSender,"&4Poprawne uzycie: &c" + getUsage());
        }
    }
}
