import React, { useState } from "react";
import { createRoot } from "react-dom/client";

function CustomerRegistrationForm() {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    age: "",
    accountType: "Savings",
    comments: ""
  });
  const [errors, setErrors] = useState({});
  const [submitted, setSubmitted] = useState(false);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setFormData((prevData) => ({ ...prevData, [name]: value }));
  };

  const validate = () => {
    const newErrors = {};

    if (formData.name.trim() === "") {
      newErrors.name = "Name is required";
    }

    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email)) {
      newErrors.email = "Enter a valid email address";
    }

    const ageValue = Number(formData.age);
    if (!formData.age || ageValue < 18 || ageValue > 100) {
      newErrors.age = "Age must be between 18 and 100";
    }

    return newErrors;
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    const validationErrors = validate();
    setErrors(validationErrors);

    if (Object.keys(validationErrors).length === 0) {
      setSubmitted(true);
    } else {
      setSubmitted(false);
    }
  };

  return (
    <div className="registration-form">
      <h2>Customer Registration</h2>

      <form onSubmit={handleSubmit}>
        <div>
          <label>Name</label>
          <input
            type="text"
            name="name"
            value={formData.name}
            onChange={handleChange}
          />
          {errors.name && <span className="error">{errors.name}</span>}
        </div>

        <div>
          <label>Email</label>
          <input
            type="text"
            name="email"
            value={formData.email}
            onChange={handleChange}
          />
          {errors.email && <span className="error">{errors.email}</span>}
        </div>

        <div>
          <label>Age</label>
          <input
            type="text"
            name="age"
            value={formData.age}
            onChange={handleChange}
          />
          {errors.age && <span className="error">{errors.age}</span>}
        </div>

        <div>
          <label>Account Type</label>
          <select name="accountType" value={formData.accountType} onChange={handleChange}>
            <option value="Savings">Savings</option>
            <option value="Current">Current</option>
          </select>
        </div>

        <div>
          <label>Comments</label>
          <textarea
            name="comments"
            value={formData.comments}
            onChange={handleChange}
          />
        </div>

        <button type="submit">Register</button>
      </form>

      {submitted && (
        <div className="success">
          Registered {formData.name} with a {formData.accountType} account
        </div>
      )}
    </div>
  );
}

const root = createRoot(document.getElementById("root"));
root.render(<CustomerRegistrationForm />);

export default CustomerRegistrationForm;
