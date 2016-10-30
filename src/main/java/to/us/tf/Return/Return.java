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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by RoboMWM on 10/29/2016.
 */
public class Return extends JavaPlugin implements Listener
{
    List<String> messages = new ArrayList<>();
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
        messages.add("MLG Fortress is either down or restarting.");
        messages.add(ChatColor.GOLD + "Please use /rejoin to attempt a reconnect, or simply wait while we automatically try to reconnect for you.");
        messages.add("Feel free to spam that command if you wish to.");
        messages.add("");
        messages.add("Also, you can join us in IRC chat if the server is down.");
        messages.add("Just click the chat tile at " + ChatColor.AQUA + ChatColor.UNDERLINE + "http://techfort.us.to");
    }

    @EventHandler
    void onCommand(PlayerCommandPreprocessEvent event)
    {
        if (event.getPlayer().isOp())
            return;
        event.getPlayer().kickPlayer("Attempting to reconnect you to MLG Fortress");
        event.getPlayer().sendMessage(ChatColor.AQUA + "" + ChatColor.UNDERLINE + "http://techfort.us.to");
        event.setCancelled(true);
    }

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        new BukkitRunnable()
        {
            Queue<String> hi = new LinkedList<>(messages);
            public void run()
            {
                if (hi.isEmpty())
                {
                    this.cancel();
                    return;
                }

                player.sendMessage(hi.remove());
            }
        }.runTaskTimer(this, 80L, 60L);
        event.setJoinMessage(null);
    }
}
