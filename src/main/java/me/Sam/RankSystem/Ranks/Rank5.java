package me.Sam.RankSystem.Ranks;

import me.Sam.RankSystem.Rank;

public class Rank5 extends Rank {

    public Rank5() {
        super("Rank5", "Uproar", "{#49fc21}[Uproar] ", 5, false);
        this.setVoteRequirement(75);
    }
}
