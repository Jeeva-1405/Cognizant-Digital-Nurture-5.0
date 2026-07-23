import React, { Component, useState, useEffect } from "react";
import { createRoot } from "react-dom/client";

class ErrorBoundary extends Component {
  constructor(props) {
    super(props);
    this.state = { hasError: false, errorMessage: "" };
  }

  static getDerivedStateFromError(error) {
    return { hasError: true, errorMessage: error.message };
  }

  componentDidCatch(error, info) {
    console.error("Caught by ErrorBoundary:", error);
    console.error("Component stack:", info.componentStack);
  }

  render() {
    if (this.state.hasError) {
      return (
        <div className="error-fallback">
          <h3>Something went wrong</h3>
          <p>{this.state.errorMessage}</p>
        </div>
      );
    }
    return this.props.children;
  }
}

function LoanCalculator(props) {
  const [amount, setAmount] = useState(props.loanAmount);

  if (amount < 0) {
    throw new Error("Loan amount cannot be negative: " + amount);
  }

  const monthlyRate = props.annualRate / 12 / 100;
  const emi = (amount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -props.months));

  return (
    <div>
      <p>Loan Amount: {amount}</p>
      <p>Monthly EMI: {emi.toFixed(2)}</p>
      <button onClick={() => setAmount(-1000)}>Trigger Error</button>
    </div>
  );
}

function DataFetcher() {
  const [data, setData] = useState(null);
  const [renderCount, setRenderCount] = useState(0);

  useEffect(() => {
    console.log("Effect ran, current renderCount:", renderCount);
    setRenderCount((prev) => prev + 1);

    const timer = setTimeout(() => {
      console.log("Simulated data load complete");
      setData({ status: "loaded", timestamp: Date.now() });
    }, 500);

    return () => {
      console.log("Cleanup: clearing timer");
      clearTimeout(timer);
    };
  }, []);

  console.log("Render count for DataFetcher:", renderCount);

  return (
    <div>
      <p>Render count: {renderCount}</p>
      <p>Data: {data ? JSON.stringify(data) : "Loading..."}</p>
    </div>
  );
}

function App() {
  return (
    <div className="app">
      <ErrorBoundary>
        <LoanCalculator loanAmount={500000} annualRate={8.5} months={60} />
      </ErrorBoundary>
      <DataFetcher />
    </div>
  );
}

const root = createRoot(document.getElementById("root"));
root.render(<App />);

export default App;
