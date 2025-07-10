const API_BASE = "http://localhost:8080/api/tasks";

async function fetchTasks() {
  const token = localStorage.getItem("token");
  console.log("Fetching tasks with token:", token);

  const res = await fetch(API_BASE, {
    headers: {
      "Authorization": `Bearer ${token}`
    }
  });

  if (!res.ok) {
    console.error("Failed to fetch tasks. Status:", res.status);
    return [];
  }

  return await res.json();
}

async function createTask() {
  const token = localStorage.getItem("token");

  const task = {
    title: document.getElementById("title").value,
    description: document.getElementById("description").value,
    time: document.getElementById("time").value,
    dueDate: document.getElementById("dueDate").value,
    priority: document.getElementById("priority").value,
    status: "ongoing"
  };

  await fetch(API_BASE, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${token}`
    },
    body: JSON.stringify(task)
  });
}

async function deleteTask(id) {
  const token = localStorage.getItem("token");

  await fetch(`${API_BASE}/${id}`, {
    method: "DELETE",
    headers: {
      "Authorization": `Bearer ${token}`
    }
  });
}

// Filtering function based on rendered .task-status
function filterTasks(status) {
  const cards = document.querySelectorAll(".task-card");
  cards.forEach(card => {
    const statusText = card.querySelector(".task-status").textContent.trim().toLowerCase();
    if (status === "All" || statusText === status.toLowerCase()) {
      card.style.display = "block";
    } else {
      card.style.display = "none";
    }
  });
}
