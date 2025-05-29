package dev.vvalio.gifter;

import org.optaplanner.core.api.domain.constraintweight.ConstraintConfigurationProvider;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import java.util.ArrayList;
import java.util.List;

/**
 * Defines the whole gift exchange event that includes all participants.
 *
 * @author vvalio
 */
@PlanningSolution
public class ExchangeEvent {
    @ValueRangeProvider(id = "personOptions")
    @ProblemFactCollectionProperty
    private List<Participant> participants;

    @PlanningEntityCollectionProperty
    private List<SingularGiftExchange> exchanges = new ArrayList<>();

    @PlanningScore
    private HardMediumSoftScore score;

    @ConstraintConfigurationProvider
    private ExchangeEventConstraintConfiguration constraintConfiguration;

    ExchangeEvent(ExchangeEventConstraintConfiguration constraintConfiguration, List<Participant> participants, List<SingularGiftExchange> exchanges) {
        this.constraintConfiguration = constraintConfiguration;
        this.participants = participants;
        this.exchanges = exchanges;
    }

    private ExchangeEvent() {
    }

    public void setScore(HardMediumSoftScore score) {
        this.score = score;
    }

    public HardMediumSoftScore getScore() {
        return score;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        System.out.println("Length: " + exchanges.size());
        for (SingularGiftExchange e : exchanges) {
            b.append(e).append('\n');
        }

        return b.toString();
    }
}
