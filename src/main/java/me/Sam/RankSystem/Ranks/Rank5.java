package me.Sam.RankSystem.Ranks;

import me.Sam.RankSystem.Rank;

public class Rank5 extends Rank {

    public Rank5() {
        super("Rank5", "Quartz", "&8[&fQuartz&8] ", 4, false);
        this.setMoneyRequirement(10000);
        this.setMcmmoRequirement(100);
        this.setJobsRequirement(50);
        this.setEnchantedItemsRequirement(50);
    }
}
