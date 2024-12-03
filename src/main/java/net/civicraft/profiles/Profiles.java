package net.civicraft.profiles;

import net.civicraft.profiles.command.Profile;
import net.civicraft.profiles.command.ProfilesCommand;
import net.civicraft.profiles.discord.ProfileCommand;
import net.civicraft.profiles.listeners.JoinLeaveListener;
import net.civicraft.profiles.listeners.PlayerClickListener;
import net.civicraft.profiles.utils.DataManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;

public final class Profiles extends JavaPlugin {
    public static Profiles instance;
    private JDA jda;
    private TextChannel textChannel;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("CiviProfiles has been successfully enabled!");

        Objects.requireNonNull(getCommand("profile")).setExecutor(new Profile());
        Objects.requireNonNull(getCommand("profiles")).setExecutor(new ProfilesCommand());

        saveDefaultConfig();
        DataManager.init(getDataFolder());

        getServer().getPluginManager().registerEvents(new JoinLeaveListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerClickListener(), this);

        if (getConfig().getBoolean("discord.enabled")) {
            try {
                jda = JDABuilder.createDefault(getConfig().getString("discord.token")).addEventListeners(new ProfileCommand()).build();
                jda.updateCommands().addCommands(Commands.slash("profile", "View a player's profile").addOption(OptionType.STRING, "player", "Player username", true)).queue();
            } catch (Exception e) {
                getLogger().warning("Failed to initialize Discord bot: " + e.getMessage());
            }
        }
    }

    public static List<String> getBlacklist() {
        return instance.getConfig().getStringList("blacklist");
    }

    public static Profiles getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        DataManager.saveStatuses();
        getLogger().info("Profiles has been successfully disabled... :( Bye!");
    }
}
