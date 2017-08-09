package com.mlaskows.antsp.exeptions;

/**
 * Created by mlaskows on 05/07/2017.
 */
public enum Reason {

    NO_BEST_ANT("Couldn't find best ant to construct solution!"),
    EMPTY_HEURISTIC_MATRIX("Heuristic matrix can't be empty!"),
    EMPTY_NN_MATRIX("Nearest neighbors matrix can't be empty!");

    private final String text;

    private Reason(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
