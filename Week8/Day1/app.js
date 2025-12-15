const API = "http://localhost:8080/employees";

const loadBtn = document.getElementById("loadBtn");
const output = document.getElementById("output");

loadBtn.addEventListener("click", async () => {
    output.textContent = "Loading...";

    try {
        const res = await fetch(API);
        if (!res.ok) throw new Error(`GET failed: ${res.status}`);

        const data = await res.json();
        output.textContent = JSON.stringify(data, null, 2);
    } catch (e) {
        output.textContent = `Error: ${e.message}`;
    }
});
