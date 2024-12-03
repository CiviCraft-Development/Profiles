package net.civicraft.profiles.discord;

import net.civicraft.profiles.utils.DataManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.awt.*;
import java.util.Objects;
import java.util.UUID;

public class ProfileCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("profile")) {
            if (event.getOption("player") != null) {
                String playerName = Objects.requireNonNull(event.getOption("player")).getAsString();
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);

                if (offlinePlayer == null || offlinePlayer.getUniqueId() == null) {
                    event.reply("Player not found or has never joined the server.").setEphemeral(true).queue();
                    return;
                }

                UUID playerUUID = offlinePlayer.getUniqueId();
                String status = DataManager.getStatus(playerUUID);
                String joinDate = DataManager.getJoinDate(playerUUID);
                boolean isOnline = offlinePlayer.isOnline();
                String onlineStatus = isOnline ? "Online" : "Offline";
                EmbedBuilder embed = new EmbedBuilder()
                        .setTitle(playerName + "'s Profile")
                        .setColor(isOnline ? Color.GREEN : Color.RED)
                        .setThumbnail(getPlayerHeadURL(playerUUID))
                        .addField("Status", status, false)
                        .addField("Join Date", joinDate, false)
                        .addField("Currently", onlineStatus, true);
                event.replyEmbeds(embed.build()).queue();
            } else {
                event.reply("Please provide a player's username!").setEphemeral(true).queue();
            }
        } else {
            event.reply("Something went wrong!").setEphemeral(true).queue();
        }
    }

    private String getPlayerHeadURL(UUID uuid) {
        return "https://mc-heads.net/avatar/" + uuid.toString();
    }
}
