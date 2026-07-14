import React, { useState } from "react";
import { createRoot } from "react-dom/client";

function LoanStatus(props) {
  if (props.status === "APPROVED") {
    return <p className="status approved">Loan Approved</p>;
  } else if (props.status === "REJECTED") {
    return <p className="status rejected">Loan Rejected</p>;
  }
  return <p className="status pending">Application Under Review</p>;
}

function AccountBalance(props) {
  return (
    <div>
      {props.balance > 0 ? (
        <p>Available Balance: Rs.{props.balance}</p>
      ) : (
        <p>Account balance is zero</p>
      )}
    </div>
  );
}

function VIPBadge(props) {
  return <div>{props.isVIP && <span className="badge">VIP Customer</span>}</div>;
}

function LoginPanel() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const toggleLogin = () => {
    setIsLoggedIn(!isLoggedIn);
  };

  let panelContent;
  if (isLoggedIn) {
    panelContent = <p>Welcome back, Jeeva</p>;
  } else {
    panelContent = <p>Please log in to continue</p>;
  }

  return (
    <div>
      {panelContent}
      <button onClick={toggleLogin}>
        {isLoggedIn ? "Logout" : "Login"}
      </button>
    </div>
  );
}

function App() {
  const customers = [
    { id: 1, name: "Ramesh Kumar", balance: 5000, isVIP: false },
    { id: 2, name: "Anita Sharma", balance: 0, isVIP: false },
    { id: 3, name: "Vikram Singh", balance: 25000, isVIP: true }
  ];

  return (
    <div className="app">
      <LoginPanel />

      {customers.map((customer) => (
        <div key={customer.id}>
          <h4>{customer.name}</h4>
          <AccountBalance balance={customer.balance} />
          <VIPBadge isVIP={customer.isVIP} />
        </div>
      ))}

      <LoanStatus status="APPROVED" />
      <LoanStatus status="REJECTED" />
      <LoanStatus status="PENDING" />
    </div>
  );
}

const root = createRoot(document.getElementById("root"));
root.render(<App />);

export default App;
