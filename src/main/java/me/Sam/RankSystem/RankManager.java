package me.Sam.RankSystem;

import com.bencodez.votingplugin.topvoter.TopVoter;
import com.bencodez.votingplugin.user.UserManager;
import com.gamingmesh.jobs.Jobs;
import me.Sam.RankSystem.Events.RankupEvent;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Map;

public class RankManager {

    public RankManager(){}

    public void rankDown(Player p) {
        PlayerStats playerStats = RankSystem.playerStatsMap.get(p.getUniqueId());
        playerStats.setRank(getRankBelow(playerStats));
    }

    public Rank getRankBelow(PlayerStats playerStats) {
        for (Map.Entry<String, Rank> entry : RankSystem.ranks.entrySet()) {
            if (entry.getValue().getPosition() == (playerStats.getRank().getPosition() - 1)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public void rankUp(Player p) {
        Rank rank;
        if (RankSystem.playerStatsMap.containsKey(p.getUniqueId())) {
            PlayerStats playerStats = RankSystem.playerStatsMap.get(p.getUniqueId());
            playerStats.setRank(getNextRank(playerStats));
            rank = playerStats.getRank();
        } else {
            rank = getFirstRank();
        }
        RankupEvent rankupEvent = new RankupEvent(p, rank);
        Bukkit.getPluginManager().callEvent(rankupEvent);
        sendRewardCommands(p, rank);
        RankSystem.econ.withdrawPlayer(p, rank.getMoneyRequirement());
    }

    public void rankUpNoWithdraw(Player p) {
        Rank rank;
        if (RankSystem.playerStatsMap.containsKey(p.getUniqueId())) {
            PlayerStats playerStats = RankSystem.playerStatsMap.get(p.getUniqueId());
            playerStats.setRank(getNextRank(playerStats));
            rank = playerStats.getRank();
        } else {
            rank = getFirstRank();
        }
        RankupEvent rankupEvent = new RankupEvent(p, rank);
        Bukkit.getPluginManager().callEvent(rankupEvent);
        sendRewardCommands(p, rank);
    }

    public Rank getPlayerRankInProgress(Player p) {
        if (!RankSystem.playerStatsMap.containsKey(p.getUniqueId())) {
            return getFirstRank();
        } else {
            return getNextRank(RankSystem.playerStatsMap.get(p.getUniqueId()));
        }
    }

    public boolean canRankup(Player p) {
        if (RankSystem.playerStatsMap.containsKey(p.getUniqueId())) {
            PlayerStats playerStats = RankSystem.playerStatsMap.get(p.getUniqueId());
            if (playerStats.getRank().isLastRank()) {
                return false;
            }
            Rank nextRank = getNextRank(playerStats);
            if (nextRank.getVoteRequirement() != 0)
                if (getCurrentVotes(p) < nextRank.getVoteRequirement())
                    return false;
            if (nextRank.getMoneyRequirement() != 0)
                if (getCurrentBalance(p) < nextRank.getMoneyRequirement())
                    return false;
            if (nextRank.getMcmmoRequirement() != 0)
                if (getMCMMOPowerLevel(p) < nextRank.getMcmmoRequirement())
                    return false;
            if (nextRank.getJobsRequirement() != 0)
                if (getJobsPowerLevel(p) < nextRank.getJobsRequirement())
                    return false;
            if (nextRank.getEnchantedItemsRequirement() != 0)
                if (getItemsEnchanted(p) < nextRank.getEnchantedItemsRequirement())
                    return false;
            if (nextRank.getMobsKilledRequirement() != 0)
                if (getMobsKilled(p) < nextRank.getMobsKilledRequirement())
                    return false;
            if (nextRank.getAnimalsBredRequirement() != 0)
                if (getAnimalsBred(p) < nextRank.getAnimalsBredRequirement())
                    return false;
            if (nextRank.getWinRaidsRequirement() != 0)
                if (getRaidsWon(p) < nextRank.getWinRaidsRequirement())
                    return false;
            if (nextRank.getCatchFishRequirement() != 0)
                if (getFishCaught(p) < nextRank.getCatchFishRequirement())
                    return false;
            if (nextRank.getEnderDragonKilledRequirement() != 0)
                if (getEnderDragonsKilled(p) < nextRank.getEnderDragonKilledRequirement())
                    return false;
            return true;
        } else {
            Rank nextRank = getFirstRank();
            if (nextRank.getVoteRequirement() != 0)
                if (getCurrentVotes(p) < nextRank.getVoteRequirement())
                    return false;
            if (nextRank.getMoneyRequirement() != 0)
                if (getCurrentBalance(p) < nextRank.getMoneyRequirement())
                    return false;
            if (nextRank.getMcmmoRequirement() != 0)
                if (getMCMMOPowerLevel(p) < nextRank.getMcmmoRequirement())
                    return false;
            if (nextRank.getJobsRequirement() != 0)
                if (getJobsPowerLevel(p) < nextRank.getJobsRequirement())
                    return false;
            if (nextRank.getEnchantedItemsRequirement() != 0)
                if (getItemsEnchanted(p) < nextRank.getEnchantedItemsRequirement())
                    return false;
            if (nextRank.getMobsKilledRequirement() != 0)
                if (getMobsKilled(p) < nextRank.getMobsKilledRequirement())
                    return false;
            if (nextRank.getAnimalsBredRequirement() != 0)
                if (getAnimalsBred(p) < nextRank.getAnimalsBredRequirement())
                    return false;
            if (nextRank.getWinRaidsRequirement() != 0)
                if (getRaidsWon(p) < nextRank.getWinRaidsRequirement())
                    return false;
            if (nextRank.getCatchFishRequirement() != 0)
                if (getFishCaught(p) < nextRank.getCatchFishRequirement())
                    return false;
            if (nextRank.getEnderDragonKilledRequirement() != 0)
                if (getEnderDragonsKilled(p) < nextRank.getEnderDragonKilledRequirement())
                    return false;
            return true;
        }
    }

    public boolean passedPlaytime(Player p, Rank rank) {
        int dayticks = rank.getPlaytimeRequirementDays() * 24 * 60 * 60 * 20;
        int hourticks = rank.getPlaytimeRequirementHours() * 60 * 60 * 20;
        int totalTicks = dayticks + hourticks;
        if (p.getTicksLived() >= totalTicks) {
            return true;
        }
        return false;
    }

    public int getCurrentBalance(Player p) {
        return (int) RankSystem.econ.getBalance(p);
    }
    public int getCurrentVotes(Player p) {
        return UserManager.getInstance().getVotingPluginUser(p.getUniqueId()).getTotal(TopVoter.AllTime);
    }

    public int getMCMMOPowerLevel(Player player) {
        return com.gmail.nossr50.util.player.UserManager.getPlayer(player).getPowerLevel();
    }

    public int getJobsPowerLevel(Player player) {
        return Jobs.getPlayerManager().getJobsPlayer(player).getTotalLevels();
    }

    public int getAnimalsBred(Player player) {
        return player.getStatistic(Statistic.ANIMALS_BRED);
    }

    public int getFishCaught(Player player) {
        return player.getStatistic(Statistic.FISH_CAUGHT);
    }

    public int getRaidsWon(Player player) {
        return player.getStatistic(Statistic.RAID_WIN);
    }

    public int getMobsKilled(Player player) {
        return player.getStatistic(Statistic.MOB_KILLS);
    }

    public int getEnderDragonsKilled(Player player) {
        return player.getStatistic(Statistic.KILL_ENTITY, EntityType.ENDER_DRAGON);
    }

    public int getItemsEnchanted(Player player) {
        return player.getStatistic(Statistic.ITEM_ENCHANTED);
    }

    public String isPassedMcmmo(Player p, Rank rank) {
        if (getMCMMOPowerLevel(p) >= rank.getMcmmoRequirement()) {
            return "&a&l✔";
        } else {
            return "&c&l✖ &7Current: &f" + getMCMMOPowerLevel(p);
        }
    }

    public String isPassedJobs(Player p, Rank rank) {
        if (getJobsPowerLevel(p) >= rank.getJobsRequirement()) {
            return "&a&l✔";
        } else {
            return "&c&l✖ &7Current: &f" + getJobsPowerLevel(p);
        }
    }

    public String isPassedAnimalsBred(Player p, Rank rank) {
        if (getAnimalsBred(p) >= rank.getAnimalsBredRequirement()) {
            return "&a&l✔";
        } else {
            return "&c&l✖ &7Current: &f" + getAnimalsBred(p);
        }
    }

    public String isPassedMobsKilled(Player p, Rank rank) {
        if (getMobsKilled(p) >= rank.getMobsKilledRequirement()) {
            return "&a&l✔";
        } else {
            return "&c&l✖ &7Current: &f" + getMobsKilled(p);
        }
    }

    public String isPassedFishCaught(Player p, Rank rank) {
        if (getFishCaught(p) >= rank.getCatchFishRequirement()) {
            return "&a&l✔";
        } else {
            return "&c&l✖ &7Current: &f" + getFishCaught(p);
        }
    }

    public String isPassedItemsEnchanted(Player p, Rank rank) {
        if (getItemsEnchanted(p) >= rank.getEnchantedItemsRequirement()) {
            return "&a&l✔";
        } else {
            return "&c&l✖ &7Current: &f" + getItemsEnchanted(p);
        }
    }

    public String isPassedEnderdragonsKilled(Player p, Rank rank) {
        if (getEnderDragonsKilled(p) >= rank.getEnderDragonKilledRequirement()) {
            return "&a&l✔";
        } else {
            return "&c&l✖ &7Current: &f" + getEnderDragonsKilled(p);
        }
    }

    public String isPassedRaidsWon(Player p, Rank rank) {
        if (getRaidsWon(p) >= rank.getWinRaidsRequirement()) {
            return "&a&l✔";
        } else {
            return "&c&l✖ &7Current: &f" + getRaidsWon(p);
        }
    }


    public String isPassedBalance(Player p, Rank rank) {
        if (getCurrentBalance(p) >= rank.getMoneyRequirement()) {
            return "&a&l✔";
        } else {
            return "&c&l✖ &7Current: &f" + getCurrentBalance(p);
        }
    }
    public String isPassedVotes(Player p, Rank rank) {
        if (getCurrentVotes(p) >= rank.getVoteRequirement()) {
            return "&a&l✔";
        } else {
            return "&c&l✖ &7Current: &f" + getCurrentVotes(p);
        }
    }
    public String isPassedPlaytime(Player p, Rank rank) {
        if (passedPlaytime(p, rank)) {
            return "&a&l✔";
        } else {
            return "&c&l✖ &7Current: &f" + getDaysLived(p) + " &7day(s) &f" + getHoursLived(p) + " &7hour(s)";
        }
    }

    public int getDaysLived(Player p) {
        return p.getTicksLived() / 20 / 60 / 60 / 24;
    }

    //Hours remaining after subtracting days
    public int getHoursLived(Player p) {
        double totalHoursLived = p.getTicksLived() / 20 / 60 / 60;
        return (int) (totalHoursLived % 24);
    }

    private void sendRewardCommands(Player p, Rank rank) {
        ConsoleCommandSender console = Bukkit.getConsoleSender();
        for (String command : rank.getRewardCommands()) {
            Bukkit.dispatchCommand(console, command.replace("%player%", p.getName()));
        }
    }

    private Rank getNextRank(PlayerStats playerStats) {
        int currentRankPosition = playerStats.getRank().getPosition();
        int nextRankPosition = currentRankPosition + 1;
        for (Map.Entry<String, Rank> entry : RankSystem.ranks.entrySet()) {
            Rank rank = entry.getValue();
            if (rank.getPosition() == nextRankPosition) {
                return rank;
            }
        }
        return null;
    }

    private Rank getFirstRank() {
        return RankSystem.ranks.get("Rank1");
    }
}
