package net.civicraft.profiles.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;

public class Messages {
    public static final TextColor PRIMARY = TextColor.color(166, 77, 255);
    public static final TextColor SECONDARY = TextColor.color(233, 199, 241);

    public static final Component PREFIX = Component.text("[Profiles] ").color(PRIMARY);

    public static final Component PLUGIN_INFO = PREFIX.append(Component.text("Profiles is developed by tair0 with CiviCraft Development. ").color(SECONDARY).append(Component.text("Report issues on GitHub!").color(NamedTextColor.AQUA).decorate(TextDecoration.UNDERLINED).clickEvent(ClickEvent.openUrl("https://github.com/CiviCraft-Development/Profiles/issues"))));
    public static final Component PLUGIN_RELOAD = PREFIX.append(Component.text("Profiles has been reloaded successfully!").color(SECONDARY));
    public static final Component NO_PERMISSION = PREFIX.append(Component.text("You do not have permission to use this command.").color(SECONDARY));
    public static final Component COMMAND_DISABLED = PREFIX.append(Component.text("Command disabled.").color(SECONDARY));

    public static final Component NEVER_JOINED = PREFIX.append(Component.text("Player has never joined the server.").color(SECONDARY));
    public static final Component OPEN_PROFILE = PREFIX.append(Component.text("Opening your profile...").color(SECONDARY));

    public static Component OPEN_OTHER_PROFILE(Player player) {
        return PREFIX.append(Component.text("Opening " + player.getName() + "'s Profile...").color(SECONDARY));
    }

    public static Component STATUS_UPDATE = PREFIX.append(Component.text("Your status has been updated to: ").color(SECONDARY));
    public static Component INVALID_STATUS = PREFIX.append(Component.text("Invalid status.").color(SECONDARY));

}
