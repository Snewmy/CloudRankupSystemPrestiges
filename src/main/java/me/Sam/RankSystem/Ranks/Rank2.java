package me.Sam.RankSystem.Ranks;

import me.Sam.RankSystem.Rank;

public class Rank2 extends Rank {

    public Rank2() {
        super("Rank2", "Ruby", "&8[&4Ruby&8] ", 1, false);
        this.setMoneyRequirement(2500);
        this.setMcmmoRequirement(25);
        this.setJobsRequirement(20);
        this.setAnimalsBredRequirement(50);
    }
}
