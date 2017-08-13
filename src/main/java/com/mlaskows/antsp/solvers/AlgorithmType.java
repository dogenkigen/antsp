package com.mlaskows.antsp.solvers;

public enum AlgorithmType {

    ANT_SYSTEM("Ant System"),
    MIN_MAX("Min Max Ant System"),
    RANK_BASED("Rank Based Ant System"),
    ELITIST("Elitist Ant System");

    private final String text;

    AlgorithmType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
