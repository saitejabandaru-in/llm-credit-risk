package in.saitejabandaru.llmproof;

import java.util.ArrayList;
import java.util.List;

public class LendingFairnessEvaluator {

    public static class LoanApplication {
        public String id;
        public boolean isPrivileged; // demographic group indicator
        public boolean isApproved;   // credit risk outcome
        public boolean isDefaulted;  // actual default status (ground truth)

        public LoanApplication(String id, boolean isPrivileged, boolean isApproved, boolean isDefaulted) {
            this.id = id;
            this.isPrivileged = isPrivileged;
            this.isApproved = isApproved;
            this.isDefaulted = isDefaulted;
        }
    }

    private final List<LoanApplication> applications = new ArrayList<>();

    public void recordApplication(LoanApplication app) {
        applications.add(app);
    }

    /**
     * Calculates the Demographic Parity Ratio (DPR).
     * Value should be >= 0.8 to satisfy the four-fifths disparate impact rule.
     */
    public double calculateDemographicParityRatio() {
        int privilegedCount = 0;
        int privilegedApproved = 0;
        int unprivilegedCount = 0;
        int unprivilegedApproved = 0;

        for (LoanApplication app : applications) {
            if (app.isPrivileged) {
                privilegedCount++;
                if (app.isApproved) privilegedApproved++;
            } else {
                unprivilegedCount++;
                if (app.isApproved) unprivilegedApproved++;
            }
        }

        if (privilegedCount == 0 || unprivilegedCount == 0) return 1.0;

        double privilegedRate = (double) privilegedApproved / privilegedCount;
        double unprivilegedRate = (double) unprivilegedApproved / unprivilegedCount;

        if (privilegedRate == 0.0) return 1.0;
        return unprivilegedRate / privilegedRate;
    }

    /**
     * Calculates the Equal Opportunity Difference (EOD).
     * True Positive Rate (TPR) difference between groups.
     * Difference should be close to 0.0 for fair models.
     */
    public double calculateEqualOpportunityDifference() {
        int privilegedGoodCount = 0; // Creditworthy (didn't default)
        int privilegedGoodApproved = 0;
        int unprivilegedGoodCount = 0;
        int unprivilegedGoodApproved = 0;

        for (LoanApplication app : applications) {
            boolean isCreditworthy = !app.isDefaulted;
            if (isCreditworthy) {
                if (app.isPrivileged) {
                    privilegedGoodCount++;
                    if (app.isApproved) privilegedGoodApproved++;
                } else {
                    unprivilegedGoodCount++;
                    if (app.isApproved) unprivilegedGoodApproved++;
                }
            }
        }

        if (privilegedGoodCount == 0 || unprivilegedGoodCount == 0) return 0.0;

        double privilegedTPR = (double) privilegedGoodApproved / privilegedGoodCount;
        double unprivilegedTPR = (double) unprivilegedGoodApproved / unprivilegedGoodCount;

        return unprivilegedTPR - privilegedTPR;
    }

    public void clear() {
        applications.clear();
    }
}
