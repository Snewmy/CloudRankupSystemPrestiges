package me.Sam.RankSystem.Commands;

import me.Sam.RankSystem.PlayerStats;
import me.Sam.RankSystem.RankSystem;
import me.Sam.RankSystem.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RankPrefixCommand implements CommandExecutor {
    public RankPrefixCommand() {
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (cmd.getName().equalsIgnoreCase("rankprefix") && sender instanceof Player player) {
            if (args.length < 1) {
                player.sendMessage(Utils.chat("&cPlease specify, /rankprefix on/off"));
                return false;
            }

            if (!RankSystem.playerStatsMap.containsKey(player.getUniqueId())) {
                player.sendMessage(Utils.chat("&cYou must rank up first."));
                return false;
            }

            PlayerStats playerStats = (PlayerStats)RankSystem.playerStatsMap.get(player.getUniqueId());
            if (args[0].equalsIgnoreCase("on")) {
                if (playerStats.isPrefixOn()) {
                    player.sendMessage(Utils.chat("&cYour rank prefix is already turned on!"));
                    return false;
                }

                playerStats.setPrefixOn(true);
                player.sendMessage(Utils.chat("{#8bf7f7}&lCloud &8&l≫ &fToggled rank prefix {#8bf7f7}on!"));
                return false;
            }

            if (args[0].equalsIgnoreCase("off")) {
                if (!playerStats.isPrefixOn()) {
                    player.sendMessage(Utils.chat("&cYour rank prefix is already turned off!"));
                    return false;
                }

                playerStats.setPrefixOn(false);
                player.sendMessage(Utils.chat("{#8bf7f7}&lCloud &8&l≫ &fToggled rank prefix {#8bf7f7}off!"));
                return false;
            }
        }

        return false;
    }
}
