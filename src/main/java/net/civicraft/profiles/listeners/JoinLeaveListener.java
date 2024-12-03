package net.civicraft.profiles.listeners;

import net.civicraft.profiles.utils.DataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class JoinLeaveListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        if (!DataManager.isPlayerRegistered(uuid)) {
            DataManager.handlePlayerJoin(player);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        DataManager.handlePlayerQuit(e.getPlayer());
    }
}
