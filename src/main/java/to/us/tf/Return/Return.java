package to.us.tf.Return;

import com.destroystokyo.paper.Title;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by RoboMWM on 10/29/2016.
 */
public class Return extends JavaPlugin implements Listener
{
    Title yURHere;
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(this, this);
        new BukkitRunnable()
        {
            public void run()
            {
                for (Player player : getServer().getOnlinePlayers())
                    if (!player.isOp())
                        player.kickPlayer("Attempting to reconnect you to MLG Fortress");
            }
        }.runTaskTimer(this, 120L * 20L, 30L * 20L);
        Title.Builder builder = new Title.Builder();
        builder.title("MLG Fortress is");
        builder.subtitle("restarting, or is down");
        builder.stay(20 * 10);
        yURHere = builder.build();
    }

    @EventHandler
    void onCommand(PlayerCommandPreprocessEvent event)
    {
        if (event.getPlayer().isOp())
            return;
        event.getPlayer().kickPlayer("Attempting to reconnect you to MLG Fortress");
        event.setCancelled(true);
    }

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        player.sendTitle(yURHere);
        player.sendMessage("MLG Fortress is either down or restarting.");
        player.sendMessage("Please use /rejoin to attempt a reconnect, or simply wait while we automatically try to reconnect for you.");
        player.sendMessage("Feel free to spam that command if you wish to.");
        player.sendMessage("");
        player.sendMessage("Also, you can join us in IRC chat if the server is down.");
        player.sendMessage("Just click the chat tile at " + ChatColor.BLUE + ChatColor.UNDERLINE + "http://techfort.us.to");
    }
}
