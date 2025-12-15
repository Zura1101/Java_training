// Your working backend endpoint (Option A)
const API = "http://localhost:8080/employees";

const tbody = document.getElementById("tbody");
const refreshBtn = document.getElementById("refreshBtn");
const statusEl = document.getElementById("status");

const addForm = document.getElementById("addForm");
const nameEl = document.getElementById("name");
const deptEl = document.getElementById("department");
const salaryEl = document.getElementById("salary");

function showStatus(msg, type = "ok") {
    statusEl.textContent = msg;
    statusEl.classList.remove("hidden", "ok", "err");
    statusEl.classList.add(type);
}
function hideStatus() {
    statusEl.classList.add("hidden");
}
function escapeHtml(str) {
    return String(str ?? "")
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}

function renderEmployees(list) {
    tbody.innerHTML = "";

    if (!Array.isArray(list) || list.length === 0) {
        tbody.innerHTML = `<tr><td colspan="4" class="muted">No employees found.</td></tr>`;
        return;
    }

    for (const e of list) {
        const id = e.id ?? "";
        const name = e.name ?? "";
        const department = e.department ?? "";
        const salary = e.salary ?? "";

        const tr = document.createElement("tr");
        tr.innerHTML = `
      <td>${escapeHtml(id)}</td>
      <td>${escapeHtml(name)}</td>
      <td>${escapeHtml(department)}</td>
      <td>${escapeHtml(salary)}</td>
    `;
        tbody.appendChild(tr);
    }
}

async function loadEmployees() {
    hideStatus();
    try {
        showStatus("Loading employees...", "ok");
        const res = await fetch(API);
        const text = await res.text();

        if (!res.ok) {
            showStatus(`HTTP ${res.status}: ${text}`, "err");
            return;
        }

        const data = JSON.parse(text);
        renderEmployees(data);
        showStatus("Employees loaded.", "ok");
    } catch (err) {
        showStatus(`Error: ${err.message}`, "err");
    }
}

refreshBtn.addEventListener("click", loadEmployees);

// Day2: Form is UI-only (no POST yet)
addForm.addEventListener("submit", (e) => {
    e.preventDefault();

    const name = nameEl.value.trim();
    const department = deptEl.value.trim();
    const salary = salaryEl.value.trim();

    if (!name || !department || !salary) {
        showStatus("Please fill all fields.", "err");
        return;
    }

    // UI confirmation only
    showStatus(
        `Day2 UI Ready ✅ (Name=${name}, Dept=${department}, Salary=${salary}). Day3 will POST to backend.`,
        "ok"
    );

    addForm.reset();
});

loadEmployees();
