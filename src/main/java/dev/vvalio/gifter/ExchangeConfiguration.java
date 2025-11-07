package dev.vvalio.gifter;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.solver.EnvironmentMode;
import org.optaplanner.core.config.solver.SolverConfig;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Configuration for the solver. Use this instead of the individual {@link Participant} and
 * {@link SingularGiftExchange} classes to create an {@link ExchangeEvent}.
 *
 * @author vvalio
 */
class ExchangeConfiguration {
    private final List<Participant> participants = new ArrayList<>();
    private final Map<String, Set<String>> blacklist = new HashMap<String, Set<String>>();
    private final ExchangeRules exchangeRules;

    public ExchangeConfiguration(ExchangeRules exchangeRules, Collection<Participant> initialParticipants) {
        this(exchangeRules, initialParticipants, null);
    }

    public ExchangeConfiguration(ExchangeRules exchangeRules, Collection<Participant> initialParticipants, Map<String, Set<String>> blacklist) {
        this.exchangeRules = exchangeRules;
        if (initialParticipants != null) {
            participants.addAll(initialParticipants);
        }

        if (blacklist != null) {
            this.blacklist.putAll(blacklist);
        }
    }

    public Map<String, Set<String>> getBlacklist() {
        return Collections.unmodifiableMap(blacklist);
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public ExchangeRules getExchangeRules() {
        return exchangeRules;
    }

    public void addParticipant(Participant participant) {
        participants.add(Objects.requireNonNull(participant, "participant"));
    }

    private List<SingularGiftExchange> createExchanges() {
        final var result = new ArrayList<SingularGiftExchange>();
        long counter = 0;

        for (final var participant : participants) {
            final var exchange = new SingularGiftExchange();
            exchange.setFrom(participant);
            exchange.setId(counter++);
            exchange.rules = exchangeRules;

            // Only add the blacklisted receiver for the participant if blacklisting is enabled
            if (exchangeRules.blacklist()) {
                exchange.blacklistedReceivers = blacklist.get(exchange.getFrom().id());
            }

            result.add(exchange);
        }

        return result;
    }

    /**
     * Returns an event object for this configuration, ready to be solved.
     *
     * @return the exchange event to be solved
     */
    public ExchangeEvent toEvent() {
        return new ExchangeEvent(participants, createExchanges());
    }

    /**
     * Configures a solver.
     *
     * @param timeLimit the time limit at which the solver must terminate
     * @return the new solver
     */
    public Solver<ExchangeEvent> createSolver(Duration timeLimit) {
        return SolverFactory.<ExchangeEvent>create(new SolverConfig()
                        .withMoveThreadCount("AUTO")
                        .withEntityClasses(SingularGiftExchange.class)
                        .withSolutionClass(ExchangeEvent.class)
                        .withEnvironmentMode(EnvironmentMode.NON_REPRODUCIBLE)
                        .withConstraintProviderClass(ExchangeEventConstraintProvider.class)
                        .withTerminationSpentLimit(timeLimit))
                .buildSolver();
    }
}
