package me.Sam.RankSystem.Ranks;

import me.Sam.RankSystem.Rank;

public class Rank4 extends Rank {

    public Rank4() {
        super("Rank4", "Pearl", "&8[&dPearl&8] ", 3, false);
        this.setMoneyRequirement(7500);
        this.setMcmmoRequirement(75);
        this.setJobsRequirement(40);
        this.setMobsKilledRequirement(500);
    }
}
