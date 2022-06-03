package me.Sam.RankSystem;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static me.Sam.RankSystem.ButtonType.RANKINPROGRESS;

public class GUI {

    RankManager rankManager;

    public GUI() {
        this.rankManager = new RankManager();
    }

    public void createGUI(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 36, Utils.chat("{#8bf7f7}/rankup"));
        ItemStack cyanGlass = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1);
        ItemMeta itemMeta = cyanGlass.getItemMeta();
        itemMeta.setDisplayName(" ");
        cyanGlass.setItemMeta(itemMeta);
        ItemStack back = new ItemStack(Material.ARROW, 1);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName(Utils.chat("&4&lBack to ranks"));
        back.setItemMeta(backMeta);
        inventory.setItem(0, cyanGlass);
        inventory.setItem(1, cyanGlass);
        inventory.setItem(2, cyanGlass);
        inventory.setItem(3, cyanGlass);
        inventory.setItem(4, cyanGlass);
        inventory.setItem(5, cyanGlass);
        inventory.setItem(6, cyanGlass);
        inventory.setItem(7, cyanGlass);
        inventory.setItem(8, cyanGlass);
        inventory.setItem(9, cyanGlass);
        inventory.setItem(17, cyanGlass);
        inventory.setItem(18, cyanGlass);
        inventory.setItem(19, cyanGlass);
        inventory.setItem(22, cyanGlass);
        inventory.setItem(25, cyanGlass);
        inventory.setItem(26, cyanGlass);
        inventory.setItem(27, cyanGlass);
        inventory.setItem(28, cyanGlass);
        inventory.setItem(29, cyanGlass);
        inventory.setItem(30, cyanGlass);
        inventory.setItem(31, cyanGlass);
        inventory.setItem(32, cyanGlass);
        inventory.setItem(33, cyanGlass);
        inventory.setItem(34, cyanGlass);
        inventory.setItem(35, back);
        for (Map.Entry<String, Rank> entry : RankSystem.ranks.entrySet()) {
            Rank rank = entry.getValue();
            inventory.addItem(addRank(player, rank));
        }
        player.openInventory(inventory);
    }

    public ItemStack addRank(Player player, Rank rank) {
        ItemStack itemStack;
        switch (getPlayerButtonType(player, rank)) {
            case RANKCOMPLETED:
                itemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(Utils.chat("&a&lCOMPLETED"));
                List<String> lore = new ArrayList<>();
                lore.add(Utils.chat("{#8bf7f7}Rank&7: " + rank.getChatPrefix()));
                lore.add(Utils.chat("{#8bf7f7}Requirements&7: &aCompleted ✔"));
                lore.add("");
                lore.add(Utils.chat("{#8bf7f7}Rewards&7:"));
                for (String rewardLine : rank.getRewardList()) {
                    lore.add(Utils.chat("&b&l◆ &f" + rewardLine));
                }
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
                break;
            case RANKINPROGRESS:
                itemStack = new ItemStack(Material.EMERALD, 1);
                ItemMeta itemMeta2 = itemStack.getItemMeta();
                itemMeta2.addEnchant(Enchantment.ARROW_DAMAGE, 1, false);
                itemMeta2.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                itemMeta2.setDisplayName(Utils.chat("&e&lIN PROGRESS"));
                List<String> lore2 = new ArrayList<>();
                if (rankManager.canRankup(player)) {
                    lore2.add(Utils.chat("&a&lYou can rankup now!"));
                }
                lore2.add(Utils.chat("{#8bf7f7}Rank&7: " + rank.getChatPrefix()));
                lore2.add(Utils.chat("{#8bf7f7}Requirements&7:"));
                if (rank.getPlaytimeRequirementDays() != 0)
                    lore2.add(Utils.chat("&bTime Played: &f" + rank.getPlaytimeRequirementDays() + " &7Day(s) &f" + rank.getPlaytimeRequirementHours() + " &7Hours(s) " + rankManager.isPassedPlaytime(player, rank)));
                if (rank.getVoteRequirement() != 0)
                    lore2.add(Utils.chat("&bVotes: &f" + rank.getVoteRequirement() + " " + rankManager.isPassedVotes(player, rank)));

                if (rank.getMoneyRequirement() != 0)
                    lore2.add(Utils.chat("&bMoney: &f" + rank.getMoneyRequirement() + " " + rankManager.isPassedBalance(player, rank)));
                if (rank.getMcmmoRequirement() != 0)
                    lore2.add(Utils.chat("&bmcMMO Powerlevel: &f" + rank.getMcmmoRequirement() + " " + rankManager.isPassedMcmmo(player, rank)));
                if (rank.getJobsRequirement() != 0)
                    lore2.add(Utils.chat("&bJobs Powerlevel: &f" + rank.getJobsRequirement() + " " + rankManager.isPassedJobs(player, rank)));
                if (rank.getEnchantedItemsRequirement() != 0)
                    lore2.add(Utils.chat("&bItems Enchanted: &f" + rank.getEnchantedItemsRequirement() + " " + rankManager.isPassedItemsEnchanted(player, rank)));
                if (rank.getMobsKilledRequirement() != 0)
                    lore2.add(Utils.chat("&bMonsters Killed: &f" + rank.getMobsKilledRequirement() + " " + rankManager.isPassedMobsKilled(player, rank)));
                if (rank.getAnimalsBredRequirement() != 0)
                    lore2.add(Utils.chat("&bAnimals Bred: &f" + rank.getAnimalsBredRequirement() + " " + rankManager.isPassedAnimalsBred(player, rank)));
                if (rank.getWinRaidsRequirement() != 0)
                    lore2.add(Utils.chat("&bRaids Won: &f" + rank.getWinRaidsRequirement() + " " + rankManager.isPassedRaidsWon(player, rank)));
                if (rank.getCatchFishRequirement() != 0)
                    lore2.add(Utils.chat("&bFish Caught: &f" + rank.getCatchFishRequirement() + " " + rankManager.isPassedFishCaught(player, rank)));
                if (rank.getEnderDragonKilledRequirement() != 0)
                    lore2.add(Utils.chat("&bEnderDragons Killed: &f" + rank.getEnderDragonKilledRequirement() + " " + rankManager.isPassedEnderdragonsKilled(player, rank)));
                lore2.add(" ");
                lore2.add(Utils.chat("{#8bf7f7}Rewards&7:"));
                for (String rewardLine : rank.getRewardList()) {
                    lore2.add(Utils.chat("&b&l◆ &f" + rewardLine));
                }
                itemMeta2.setLore(lore2);
                itemStack.setItemMeta(itemMeta2);
                break;
            case RANKLOCKED:
                itemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
                ItemMeta itemMeta3 = itemStack.getItemMeta();
                itemMeta3.setDisplayName(Utils.chat("&c&lLOCKED"));
                List<String> lore3 = new ArrayList<>();
                lore3.add(Utils.chat("{#8bf7f7}Rank&7: " + rank.getChatPrefix()));
                lore3.add(Utils.chat("{#8bf7f7}Requirements&7:"));
                if (rank.getPlaytimeRequirementDays() != 0)
                    lore3.add(Utils.chat("&bTime Played: &f" + rank.getPlaytimeRequirementDays() + " &7Day(s) &f" + rank.getPlaytimeRequirementHours() + " &7Hours(s) " + rankManager.isPassedPlaytime(player, rank)));
                if (rank.getVoteRequirement() != 0)
                    lore3.add(Utils.chat("&bVotes: &f" + rank.getVoteRequirement() + " " + rankManager.isPassedVotes(player, rank)));
                if (rank.getMoneyRequirement() != 0)
                    lore3.add(Utils.chat("&bMoney: &f" + rank.getMoneyRequirement() + " " + rankManager.isPassedBalance(player, rank)));
                if (rank.getMcmmoRequirement() != 0)
                    lore3.add(Utils.chat("&bmcMMO Powerlevel: &f" + rank.getMcmmoRequirement() + " " + rankManager.isPassedMcmmo(player, rank)));
                if (rank.getJobsRequirement() != 0)
                    lore3.add(Utils.chat("&bJobs Powerlevel: &f" + rank.getJobsRequirement() + " " + rankManager.isPassedJobs(player, rank)));
                if (rank.getEnchantedItemsRequirement() != 0)
                    lore3.add(Utils.chat("&bItems Enchanted: &f" + rank.getEnchantedItemsRequirement() + " " + rankManager.isPassedItemsEnchanted(player, rank)));
                if (rank.getMobsKilledRequirement() != 0)
                    lore3.add(Utils.chat("&bMonsters Killed: &f" + rank.getMobsKilledRequirement() + " " + rankManager.isPassedMobsKilled(player, rank)));
                if (rank.getAnimalsBredRequirement() != 0)
                    lore3.add(Utils.chat("&bAnimals Bred: &f" + rank.getAnimalsBredRequirement() + " " + rankManager.isPassedAnimalsBred(player, rank)));
                if (rank.getWinRaidsRequirement() != 0)
                    lore3.add(Utils.chat("&bRaids Won: &f" + rank.getWinRaidsRequirement() + " " + rankManager.isPassedRaidsWon(player, rank)));
                if (rank.getCatchFishRequirement() != 0)
                    lore3.add(Utils.chat("&bFish Caught: &f" + rank.getCatchFishRequirement() + " " + rankManager.isPassedFishCaught(player, rank)));
                if (rank.getEnderDragonKilledRequirement() != 0)
                    lore3.add(Utils.chat("&bEnderDragons Killed: &f" + rank.getEnderDragonKilledRequirement() + " " + rankManager.isPassedEnderdragonsKilled(player, rank)));
                lore3.add(" ");
                lore3.add(Utils.chat("{#8bf7f7}Rewards&7:"));
                for (String rewardLine : rank.getRewardList()) {
                    lore3.add(Utils.chat("&b&l◆ &f" + rewardLine));
                }
                itemMeta3.setLore(lore3);
                itemStack.setItemMeta(itemMeta3);
                break;
            default:
                //this will never happen lmao
                itemStack = new ItemStack(Material.STICK, 1);
        }
        return itemStack;
    }

    public ButtonType getPlayerButtonType(Player player, Rank rank) {
        if (rankManager.getPlayerRankInProgress(player) == null) {
            return ButtonType.RANKCOMPLETED;
        }
        if (rankManager.getPlayerRankInProgress(player).getPosition() == rank.getPosition()) {
            return ButtonType.RANKINPROGRESS;
        }
        if (RankSystem.playerStatsMap.containsKey(player.getUniqueId())) {
            PlayerStats playerStats = RankSystem.playerStatsMap.get(player.getUniqueId());
            if (rank.getPosition() <= playerStats.getRank().getPosition()) {
                return ButtonType.RANKCOMPLETED;
            } else if (rank.getPosition() > rankManager.getPlayerRankInProgress(player).getPosition()) {
                return ButtonType.RANKLOCKED;
            }
        }
        return ButtonType.RANKLOCKED;
    }

}
