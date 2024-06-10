// Check if user is authenticated
/*if (!sessionStorage.getItem('isLoggedIn')) {
    // Redirect to login page
    window.location.href = 'login';
}*/

// Logout function
function logout() {
    // Remove authentication flag from sessionStorage
    sessionStorage.removeItem('isLoggedIn');
    
    // Redirect to login page
    window.location.href = 'login';
}

// Prevent user from navigating back to dashboard using browser's back button
window.addEventListener('popstate', function(event) {
    // Redirect to login page
    window.location.href = 'login';
});

// Your existing JavaScript code for dashboard functionalities can go here
// For example:
document.getElementById('burger-menu').addEventListener('click', function() {
    // Toggle sidebar visibility
    document.getElementById('sidebar').classList.toggle('active');
});

// Get current date
var currentDate = new Date();

// Format date (e.g., May 8, 2024)
var options = { year: 'numeric', month: 'long', day: 'numeric' };
var formattedDate = currentDate.toLocaleDateString('en-US', options);

// Update current date element
document.getElementById('current-date').textContent = formattedDate;

// Get the modal element
var modal = document.getElementById('expenses-modal');

// Get the button that opens the modal
var btn = document.getElementById("view-expenses-btn");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal
btn.onclick = function() {
    modal.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

// Chart.js initialization
/*var ctx = document.getElementById('expense-chart').getContext('2d');
var myChart = new Chart(ctx, {
    type: 'bar',
    data: {
        labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
        datasets: [{
            label: 'Monthly Expenses',
            data: [500, 600, 700, 800, 900, 600, 1100, 1200, 1300, 1400, 1500, 500], // Sample data
            backgroundColor: 'rgba(54, 162, 235, 0.6)', // Adjust color as needed
            borderColor: 'rgba(54, 162, 235, 1)', // Adjust color as needed
            borderWidth: 1
        }]
    },
    options: {
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true
                }
            }]
        }
    }
});*/

// Inside the <script> tag, after existing code

document.addEventListener('DOMContentLoaded', function() {
    // Get form elements
    var itemInput = document.getElementById('item');
    var costGroup = document.getElementById('cost-group');
    var costInput = document.getElementById('cost');
    var quantityGroup = document.getElementById('quantity-group');
    var quantityInput = document.getElementById('quantity');
    var locationGroup = document.getElementById('location-group');
    var locationInput = document.getElementById('location');
    var addPurchaseBtn = document.getElementById('add-purchase-btn');
    var purchaseList = document.getElementById('purchase-list');

    // Show cost input when item is entered
    itemInput.addEventListener('input', function() {
        if (itemInput.value.trim() !== '') {
            costGroup.classList.remove('hidden');
        } else {
            costGroup.classList.add('hidden');
        }
    });

    // Show quantity input when cost is entered
    costInput.addEventListener('input', function() {
        if (costInput.value.trim() !== '') {
            quantityGroup.classList.remove('hidden');
        } else {
            quantityGroup.classList.add('hidden');
        }
    });

    // Show location input when quantity is entered
    quantityInput.addEventListener('input', function() {
        if (quantityInput.value.trim() !== '') {
            locationGroup.classList.remove('hidden');
        } else {
            locationGroup.classList.add('hidden');
        }
    });

    // Add purchase to list
    addPurchaseBtn.addEventListener('click', function() {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", MyApp.expense, true);
        xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200)
            console.log(xhr.responseText);
        }
        xhr.send();

        var item = itemInput.value.trim();
        var cost = costInput.value.trim();
        var quantity = quantityInput.value.trim();
        var location = locationInput.value.trim();

        // Validate inputs
        if (item === '' || cost === '' || quantity === '' || location === '') {
            alert('Please fill in all fields.');
            return;
        }

        // Add purchase to list display (customize this according to your layout)
        var listItem = document.createElement('div');
        listItem.textContent = `Item: ${item}, Cost: K ${cost}, Quantity: ${quantity}, Location: ${location}`;
        purchaseList.appendChild(listItem);

        // Clear form inputs
        itemInput.value = '';
        costInput.value = '';
        quantityInput.value = '';
        locationInput.value = '';

        // Hide form inputs
        costGroup.classList.add('hidden');
        quantityGroup.classList.add('hidden');
        locationGroup.classList.add('hidden');
        //window.location.href = MyApp.expense;
    });
});
// Inside the <script> tag, after existing code

document.addEventListener('DOMContentLoaded', function() {
    // Get elements
    var currentDateSpan = document.getElementById('date');
    var currentTimeSpan = document.getElementById('time');
    var clearListBtn = document.getElementById('clear-list-btn');
    var purchaseList = document.getElementById('purchase-list');
    var totalAmountSpan = document.getElementById('total-amount');
    var listItemCounter = 0;

    // Display current date
    var currentDate = new Date();
    currentDateSpan.textContent = currentDate.toDateString();

    // Display current time
    function updateTime() {
        var currentTime = new Date();
        var hours = currentTime.getHours();
        var minutes = currentTime.getMinutes();
        var seconds = currentTime.getSeconds();

        // Add leading zeros if necessary
        hours = (hours < 10) ? '0' + hours : hours;
        minutes = (minutes < 10) ? '0' + minutes : minutes;
        seconds = (seconds < 10) ? '0' + seconds : seconds;

        // Update time display
        currentTimeSpan.textContent = hours + ':' + minutes + ':' + seconds;
    }

    // Call updateTime every second
    setInterval(updateTime, 1000);

    // Clear list button functionality
    clearListBtn.addEventListener('click', function() {
        purchaseList.innerHTML = ''; // Clear the list
        totalAmountSpan.textContent = '0.00'; // Reset total amount
        listItemCounter = 0; // Reset list item counter
    });

    // Add purchase to list
    addPurchaseBtn.addEventListener('click', function() {
        var item = itemInput.value.trim();
        var cost = costInput.value.trim();
        var quantity = quantityInput.value.trim();
        var location = locationInput.value.trim();

        // Validate inputs
        if (item === '' || cost === '' || quantity === '' || location === '') {
            alert('Please fill in all fields.');
            return;
        }

        // Increment list item counter
        listItemCounter++;

        // Get current date and time
        var currentDate = new Date();
        var currentTime = currentDate.toLocaleTimeString();
        var currentDateStr = currentDate.toDateString();

        // Create list item
        var listItem = document.createElement('div');
        listItem.innerHTML = `
            <div>Item ${listItemCounter}: Commodity: ${item}</div>
            <div>Quantity: ${quantity}</div>
            <div>Amount: ${cost}</div>
            <div>Place: ${location}</div>
            <div>Date: ${currentDateStr}</div>
            <div>Time: ${currentTime}</div>
            <hr>
        `;
        purchaseList.appendChild(listItem);

        // Update total amount
        var totalAmount = parseFloat(cost) * parseInt(quantity);
        var currentTotal = parseFloat(totalAmountSpan.textContent);
        totalAmountSpan.textContent = (currentTotal + totalAmount).toFixed(2);

        // Clear form inputs
        itemInput.value = '';
        costInput.value = '';
        quantityInput.value = '';
        locationInput.value = '';

        // Hide form inputs
        costGroup.classList.add('hidden');
        quantityGroup.classList.add('hidden');
        locationGroup.classList.add('hidden');
    });
});


