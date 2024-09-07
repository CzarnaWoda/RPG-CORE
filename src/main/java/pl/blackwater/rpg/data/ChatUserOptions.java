package pl.blackwater.rpg.data;

import org.bukkit.Material;
import pl.blackwater.rpg.RPG;
import pl.blackwater.spigotplugin.spigot.inventory.actions.IAction;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.ItemBuilder;

public enum ChatUserOptions {


    FIGHT_MESSAGE_ON_CHAT(ChatUtil.fixColor("&c⚔ &eWIADOMOSCI O WALCE NA CHACIE &c⚔"),Material.DIAMOND_SWORD),
    FIGHT_MESSAGE_ON_BOSSBAR(ChatUtil.fixColor("&c⚔ &eWIADOMOSCI O WALCE NA BOSSBAR &c⚔"),Material.GOLDEN_SWORD),

    MYSTERYBOX_GIVEALL_MESSAGE_CHAT(ChatUtil.fixColor("&e☘ " + ChatUtil.hexColor("#E09D1C") + "WIADOMOSCI O OTRZYMANIU SKRZYNKI &8("+ ChatUtil.hexColor("#E09D1C") +"GIVEALL&8) &e☘"),Material.CHEST),

    MYSTERYBOX_CHAT_MESSAGE(ChatUtil.fixColor(ChatUtil.hexColor("#AA54EB") + "⚅ " + ChatUtil.hexColor("#F74EFE") + "WIADOMOSCI O DROPIE Z SKRZYNEK " + ChatUtil.hexColor("#AA54EB") + "⚅"),Material.DIAMOND_BLOCK),


    DROP_FROM_MOB_CHAT(ChatUtil.fixColor(ChatUtil.hexColor("#8C09B1☠ #E189FAWIADOMOSCI O DROPIE Z MOBOW #8C09B1☠")),Material.LEATHER),

    BROADCAST_MESSAGE_CHAT(ChatUtil.fixColor(ChatUtil.hexColor("#FFF691⚡ #B4F2C3WIADOMOSCI GLOBALNE #FFF691⚡")),Material.BEACON),
    GUILD_MESSAGE_CHAT(ChatUtil.hexColor("#9363e0⚔ #5033b8WIADOMOSCI GILDYJNE GLOBALNE #9363e0⚔"),Material.DRAGON_EGG);
    private final String guiTitle;
    private final Material guiMaterial;


    ChatUserOptions(String guiTitle, Material guiMaterial) {
        this.guiMaterial = guiMaterial;
        this.guiTitle = guiTitle;
    }


    public Material getGuiMaterial() {
        return guiMaterial;
    }

    public String getGuiTitle() {
        return guiTitle;
    }
    public static boolean getBooleanAndToggle(ChatUserOptions options,ChatUser user){
        switch (options){
            case FIGHT_MESSAGE_ON_CHAT:
                user.setFightMessageOnChat(!user.isFightMessageOnChat());
                return user.isFightMessageOnChat();
            case FIGHT_MESSAGE_ON_BOSSBAR:
                user.setFightBossBossBar(!user.isFightBossBossBar());
                return user.isFightBossBossBar();
            case MYSTERYBOX_GIVEALL_MESSAGE_CHAT:
                user.setMysteryBoxGiveAllMessageChat(!user.isMysteryBoxGiveAllMessageChat());
                return user.isMysteryBoxGiveAllMessageChat();
            case MYSTERYBOX_CHAT_MESSAGE:
                user.setMysteryBoxMessageChat(!user.isMysteryBoxMessageChat());
                return user.isMysteryBoxMessageChat();
            case DROP_FROM_MOB_CHAT:
                user.setDropFromMobMessageChat(!user.isDropFromMobMessageChat());
                return user.isDropFromMobMessageChat();
            case BROADCAST_MESSAGE_CHAT:
                user.setBroadcastMessageChat(!user.isBroadcastMessageChat());
                return user.isBroadcastMessageChat();
            case GUILD_MESSAGE_CHAT:
                user.setGuildMessageChat(!user.isGuildMessageChat());
                return user.isGuildMessageChat();
            default:
                return false;
        }
    }
    public static boolean getBoolean(ChatUserOptions options,ChatUser user){
        switch (options){
            case FIGHT_MESSAGE_ON_CHAT:
                return user.isFightMessageOnChat();
            case FIGHT_MESSAGE_ON_BOSSBAR:
                return user.isFightBossBossBar();
            case MYSTERYBOX_GIVEALL_MESSAGE_CHAT:
                return user.isMysteryBoxGiveAllMessageChat();
            case MYSTERYBOX_CHAT_MESSAGE:
                return user.isMysteryBoxMessageChat();
            case DROP_FROM_MOB_CHAT:
                return user.isDropFromMobMessageChat();
            case BROADCAST_MESSAGE_CHAT:
                return user.isBroadcastMessageChat();
            case GUILD_MESSAGE_CHAT:
                return user.isGuildMessageChat();
            default:
                return false;
        }
    }
}
