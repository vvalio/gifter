package dev.vvalio.gifter;

import org.optaplanner.core.api.domain.constraintweight.ConstraintConfiguration;
import org.optaplanner.core.api.domain.constraintweight.ConstraintWeight;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;

/**
 * Constraint configuration for the exchange event. Allows turning some constraints on or off.
 *
 * @author vvalio
 */
@ConstraintConfiguration(constraintPackage = "...exchangeEvent.score")
class ExchangeEventConstraintConfiguration {
    @ConstraintWeight("Same family")
    private HardMediumSoftScore sameFamilyConflict = HardMediumSoftScore.ONE_HARD;

    @ConstraintWeight("Each other")
    private HardMediumSoftScore giftEachOtherConflict = HardMediumSoftScore.ONE_HARD;

    private ExchangeEventConstraintConfiguration() {}

    public static ExchangeEventConstraintConfiguration getInstance(ExchangeRules rules) {
        final var result = new ExchangeEventConstraintConfiguration();
        if (rules.allowSameFamily()) {
            result.sameFamilyConflict = HardMediumSoftScore.ONE_SOFT;
        }

        if (rules.allowEachOther()) {
            result.giftEachOtherConflict = HardMediumSoftScore.ONE_SOFT;
        }

        return result;
    }
}
