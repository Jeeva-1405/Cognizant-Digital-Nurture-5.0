const zeroShotPrompt = `
Classify the sentiment of this customer review as POSITIVE, NEGATIVE, or NEUTRAL.

Review: "The loan approval process was quick, but the customer support was unresponsive when I had questions."
`;

const fewShotPrompt = `
Classify the sentiment of the review as POSITIVE, NEGATIVE, or NEUTRAL.

Review: "Excellent service and fast disbursement."
Sentiment: POSITIVE

Review: "Application got rejected without any clear reason."
Sentiment: NEGATIVE

Review: "The process was okay, nothing special either way."
Sentiment: NEUTRAL

Review: "The loan approval process was quick, but the customer support was unresponsive when I had questions."
Sentiment:
`;

const chainOfThoughtPrompt = `
A customer has a monthly income of Rs.45000 and existing EMI obligations of Rs.12000.
Banks typically approve new loans only if total EMI does not exceed 50% of monthly income.
The customer wants a new loan with an EMI of Rs.10000.

Think step by step:
1. Calculate the maximum allowed EMI (50% of income).
2. Add the proposed new EMI to the existing EMI.
3. Compare total EMI against the maximum allowed EMI.
4. State whether the loan should be APPROVED or REJECTED, with the reasoning.
`;

function buildCodeGenerationPrompt(functionDescription, language) {
  return "Write a " + language + " function that " + functionDescription
    + ". Include appropriate parameter validation and return a clear result. "
    + "Do not include any explanation, only the code.";
}

function buildRefactoringPrompt(codeSnippet, goal) {
  return "Refactor the following code to " + goal + ":\n\n"
    + codeSnippet
    + "\n\nReturn only the refactored code with no additional commentary.";
}

function buildTestGenerationPrompt(className, methodSignatures) {
  return "Generate JUnit 5 test cases for the class " + className
    + " covering these methods: " + methodSignatures.join(", ")
    + ". Include both positive and negative test scenarios using Mockito where dependencies exist.";
}

function demonstratePromptTechniques() {
  console.log("Zero-shot prompt:");
  console.log(zeroShotPrompt);

  console.log("Few-shot prompt:");
  console.log(fewShotPrompt);

  console.log("Chain-of-thought prompt:");
  console.log(chainOfThoughtPrompt);

  const codePrompt = buildCodeGenerationPrompt(
    "checks whether a given loan applicant is eligible based on age, income, and credit score",
    "Java"
  );
  console.log("Code generation prompt:");
  console.log(codePrompt);

  const refactorPrompt = buildRefactoringPrompt(
    "if (age >= 21) { if (income >= 25000) { return true; } else { return false; } } else { return false; }",
    "use guard clauses and reduce nesting"
  );
  console.log("Refactoring prompt:");
  console.log(refactorPrompt);

  const testPrompt = buildTestGenerationPrompt(
    "LoanEligibilityService",
    ["checkEligibility(int, double, int, boolean)"]
  );
  console.log("Test generation prompt:");
  console.log(testPrompt);
}

demonstratePromptTechniques();
