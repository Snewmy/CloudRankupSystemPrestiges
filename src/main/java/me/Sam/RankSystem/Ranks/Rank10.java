package me.Sam.RankSystem.Ranks;

import me.Sam.RankSystem.Rank;

public class Rank10 extends Rank {

    public Rank10() {
        super("Rank10", "Diamond", "&8[&b&lDiamond&8] ", 9, true);
        this.setMoneyRequirement(35000);
        this.setMcmmoRequirement(300);
        this.setJobsRequirement(115);
        this.setEnderDragonKilledRequirement(35);
    }
}
