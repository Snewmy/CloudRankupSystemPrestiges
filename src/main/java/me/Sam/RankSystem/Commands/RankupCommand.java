package me.Sam.RankSystem.Commands;

import me.Sam.RankSystem.GUI;
import me.Sam.RankSystem.PlayerStats;
import me.Sam.RankSystem.RankManager;
import me.Sam.RankSystem.RankSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RankupCommand implements CommandExecutor {
    public RankupCommand() {
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (cmd.getName().equalsIgnoreCase("rankup") && sender instanceof Player player) {
            RankManager rankManager = new RankManager();
            if (!RankSystem.playerStatsMap.containsKey(player.getUniqueId())) {
                PlayerStats playerStats = new PlayerStats(rankManager.getFirstRank());
                RankSystem.playerStatsMap.put(player.getUniqueId(), playerStats);
            }

            GUI gui = new GUI();
            gui.createGUI(player);
        }

        return false;
    }
}
