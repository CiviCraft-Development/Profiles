package net.civicraft.profiles.utils;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PlayerUtils {
    private static final LuckPerms instance = LuckPermsProvider.get();
    public static @NotNull GuiItem getOnlineStatus(Player player) {
        ItemStack item;
        ItemMeta meta;

        if (player.isOnline()) {
            item = new ItemStack(Material.GREEN_TERRACOTTA, 1);
            meta = item.getItemMeta();
            meta.displayName(Component.text("Online").color(NamedTextColor.GREEN));
            hide(meta);
            item.setItemMeta(meta);
        } else if (player.hasPlayedBefore() && !player.isOnline()) {
            item = new ItemStack(Material.RED_TERRACOTTA, 1);
            meta = item.getItemMeta();
            meta.displayName(Component.text("Offline").color(NamedTextColor.RED));
            hide(meta);
            item.setItemMeta(meta);
        } else {
            item = new ItemStack(Material.GRAY_TERRACOTTA, 1);
            meta = item.getItemMeta();
            meta.displayName(Component.text("Never Joined").color(NamedTextColor.DARK_GRAY));
            hide(meta);
            item.setItemMeta(meta);
        }
        return new GuiItem(item);
    }

    public static @NotNull GuiItem getPlayerHead(Player player) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
        skullMeta.setOwningPlayer(player);
        skullMeta.displayName(player.displayName().color(NamedTextColor.DARK_PURPLE));
        skullMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(skullMeta);

        return new GuiItem(item);
    }

    public static ItemStack getStatus(Player viewer, Player target) {
        String status = DataManager.getStatus(target.getUniqueId());
        ItemStack item = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(status));

        List<Component> lore = new ArrayList<>();
        if (viewer.equals(target)) {
            lore.add(Component.text("Right Click to Change").color(NamedTextColor.GRAY).decorate(TextDecoration.ITALIC));
        }
        meta.lore(lore);
        hide(meta);

        item.setItemMeta(meta);

        return item;
    }

    public static GuiItem getJob(Player player) {
        ItemStack item = new ItemStack(Material.STONE_PICKAXE, 1);
        User user = instance.getUserManager().getUser(player.getUniqueId());
        assert user != null;String primgroup = user.getPrimaryGroup();
        Group group = instance.getGroupManager().getGroup(primgroup);

        String job = group != null
                ? group.getFriendlyName()
                : primgroup;
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(job));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        hide(meta);
        item.setItemMeta(meta);

        return new GuiItem(item);
    }

    public static GuiItem getJoinDate(Player player) {
        ItemStack item = new ItemStack(Material.PURPLE_CANDLE, 1);
        ItemMeta meta = item.getItemMeta();
        String joinDate = DataManager.getJoinDate(player.getUniqueId());
        meta.displayName(Component.text("Joined " + joinDate).color(NamedTextColor.BLUE));
        hide(meta);
        item.setItemMeta(meta);

        return new GuiItem(item);
    }

    private static void hide(ItemMeta meta) {
        meta.addItemFlags(
                ItemFlag.HIDE_ATTRIBUTES,
                ItemFlag.HIDE_ENCHANTS,
                ItemFlag.HIDE_DESTROYS,
                ItemFlag.HIDE_PLACED_ON,
                ItemFlag.HIDE_ADDITIONAL_TOOLTIP,
                ItemFlag.HIDE_UNBREAKABLE
        );
    }
}
