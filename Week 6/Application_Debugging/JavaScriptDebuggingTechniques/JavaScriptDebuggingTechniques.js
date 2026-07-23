function calculateInterest(principal, rate, years) {
  debugger;
  const interest = principal * (rate / 100) * years;
  console.log("Principal:", principal, "Rate:", rate, "Years:", years);
  console.log("Calculated interest:", interest);
  return interest;
}

function validateCustomer(customer) {
  console.group("Validating customer: " + customer.name);

  const errors = [];

  if (!customer.name || customer.name.trim() === "") {
    errors.push("Name is required");
  }
  console.assert(customer.age >= 18, "Customer age below 18:", customer.age);

  if (customer.age < 18) {
    errors.push("Customer must be at least 18 years old");
  }

  if (!customer.email || !customer.email.includes("@")) {
    errors.push("Valid email is required");
  }

  console.table(errors);
  console.groupEnd();

  return errors;
}

function processTransactions(transactions) {
  console.time("processTransactions");

  const results = transactions.map((txn, index) => {
    console.log("Processing transaction " + index + ":", txn);

    try {
      if (txn.amount <= 0) {
        throw new Error("Invalid amount for transaction " + txn.id);
      }
      return { ...txn, status: "SUCCESS" };
    } catch (error) {
      console.error("Transaction failed:", error.message);
      console.trace("Trace for failed transaction");
      return { ...txn, status: "FAILED", error: error.message };
    }
  });

  console.timeEnd("processTransactions");
  return results;
}

function runDebuggingDemo() {
  const interestResult = calculateInterest(200000, 8.5, 5);
  console.log("Interest result:", interestResult);

  const customers = [
    { name: "Jeeva Elango", age: 22, email: "jeeva@example.com" },
    { name: "", age: 16, email: "invalidemail" }
  ];

  customers.forEach((customer) => {
    const errors = validateCustomer(customer);
    console.log("Validation errors:", errors);
  });

  const transactions = [
    { id: "T1", amount: 1500 },
    { id: "T2", amount: -200 },
    { id: "T3", amount: 3000 }
  ];

  const processed = processTransactions(transactions);
  console.log("Processed transactions:", processed);
}

runDebuggingDemo();
