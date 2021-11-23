package me.Sam.RankSystem.Commands;

import me.Sam.RankSystem.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class RankupAdminCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (cmd.getName().equalsIgnoreCase("rankupadmin")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (!p.hasPermission("SamsRankSystem.admin")) {
                    return false;
                }
                RankManager rankManager = new RankManager();
                if (args.length < 2) {
                    p.sendMessage(Utils.chat("&7/rankupadmin check (playername)"));
                    p.sendMessage(Utils.chat("&7/rankupadmin promote (playername)"));
                    p.sendMessage(Utils.chat("&7/rankupadmin demote (playername)"));
                    return false;
                }
                if (args[0].equalsIgnoreCase("check")) {
                    String playerName = args[1];
                    if (Bukkit.getPlayer(playerName) == null) {
                        p.sendMessage(Utils.chat("&cInvalid player"));
                        return false;
                    }
                    Player receiver = Bukkit.getPlayer(playerName);
                    if (RankSystem.playerStatsMap.containsKey(receiver.getUniqueId())) {
                        PlayerStats playerStats = RankSystem.playerStatsMap.get(receiver.getUniqueId());
                        p.sendMessage(Utils.chat("&7----------&f" + receiver.getName() + "'s Rankup Stats&7" + "&7----------&f"));
                        p.sendMessage(Utils.chat("&3Current Rank: &f" + playerStats.getRank().getName()));
                        if (playerStats.getRank().isLastRank()) {
                            p.sendMessage(Utils.chat("&3Next Rank: &fis already last rank"));
                        } else {
                            p.sendMessage(Utils.chat("&3Next Rank: &f" + rankManager.getPlayerRankInProgress(receiver).getName()));
                        }
                        p.sendMessage(Utils.chat("&3Passed Vote Requirement: " + rankManager.isPassedVotes(receiver, rankManager.getPlayerRankInProgress(receiver))));
                        p.sendMessage(Utils.chat("&3Passed Money Requirement: " + rankManager.isPassedBalance(receiver, rankManager.getPlayerRankInProgress(receiver))));
                        p.sendMessage(Utils.chat("&3Passed Playtime Requirement: " + rankManager.isPassedPlaytime(receiver, rankManager.getPlayerRankInProgress(receiver))));
                        return false;
                    } else {
                        p.sendMessage(Utils.chat("&7----------&f" + receiver.getName() + "'s Rankup Stats&7" + "&7----------&f"));
                        p.sendMessage(Utils.chat("&3Current Rank: &fNone"));
                        p.sendMessage(Utils.chat("&3Next Rank: &f" + rankManager.getPlayerRankInProgress(receiver).getName()));
                        p.sendMessage(Utils.chat("&3Passed Vote Requirement: " + rankManager.isPassedVotes(receiver, rankManager.getPlayerRankInProgress(receiver))));
                        p.sendMessage(Utils.chat("&3Passed Money Requirement: " + rankManager.isPassedBalance(receiver, rankManager.getPlayerRankInProgress(receiver))));
                        p.sendMessage(Utils.chat("&3Passed Playtime Requirement: " + rankManager.isPassedPlaytime(receiver, rankManager.getPlayerRankInProgress(receiver))));
                    }
                    return false;
                } else if (args[0].equalsIgnoreCase("promote")) {
                    String playerName = args[1];
                    if (Bukkit.getPlayer(playerName) == null) {
                        p.sendMessage(Utils.chat("&cInvalid player"));
                        return false;
                    }
                    Player receiver = Bukkit.getPlayer(playerName);
                    if (RankSystem.playerStatsMap.containsKey(receiver.getUniqueId())) {
                        PlayerStats playerStats = RankSystem.playerStatsMap.get(receiver.getUniqueId());
                        if (playerStats.getRank().isLastRank()) {
                            p.sendMessage(Utils.chat("&cThat player is already the maximum rank!"));
                            return false;
                        }
                    }
                    rankManager.rankUpNoWithdraw(receiver);
                    p.sendMessage(Utils.chat("&aYou have successfully force ranked up " + receiver.getName() + "!"));
                    receiver.sendMessage(Utils.chat("&a" + p.getName() + " has forcefully ranked you up!"));
                    return false;
                } else if (args[0].equalsIgnoreCase("demote")) {
                    String playerName = args[1];
                    if (Bukkit.getPlayer(playerName) == null) {
                        p.sendMessage(Utils.chat("&cInvalid player"));
                        return false;
                    }
                    Player receiver = Bukkit.getPlayer(playerName);
                    if (RankSystem.playerStatsMap.containsKey(receiver.getUniqueId())) {
                        PlayerStats playerStats = RankSystem.playerStatsMap.get(receiver.getUniqueId());
                        if (playerStats.getRank().getPosition() == 0) {
                            p.sendMessage(Utils.chat("&cThat player is already the lowest rank!"));
                            return false;
                        }
                        rankManager.rankDown(receiver);
                        p.sendMessage(Utils.chat("&aYou have successfully force ranked down " + receiver.getName() + "!"));
                        receiver.sendMessage(Utils.chat("&a" + p.getName() + " has forcefully ranked you down!"));
                        return false;
                    } else {
                        p.sendMessage(Utils.chat("&cThat player is not even the first rank yet."));
                        return false;
                    }
                } else if (args[0].equalsIgnoreCase("reload")) {
                    long startTime = System.currentTimeMillis();
                    RankSystem.instance.saveData();
                    RankSystem.playerStatsMap.clear();
                    RankSystem.instance.loadData();
                    for (Map.Entry<String, Rank> entry : RankSystem.ranks.entrySet()) {
                        Rank rank = entry.getValue();
                        rank.setRewardCommands(RankSystem.instance.getConfig().getStringList(rank.getKey() + ".RewardCommands"));
                        rank.setRewardList(RankSystem.instance.getConfig().getStringList(rank.getKey() + ".RewardList"));
                    }
                    long timeTaken = System.currentTimeMillis() - startTime;
                    sender.sendMessage(Utils.chat("&aReload successful. Took " + timeTaken + "ms."));
                    return false;
                }
            }
        }
        return false;
    }
}
