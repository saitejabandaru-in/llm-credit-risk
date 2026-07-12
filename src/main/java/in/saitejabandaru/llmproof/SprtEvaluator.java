package in.saitejabandaru.llmproof;

public class SprtEvaluator {
    public enum Decision {
        CONTINUE,
        ACCEPT_H1, // Model B is statistically superior
        ACCEPT_H0  // Model B is not statistically superior (or identical)
    }

    private final double alpha;
    private final double beta;
    private final double p0;
    private final double p1;

    private final double logLowerBound;
    private final double logUpperBound;
    private double logLikelihoodRatio = 0.0;

    private int sampleCount = 0;
    private int successCount = 0;

    public SprtEvaluator(double alpha, double beta, double p0, double p1) {
        if (p0 <= 0 || p0 >= 1 || p1 <= 0 || p1 >= 1 || p0 >= p1) {
            throw new IllegalArgumentException("Hypothesis bounds must satisfy 0 < p0 < p1 < 1");
        }
        if (alpha <= 0 || alpha >= 1 || beta <= 0 || beta >= 1) {
            throw new IllegalArgumentException("Error bounds must satisfy 0 < alpha, beta < 1");
        }

        this.alpha = alpha;
        this.beta = beta;
        this.p0 = p0;
        this.p1 = p1;

        this.logLowerBound = Math.log(beta / (1.0 - alpha));
        this.logUpperBound = Math.log((1.0 - beta) / alpha);
    }

    // Default constructor
    public SprtEvaluator() {
        this(0.05, 0.10, 0.50, 0.70);
    }

    public Decision addSample(boolean isSuccess) {
        sampleCount++;
        if (isSuccess) successCount++;

        double pSuccessRatio = p1 / p0;
        double pFailureRatio = (1.0 - p1) / (1.0 - p0);

        logLikelihoodRatio += Math.log(isSuccess ? pSuccessRatio : pFailureRatio);

        if (logLikelihoodRatio >= logUpperBound) {
            return Decision.ACCEPT_H1;
        } else if (logLikelihoodRatio <= logLowerBound) {
            return Decision.ACCEPT_H0;
        }
        return Decision.CONTINUE;
    }

    public double getLogLikelihoodRatio() {
        return logLikelihoodRatio;
    }

    public int getSampleCount() {
        return sampleCount;
    }

    public int getSuccessCount() {
        return successCount;
    }
}
