import React, { useEffect, useState } from "react";
import { createRoot } from "react-dom/client";
import axios from "axios";

function UserListFetch() {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetch("https://jsonplaceholder.typicode.com/users")
      .then((response) => {
        if (!response.ok) {
          throw new Error("Request failed with status " + response.status);
        }
        return response.json();
      })
      .then((data) => {
        setUsers(data);
        setLoading(false);
      })
      .catch((err) => {
        setError(err.message);
        setLoading(false);
      });
  }, []);

  if (loading) return <p>Loading users using fetch...</p>;
  if (error) return <p>Error: {error}</p>;

  return (
    <div>
      <h3>Users loaded with fetch</h3>
      <ul>
        {users.map((user) => (
          <li key={user.id}>
            {user.name} ({user.email})
          </li>
        ))}
      </ul>
    </div>
  );
}

function PostListAxios() {
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios
      .get("https://jsonplaceholder.typicode.com/posts", { params: { _limit: 5 } })
      .then((response) => {
        setPosts(response.data);
        setLoading(false);
      })
      .catch((err) => {
        setError(err.message);
        setLoading(false);
      });
  }, []);

  if (loading) return <p>Loading posts using axios...</p>;
  if (error) return <p>Error: {error}</p>;

  return (
    <div>
      <h3>Posts loaded with axios</h3>
      <ul>
        {posts.map((post) => (
          <li key={post.id}>{post.title}</li>
        ))}
      </ul>
    </div>
  );
}

function CreatePostForm() {
  const [title, setTitle] = useState("");
  const [status, setStatus] = useState("");

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await axios.post("https://jsonplaceholder.typicode.com/posts", {
        title: title,
        userId: 1
      });
      setStatus("Created post with id " + response.data.id);
      setTitle("");
    } catch (err) {
      setStatus("Failed to create post: " + err.message);
    }
  };

  return (
    <div>
      <h3>Create a post using axios POST</h3>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          value={title}
          onChange={(event) => setTitle(event.target.value)}
          placeholder="Post title"
        />
        <button type="submit">Submit</button>
      </form>
      <p>{status}</p>
    </div>
  );
}

function App() {
  return (
    <div className="app">
      <UserListFetch />
      <PostListAxios />
      <CreatePostForm />
    </div>
  );
}

const root = createRoot(document.getElementById("root"));
root.render(<App />);

export default App;
