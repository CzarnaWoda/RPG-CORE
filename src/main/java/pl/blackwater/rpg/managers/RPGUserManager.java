package pl.blackwater.rpg.managers;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import pl.blackwater.rpg.data.RPGUser;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface RPGUserManager {

    RPGUser getUser(UUID uuid);

    RPGUser getUser(String lastName);

    Map<UUID, RPGUser> getUsers();

    void joinToTheGame(Player player);
    void leftTheGame(Player player);

    Optional<RPGUser> getOptionalUser(UUID uuid);

    void setup();

    RPGUser createUser(Player player);

    void saveUsers();

    Map<Player, BukkitTask> getTasks();

}
