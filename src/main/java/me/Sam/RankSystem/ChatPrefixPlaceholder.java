package me.Sam.RankSystem;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class ChatPrefixPlaceholder extends PlaceholderExpansion {
    private RankSystem main;

    public ChatPrefixPlaceholder(RankSystem main) {
        this.main = main;
    }

    public boolean persist() {
        return true;
    }

    public boolean canRegister() {
        return true;
    }

    public String getAuthor() {
        return this.main.getDescription().getAuthors().toString();
    }

    public String getIdentifier() {
        return "CloudRankSystem";
    }

    public String getVersion() {
        return this.main.getDescription().getVersion();
    }

    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) {
            return "";
        } else {
            PlayerStats playerStats;
            if (identifier.equals("chatprefix")) {
                if (!RankSystem.playerStatsMap.containsKey(player.getUniqueId())) {
                    return Utils.chat("{#7a8282}[Newbie] ");
                } else {
                    playerStats = (PlayerStats)RankSystem.playerStatsMap.get(player.getUniqueId());
                    return !playerStats.isPrefixOn() ? "" : Utils.chat(playerStats.getRank().getChatPrefix());
                }
            } else if (identifier.equals("forcedprefix")) {
                if (!RankSystem.playerStatsMap.containsKey(player.getUniqueId())) {
                    return Utils.chat("{#7a8282}[Newbie] ");
                } else {
                    playerStats = (PlayerStats)RankSystem.playerStatsMap.get(player.getUniqueId());
                    return Utils.chat(playerStats.getRank().getChatPrefix());
                }
            } else if (identifier.equals("prestigeprefix")) {
                if (!RankSystem.playerStatsMap.containsKey(player.getUniqueId())) {
                    return Utils.chat("");
                } else {
                    playerStats = (PlayerStats)RankSystem.playerStatsMap.get(player.getUniqueId());
                    return playerStats.getPrestige() < 1 ? "" : playerStats.getPrestigeIcon() + " ";
                }
            } else if (identifier.equals("isrankprefixon")) {
                if (!RankSystem.playerStatsMap.containsKey(player.getUniqueId())) {
                    return Utils.chat("yes");
                } else {
                    playerStats = (PlayerStats)RankSystem.playerStatsMap.get(player.getUniqueId());
                    return playerStats.isPrefixOn() ? "yes" : "no";
                }
            } else {
                return null;
            }
        }
    }
}
