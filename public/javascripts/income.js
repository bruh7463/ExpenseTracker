// Get current date
var currentDate = new Date();

// Format date (e.g., May 8, 2024)
var options = { year: 'numeric', month: 'long', day: 'numeric' };
var formattedDate = currentDate.toLocaleDateString('en-US', options);

// Update current date element
document.getElementById('current-date').textContent = formattedDate;