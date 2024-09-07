package pl.blackwater.rpg.listeners;

import eu.decentsoftware.holograms.api.DHAPI;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import pl.blackwater.rpg.RPG;
import pl.blackwater.rpg.mine.Ore;
import pl.blackwater.spigotplugin.util.ChatUtil;
import pl.blackwater.spigotplugin.util.InventoryUtil;

import javax.swing.text.html.Option;
import java.util.Optional;

@RequiredArgsConstructor
public class PlayerBreakBlockListener implements Listener {

    private final RPG plugin;
    @EventHandler
    public void onBreak(BlockBreakEvent e){
        final Block b = e.getBlock();

        final Optional<Ore> optionalOre = plugin.getOreStorage().getOres().stream().filter(ore -> ore.getBlock().equals(b)).findAny();

        optionalOre.ifPresent(ore -> {
            e.setCancelled(true);
            if(ore.getProgress() >= ore.getAmount()){
                final Player player = e.getPlayer();
                InventoryUtil.giveItems(player,ore.getOreItem());
                ore.removeHologram();
                plugin.getOreStorage().getOres().remove(ore);
                b.setType(Material.AIR);
                player.sendTitle(ChatUtil.hexColor("#618dfa⛏ #61e3faUDALO CI SIE WYKOPAC RUDE #618dfa⛏"),ChatUtil.hexColor("#618dfa⛏ #7b61fa" +ore.getOreType().name().toUpperCase() + " #618dfa⛏"));
                plugin.getRpgUserManager().getUser(player.getUniqueId()).addMinedOre(ore.getOreType(),1);
            }else {
                if(ore.getAmount() > 20) {
                    if (!DHAPI.getHologramLine(ore.getHologram().getPage(0), 2).getText().equalsIgnoreCase(ChatUtil.hexColor("#FDAA88[ " + getProgressCap(ore.getProgress(), ore.getAmount()) + " #FDAA88]"))) {
                        DHAPI.setHologramLine(ore.getHologram(), 2, ChatUtil.hexColor("#FDAA88[ " + getProgressCap(ore.getProgress(), ore.getAmount()) + " #FDAA88]"));
                    }
                }else{
                    DHAPI.setHologramLine(ore.getHologram(), 2, ChatUtil.hexColor("#FDAA88[ " + getProgress(ore.getProgress(), ore.getAmount()) + " #FDAA88]"));
                }
                ore.addProgress(1);
            }
        });
        if (!e.getPlayer().hasPermission("rpg.admin")){
            e.setCancelled(true);
        }
    }

    public static String getProgress(int progress, int amount){
        int left = amount - progress;
        StringBuilder output = new StringBuilder();
        for(int i  = 0 ; i < progress ; i ++){
            output.append("#40F602").append("█");
        }
        for(int i = 0 ; i < left; i ++){
            output.append("#FE3D07").append("█");
        }
        return ChatUtil.hexColor(output.toString());
    }
    public static String getProgressCap(int progress, int amount){
        StringBuilder output = new StringBuilder();
        double percent = (double)progress/amount;
        int finalProgress = (int) (20.0*percent);
        int left = (int) (20.0 - finalProgress);

        if(finalProgress + left > 20){
            System.out.println("final: " + finalProgress + ", left: " + left + ", percent: " + percent + ", finalcalc: " + (20.0*percent) + ", leftcalc: " + (20.0 - finalProgress));
        }
        for(int i  = 0 ; i < finalProgress ; i ++){
            output.append("#40F602").append("█");
        }
        for(int i = 0 ; i < left; i ++){
            output.append("#FE3D07").append("█");
        }
        return ChatUtil.hexColor(output.toString());
    }
}
