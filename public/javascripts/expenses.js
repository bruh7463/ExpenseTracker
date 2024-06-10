document.addEventListener('DOMContentLoaded', function() {
    // Add click event listener to dots for action dropdown
    var dots = document.querySelectorAll('.dots');
    dots.forEach(function(dot) {
        dot.addEventListener('click', function(event) {
            var dropdown = this.nextElementSibling;
            dropdown.style.display = dropdown.style.display === 'block' ? 'none' : 'block';
        });
    });

    // Close action dropdown when clicking outside
    document.addEventListener('click', function(event) {
        if (!event.target.matches('.dots')) {
            var dropdowns = document.querySelectorAll('.dropdown-content');
            dropdowns.forEach(function(dropdown) {
                dropdown.style.display = 'none';
            });
        }
    });

    // Update current date
    var currentDate = new Date();
    var options = { year: 'numeric', month: 'long', day: 'numeric' };
    var formattedDate = currentDate.toLocaleDateString('en-US', options);
    document.getElementById('current-date').textContent = formattedDate;

    // Logout function
    function logout() {
        // Remove authentication flag from sessionStorage
        sessionStorage.removeItem('isLoggedIn');
        
        // Redirect to login page
        window.location.href = 'login';
    }

    // Prevent user from navigating back to dashboard using browser's back button
    window.addEventListener('popstate', function(event) {
        window.location.href = 'login';
    });

    // Add search functionality
    var searchInput = document.querySelector('#search-wrapper input[type="text"]');
    searchInput.addEventListener('input', function(event) {
        var searchTerm = event.target.value.toLowerCase();
        var rows = document.querySelectorAll('tbody tr');
        rows.forEach(function(row) {
            var rowData = row.textContent.toLowerCase();
            if (rowData.includes(searchTerm)) {
                row.style.display = 'table-row';
            } else {
                row.style.display = 'none';
            }
        });
    });
    document.getElementById('logout-button').addEventListener('click', logout);
});
