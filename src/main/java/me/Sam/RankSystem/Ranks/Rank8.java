package me.Sam.RankSystem.Ranks;

import me.Sam.RankSystem.Rank;

public class Rank8 extends Rank {

    public Rank8() {
        super("Rank8", "Emerald", "&8[&aEmerald&8] ", 7, false);
        this.setMoneyRequirement(25000);
        this.setMcmmoRequirement(200);
        this.setJobsRequirement(80);
        this.setMobsKilledRequirement(2000);
    }
}
