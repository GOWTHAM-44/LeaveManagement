async function loadLeaves() {
  const res = await fetch("http://localhost:8080/api/leaves/all");
  const leaves = await res.json();

  const tbody = document.querySelector("#leaveTable tbody");
  tbody.innerHTML = "";
  leaves.forEach(l => {
    tbody.innerHTML += `
      <tr>
        <td>${l.id}</td>
        <td>${l.employeeId}</td>
        <td>${l.startDate}</td>
        <td>${l.endDate}</td>
        <td>${l.reason}</td>
      </tr>
    `;
  });
}

loadLeaves();
