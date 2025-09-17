document.getElementById("leaveForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const data = {
    employeeId: e.target.employeeId.value,
    startDate: e.target.startDate.value,
    endDate: e.target.endDate.value,
    reason: e.target.reason.value
  };

  await fetch("http://localhost:8080/api/leaves/apply", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data)
  });

  alert("Leave Applied Successfully!");
  e.target.reset();
});
