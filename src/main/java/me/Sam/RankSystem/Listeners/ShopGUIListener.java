package me.Sam.RankSystem.Listeners;

import me.Sam.RankSystem.PlayerStats;
import me.Sam.RankSystem.RankManager;
import me.Sam.RankSystem.RankSystem;
import me.Sam.RankSystem.Utils;
import net.brcdev.shopgui.event.ShopPreTransactionEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ShopGUIListener implements Listener {
    public ShopGUIListener() {
    }

    @EventHandler
    public void onSell(ShopPreTransactionEvent event) {
        Player player = event.getPlayer();
        RankManager rankManager = new RankManager();
        PlayerStats playerStats;
        if (!RankSystem.playerStatsMap.containsKey(player.getUniqueId())) {
            playerStats = new PlayerStats(rankManager.getFirstRank());
            RankSystem.playerStatsMap.put(player.getUniqueId(), playerStats);
        }

        playerStats = (PlayerStats)RankSystem.playerStatsMap.get(player.getUniqueId());
        if (playerStats.getPrestige() > 0) {
            double bonusMultiplier = 0.0;
            switch (playerStats.getPrestige()) {
                case 1:
                    bonusMultiplier = 1.15;
                    break;
                case 2:
                    bonusMultiplier = 1.3;
                    break;
                case 3:
                    bonusMultiplier = 1.45;
                case 4:
                    bonusMultiplier = 1.60;
            }

            event.setPrice(event.getPrice() * bonusMultiplier);
            double bonusAmount = bonusMultiplier * event.getPrice() - event.getPrice();
            double var10001 = (double)Math.round(bonusAmount * 100.0);
            player.sendMessage(Utils.chat("&aYou received an extra $" + var10001 / 100.0 + " from your prestige booster!"));
        }

    }
}
