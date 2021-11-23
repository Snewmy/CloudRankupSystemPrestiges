package me.Sam.RankSystem.Ranks;

import me.Sam.RankSystem.Rank;

public class Rank3 extends Rank {

    public Rank3() {
        super("Rank3", "Lapis", "&8[&1Lapis&8] ", 2, false);
        this.setMoneyRequirement(5000);
        this.setMcmmoRequirement(50);
        this.setJobsRequirement(30);
        this.setCatchFishRequirement(40);
    }
}
