package dev.vvalio.gifter;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.util.Objects;
import java.util.Set;

/**
 * Represents a single gift exchange from one participant to another.
 *
 * @author vvalio
 */
@PlanningEntity
class SingularGiftExchange {
    @PlanningId
    private Long id;

    private Participant from;

    @PlanningVariable(valueRangeProviderRefs = "personOptions")
    private Participant to;

    // The rules currently in place, filled in later
    ExchangeRules rules;

    // Blacklisted receivers, if any. ID.
    Set<String> blacklistedReceivers;

    /**
     * Creates a new gift exchange.
     *
     * @param from the participant the gift is from
     * @param to   the participant that receives the gift
     * @param id   the id of the exchange
     */
    public SingularGiftExchange(Participant from, Participant to, Long id) {
        Objects.requireNonNull(from, "from must not be null");
        Objects.requireNonNull(to, "to must not be null");
        Objects.requireNonNull(id, "id must not be null");

        this.from = from;
        this.to = to;
        this.id = id;
    }

    public SingularGiftExchange(long id) {
        this.id = id;
    }

    public SingularGiftExchange() {
        this.id = 0L;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Participant getFrom() {
        return from;
    }

    public Participant getTo() {
        return to;
    }

    public void setFrom(Participant from) {
        this.from = from;
    }

    public void setTo(Participant to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "{%d %s => %s}".formatted(id, from, to);
    }
}
