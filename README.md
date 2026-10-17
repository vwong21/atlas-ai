# 🧭 Atlas AI

AI customer service assistant that triages appliance orders into routed support cases. Spring Boot, Python, scikit-learn, Claude API. Takes routine requests off call agents by creating cases directly.

> 📌 Atlas AI is built for a hypothetical large appliance retailer. It is a personal portfolio project, and all policies, data, and documents are invented.

## 🧩 The problem

Retailer customer service lines are often bombarded with a high volume of requests. Most of these are easy cases, but they still take time to create and send to the team that handles them.

Atlas AI takes on that first layer. Customers get quick support for simple issues, and call agents are freed up for the harder calls.

## ✨ What Atlas AI does

- 💬 Answers questions it can answer from the retailer's policies
- 📝 Collects the details a case needs: order identifier, customer information, issue description, and product SKU
- 🏷️ Classifies the request (cancellation, return or exchange, service or installation, appliance condition, and others) and fills out a structured case with a destination
- 📦 Combines multiple requests about the same product into one case and opens a separate case for each additional product
- 🔍 Detects likely duplicate cases
- 🙋 Hands complaints, unclear situations, and judgment calls to a human agent
- 🚫 Declines off-topic requests

Atlas AI never executes a transaction. Its output is the details needed to create a case in a CRM, where the case should go, and a proposed resolution for a person to review.

## ⚙️ How it works

Each customer message goes through a pipeline:

1. 🎯 **Classifier first.** Message embeddings feed a logistic regression classifier that predicts the case type. A request only receives a case type if the customer actually asks for that action, so plain information questions are not turned into cases.
2. 🤖 **LLM fallback.** When the classifier is not confident, a small Claude model makes the call. A larger Claude model handles response drafting and harder reasoning.
3. ✅ **Validation and routing.** Business rules check that required fields are present, route the case, and decide when to escalate to a person.

## 🏗️ Architecture

| Component       | Technology        | Role                                                                            |
| --------------- | ----------------- | ------------------------------------------------------------------------------- |
| ☕ Core service | Java, Spring Boot | API, pipeline orchestration, validation, routing rules, case storage, LLM calls |
| 🐍 ML service   | Python            | Embeddings and trained classifiers                                              |
| 📊 Classifier   | scikit-learn      | Logistic regression on sentence embeddings                                      |
| 🔢 Embeddings   | all-MiniLM-L6-v2  | Starting model, other options to be compared                                    |
| 🧠 LLM          | Claude API        | Small tier for fallback, larger tier for responses                              |

The Java service owns the business logic, where type safety and reliable validation matter. The Python service exposes only the ML pieces, where the tooling is strongest.

## 🚧 Status

In active development. The classifier, responses, and routing are being improved over time, and evaluation will be used to check whether each piece earns its place in the pipeline.

## 🚀 Running locally

Setup instructions will be added as the services stabilize.

Configuration uses environment variables. Copy `.env.example` to `.env` and add your own API key. 🔐 Never commit real keys.

## 🗺️ Roadmap

- 🧪 Evaluate the LLM fallback against the classifier alone and against sending low-confidence cases straight to a person
- 🔬 Compare embedding models
- 📈 Add an evaluation harness for classification and routing quality
- 🔁 Improve duplicate detection
