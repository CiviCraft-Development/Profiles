package net.civicraft.profiles.listeners;

import net.civicraft.profiles.gui.ProfileGUI;
import net.civicraft.profiles.utils.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerClickListener implements Listener {
    @EventHandler
    public void onPlayerClick(PlayerInteractEntityEvent e) {
        Player player = e.getPlayer();
        if (player.hasPermission("profiles.others") && player.isSneaking() && e.getRightClicked() instanceof Player target) {
            ProfileGUI.getProfile(player, target);
        } else {
            player.sendMessage(Messages.NO_PERMISSION);
        }
    }
}
