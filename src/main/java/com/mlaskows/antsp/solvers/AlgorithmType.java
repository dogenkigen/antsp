package com.mlaskows.antsp.solvers;

import java.util.EnumSet;

public enum AlgorithmType {

    ANT_SYSTEM("Ant System"),
    MIN_MAX("MAX-MIN Ant System"),
    RANK_BASED("Rank Based Ant System"),
    ELITIST("Elitist Ant System");

    private static final EnumSet<AlgorithmType> antBased =
            EnumSet.of(ANT_SYSTEM, MIN_MAX, RANK_BASED, ELITIST);

    private final String text;

    AlgorithmType(final String text) {
        this.text = text;
    }

    public boolean isAntBased() {
        return antBased.contains(this);
    }

    @Override

    public String toString() {
        return text;
    }
}
