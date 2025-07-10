const API_BASE = "http://localhost:8080/api/auth"; // We'll build this later

function showToast(message) {
  const toast = document.getElementById("toast");
  toast.innerText = message;
  toast.classList.add("show");
  setTimeout(() => toast.classList.remove("show"), 2500);
}

// Register
const regForm = document.getElementById("registerForm");
if (regForm) {
  regForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    const res = await fetch(`${API_BASE}/register`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, password })
    });

    if (res.ok) {
      showToast("Account created! Redirecting...");
      setTimeout(() => window.location.href = "login.html", 2000);
    } else {
      showToast("Registration failed");
    }
  });
}

// Login
const loginForm = document.getElementById("loginForm");
if (loginForm) {
  loginForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    const res = await fetch(`${API_BASE}/login`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, password })
    });

    if (res.ok) {
      const data = await res.json();
      localStorage.setItem("token", data.token);
      localStorage.setItem("username", username);
      showToast("Login successful! Redirecting...");
      setTimeout(() => 1500);
      if (username === "admin") {
          window.location.href = "admin-dashboard.html";
      } else {
          window.location.href = "index.html";
      }

    }
 else {
      showToast("Login failed. Check credentials.");
    }
  });
}


  function logout() {
    localStorage.removeItem("token");
    window.location.href = "login.html";
  }


