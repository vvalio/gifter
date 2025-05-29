package dev.vvalio.gifter;

import org.optaplanner.core.api.domain.lookup.PlanningId;

/**
 * Represents a participant in the gift exchange.
 *
 * @author vvalio
 */
public class Participant {
    private final String name;
    private final String id;
    private final String familyId;

    /**
     * Creates a new participant.
     *
     * @param name     the full name of the participant
     * @param id       a shorter ID for the participant
     * @param familyId any identifier used to deduce if two participants are in the same family or not
     */
    public Participant(String name, String id, String familyId) {
        this.name = name;
        this.id = id;
        this.familyId = familyId;
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

        return familyId.equals(other.getFamilyId());
    }

    public String getName() {
        return name;
    }

    @PlanningId
    public String getId() {
        return id;
    }

    public String getFamilyId() {
        return familyId;
    }

    @Override
    public String toString() {
        return "[%s %s %s]".formatted(id, name, familyId);
    }
}
