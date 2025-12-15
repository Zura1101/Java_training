// Same-origin now (frontend served by Spring Boot), so no CORS needed:
const API_BASE = "/employees";

const tbody = document.getElementById("tbody");
const refreshBtn = document.getElementById("refreshBtn");
const searchEl = document.getElementById("search");
const busyEl = document.getElementById("busy");
const toastEl = document.getElementById("toast");

const addForm = document.getElementById("addForm");
const addName = document.getElementById("addName");
const addDept = document.getElementById("addDept");
const addSalary = document.getElementById("addSalary");

const updForm = document.getElementById("updForm");
const updId = document.getElementById("updId");
const updName = document.getElementById("updName");
const updDept = document.getElementById("updDept");
const updSalary = document.getElementById("updSalary");

let allEmployees = [];

function setBusy(b) { busyEl.classList.toggle("hidden", !b); }
function showToast(msg, type = "ok") {
    toastEl.textContent = msg;
    toastEl.classList.remove("hidden", "ok", "err");
    toastEl.classList.add(type);
    clearTimeout(showToast._t);
    showToast._t = setTimeout(() => toastEl.classList.add("hidden"), 3000);
}

async function readText(res) { const t = await res.text(); return t || ""; }

async function apiGetAll() {
    const res = await fetch(API_BASE);
    const text = await readText(res);
    if (!res.ok) throw new Error(`GET ${res.status}\n${text}`);
    return JSON.parse(text);
}
async function apiAdd(payload) {
    const res = await fetch(API_BASE, { method: "POST", headers: { "Content-Type": "application/json" }, body: JSON.stringify(payload) });
    const text = await readText(res);
    if (!res.ok) throw new Error(`POST ${res.status}\n${text}`);
    return text ? JSON.parse(text) : null;
}
async function apiUpdate(id, payload) {
    const res = await fetch(`${API_BASE}/${encodeURIComponent(id)}`, { method: "PUT", headers: { "Content-Type": "application/json" }, body: JSON.stringify(payload) });
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
    if (!list.length) {
        tbody.innerHTML = `<tr><td colspan="5" class="muted">No employees found.</td></tr>`;
        return;
    }
    for (const e of list) {
        const tr = document.createElement("tr");
        tr.dataset.emp = JSON.stringify(e);
        tr.innerHTML = `
      <td>${e.id ?? ""}</td>
      <td>${e.name ?? ""}</td>
      <td>${e.department ?? ""}</td>
      <td>${e.salary ?? ""}</td>
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
    render(allEmployees.filter(e =>
        String(e.name ?? "").toLowerCase().includes(q) ||
        String(e.department ?? "").toLowerCase().includes(q)
    ));
}

async function reload() {
    setBusy(true);
    try {
        allEmployees = await apiGetAll();
        applySearch();
        showToast("Loaded ✅", "ok");
    } catch (e) {
        showToast(e.message, "err");
    } finally {
        setBusy(false);
    }
}

refreshBtn.addEventListener("click", reload);
searchEl.addEventListener("input", applySearch);

addForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const name = addName.value.trim();
    const department = addDept.value.trim();
    const salary = Number(addSalary.value);

    if (!name || !department || Number.isNaN(salary) || salary < 0) {
        showToast("Please enter valid Name, Department, Salary.", "err");
        return;
    }

    setBusy(true);
    try {
        await apiAdd({ name, department, salary });
        addForm.reset();
        await reload();
        showToast("Employee added ✅", "ok");
    } catch (err) {
        showToast(err.message, "err");
    } finally {
        setBusy(false);
    }
});

updForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const id = Number(updId.value);
    const name = updName.value.trim();
    const department = updDept.value.trim();
    const salary = Number(updSalary.value);

    if (!id || id < 1) { return showToast("Valid ID required.", "err"); }
    if (!name || !department || Number.isNaN(salary) || salary < 0) {
        return showToast("Please enter valid Name, Department, Salary.", "err");
    }

    setBusy(true);
    try {
        await apiUpdate(id, { name, department, salary });
        await reload();
        showToast("Employee updated ✅", "ok");
    } catch (err) {
        showToast(err.message, "err");
    } finally {
        setBusy(false);
    }
});

tbody.addEventListener("click", async (e) => {
    const btn = e.target.closest("button");
    if (!btn) return;

    const tr = btn.closest("tr");
    const emp = JSON.parse(tr.dataset.emp || "{}");

    if (btn.dataset.action === "edit") {
        updId.value = emp.id ?? "";
        updName.value = emp.name ?? "";
        updDept.value = emp.department ?? "";
        updSalary.value = emp.salary ?? "";
        showToast(`Loaded ${emp.id} into Update form.`, "ok");
    }

    if (btn.dataset.action === "delete") {
        if (!confirm(`Delete employee ${emp.id}?`)) return;
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

reload();
