package net.civicraft.profiles.gui;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import net.civicraft.profiles.utils.PlayerUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ProfileGUI {
    public static void getProfile(@NotNull Player player, @NotNull Player target) {
        ChestGui gui = new ChestGui(3, target.getName() + "'s Profile");

        OutlinePane background = new OutlinePane(0, 0, 9, 3, Pane.Priority.LOWEST);
        background.addItem(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
        background.setRepeat(true);

        gui.addPane(background);

        StaticPane info = new StaticPane(2, 1, 5, 1, Pane.Priority.HIGHEST);
        info.addItem(PlayerUtils.getPlayerHead(target), 0, 0);
        info.addItem(PlayerUtils.getOnlineStatus(target), 1, 0);
        info.addItem(new GuiItem(PlayerUtils.getStatus(player, target), event -> {
            if (event.isRightClick()) {
                event.setCancelled(true);
                if (player.equals(target)) {
                    StatusInput.statusInput((Player) event.getWhoClicked());
                }
            }
        }), 2, 0);
        info.addItem(PlayerUtils.getJob(target), 3, 0);
        info.addItem(PlayerUtils.getJoinDate(target), 4, 0);

        gui.setOnGlobalClick(event -> event.setCancelled(true));

        gui.addPane(info);
        gui.show(player);
    }
}
