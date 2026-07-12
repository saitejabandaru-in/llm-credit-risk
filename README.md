# 📊 llm-proof-java

<p align="center">
  <img src="https://img.shields.io/badge/java-17%2B-blue.svg?style=flat-square&logo=openjdk" alt="Java Version" />
  <img src="https://img.shields.io/badge/license-MIT-blue.svg?style=flat-square" alt="License" />
</p>

`llm-proof-java` is a fast Wald's Sequential Probability Ratio Testing (SPRT) statistical evaluator for LLM and AI agent comparison in Java. It lets you statistically compare LLM responses in enterprise Java and Spring Boot applications and stop testing early as soon as mathematical significance is reached, saving up to 50%+ in token evaluation costs.

---

## 🚀 Quick Start (Java)

```java
import in.saitejabandaru.llmproof.SprtEvaluator;

// Configure evaluator (alpha = 5%, beta = 10%)
SprtEvaluator evaluator = new SprtEvaluator(0.05, 0.10, 0.50, 0.70);

// Record outcomes (true for preferred model win, false otherwise)
evaluator.addSample(true);
evaluator.addSample(true);

SprtEvaluator.Decision decision = evaluator.addSample(true);

if (decision == SprtDecision.Decision.ACCEPT_H1) {
    System.out.println("Alternative model is statistically superior. Stop testing early!");
}
```

---

## 📄 License
MIT License.
