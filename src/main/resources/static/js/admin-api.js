const adminToken = localStorage.getItem("token");

// Check if token is present, else redirect to login
if (!adminToken) {
  window.location.href = "login.html";
}

// ====================== USERS ======================
async function fetchAllUsers() {
  const res = await fetch("http://localhost:8080/api/admin/users", {
    headers: {
      "Authorization": `Bearer ${adminToken}`
    }
  });
  const users = await res.json();
  const tbody = document.querySelector("#users-table tbody");

  tbody.innerHTML = users.map(user => `
    <tr>
      <td>${user.username}</td>
      <td>${user.role}</td>
      <td>
        <button onclick="deleteUser('${user.username}')">Delete</button>
      </td>
    </tr>
  `).join("");
}


@GetMapping("/users")
public List<User> getAllUsers(Authentication auth) {
    System.out.println("Admin Accessed by: " + auth.getName());
    return userRepo.findAll();
}


async function deleteUser(username) {
  if (!confirm(`Are you sure to delete user ${username}?`)) return;
  await fetch(`http://localhost:8080/api/admin/users/${username}`, {
    method: "DELETE",
    headers: {
      "Authorization": `Bearer ${adminToken}`
    }
  });
  fetchAllUsers();
}

// ====================== TASKS ======================
async function fetchAllTasks() {
  const res = await fetch("http://localhost:8080/api/admin/tasks", {
    headers: {
      "Authorization": `Bearer ${adminToken}`
    }
  });
  const tasks = await res.json();
  const tbody = document.querySelector("#tasks-table tbody");

  tbody.innerHTML = tasks.map(t => `
    <tr>
      <td>${t.title}</td>
      <td>${t.user.username}</td>
      <td>${t.status}</td>
      <td>${t.dueDate}</td>
      <td>
        <button onclick="deleteTask(${t.id})">Delete</button>
      </td>
    </tr>
  `).join("");
}

async function deleteTask(id) {
  if (!confirm("Are you sure to delete this task?")) return;
  await fetch(`http://localhost:8080/api/admin/tasks/${id}`, {
    method: "DELETE",
    headers: {
      "Authorization": `Bearer ${adminToken}`
    }
  });
  fetchAllTasks();
}

// ====================== ASSIGN TASK ======================
document.getElementById("assignTaskForm").addEventListener("submit", async e => {
  e.preventDefault();

  const task = {
    title: document.getElementById("title").value,
    description: document.getElementById("description").value,
    dueDate: document.getElementById("dueDate").value,
    priority: document.getElementById("priority").value,
    username: document.getElementById("username").value,
    status: "ongoing"
  };

  await fetch("http://localhost:8080/api/admin/assign-task", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${adminToken}`
    },
    body: JSON.stringify(task)
  });

  showToast("Task assigned successfully!");
  fetchAllTasks();
});

// ============== Init ==============
window.onload = () => {
  fetchAllUsers();
  fetchAllTasks();
};

function showToast(msg) {
  const toast = document.getElementById("toast");
  toast.innerText = msg;
  toast.style.display = "block";
  setTimeout(() => toast.style.display = "none", 3000);
}

function logout() {
  localStorage.removeItem("token");
  window.location.href = "login.html";
}
