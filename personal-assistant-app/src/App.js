import React, { useState, useEffect } from "react";
import axios from "axios";
import "bootstrap/dist/css/bootstrap.min.css";
import { Modal, Button, Form } from "react-bootstrap";
import { useNavigate } from 'react-router-dom';  // for navigation after login/register

const App = () => {
  const [tasks, setTasks] = useState([]);
  const [showAddModal, setShowAddModal] = useState(false);
  const [showAiModal, setShowAiModal] = useState(false);
  const [newTask, setNewTask] = useState({ title: "", description: "", dueDate: "" });
  const [aiPrompt, setAiPrompt] = useState("");
  const [user, setUser] = useState(null);

  const taskApiUrl = "http://localhost:4000/api/v1/tasks";
  const nlpApiUrl = "http://localhost:5000/api/ask/ai";

  const navigate = useNavigate();  // hook for programmatic navigation

  useEffect(() => {
    const savedToken = localStorage.getItem('token');
    if (savedToken) {
      setUser({ token: savedToken });
      fetchTasks(savedToken);
    }
  }, []);

  const fetchTasks = async (token) => {
    try {
      const response = await axios.get(taskApiUrl, {
        headers: { Authorization: `Bearer ${token}` }
      });
      setTasks(response.data);
    } catch (error) {
      console.error("Error fetching tasks:", error);
    }
  };

  const handleLogin = async (username, password) => {
    try {
      const response = await axios.post("http://localhost:3000/api/v1/users/login", { username, password });
      const { token } = response.data;
      localStorage.setItem('token', token);  // Save token to local storage
      setUser({ token });
      fetchTasks(token);
      navigate("/tasks");  // Navigate to task list after successful login
    } catch (error) {
      console.error("Login failed:", error);
    }
  };

  const handleRegister = async (username, email, password) => {
    try {
      const response = await axios.post("http://localhost:3000/api/v1/users/register", { username, email, password });
      const { token } = response.data;
      localStorage.setItem('token', token);  // Save token to local storage
      setUser({ token });
      fetchTasks(token);
      navigate("/tasks");  // Navigate to task list after successful registration
    } catch (error) {
      console.error("Registration failed:", error);
    }
  };

  const handleAddTask = async () => {
    const token = user.token;
    try {
      await axios.post(taskApiUrl, newTask, {
        headers: { Authorization: `Bearer ${token}` }
      });
      fetchTasks(token);
      setShowAddModal(false);
      setNewTask({ title: "", description: "", dueDate: "" });
    } catch (error) {
      console.error("Error adding task:", error);
    }
  };

  const handleGenerateTask = async () => {
    const token = user.token;
    try {
      const response = await axios.post(nlpApiUrl, { queryText: aiPrompt }, {
        headers: { Authorization: `Bearer ${token}` }
      });
      if (response.data.task.success) {
        const task = response.data.task;
        await axios.post(taskApiUrl, {
          title: task.title,
          description: task.description,
          dueDate: task.dueDate,
        }, {
          headers: { Authorization: `Bearer ${token}` }
        });
        fetchTasks(token);
        setShowAiModal(false);
        setAiPrompt("");
      } else {
        // If success is false, show an alert with the reason
        alert(response.data.task.reason);
      }
    } catch (error) {
      console.error("Error generating task:", error);
    }
  };
  

  if (!user) {
    return (
      <div className="container mt-5">
        <h2>Login or Register</h2>
        <Form>
          <Form.Group>
            <Form.Label>Username</Form.Label>
            <Form.Control type="text" placeholder="Enter username" id="username" />
          </Form.Group>
          <Form.Group>
            <Form.Label>Password</Form.Label>
            <Form.Control type="password" placeholder="Enter password" id="password" />
          </Form.Group>
          <Button className="mt-3" onClick={() => handleLogin(document.getElementById('username').value, document.getElementById('password').value)}>Login</Button>
          <div className="mt-3">
            <h5>Or Register</h5>
            <Form.Group>
              <Form.Label>Email</Form.Label>
              <Form.Control type="email" placeholder="Enter email" id="email" />
            </Form.Group>
            <Button variant="secondary" className="mt-3" onClick={() => handleRegister(document.getElementById('username').value, document.getElementById('email').value, document.getElementById('password').value)}>Register</Button>
          </div>
        </Form>
      </div>
    );
  }

  return (
    <div className="container mt-5">
      <h2>Task List</h2>
      <ul className="list-group mb-3">
        {tasks.map((task, index) => (
          <li key={index} className="list-group-item">
            <h5>{task.title}</h5>
            <p>{task.description}</p>
            <small>Due: {new Date(task.dueDate).toLocaleString()}</small>
          </li>
        ))}
      </ul>
      <Button variant="primary" onClick={() => setShowAddModal(true)}>Add New Task</Button>
      <Button variant="success" className="ms-3" onClick={() => setShowAiModal(true)}>Generate Task Using AI</Button>

      {/* Add Task Modal */}
      <Modal show={showAddModal} onHide={() => setShowAddModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Add New Task</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group>
              <Form.Label>Title</Form.Label>
              <Form.Control type="text" value={newTask.title} onChange={(e) => setNewTask({ ...newTask, title: e.target.value })} />
            </Form.Group>
            <Form.Group>
              <Form.Label>Description</Form.Label>
              <Form.Control as="textarea" value={newTask.description} onChange={(e) => setNewTask({ ...newTask, description: e.target.value })} />
            </Form.Group>
            <Form.Group>
              <Form.Label>Due Date</Form.Label>
              <Form.Control type="datetime-local" value={newTask.dueDate} onChange={(e) => setNewTask({ ...newTask, dueDate: e.target.value })} />
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowAddModal(false)}>Close</Button>
          <Button variant="primary" onClick={handleAddTask}>Save Task</Button>
        </Modal.Footer>
      </Modal>

      {/* AI Task Modal */}
      <Modal show={showAiModal} onHide={() => setShowAiModal(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Generate Task Using AI</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group>
              <Form.Label>Prompt</Form.Label>
              <Form.Control as="textarea" value={aiPrompt} onChange={(e) => setAiPrompt(e.target.value)} />
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setShowAiModal(false)}>Close</Button>
          <Button variant="success" onClick={handleGenerateTask}>Generate Task</Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default App;
