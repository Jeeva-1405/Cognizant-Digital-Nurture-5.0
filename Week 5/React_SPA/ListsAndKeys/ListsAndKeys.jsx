import React, { useState } from "react";
import { createRoot } from "react-dom/client";

function TaskItem(props) {
  return (
    <li className={props.completed ? "completed" : ""}>
      {props.title}
      <button onClick={() => props.onToggle(props.id)}>
        {props.completed ? "Undo" : "Complete"}
      </button>
      <button onClick={() => props.onDelete(props.id)}>Delete</button>
    </li>
  );
}

function TaskList() {
  const [tasks, setTasks] = useState([
    { id: 1, title: "Prepare DN 5.0 assessment notes", completed: false },
    { id: 2, title: "Push Week 2 exercises to GitHub", completed: true },
    { id: 3, title: "Review Framecape careers page", completed: false },
    { id: 4, title: "Update portfolio achievements section", completed: false }
  ]);
  const [newTask, setNewTask] = useState("");

  const handleToggle = (id) => {
    setTasks(
      tasks.map((task) =>
        task.id === id ? { ...task, completed: !task.completed } : task
      )
    );
  };

  const handleDelete = (id) => {
    setTasks(tasks.filter((task) => task.id !== id));
  };

  const handleAdd = () => {
    if (newTask.trim() === "") {
      return;
    }
    const nextId = tasks.length > 0 ? Math.max(...tasks.map((t) => t.id)) + 1 : 1;
    setTasks([...tasks, { id: nextId, title: newTask, completed: false }]);
    setNewTask("");
  };

  return (
    <div className="task-list">
      <h2>Task List</h2>

      <input
        type="text"
        value={newTask}
        onChange={(event) => setNewTask(event.target.value)}
        placeholder="Enter a new task"
      />
      <button onClick={handleAdd}>Add Task</button>

      <ul>
        {tasks.map((task) => (
          <TaskItem
            key={task.id}
            id={task.id}
            title={task.title}
            completed={task.completed}
            onToggle={handleToggle}
            onDelete={handleDelete}
          />
        ))}
      </ul>

      <p>Pending: {tasks.filter((t) => !t.completed).length}</p>
      <p>Completed: {tasks.filter((t) => t.completed).length}</p>
    </div>
  );
}

const root = createRoot(document.getElementById("root"));
root.render(<TaskList />);

export default TaskList;
