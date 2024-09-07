package pl.blackwater.rpg.data;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChatUser implements Serializable {

    public ChatUser(){}

    private boolean fightMessageOnChat = true;
    private boolean fightBossBossBar = true;
    private boolean mysteryBoxMessageChat = true;
    private boolean mysteryBoxGiveAllMessageChat = true;
    private boolean dropFromMobMessageChat = true;
    private boolean broadcastMessageChat = true;
    private boolean guildMessageChat = true;


}
