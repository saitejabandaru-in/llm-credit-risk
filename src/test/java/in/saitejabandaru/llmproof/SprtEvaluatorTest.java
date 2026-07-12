package in.saitejabandaru.llmproof;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SprtEvaluatorTest {

    @Test
    public void testH0Decision() {
        SprtEvaluator evaluator = new SprtEvaluator(0.05, 0.10, 0.50, 0.70);
        SprtEvaluator.Decision decision = SprtEvaluator.Decision.CONTINUE;

        for (int i = 0; i < 20; i++) {
            decision = evaluator.addSample(false);
            if (decision != SprtEvaluator.Decision.CONTINUE) {
                break;
            }
        }
        assertEquals(SprtEvaluator.Decision.ACCEPT_H0, decision);
    }

    @Test
    public void testH1Decision() {
        SprtEvaluator evaluator = new SprtEvaluator(0.05, 0.10, 0.50, 0.70);
        SprtEvaluator.Decision decision = SprtEvaluator.Decision.CONTINUE;

        for (int i = 0; i < 20; i++) {
            decision = evaluator.addSample(true);
            if (decision != SprtEvaluator.Decision.CONTINUE) {
                break;
            }
        }
        assertEquals(SprtEvaluator.Decision.ACCEPT_H1, decision);
    }
}
