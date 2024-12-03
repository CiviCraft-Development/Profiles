package net.civicraft.profiles.command;

import net.civicraft.profiles.Profiles;
import net.civicraft.profiles.gui.ProfileGUI;
import net.civicraft.profiles.utils.DataManager;
import net.civicraft.profiles.utils.Messages;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static net.civicraft.profiles.gui.StatusInput.isBlacklisted;

public class Profile implements CommandExecutor {
    Profiles instance = Profiles.getInstance();
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player && player.hasPermission("profiles.view")) {
            if (strings.length == 0) {
                ProfileGUI.getProfile(player, player);
                player.sendMessage(Messages.OPEN_PROFILE);
            } else if (strings.length == 1 && player.hasPermission("profiles.others")) {
                Player target = Bukkit.getPlayer(strings[0]);
                assert target != null;
                if (target.isOnline() || (target.hasPlayedBefore() && !target.isOnline())) {
                    ProfileGUI.getProfile(player, target);
                    player.sendMessage(Messages.OPEN_OTHER_PROFILE(target));
                } else {
                    player.sendMessage(Messages.NEVER_JOINED);
                }
            } else if (instance.getConfig().getBoolean("command.setstatus")) {
                if (strings.length >= 2 && strings[0].equalsIgnoreCase("setstatus")) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 1; i < strings.length; i++) {
                        sb.append(strings[i]).append(" ");
                    }
                    String newStatus = sb.toString().trim();
                    if (isBlacklisted(newStatus, Profiles.getBlacklist())) {
                        player.sendMessage(Messages.INVALID_STATUS);
                    } else {
                        DataManager.setStatus(player, newStatus);
                        player.sendMessage(Messages.STATUS_UPDATE.append(Component.text(newStatus)));
                    }
                } else {
                    player.sendMessage(Messages.NO_PERMISSION);
                }
            } else {
                player.sendMessage(Messages.COMMAND_DISABLED);
            }
        } else {
            commandSender.sendMessage(Messages.NO_PERMISSION);
        }
        return true;
    }
}
