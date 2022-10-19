package me.Sam.RankSystem;

public enum PrestigeIcon {
    ONE("①"),
    TWO("②"),
    THREE("③"),
    FOUR("④");

    public String icon;

    private PrestigeIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return this.icon;
    }
}
