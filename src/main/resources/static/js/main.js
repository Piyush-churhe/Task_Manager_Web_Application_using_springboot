let allTasks = [];

window.onload = async () => {
  allTasks = await fetchTasks();
  renderTasks(allTasks); // Show all tasks by default
};

function renderTasks(tasks) {
  const container = document.getElementById("task-list");
  container.innerHTML = tasks.map(t => `
    <div class="task-card">
      <h3>${t.title}</h3>
      <p>${t.description}</p>
      <p><strong>Status:</strong> ${t.status}</p>
      <p>Time: ${t.time}</p>
      <p><strong>Due:</strong> ${t.dueDate}</p>
      <p><strong>Priority:</strong> ${t.priority}</p>
      <button onclick="window.location.href='edit-task.html?id=${t.id}'">Edit</button>
      <button onclick="deleteTask(${t.id}).then(() => location.reload())">Delete</button>
    </div>
  `).join("");
}

function filterTasks(status) {
  if (status === "All") {
    renderTasks(allTasks);
  } else {
    const filtered = allTasks.filter(t => t.status.toLowerCase() === status.toLowerCase());
    renderTasks(filtered);
  }
}
