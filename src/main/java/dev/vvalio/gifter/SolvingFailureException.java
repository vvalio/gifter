package dev.vvalio.gifter;

import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;

public class SolvingFailureException extends Exception {
    private final HardMediumSoftScore resultScore;

    public SolvingFailureException(HardMediumSoftScore resultScore) {
        super("Solving failed with score " + resultScore);
        this.resultScore = resultScore;
    }

    public SolvingFailureException(String message) {
        super("Solving failed: " + message);
        this.resultScore = null;
    }

    public SolvingFailureException(String message, HardMediumSoftScore resultScore) {
        super("Solving failed with score " + resultScore + ": " + message);
        this.resultScore = resultScore;
    }

    public HardMediumSoftScore getResultScore() {
        return resultScore;
    }
}
