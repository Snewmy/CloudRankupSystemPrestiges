package me.Sam.RankSystem.Ranks;

import me.Sam.RankSystem.Rank;

public class Rank9 extends Rank {

    public Rank9() {
        super("Rank9", "Sapphire", "&8[&1Sapphire&8] ", 8, false);
        this.setMoneyRequirement(30000);
        this.setMcmmoRequirement(250);
        this.setJobsRequirement(100);
        this.setWinRaidsRequirement(30);
    }
}
