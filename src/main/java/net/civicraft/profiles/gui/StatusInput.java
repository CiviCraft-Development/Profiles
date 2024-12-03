package net.civicraft.profiles.gui;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.AnvilGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import net.civicraft.profiles.Profiles;
import net.civicraft.profiles.utils.DataManager;
import net.civicraft.profiles.utils.Messages;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StatusInput {
    public static void statusInput(Player player)
    {
        AnvilGui statGui = new AnvilGui("Enter new status:");

        ItemStack paper = new ItemStack(Material.PAPER);
        ItemMeta meta = paper.getItemMeta();
        meta.displayName(Component.text("New status...").color(NamedTextColor.GRAY));
        paper.setItemMeta(meta);

        ItemStack status = new ItemStack(Material.PAPER);
        ItemMeta statusMeta = status.getItemMeta();
        statusMeta.displayName(Component.text("Confirm").color(NamedTextColor.GREEN));
        status.setItemMeta(statusMeta);

        GuiItem item = new GuiItem(paper, event -> event.setCancelled(true));
        StaticPane pane1 = new StaticPane(0, 0, 1, 1);
        pane1.addItem(item, 0, 0);
        statGui.getFirstItemComponent().addPane(pane1);

        StaticPane pane2 = getStaticPane(player, status, statGui);
        statGui.getResultComponent().addPane(pane2);

        statGui.show(player);
    }

    private static @NotNull StaticPane getStaticPane(Player player, ItemStack status, AnvilGui statGui) {
        GuiItem statusItem = new GuiItem(status, event -> {
            String input = statGui.getRenameText();
            if (input.isEmpty() || input.equals("New status...") || isBlacklisted(input, Profiles.getBlacklist())) {
                player.sendMessage(Messages.INVALID_STATUS);
            } else {
                DataManager.setStatus(player, input);
                player.sendMessage(Messages.STATUS_UPDATE.append(Component.text(input)));
            }
            player.closeInventory();
        });
        StaticPane pane2 = new StaticPane(0, 0, 1, 1);
        pane2.addItem(statusItem, 0, 0);
        return pane2;
    }

    public static boolean isBlacklisted(String input, List<String> blacklist) {
        for (String word : blacklist) {
            if (input.toLowerCase().contains(word.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}