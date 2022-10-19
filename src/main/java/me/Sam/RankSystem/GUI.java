package me.Sam.RankSystem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUI {
    RankManager rankManager = new RankManager();

    public GUI() {
    }

    public void createGUI(Player player) {
        PlayerStats playerStats = (PlayerStats)RankSystem.playerStatsMap.get(player.getUniqueId());
        Inventory inventory = Bukkit.createInventory((InventoryHolder)null, 36, Utils.chat("{#56eafa}/rankup"));
        ItemStack cyanGlass = new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1);
        ItemMeta itemMeta = cyanGlass.getItemMeta();
        itemMeta.setDisplayName(" ");
        cyanGlass.setItemMeta(itemMeta);
        ItemStack back = new ItemStack(Material.ARROW, 1);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName(Utils.chat("&4&lBack to ranks"));
        back.setItemMeta(backMeta);
        ItemStack prestigeIcon = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta prestigeMeta = prestigeIcon.getItemMeta();
        prestigeMeta.setDisplayName(Utils.chat("{#56eafa}&lPrestige"));
        List<String> prestigeLore = new ArrayList();
        prestigeLore.add(Utils.chat("&7Start at the first rank again,"));
        prestigeLore.add(Utils.chat("&7keep your perks, and get a"));
        prestigeLore.add(Utils.chat("{#56eafa}15% &7sell booster for each prestige!"));
        prestigeLore.add(Utils.chat("&f(Stacks with previous boosts!)"));
        prestigeLore.add(Utils.chat(""));
        prestigeLore.add(Utils.chat("&fCurrent Prestige: {#56eafa}" + playerStats.getPrestige()));
        prestigeLore.add(Utils.chat("&fMax Prestige: {#56eafa}4"));
        if (playerStats.getPrestige() == 4) {
            prestigeLore.add(Utils.chat("{#56eafa}You are max prestige!"));
        } else if (playerStats.getRank().isLastRank()) {
            prestigeLore.add(Utils.chat("&aClick me to prestige!"));
        } else {
            prestigeLore.add(Utils.chat("&cYou may not prestige yet!"));
        }

        prestigeMeta.setLore(prestigeLore);
        prestigeIcon.setItemMeta(prestigeMeta);
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
        inventory.setItem(31, prestigeIcon);
        inventory.setItem(32, cyanGlass);
        inventory.setItem(33, cyanGlass);
        inventory.setItem(34, cyanGlass);
        inventory.setItem(35, back);
        Iterator var11 = RankSystem.ranks.entrySet().iterator();

        while(var11.hasNext()) {
            Map.Entry<String, Rank> entry = (Map.Entry)var11.next();
            Rank rank = (Rank)entry.getValue();
            if (rank.getPosition() != 0) {
                inventory.addItem(new ItemStack[]{this.addRankItem(player, rank, playerStats)});
            }
        }

        player.openInventory(inventory);
    }

    public ItemStack addRankItem(Player player, Rank rank, PlayerStats playerStats) {
        switch (this.getPlayerButtonType(player, rank)) {
            case RANKCOMPLETED:
                return this.getRankCompletedItem(player, rank, playerStats);
            case RANKINPROGRESS:
                return this.getRankInProgressItem(player, rank, playerStats);
            case RANKLOCKED:
                return this.getRankLockedItem(player, rank, playerStats);
            default:
                return null;
        }
    }

    public ItemStack getRankCompletedItem(Player player, Rank rank, PlayerStats playerStats) {
        ItemStack itemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(Utils.chat("&a&lCOMPLETED"));
        List<String> lore = new ArrayList();
        lore.add(Utils.chat("{#56eafa}Rank&7: " + rank.getChatPrefix()));
        lore.add(Utils.chat("{#56eafa}Requirements&7: &aCompleted ✔"));
        int var10001;
        if (rank.getVoteRequirement() != 0) {
            var10001 = rank.getVoteRequirement();
            lore.add(Utils.chat("{#56eafa}Votes: &7" + var10001 + " " + this.rankManager.isPassedVotes(player, rank, playerStats)));
        }

        if (rank.getMoneyRequirement() != 0) {
            var10001 = rank.getMoneyRequirement();
            lore.add(Utils.chat("{#56eafa}Money: &7" + var10001 + " " + this.rankManager.isPassedBalance(player, rank, playerStats)));
        }

        Iterator var7;
        String rewardLine;
        if (playerStats.getPrestige() == 0) {
            lore.add("");
            lore.add(Utils.chat("{#56eafa}Rewards&7:"));
            var7 = rank.getRewardList().iterator();

            while(var7.hasNext()) {
                rewardLine = (String)var7.next();
                lore.add(Utils.chat("{#56eafa}&l◆ &7" + rewardLine));
            }
        } else {
            lore.add("");
            lore.add(Utils.chat("&cNote: Rewards are view only."));
            lore.add(Utils.chat("&cOnly available before prestiging."));
            lore.add(Utils.chat("{#56eafa}Rewards&7:"));
            var7 = rank.getRewardList().iterator();

            while(var7.hasNext()) {
                rewardLine = (String)var7.next();
                lore.add(Utils.chat("{#56eafa}&l◆ &7" + rewardLine));
            }
        }

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack getRankInProgressItem(Player player, Rank rank, PlayerStats playerStats) {
        ItemStack itemStack = new ItemStack(Material.EMERALD, 1);
        ItemMeta itemMeta2 = itemStack.getItemMeta();
        itemMeta2.addEnchant(Enchantment.ARROW_DAMAGE, 1, false);
        itemMeta2.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        itemMeta2.setDisplayName(Utils.chat("&e&lIN PROGRESS"));
        List<String> lore2 = new ArrayList();
        if (this.rankManager.canRankup(player)) {
            lore2.add(Utils.chat("&a&lYou can rankup now!"));
        }

        lore2.add(Utils.chat("{#56eafa}Rank&7: " + rank.getChatPrefix()));
        lore2.add(Utils.chat("{#56eafa}Requirements&7:"));
        int var10001;
        if (rank.getVoteRequirement() != 0) {
            var10001 = rank.getVoteRequirement();
            lore2.add(Utils.chat("{#56eafa}Votes: &7" + var10001 + " " + this.rankManager.isPassedVotes(player, rank, playerStats)));
        }

        if (rank.getMoneyRequirement() != 0) {
            var10001 = rank.getMoneyRequirement();
            lore2.add(Utils.chat("{#56eafa}Money: &7" + var10001 + " " + this.rankManager.isPassedBalance(player, rank, playerStats)));
        }

        Iterator var7;
        String rewardLine;
        if (playerStats.getPrestige() == 0) {
            lore2.add("");
            lore2.add(Utils.chat("{#56eafa}Rewards&7:"));
            var7 = rank.getRewardList().iterator();

            while(var7.hasNext()) {
                rewardLine = (String)var7.next();
                lore2.add(Utils.chat("{#56eafa}&l◆ &7" + rewardLine));
            }
        } else {
            lore2.add("");
            lore2.add(Utils.chat("&cNote: Rewards are view only."));
            lore2.add(Utils.chat("&cOnly available before prestiging."));
            lore2.add(Utils.chat("{#56eafa}Rewards&7:"));
            var7 = rank.getRewardList().iterator();

            while(var7.hasNext()) {
                rewardLine = (String)var7.next();
                lore2.add(Utils.chat("{#56eafa}&l◆ &7" + rewardLine));
            }
        }

        itemMeta2.setLore(lore2);
        itemStack.setItemMeta(itemMeta2);
        return itemStack;
    }

    public ItemStack getRankLockedItem(Player player, Rank rank, PlayerStats playerStats) {
        ItemStack itemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        ItemMeta itemMeta3 = itemStack.getItemMeta();
        itemMeta3.setDisplayName(Utils.chat("&c&lLOCKED"));
        List<String> lore3 = new ArrayList();
        lore3.add(Utils.chat("{#56eafa}Rank&7: " + rank.getChatPrefix()));
        lore3.add(Utils.chat("{#56eafa}Requirements&7:"));
        int var10001;
        if (rank.getVoteRequirement() != 0) {
            var10001 = rank.getVoteRequirement();
            lore3.add(Utils.chat("{#56eafa}Votes: &7" + var10001 + " " + this.rankManager.isPassedVotes(player, rank, playerStats)));
        }

        if (rank.getMoneyRequirement() != 0) {
            var10001 = rank.getMoneyRequirement();
            lore3.add(Utils.chat("{#56eafa}Money: &7" + var10001 + " " + this.rankManager.isPassedBalance(player, rank, playerStats)));
        }

        Iterator var7;
        String rewardLine;
        if (playerStats.getPrestige() == 0) {
            lore3.add("");
            lore3.add(Utils.chat("{#56eafa}Rewards&7:"));
            var7 = rank.getRewardList().iterator();

            while(var7.hasNext()) {
                rewardLine = (String)var7.next();
                lore3.add(Utils.chat("{#56eafa}&l◆ &7" + rewardLine));
            }
        } else {
            lore3.add("");
            lore3.add(Utils.chat("&cNote: Rewards are view only."));
            lore3.add(Utils.chat("&cOnly available before prestiging."));
            lore3.add(Utils.chat("{#56eafa}Rewards&7:"));
            var7 = rank.getRewardList().iterator();

            while(var7.hasNext()) {
                rewardLine = (String)var7.next();
                lore3.add(Utils.chat("{#56eafa}&l◆ &7" + rewardLine));
            }
        }

        itemMeta3.setLore(lore3);
        itemStack.setItemMeta(itemMeta3);
        return itemStack;
    }

    public ButtonType getPlayerButtonType(Player player, Rank rank) {
        if (this.rankManager.getPlayerRankInProgress(player) == null) {
            return ButtonType.RANKCOMPLETED;
        } else if (this.rankManager.getPlayerRankInProgress(player).getPosition() == rank.getPosition()) {
            return ButtonType.RANKINPROGRESS;
        } else {
            if (RankSystem.playerStatsMap.containsKey(player.getUniqueId())) {
                PlayerStats playerStats = (PlayerStats)RankSystem.playerStatsMap.get(player.getUniqueId());
                if (rank.getPosition() <= playerStats.getRank().getPosition()) {
                    return ButtonType.RANKCOMPLETED;
                }

                if (rank.getPosition() > this.rankManager.getPlayerRankInProgress(player).getPosition()) {
                    return ButtonType.RANKLOCKED;
                }
            }

            return ButtonType.RANKLOCKED;
        }
    }
}
