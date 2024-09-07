package pl.blackwater.rpg.util;

import net.minecraft.server.v1_16_R3.Packet;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class PacketUtil {

    public static void sendPackets(CraftPlayer player, Packet<?>... packets){
        if(player != null){
            for (Packet<?> packet : packets) {
                player.getHandle().playerConnection.sendPacket(packet);
            }
        }
    }

    public static void sendPackets(CraftPlayer player, List<Packet<?>> packets){
        if(player != null){
            for (Packet<?> packet : packets) {
                player.getHandle().playerConnection.sendPacket(packet);
            }
        }
    }

    public static void broadcastPacket(Packet<?> packet){
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            CraftPlayer cp = ((CraftPlayer) onlinePlayer);
            cp.getHandle().playerConnection.sendPacket(packet);
        }
    }

    public static void broadcastPacket(List<Packet<?>> packets){
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            CraftPlayer cp = ((CraftPlayer) onlinePlayer);
            for (Packet<?> packet : packets) {
                cp.getHandle().playerConnection.sendPacket(packet);
            }
        }
    }

}
