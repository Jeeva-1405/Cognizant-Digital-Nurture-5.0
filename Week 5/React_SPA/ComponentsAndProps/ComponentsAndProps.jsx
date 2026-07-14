import React, { Component } from "react";
import { createRoot } from "react-dom/client";

function EmployeeCard(props) {
  return (
    <div className="employee-card">
      <h3>{props.name}</h3>
      <p>Department: {props.department}</p>
      <p>Salary: {props.salary}</p>
    </div>
  );
}

EmployeeCard.defaultProps = {
  department: "Unassigned",
  salary: 0
};

class DepartmentSummary extends Component {
  constructor(props) {
    super(props);
    this.state = {
      totalEmployees: props.employees.length
    };
  }

  render() {
    return (
      <div className="department-summary">
        <h2>{this.props.departmentName}</h2>
        <p>Total Employees: {this.state.totalEmployees}</p>
      </div>
    );
  }
}

function App() {
  const employees = [
    { id: 1, name: "Jeeva Elango", department: "Engineering", salary: 75000 },
    { id: 2, name: "Priya Reddy", department: "Engineering", salary: 82000 },
    { id: 3, name: "Ravi Kumar", department: "Human Resources", salary: 65000 }
  ];

  return (
    <div className="app">
      <DepartmentSummary departmentName="Engineering" employees={employees} />
      {employees.map((emp) => (
        <EmployeeCard
          key={emp.id}
          name={emp.name}
          department={emp.department}
          salary={emp.salary}
        />
      ))}
      <EmployeeCard name="Suresh Patel" />
    </div>
  );
}

const root = createRoot(document.getElementById("root"));
root.render(<App />);

export default App;
