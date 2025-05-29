package dev.vvalio.gifter;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.config.solver.EnvironmentMode;
import org.optaplanner.core.config.solver.SolverConfig;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Configuration for the solver. Use this instead of the individual {@link Participant} and
 * {@link SingularGiftExchange} classes to create an {@link ExchangeEvent}.
 *
 * @author vvalio
 */
public class ExchangeConfiguration {
    private final List<Participant> participants = new ArrayList<>();
    private final ExchangeRules exchangeRules;

    public ExchangeConfiguration(ExchangeRules exchangeRules) {
        this(exchangeRules, null);
    }

    public ExchangeConfiguration(ExchangeRules exchangeRules, Collection<Participant> initialParticipants) {
        this.exchangeRules = exchangeRules;
        if (initialParticipants != null) {
            participants.addAll(initialParticipants);
        }
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
        return new ExchangeEvent(ExchangeEventConstraintConfiguration.getInstance(exchangeRules), participants, createExchanges());
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
