package me.Sam.RankSystem;

import com.bencodez.votingplugin.topvoter.TopVoter;
import com.bencodez.votingplugin.user.UserManager;
import java.util.Iterator;
import java.util.Map;
import me.Sam.RankSystem.Events.RankupEvent;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class RankManager {
    public RankManager() {
    }

    public void rankDown(Player p) {
        PlayerStats playerStats = (PlayerStats)RankSystem.playerStatsMap.get(p.getUniqueId());
        playerStats.setRank(this.getRankBelow(playerStats));
    }

    public Rank getRankBelow(PlayerStats playerStats) {
        Iterator var2 = RankSystem.ranks.entrySet().iterator();

        Map.Entry entry;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            entry = (Map.Entry)var2.next();
        } while(((Rank)entry.getValue()).getPosition() != playerStats.getRank().getPosition() - 1);

        return (Rank)entry.getValue();
    }

    public void prestigePlayer(Player player) {
        PlayerStats playerStats = (PlayerStats)RankSystem.playerStatsMap.get(player.getUniqueId());
        playerStats.setRank(this.getFirstRank());
        playerStats.setPrestige(playerStats.getPrestige() + 1);
        ConsoleCommandSender var10000 = Bukkit.getConsoleSender();
        String var10001 = player.getName();
        Bukkit.dispatchCommand(var10000, "broadcast !{#8bf7f7}" + var10001 + " &fhas just prestiged to {#8bf7f7}Prestige " + playerStats.getPrestige() + "&f!");
        if (playerStats.getPrestige() == 4) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "broadcast !{#8bf7f7}" + player.getName() + " &fis now {#8bf7f7}Max Prestige&f!");
            playerStats.setRank((Rank)RankSystem.ranks.get("Rank11"));
        }

    }

    public boolean canPrestige(Player player) {
        PlayerStats playerStats = (PlayerStats)RankSystem.playerStatsMap.get(player.getUniqueId());
        return playerStats.getPrestige() != 4 && playerStats.getRank().isLastRank();
    }

    public void rankUp(Player p) {
        Rank rank;
        if (RankSystem.playerStatsMap.containsKey(p.getUniqueId())) {
            PlayerStats playerStats = (PlayerStats)RankSystem.playerStatsMap.get(p.getUniqueId());
            playerStats.setRank(this.getNextRank(playerStats));
            rank = playerStats.getRank();
        } else {
            rank = this.getFirstRank();
        }

        RankupEvent rankupEvent = new RankupEvent(p, rank);
        Bukkit.getPluginManager().callEvent(rankupEvent);
        PlayerStats playerStats = (PlayerStats)RankSystem.playerStatsMap.get(p.getUniqueId());
        if (playerStats.getPrestige() == 0) {
            this.sendRewardCommands(p, rank);
        } else {
            ConsoleCommandSender var10000 = Bukkit.getConsoleSender();
            String var10001 = p.getName();
            Bukkit.dispatchCommand(var10000, "broadcast !{#8bf7f7}" + var10001 + " &fhas ranked up to " + rank.getChatPrefix().trim() + "&f!");
            var10000 = Bukkit.getConsoleSender();
            var10001 = p.getName();
            Bukkit.dispatchCommand(var10000, "broadcast !{#8bf7f7}" + var10001 + " &fis prestige {#8bf7f7}" + playerStats.getPrestige() + "&f!");
        }

        RankSystem.econ.withdrawPlayer(p, (double)rank.getMoneyRequirement());
    }

    public void rankUpNoWithdraw(Player p) {
        Rank rank;
        if (RankSystem.playerStatsMap.containsKey(p.getUniqueId())) {
            PlayerStats playerStats = (PlayerStats)RankSystem.playerStatsMap.get(p.getUniqueId());
            playerStats.setRank(this.getNextRank(playerStats));
            rank = playerStats.getRank();
        } else {
            rank = this.getFirstRank();
        }

        RankupEvent rankupEvent = new RankupEvent(p, rank);
        Bukkit.getPluginManager().callEvent(rankupEvent);
        this.sendRewardCommands(p, rank);
    }

    public Rank getPlayerRankInProgress(Player p) {
        return !RankSystem.playerStatsMap.containsKey(p.getUniqueId()) ? this.getFirstRank() : this.getNextRank((PlayerStats)RankSystem.playerStatsMap.get(p.getUniqueId()));
    }

    public boolean canRankup(Player p) {
        if (RankSystem.playerStatsMap.containsKey(p.getUniqueId())) {
            PlayerStats playerStats = (PlayerStats)RankSystem.playerStatsMap.get(p.getUniqueId());
            if (playerStats.getRank().isLastRank()) {
                return false;
            } else {
                Rank nextRank = this.getNextRank(playerStats);
                Rank lastRank = (Rank)RankSystem.ranks.get("Rank11");
                int playerVotes = switch (playerStats.getPrestige()) {
                    case 1 -> this.getCurrentVotes(p) - lastRank.getVoteRequirement();
                    case 2 -> this.getCurrentVotes(p) - lastRank.getVoteRequirement() * 2;
                    case 3 -> this.getCurrentVotes(p) - lastRank.getVoteRequirement() * 3;
                    default -> this.getCurrentVotes(p);
                };

                if (nextRank.getVoteRequirement() != 0 && playerVotes < nextRank.getVoteRequirement()) {
                    return false;
                } else {
                    return nextRank.getMoneyRequirement() == 0 || this.getCurrentBalance(p) >= nextRank.getMoneyRequirement();
                }
            }
        } else {
            Rank nextRank = this.getFirstRank();
            if (nextRank.getVoteRequirement() != 0 && this.getCurrentVotes(p) < nextRank.getVoteRequirement()) {
                return false;
            } else {
                return nextRank.getMoneyRequirement() == 0 || this.getCurrentBalance(p) >= nextRank.getMoneyRequirement();
            }
        }
    }

    public boolean passedPlaytime(Player p, Rank rank, PlayerStats playerStats) {
        int dayticks = rank.getPlaytimeRequirementDays() * 24 * 60 * 60 * 20;
        int hourticks = rank.getPlaytimeRequirementHours() * 60 * 60 * 20;
        int totalTicks = dayticks + hourticks;
        int ticksToPass = switch (playerStats.getPrestige()) {
            case 1 -> totalTicks * 2;
            case 2 -> totalTicks * 3;
            case 3 -> totalTicks * 4;
            default -> totalTicks;
        };

        return p.getTicksLived() >= ticksToPass;
    }

    public int getCurrentBalance(Player p) {
        return (int)RankSystem.econ.getBalance(p);
    }

    public int getCurrentVotes(Player p) {
        return UserManager.getInstance().getVotingPluginUser(p.getUniqueId()).getTotal(TopVoter.AllTime);
    }

    public int getMCMMOPowerLevel(Player player) {
        return com.gmail.nossr50.util.player.UserManager.getPlayer(player).getPowerLevel();
    }

    /*
    public int getJobsPowerLevel(Player player) {
        return Jobs.getPlayerManager().getJobsPlayer(player).getTotalLevels();
    }

     */

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
        if (this.getMCMMOPowerLevel(p) >= rank.getMcmmoRequirement()) {
            return "&a&l✔";
        } else {
            int var10000 = this.getMCMMOPowerLevel(p);
            return "&c&l✖ &7Current: &f" + var10000;
        }
    }

    /*
    public String isPassedJobs(Player p, Rank rank) {
        if (this.getJobsPowerLevel(p) >= rank.getJobsRequirement()) {
            return "&a&l✔";
        } else {
            int var10000 = this.getJobsPowerLevel(p);
            return "&c&l✖ &7Current: &f" + var10000;
        }
    }
    */


    public String isPassedAnimalsBred(Player p, Rank rank) {
        if (this.getAnimalsBred(p) >= rank.getAnimalsBredRequirement()) {
            return "&a&l✔";
        } else {
            int var10000 = this.getAnimalsBred(p);
            return "&c&l✖ &7Current: &f" + var10000;
        }
    }

    public String isPassedMobsKilled(Player p, Rank rank) {
        if (this.getMobsKilled(p) >= rank.getMobsKilledRequirement()) {
            return "&a&l✔";
        } else {
            int var10000 = this.getMobsKilled(p);
            return "&c&l✖ &7Current: &f" + var10000;
        }
    }

    public String isPassedFishCaught(Player p, Rank rank) {
        if (this.getFishCaught(p) >= rank.getCatchFishRequirement()) {
            return "&a&l✔";
        } else {
            int var10000 = this.getFishCaught(p);
            return "&c&l✖ &7Current: &f" + var10000;
        }
    }

    public String isPassedItemsEnchanted(Player p, Rank rank) {
        if (this.getItemsEnchanted(p) >= rank.getEnchantedItemsRequirement()) {
            return "&a&l✔";
        } else {
            int var10000 = this.getItemsEnchanted(p);
            return "&c&l✖ &7Current: &f" + var10000;
        }
    }

    public String isPassedEnderdragonsKilled(Player p, Rank rank) {
        if (this.getEnderDragonsKilled(p) >= rank.getEnderDragonKilledRequirement()) {
            return "&a&l✔";
        } else {
            int var10000 = this.getEnderDragonsKilled(p);
            return "&c&l✖ &7Current: &f" + var10000;
        }
    }

    public String isPassedRaidsWon(Player p, Rank rank) {
        if (this.getRaidsWon(p) >= rank.getWinRaidsRequirement()) {
            return "&a&l✔";
        } else {
            int var10000 = this.getRaidsWon(p);
            return "&c&l✖ &7Current: &f" + var10000;
        }
    }

    public String isPassedBalance(Player p, Rank rank, PlayerStats playerStats) {
        if (this.getCurrentBalance(p) >= rank.getMoneyRequirement()) {
            return "&a&l✔";
        } else {
            int var10000 = this.getCurrentBalance(p);
            return "&c&l✖ &7Current: &f" + var10000;
        }
    }

    public String isPassedVotes(Player p, Rank rank, PlayerStats playerStats) {
        Rank lastRank = (Rank)RankSystem.ranks.get("Rank11");
        int playerVotes = switch (playerStats.getPrestige()) {
            case 1 -> this.getCurrentVotes(p) - lastRank.getVoteRequirement();
            case 2 -> this.getCurrentVotes(p) - lastRank.getVoteRequirement() * 2;
            case 3 -> this.getCurrentVotes(p) - lastRank.getVoteRequirement() * 3;
            default -> this.getCurrentVotes(p);
        };

        return playerVotes >= rank.getVoteRequirement() ? "&a&l✔" : "&c&l✖ &7Current: &f" + playerVotes;
    }

    public String isPassedPlaytime(Player p, Rank rank, PlayerStats playerStats) {
        if (this.passedPlaytime(p, rank, playerStats)) {
            return "&a&l✔";
        } else {
            int var10000 = this.getDaysLived(p);
            return "&c&l✖ &7Current: &f" + var10000 + " &7day(s) &f" + this.getHoursLived(p) + " &7hour(s)";
        }
    }

    public int getDaysLived(Player p) {
        return p.getTicksLived() / 20 / 60 / 60 / 24;
    }

    public int getHoursLived(Player p) {
        double totalHoursLived = (double)(p.getTicksLived() / 20 / 60 / 60);
        return (int)(totalHoursLived % 24.0);
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
        Iterator var4 = RankSystem.ranks.entrySet().iterator();

        Rank rank;
        do {
            if (!var4.hasNext()) {
                return null;
            }

            Map.Entry entry = (Map.Entry)var4.next();
            rank = (Rank)entry.getValue();
        } while(rank.getPosition() != nextRankPosition);

        return rank;
    }

    public Rank getFirstRank() {
        return (Rank)RankSystem.ranks.get("Rank0");
    }
}
