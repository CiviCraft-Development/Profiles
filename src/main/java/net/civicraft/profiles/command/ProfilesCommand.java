package net.civicraft.profiles.command;

import net.civicraft.profiles.utils.Messages;
import net.civicraft.profiles.Profiles;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ProfilesCommand implements CommandExecutor {
    Profiles instance = Profiles.getInstance();
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player && player.hasPermission("profiles.admin")) {
            if (strings.length == 0) {
                player.sendMessage(Messages.PLUGIN_INFO);
            } else if (strings.length == 1) {
                if (strings[0].equals("reload")) {
                    instance.reloadConfig();
                    player.sendMessage(Messages.PLUGIN_RELOAD);
                } else {
                    player.sendMessage(Messages.PLUGIN_INFO);
                }
            }
        } else {
            commandSender.sendMessage(Messages.NO_PERMISSION);
        }
        return false;
    }
}
