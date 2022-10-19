package me.Sam.RankSystem;

public class PlayerStats {
    private Rank rank;
    int prestige;
    private boolean prefixOn;

    public PlayerStats(Rank rank) {
        this.rank = rank;
        this.prefixOn = true;
        this.prestige = 0;
    }

    public void setPrefixOn(boolean prefixOn) {
        this.prefixOn = prefixOn;
    }

    public boolean isPrefixOn() {
        return this.prefixOn;
    }

    public Rank getRank() {
        return this.rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public int getPrestige() {
        return this.prestige;
    }

    public String getPrestigeIcon() {
        switch (this.prestige) {
            case 1:
                return PrestigeIcon.ONE.getIcon();
            case 2:
                return PrestigeIcon.TWO.getIcon();
            case 3:
                return PrestigeIcon.THREE.getIcon();
            case 4:
                return PrestigeIcon.FOUR.getIcon();
            default:
                return "";
        }
    }

    public void setPrestige(int prestige) {
        this.prestige = prestige;
    }
}
