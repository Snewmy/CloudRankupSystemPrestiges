package me.Sam.RankSystem.Ranks;

import me.Sam.RankSystem.Rank;

public class Rank6 extends Rank {

    public Rank6() {
        super("Rank6", "Topaz", "&8[&bTopaz&8] ", 5, false);
        this.setMoneyRequirement(15000);
        this.setMcmmoRequirement(125);
        this.setJobsRequirement(60);
        this.setCatchFishRequirement(115);
    }
}
