package dev.vvalio.gifter;

import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Gifter {
    private static final Duration DEFAULT_MAX_TIME = Duration.ofSeconds(10);

    private final ExchangeRules rules;
    private final List<Participant> participants;
    private final Map<String, Set<String>> blacklist;

    private Duration maxSolveTime = DEFAULT_MAX_TIME;
    private HardMediumSoftScore resultScore = null;

    public Gifter(ExchangeRules rules, List<Participant> participants) {
        this(rules, participants, Map.of());
    }

    public Gifter(ExchangeRules rules, List<Participant> participants, Map<String, Set<String>> blacklist) {
        this.rules = rules;
        this.participants = participants;
        this.blacklist = blacklist;
    }

    public void setMaxSolveTime(Duration maxSolveTime) {
        this.maxSolveTime = maxSolveTime;
    }

    public Duration getMaxSolveTime() {
        return maxSolveTime;
    }

    public ExchangeRules getRules() {
        return rules;
    }

    public HardMediumSoftScore getResultScore() {
        return resultScore;
    }

    public Map<Participant, Participant> solve() throws SolvingFailureException {
        final var exchangeConf = new ExchangeConfiguration(rules, participants, blacklist);
        final var solver = exchangeConf.createSolver(maxSolveTime);
        final var solution = solver.solve(exchangeConf.toEvent());

        resultScore = solution.getScore();
        if (rules.enforceHardScore() && resultScore.hardScore() < 0) {
            throw new SolvingFailureException("The solver couldn't solve the problem without breaking any hard constraints", resultScore);
        }

        return solution.getExchanges().stream()
                .collect(Collectors.toMap(SingularGiftExchange::getFrom, SingularGiftExchange::getTo));
    }
}
