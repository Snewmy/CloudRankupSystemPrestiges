package me.Sam.RankSystem;

public class PlayerStats {

    private Rank rank;
    private boolean prefixOn;

    public PlayerStats(Rank rank) {
        this.rank = rank;
        this.prefixOn = true;
    }

    public void setPrefixOn(boolean prefixOn) {
        this.prefixOn = prefixOn;
    }

    public boolean isPrefixOn() {
        return prefixOn;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }
}
