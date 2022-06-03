package me.Sam.RankSystem.Listeners;

import me.Sam.RankSystem.GUI;
import me.Sam.RankSystem.RankManager;
import me.Sam.RankSystem.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryClick implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase(Utils.chat("{#8bf7f7}/rankup"))) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            if (event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta() || !event.getCurrentItem().getItemMeta().hasDisplayName()) {
                return;
            }
            RankManager rankManager = new RankManager();
            ItemStack itemStack = event.getCurrentItem();
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta.getDisplayName().equalsIgnoreCase(Utils.chat("&a&lCOMPLETED"))) {
                player.sendMessage(Utils.chat("&cYou've already completed this rank!"));
                return;
            } else if (itemMeta.getDisplayName().equalsIgnoreCase(Utils.chat("&e&lIN PROGRESS"))) {
                if (!rankManager.canRankup(player)) {
                    player.sendMessage(Utils.chat("&cYou do not meet the requirements to rankup!"));
                    player.closeInventory();
                    GUI gui = new GUI();
                    gui.createGUI(player);
                    return;
                }
                rankManager.rankUp(player);
                player.sendMessage(Utils.chat("&aYou have successfully ranked up!"));
                GUI gui = new GUI();
                gui.createGUI(player);
            } else if (itemMeta.getDisplayName().equalsIgnoreCase(Utils.chat("&c&lLOCKED"))) {
                player.sendMessage(Utils.chat("&cYou cannot unlock this rank yet!"));
            } else if (itemMeta.getDisplayName().equalsIgnoreCase(Utils.chat("&4&lBack to ranks"))) {
                Bukkit.dispatchCommand(player, "ranks");
            }
        }
    }
}
