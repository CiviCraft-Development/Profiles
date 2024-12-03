package net.civicraft.profiles.utils;

import net.civicraft.profiles.Profiles;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataManager {

    static Profiles instance = Profiles.getInstance();
    private static final String DEFAULT_STATUS = instance.getConfig().getString("default-status");
    private static final Map<UUID, String> statusMap = new HashMap<>();
    private static final Map<UUID, String> joinDateMap = new HashMap<>();
    private static File dataFile;
    private static FileConfiguration dataConfig;

    public static void init(File pluginDataFolder) {
        dataFile = new File(pluginDataFolder, "data.yml");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                instance.getLogger().severe("Could not create data.yml: " + e.getMessage());
            }
        }
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
        loadStatuses();
    }

    private static void loadStatuses() {
        if (dataConfig == null) return;
        for (String key : dataConfig.getKeys(false)) {
            UUID uuid;
            try {
                uuid = UUID.fromString(key);
            } catch (IllegalArgumentException e) {
                instance.getLogger().warning("Invalid UUID in data.yml: " + key);
                continue;
            }
            String status = dataConfig.getString(key + ".status", DEFAULT_STATUS);
            String joinDate = dataConfig.getString(key + ".joinDate");
            statusMap.put(uuid, status);
            if (joinDate != null) {
                joinDateMap.put(uuid, joinDate);
            }
        }
        instance.getLogger().info("Statuses loaded successfully!");
    }

    public static void saveStatuses() {
        if (dataConfig == null) return;
        for (UUID uuid : statusMap.keySet()) {
            dataConfig.set(uuid.toString() + ".status", statusMap.get(uuid));
            dataConfig.set(uuid + ".joinDate", joinDateMap.get(uuid));
        }
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            instance.getLogger().severe(":( Could not save data.yml: " + e.getMessage());
        }
    }

    public static void setStatus(Player player, String status) {
        UUID uuid = player.getUniqueId();
        statusMap.put(uuid, status);
        dataConfig.set(uuid + ".status", status);
        saveStatuses();
    }

    public static String getStatus(UUID uuid) {
        return statusMap.getOrDefault(uuid, DEFAULT_STATUS);
    }

    public static void handlePlayerJoin(Player player) {
        UUID uuid = player.getUniqueId();
        if (!statusMap.containsKey(uuid)) {
            String joinDate = new SimpleDateFormat("MMM dd, yyyy").format(new Date());
            joinDateMap.put(uuid, joinDate);
            dataConfig.set(uuid + ".joinDate", joinDate);
            statusMap.put(uuid, DEFAULT_STATUS);
            dataConfig.set(uuid + ".status", DEFAULT_STATUS);
            saveStatuses();
        }
    }

    public static String getJoinDate(UUID uuid) {
        String joinDate = joinDateMap.get(uuid);
        if (joinDate == null) {
            return "Unknown";
        }
        return joinDate;
    }

    public static void handlePlayerQuit(Player player) {
        UUID uuid = player.getUniqueId();
        String status = statusMap.get(uuid);
        if (status != null) {
            dataConfig.set(uuid + ".status", status);
        }
        String joinDate = joinDateMap.get(uuid);
        if (joinDate != null) {
            dataConfig.set(uuid + ".joinDate", joinDate);
        }
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            instance.getLogger().severe("Could not save data.yml for player " + player.getName() + ": " + e.getMessage());
        }
    }

    public static boolean isPlayerRegistered(UUID uuid) {
        return statusMap.containsKey(uuid) || dataConfig.contains(uuid.toString());
    }
}