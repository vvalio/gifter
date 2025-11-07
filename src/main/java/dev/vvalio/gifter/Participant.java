package dev.vvalio.gifter;

import org.optaplanner.core.api.domain.lookup.PlanningId;

/**
 * Represents a participant in the gift exchange.
 *
 * @author vvalio
 */
public record Participant(String name, String id, String familyId) {
    /**
     * Creates a new participant.
     *
     * @param name     the full name of the participant
     * @param id       a shorter ID for the participant
     * @param familyId any identifier used to deduce if two participants are in the same family or not
     */
    public Participant {
    }

    /**
     * Returns {@code true} if the given participant is in the same family as this one.
     *
     * @param other the other participant
     * @return {@code true} if the two participants are in the same family
     */
    public boolean isInSameFamily(Participant other) {
        if (other == null) {
            return false;
        }

        return familyId.equals(other.familyId());
    }

    @Override
    @PlanningId
    public String id() {
        return id;
    }

    @Override
    public String toString() {
        return "[%s %s %s]".formatted(id, name, familyId);
    }

    /**
     * Distinct method to check if the other participant effectively represents the same person as {@code this}.
     *
     * @param other the other participant
     */
    public boolean isSamePerson(Participant other) {
        return id.equals(other.id);
    }
}
