const API_BASE = "http://localhost:8080/employees";

const tbody = document.getElementById("tbody");
const refreshBtn = document.getElementById("refreshBtn");
const searchEl = document.getElementById("search");
const busyEl = document.getElementById("busy");
const toastEl = document.getElementById("toast");

// Add form elements
const addForm = document.getElementById("addForm");
const addName = document.getElementById("addName");
const addDept = document.getElementById("addDept");
const addSalary = document.getElementById("addSalary");
const addNameErr = document.getElementById("addNameErr");
const addDeptErr = document.getElementById("addDeptErr");
const addSalaryErr = document.getElementById("addSalaryErr");

// Update form elements
const updForm = document.getElementById("updForm");
const updId = document.getElementById("updId");
const updName = document.getElementById("updName");
const updDept = document.getElementById("updDept");
const updSalary = document.getElementById("updSalary");
const updIdErr = document.getElementById("updIdErr");
const updNameErr = document.getElementById("updNameErr");
const updDeptErr = document.getElementById("updDeptErr");
const updSalaryErr = document.getElementById("updSalaryErr");

let allEmployees = [];

function setBusy(isBusy) {
    busyEl.classList.toggle("hidden", !isBusy);
    document.querySelectorAll("button, input").forEach(el => (el.disabled = isBusy));
    // keep search enabled even when busy (optional)
    searchEl.disabled = false;
}

function showToast(msg, type = "ok") {
    toastEl.textContent = msg;
    toastEl.classList.remove("hidden", "ok", "err");
    toastEl.classList.add(type);
    clearTimeout(showToast._t);
    showToast._t = setTimeout(() => toastEl.classList.add("hidden"), 3500);
}

function clearErrors() {
    [addNameErr, addDeptErr, addSalaryErr, updIdErr, updNameErr, updDeptErr, updSalaryErr]
        .forEach(e => (e.textContent = ""));
}

function escapeHtml(str) {
    return String(str ?? "")
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}

async function readText(res) {
    const t = await res.text();
    return t || "";
}

function jsonHeaders() {
    return { "Content-Type": "application/json" };
}

async function apiGetAll() {
    const res = await fetch(API_BASE);
    const text = await readText(res);
    if (!res.ok) throw new Error(`GET ${res.status}\n${text}`);
    return JSON.parse(text);
}

async function apiAdd(payload) {
    const res = await fetch(API_BASE, {
        method: "POST",
        headers: jsonHeaders(),
        body: JSON.stringify(payload),
    });
    const text = await readText(res);
    if (!res.ok) throw new Error(`POST ${res.status}\n${text}`);
    return text ? JSON.parse(text) : null;
}

async function apiUpdate(id, payload) {
    const res = await fetch(`${API_BASE}/${encodeURIComponent(id)}`, {
        method: "PUT",
        headers: jsonHeaders(),
        body: JSON.stringify(payload),
    });
    const text = await readText(res);
    if (!res.ok) throw new Error(`PUT ${res.status}\n${text}`);
    return text ? JSON.parse(text) : null;
}

async function apiDelete(id) {
    const res = await fetch(`${API_BASE}/${encodeURIComponent(id)}`, { method: "DELETE" });
    const text = await readText(res);
    if (!res.ok) throw new Error(`DELETE ${res.status}\n${text}`);
    return text;
}

function render(list) {
    tbody.innerHTML = "";

    if (!Array.isArray(list) || list.length === 0) {
        tbody.innerHTML = `<tr><td colspan="5" class="muted">No employees found.</td></tr>`;
        return;
    }

    for (const e of list) {
        const tr = document.createElement("tr");
        tr.dataset.emp = JSON.stringify(e);

        tr.innerHTML = `
      <td>${escapeHtml(e.id)}</td>
      <td>${escapeHtml(e.name)}</td>
      <td>${escapeHtml(e.department)}</td>
      <td>${escapeHtml(e.salary)}</td>
      <td>
        <button class="btn small" data-action="edit">Edit</button>
        <button class="btn small danger" data-action="delete">Delete</button>
      </td>
    `;
        tbody.appendChild(tr);
    }
}

function applySearch() {
    const q = searchEl.value.trim().toLowerCase();
    if (!q) return render(allEmployees);

    const filtered = allEmployees.filter(e =>
        String(e.name ?? "").toLowerCase().includes(q) ||
        String(e.department ?? "").toLowerCase().includes(q)
    );
    render(filtered);
}

function validateEmployee(name, dept, salary, errEls) {
    let ok = true;

    if (!name || name.length < 2) {
        errEls.name.textContent = "Name must be at least 2 characters.";
        ok = false;
    }

    if (!dept || dept.length < 2) {
        errEls.dept.textContent = "Department must be at least 2 characters.";
        ok = false;
    }

    const sal = Number(salary);
    if (Number.isNaN(sal) || sal < 0) {
        errEls.salary.textContent = "Salary must be a number >= 0.";
        ok = false;
    }

    return { ok, salaryNumber: sal };
}

async function reload() {
    clearErrors();
    setBusy(true);
    try {
        allEmployees = await apiGetAll();
        applySearch();
        showToast("Employees loaded ✅", "ok");
    } catch (e) {
        showToast(e.message, "err");
    } finally {
        setBusy(false);
    }
}

refreshBtn.addEventListener("click", reload);
searchEl.addEventListener("input", applySearch);

// POST
addForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    clearErrors();

    const name = addName.value.trim();
    const dept = addDept.value.trim();
    const salary = addSalary.value.trim();

    const v = validateEmployee(name, dept, salary, { name: addNameErr, dept: addDeptErr, salary: addSalaryErr });
    if (!v.ok) return;

    setBusy(true);
    try {
        await apiAdd({ name, department: dept, salary: v.salaryNumber });
        addForm.reset();
        await reload();
        showToast("Employee added ✅", "ok");
    } catch (err) {
        showToast(err.message, "err");
    } finally {
        setBusy(false);
    }
});

// PUT
updForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    clearErrors();

    const id = Number(updId.value);
    if (!id || id < 1) {
        updIdErr.textContent = "Valid ID required.";
        return;
    }

    const name = updName.value.trim();
    const dept = updDept.value.trim();
    const salary = updSalary.value.trim();

    const v = validateEmployee(name, dept, salary, { name: updNameErr, dept: updDeptErr, salary: updSalaryErr });
    if (!v.ok) return;

    setBusy(true);
    try {
        await apiUpdate(id, { name, department: dept, salary: v.salaryNumber });
        await reload();
        showToast("Employee updated ✅", "ok");
    } catch (err) {
        showToast(err.message, "err");
    } finally {
        setBusy(false);
    }
});

// Table actions
tbody.addEventListener("click", async (e) => {
    const btn = e.target.closest("button");
    if (!btn) return;

    const tr = btn.closest("tr");
    const emp = JSON.parse(tr.dataset.emp || "{}");
    const action = btn.dataset.action;

    if (action === "edit") {
        updId.value = emp.id ?? "";
        updName.value = emp.name ?? "";
        updDept.value = emp.department ?? "";
        updSalary.value = emp.salary ?? "";
        showToast(`Loaded employee ${emp.id} into update form.`, "ok");
    }

    if (action === "delete") {
        const yes = confirm(`Delete employee ${emp.id}?`);
        if (!yes) return;

        setBusy(true);
        try {
            await apiDelete(emp.id);
            await reload();
            showToast("Employee deleted ✅", "ok");
        } catch (err) {
            showToast(err.message, "err");
        } finally {
            setBusy(false);
        }
    }
});

// initial load
reload();
