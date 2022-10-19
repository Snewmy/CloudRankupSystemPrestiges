package me.Sam.RankSystem.Events;

import me.Sam.RankSystem.Rank;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RankupEvent extends Event {
    public static final HandlerList HANDLERS = new HandlerList();
    private Player player;
    private Rank rank;

    public RankupEvent(Player player, Rank rank) {
        this.player = player;
        this.rank = rank;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Rank getRank() {
        return this.rank;
    }
}
