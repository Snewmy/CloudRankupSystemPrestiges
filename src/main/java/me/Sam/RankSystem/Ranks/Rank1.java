package me.Sam.RankSystem.Ranks;

import me.Sam.RankSystem.Rank;

public class Rank1 extends Rank {

    public Rank1() {
        super("Rank1", "Jade", "&8[&aJade&8] ", 0, false);
        this.setMoneyRequirement(1000);
        this.setMcmmoRequirement(10);
        this.setJobsRequirement(5);
    }
}
