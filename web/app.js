const API_URL = "/api/students";

const form = document.getElementById("student-form");
const tableBody = document.getElementById("student-table-body");
const statusMsg = document.getElementById("status-msg");
const emptyMsg = document.getElementById("empty-msg");

// Fetches the student list from the Java backend and renders the table
async function loadStudents() {
  const res = await fetch(API_URL);
  const students = await res.json();

  tableBody.innerHTML = "";
  emptyMsg.hidden = students.length > 0;

  students.forEach((s) => {
    const row = document.createElement("tr");
    row.innerHTML = `
      <td data-label="ID">${s.id}</td>
      <td data-label="Name">${s.name}</td>
      <td data-label="Email">${s.email}</td>
      <td data-label="Course">${s.course}</td>
      <td data-label="Year">${s.year}</td>
      <td data-label=""><button class="delete-btn" data-id="${s.id}">Delete</button></td>
    `;
    tableBody.appendChild(row);
  });
}

// Handles the "Add Student" form submission
form.addEventListener("submit", async (e) => {
  e.preventDefault();

  const formData = new URLSearchParams(new FormData(form));

  const res = await fetch(API_URL, {
    method: "POST",
    headers: { "Content-Type": "application/x-www-form-urlencoded" },
    body: formData.toString(),
  });

  if (res.ok) {
    form.reset();
    statusMsg.textContent = "Student added.";
    loadStudents();
  } else {
    statusMsg.textContent = "Something went wrong. Please try again.";
  }

  setTimeout(() => (statusMsg.textContent = ""), 2500);
});

// Handles clicks on any "Delete" button (event delegation)
tableBody.addEventListener("click", async (e) => {
  if (e.target.classList.contains("delete-btn")) {
    const id = e.target.dataset.id;
    const res = await fetch(`${API_URL}?id=${id}`, { method: "DELETE" });
    if (res.ok) {
      loadStudents();
    }
  }
});

loadStudents();
