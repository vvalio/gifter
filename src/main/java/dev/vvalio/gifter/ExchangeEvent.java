package dev.vvalio.gifter;

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
class ExchangeEvent {
    @ValueRangeProvider(id = "personOptions")
    @ProblemFactCollectionProperty
    private List<Participant> participants;

    @PlanningEntityCollectionProperty
    private List<SingularGiftExchange> exchanges = new ArrayList<>();

    @PlanningScore
    private HardMediumSoftScore score;

    ExchangeEvent(List<Participant> participants, List<SingularGiftExchange> exchanges) {
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

    List<SingularGiftExchange> getExchanges() {
        return exchanges;
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
