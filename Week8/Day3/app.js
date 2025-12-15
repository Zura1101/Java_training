// Your working backend base (Option A)
const API_BASE = "http://localhost:8080/employees";

const tbody = document.getElementById("tbody");
const refreshBtn = document.getElementById("refreshBtn");
const statusEl = document.getElementById("status");

// Add
const addForm = document.getElementById("addForm");
const addName = document.getElementById("addName");
const addDept = document.getElementById("addDept");
const addSalary = document.getElementById("addSalary");

// Update
const updForm = document.getElementById("updForm");
const updId = document.getElementById("updId");
const updName = document.getElementById("updName");
const updDept = document.getElementById("updDept");
const updSalary = document.getElementById("updSalary");

function showStatus(msg, type = "ok") {
    statusEl.textContent = msg;
    statusEl.classList.remove("hidden", "ok", "err");
    statusEl.classList.add(type);
}
function hideStatus() { statusEl.classList.add("hidden"); }

function escapeHtml(str) {
    return String(str ?? "")
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}

async function safeText(res) {
    const t = await res.text();
    return t || "";
}

function jsonHeaders() {
    return { "Content-Type": "application/json" };
}

async function getEmployees() {
    const res = await fetch(API_BASE);
    const text = await safeText(res);
    if (!res.ok) throw new Error(`GET ${res.status}: ${text}`);
    return JSON.parse(text);
}

async function addEmployee(payload) {
    const res = await fetch(API_BASE, {
        method: "POST",
        headers: jsonHeaders(),
        body: JSON.stringify(payload),
    });
    const text = await safeText(res);
    if (!res.ok) throw new Error(`POST ${res.status}: ${text}`);
    return text ? JSON.parse(text) : null;
}

async function updateEmployee(id, payload) {
    const res = await fetch(`${API_BASE}/${encodeURIComponent(id)}`, {
        method: "PUT",
        headers: jsonHeaders(),
        body: JSON.stringify(payload),
    });
    const text = await safeText(res);
    if (!res.ok) throw new Error(`PUT ${res.status}: ${text}`);
    return text ? JSON.parse(text) : null;
}

async function deleteEmployee(id) {
    const res = await fetch(`${API_BASE}/${encodeURIComponent(id)}`, {
        method: "DELETE",
    });
    const text = await safeText(res);
    if (!res.ok) throw new Error(`DELETE ${res.status}: ${text}`);
    return text;
}

function renderEmployees(list) {
    tbody.innerHTML = "";

    if (!Array.isArray(list) || list.length === 0) {
        tbody.innerHTML = `<tr><td colspan="5" class="muted">No employees found.</td></tr>`;
        return;
    }

    for (const e of list) {
        const id = e.id ?? "";
        const name = e.name ?? "";
        const department = e.department ?? "";
        const salary = e.salary ?? "";

        const tr = document.createElement("tr");
        tr.dataset.emp = JSON.stringify({ id, name, department, salary });

        tr.innerHTML = `
      <td>${escapeHtml(id)}</td>
      <td>${escapeHtml(name)}</td>
      <td>${escapeHtml(department)}</td>
      <td>${escapeHtml(salary)}</td>
      <td>
        <button class="btn small" data-action="edit">Edit</button>
        <button class="btn small danger" data-action="delete">Delete</button>
      </td>
    `;
        tbody.appendChild(tr);
    }
}

async function reload() {
    hideStatus();
    try {
        showStatus("Loading employees...", "ok");
        const list = await getEmployees();
        renderEmployees(list);
        showStatus("Employees loaded.", "ok");
    } catch (e) {
        showStatus(e.message, "err");
    }
}

refreshBtn.addEventListener("click", reload);

// POST
addForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const payload = {
        name: addName.value.trim(),
        department: addDept.value.trim(),
        salary: Number(addSalary.value),
    };

    if (!payload.name || !payload.department || Number.isNaN(payload.salary)) {
        showStatus("Please fill valid Name, Department, Salary.", "err");
        return;
    }

    try {
        showStatus("Adding employee...", "ok");
        await addEmployee(payload);
        addForm.reset();
        await reload();
        showStatus("Employee added ✅", "ok");
    } catch (e2) {
        showStatus(e2.message, "err");
    }
});

// PUT
updForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const id = Number(updId.value);
    const payload = {
        name: updName.value.trim(),
        department: updDept.value.trim(),
        salary: Number(updSalary.value),
    };

    if (!id || id < 1) return showStatus("Valid ID is required.", "err");
    if (!payload.name || !payload.department || Number.isNaN(payload.salary)) {
        return showStatus("Please fill valid Name, Department, Salary.", "err");
    }

    try {
        showStatus("Updating employee...", "ok");
        await updateEmployee(id, payload);
        await reload();
        showStatus("Employee updated ✅", "ok");
    } catch (e2) {
        showStatus(e2.message, "err");
    }
});

// Edit/Delete buttons
tbody.addEventListener("click", async (e) => {
    const btn = e.target.closest("button");
    if (!btn) return;

    const action = btn.dataset.action;
    const tr = btn.closest("tr");
    const emp = JSON.parse(tr.dataset.emp || "{}");

    if (action === "edit") {
        updId.value = emp.id ?? "";
        updName.value = emp.name ?? "";
        updDept.value = emp.department ?? "";
        updSalary.value = emp.salary ?? "";
        showStatus(`Loaded employee ${emp.id} into Update form.`, "ok");
    }

    if (action === "delete") {
        const yes = confirm(`Delete employee ${emp.id}?`);
        if (!yes) return;

        try {
            showStatus("Deleting employee...", "ok");
            await deleteEmployee(emp.id);
            await reload();
            showStatus("Employee deleted ✅", "ok");
        } catch (e2) {
            showStatus(e2.message, "err");
        }
    }
});

reload();
