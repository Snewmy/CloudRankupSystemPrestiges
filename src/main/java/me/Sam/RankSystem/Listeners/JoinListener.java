package me.Sam.RankSystem.Listeners;

import me.Sam.RankSystem.PlayerStats;
import me.Sam.RankSystem.Rank;
import me.Sam.RankSystem.RankSystem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    public JoinListener() {
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!RankSystem.playerStatsMap.containsKey(player.getUniqueId())) {
            boolean defaultRank = false;
            PlayerStats playerStats = new PlayerStats((Rank)RankSystem.ranks.get("Rank0"));
            if (!player.isOp()) {
                if (player.hasPermission("group.rank11")) {
                    playerStats.setRank((Rank)RankSystem.ranks.get("Rank11"));
                } else if (player.hasPermission("group.rank10")) {
                    playerStats.setRank((Rank)RankSystem.ranks.get("Rank10"));
                } else if (player.hasPermission("group.rank9")) {
                    playerStats.setRank((Rank)RankSystem.ranks.get("Rank9"));
                } else if (player.hasPermission("group.rank8")) {
                    playerStats.setRank((Rank)RankSystem.ranks.get("Rank8"));
                } else if (player.hasPermission("group.rank7")) {
                    playerStats.setRank((Rank)RankSystem.ranks.get("Rank7"));
                } else if (player.hasPermission("group.rank6")) {
                    playerStats.setRank((Rank)RankSystem.ranks.get("Rank6"));
                } else if (player.hasPermission("group.rank5")) {
                    playerStats.setRank((Rank)RankSystem.ranks.get("Rank5"));
                } else if (player.hasPermission("group.rank4")) {
                    playerStats.setRank((Rank)RankSystem.ranks.get("Rank4"));
                } else if (player.hasPermission("group.rank3")) {
                    playerStats.setRank((Rank)RankSystem.ranks.get("Rank3"));
                } else if (player.hasPermission("group.rank2")) {
                    playerStats.setRank((Rank)RankSystem.ranks.get("Rank2"));
                } else if (player.hasPermission("group.rank1")) {
                    playerStats.setRank((Rank)RankSystem.ranks.get("Rank1"));
                } else {
                    defaultRank = true;
                }

                if (!defaultRank) {
                    RankSystem.playerStatsMap.put(player.getUniqueId(), playerStats);
                }
            }
        }

    }
}
