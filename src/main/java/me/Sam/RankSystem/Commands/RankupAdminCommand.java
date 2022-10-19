package me.Sam.RankSystem.Commands;

import me.Sam.RankSystem.PlayerStats;
import me.Sam.RankSystem.Rank;
import me.Sam.RankSystem.RankManager;
import me.Sam.RankSystem.RankSystem;
import me.Sam.RankSystem.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RankupAdminCommand implements CommandExecutor {
    public RankupAdminCommand() {
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (cmd.getName().equalsIgnoreCase("rankupadmin") && sender instanceof Player p) {
            if (!p.hasPermission("SamsRankSystem.admin")) {
                return false;
            }

            RankManager rankManager = new RankManager();
            if (args.length < 2 && !args[0].equalsIgnoreCase("reload")) {
                p.sendMessage(Utils.chat("&7/rankupadmin check (playername)"));
                p.sendMessage(Utils.chat("&7/rankupadmin promote (playername)"));
                p.sendMessage(Utils.chat("&7/rankupadmin demote (playername)"));
                return false;
            }

            Player receiver;
            String playerName;
            PlayerStats playerStats;
            if (args[0].equalsIgnoreCase("check")) {
                playerName = args[1];
                if (!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(playerName))) {
                    p.sendMessage(Utils.chat("&cInvalid player"));
                    return false;
                }

                receiver = Bukkit.getPlayer(playerName);
                Rank var10001;
                String var13;
                if (RankSystem.playerStatsMap.containsKey(receiver.getUniqueId())) {
                    playerStats = (PlayerStats)RankSystem.playerStatsMap.get(receiver.getUniqueId());
                    p.sendMessage(Utils.chat("&7----------&f" + receiver.getName() + "'s Rankup Stats&7&7----------&f"));
                    p.sendMessage(Utils.chat("&3Current Rank: &f" + playerStats.getRank().getName()));
                    if (playerStats.getRank().isLastRank()) {
                        p.sendMessage(Utils.chat("&3Next Rank: &fis already last rank"));
                    } else {
                        var10001 = rankManager.getPlayerRankInProgress(receiver);
                        p.sendMessage(Utils.chat("&3Next Rank: &f" + var10001.getName()));
                    }

                    var13 = rankManager.isPassedVotes(receiver, rankManager.getPlayerRankInProgress(receiver), playerStats);
                    p.sendMessage(Utils.chat("&3Passed Vote Requirement: " + var13));
                    var13 = rankManager.isPassedBalance(receiver, rankManager.getPlayerRankInProgress(receiver), playerStats);
                    p.sendMessage(Utils.chat("&3Passed Money Requirement: " + var13));
                    var13 = rankManager.isPassedPlaytime(receiver, rankManager.getPlayerRankInProgress(receiver), playerStats);
                    p.sendMessage(Utils.chat("&3Passed Playtime Requirement: " + var13));
                    return false;
                }

                playerStats = new PlayerStats(rankManager.getFirstRank());
                RankSystem.playerStatsMap.put(p.getUniqueId(), playerStats);
                p.sendMessage(Utils.chat("&7----------&f" + receiver.getName() + "'s Rankup Stats&7&7----------&f"));
                p.sendMessage(Utils.chat("&3Current Rank: &fNone"));
                var10001 = rankManager.getPlayerRankInProgress(receiver);
                p.sendMessage(Utils.chat("&3Next Rank: &f" + var10001.getName()));
                var13 = rankManager.isPassedVotes(receiver, rankManager.getPlayerRankInProgress(receiver), playerStats);
                p.sendMessage(Utils.chat("&3Passed Vote Requirement: " + var13));
                var13 = rankManager.isPassedBalance(receiver, rankManager.getPlayerRankInProgress(receiver), playerStats);
                p.sendMessage(Utils.chat("&3Passed Money Requirement: " + var13));
                var13 = rankManager.isPassedPlaytime(receiver, rankManager.getPlayerRankInProgress(receiver), playerStats);
                p.sendMessage(Utils.chat("&3Passed Playtime Requirement: " + var13));
                return false;
            }

            if (args[0].equalsIgnoreCase("promote")) {
                playerName = args[1];
                if (Bukkit.getPlayer(playerName) == null) {
                    p.sendMessage(Utils.chat("&cInvalid player"));
                    return false;
                }

                receiver = Bukkit.getPlayer(playerName);
                if (RankSystem.playerStatsMap.containsKey(receiver.getUniqueId())) {
                    playerStats = (PlayerStats)RankSystem.playerStatsMap.get(receiver.getUniqueId());
                    if (playerStats.getRank().isLastRank()) {
                        p.sendMessage(Utils.chat("&cThat player is already the maximum rank!"));
                        return false;
                    }
                }

                rankManager.rankUpNoWithdraw(receiver);
                p.sendMessage(Utils.chat("&aYou have successfully force ranked up " + receiver.getName() + "!"));
                receiver.sendMessage(Utils.chat("&a" + p.getName() + " has forcefully ranked you up!"));
                return false;
            }

            if (args[0].equalsIgnoreCase("demote")) {
                playerName = args[1];
                if (Bukkit.getPlayer(playerName) == null) {
                    p.sendMessage(Utils.chat("&cInvalid player"));
                    return false;
                }

                receiver = Bukkit.getPlayer(playerName);
                if (RankSystem.playerStatsMap.containsKey(receiver.getUniqueId())) {
                    playerStats = (PlayerStats)RankSystem.playerStatsMap.get(receiver.getUniqueId());
                    if (playerStats.getRank().getPosition() == 0) {
                        p.sendMessage(Utils.chat("&cThat player is already the lowest rank!"));
                        return false;
                    }

                    rankManager.rankDown(receiver);
                    p.sendMessage(Utils.chat("&aYou have successfully force ranked down " + receiver.getName() + "!"));
                    receiver.sendMessage(Utils.chat("&a" + p.getName() + " has forcefully ranked you down!"));
                    return false;
                }

                p.sendMessage(Utils.chat("&cThat player is not even the first rank yet."));
                return false;
            }

            if (args[0].equalsIgnoreCase("reload")) {
                long startTime = System.currentTimeMillis();
                RankSystem.instance.saveData();
                RankSystem.playerStatsMap.clear();
                RankSystem.instance.loadData();
                RankSystem.ranks.clear();
                RankSystem.instance.reloadConfig();
                RankSystem.instance.initRanks();
                long timeTaken = System.currentTimeMillis() - startTime;
                sender.sendMessage(Utils.chat("&aReload successful. Took " + timeTaken + "ms."));
                return false;
            }
        }

        return false;
    }
}
