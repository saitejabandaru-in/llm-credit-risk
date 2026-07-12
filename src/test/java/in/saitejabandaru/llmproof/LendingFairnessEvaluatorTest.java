package in.saitejabandaru.llmproof;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LendingFairnessEvaluatorTest {

    @Test
    public void testDemographicParityRatio() {
        LendingFairnessEvaluator evaluator = new LendingFairnessEvaluator();

        // Privileged Group: 100 applications, 80 approved (80% rate)
        for (int i = 0; i < 100; i++) {
            evaluator.recordApplication(new LendingFairnessEvaluator.LoanApplication(
                    "p-" + i, true, i < 80, false
            ));
        }

        // Unprivileged Group: 100 applications, 70 approved (70% rate)
        for (int i = 0; i < 100; i++) {
            evaluator.recordApplication(new LendingFairnessEvaluator.LoanApplication(
                    "u-" + i, false, i < 70, false
            ));
        }

        double dpr = evaluator.calculateDemographicParityRatio();
        assertEquals(0.875, dpr, 0.001); // 0.70 / 0.80 = 0.875
        assertTrue(dpr >= 0.80); // Satisfies 4/5ths rule
    }

    @Test
    public void testEqualOpportunityDifference() {
        LendingFairnessEvaluator evaluator = new LendingFairnessEvaluator();

        // Privileged Group: 50 creditworthy, 40 approved (80% TPR)
        for (int i = 0; i < 50; i++) {
            evaluator.recordApplication(new LendingFairnessEvaluator.LoanApplication(
                    "p-" + i, true, i < 40, false
            ));
        }

        // Unprivileged Group: 50 creditworthy, 30 approved (60% TPR)
        for (int i = 0; i < 50; i++) {
            evaluator.recordApplication(new LendingFairnessEvaluator.LoanApplication(
                    "u-" + i, false, i < 30, false
            ));
        }

        double eod = evaluator.calculateEqualOpportunityDifference();
        assertEquals(-0.20, eod, 0.001); // 0.60 - 0.80 = -0.20
    }
}
