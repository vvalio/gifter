package dev.vvalio.gifter;

import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;

public class ExchangeEventConstraintProvider implements ConstraintProvider {
    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                sameReceiverConflict(constraintFactory),
                selfGiftingConflict(constraintFactory),
                sameFamilyConflict(constraintFactory),
                giftEachOtherConflict(constraintFactory),
                blacklistConflict(constraintFactory),
        };
    }

    private Constraint sameReceiverConflict(ConstraintFactory constraintFactory) {
        return constraintFactory.forEachUniquePair(SingularGiftExchange.class,
                        Joiners.equal(SingularGiftExchange::getTo))
                .penalize(HardMediumSoftScore.ONE_HARD).asConstraint("Same receiver");
    }

    private Constraint sameFamilyConflict(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(SingularGiftExchange.class)
                .filter(assignment -> !assignment.rules.allowSameFamily() && assignment.getFrom().isInSameFamily(assignment.getTo()))
                .penalize(HardMediumSoftScore.ONE_HARD).asConstraint("Same family");
    }

    private Constraint selfGiftingConflict(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(SingularGiftExchange.class)
                .filter(assignment -> assignment.getFrom().equals(assignment.getTo()))
                .penalize(HardMediumSoftScore.ONE_HARD).asConstraint("Self-gifting");
    }

    private Constraint giftEachOtherConflict(ConstraintFactory constraintFactory) {
        return constraintFactory.forEachUniquePair(SingularGiftExchange.class,
                        Joiners.equal(SingularGiftExchange::getFrom, SingularGiftExchange::getTo),
                        Joiners.equal(SingularGiftExchange::getTo, SingularGiftExchange::getFrom))
                .filter((assignment, ignored) -> !assignment.rules.allowEachOther())
                .penalize(HardMediumSoftScore.ONE_HARD).asConstraint("Each other");
    }

    private Constraint blacklistConflict(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(SingularGiftExchange.class)
                .filter(
                        assignment -> assignment.blacklistedReceivers != null
                                && assignment.blacklistedReceivers.contains(assignment.getTo().id()))
                .penalize(HardMediumSoftScore.ONE_HARD).asConstraint("Blacklist");
    }
}
