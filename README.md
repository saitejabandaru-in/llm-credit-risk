# 📊 llm-proof-credit-risk (Java/Maven)

<p align="center">
  <img src="https://img.shields.io/badge/java-17%2B-blue.svg?style=flat-square&logo=openjdk" alt="Java Version" />
  <img src="https://img.shields.io/badge/license-MIT-blue.svg?style=flat-square" alt="License" />
</p>

`llm-proof-credit-risk` is an enterprise credit scoring fairness compliance auditor for Spring Boot AI banking systems. It calculates Demographic Parity Ratio (DPR) and Equal Opportunity Difference (EOD) on lending decisions to ensure compliance with fair lending acts.

---

## 🚀 Quick Start (Java)

```java
import in.saitejabandaru.llmproof.LendingFairnessEvaluator;

LendingFairnessEvaluator evaluator = new LendingFairnessEvaluator();

// Record applications: id, isPrivileged, isApproved, isDefaulted
evaluator.recordApplication(new LendingFairnessEvaluator.LoanApplication("app-1", true, true, false));
evaluator.recordApplication(new LendingFairnessEvaluator.LoanApplication("app-2", false, false, true));

double dpr = evaluator.calculateDemographicParityRatio();
double eod = evaluator.calculateEqualOpportunityDifference();

System.out.println("Demographic Parity Ratio: " + dpr);
System.out.println("Equal Opportunity Difference: " + eod);
```

---

## 📄 License
MIT License.
