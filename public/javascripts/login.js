/*document.getElementById('login-form').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent default form submission

    // Dummy authentication logic
    var username = document.getElementById('username').value;
    var password = document.getElementById('password').value;

    if (username === 'user' && password === 'password') {
        // Authentication successful
        sessionStorage.setItem('isLoggedIn', true);

        // Replace the current URL in the history with the dashboard URL
        history.replaceState(null, '', 'dashboard');

        // Redirect to dashboard
        window.location.href = 'dashboard';
    } else {
        // Authentication failed
        document.getElementById('error-message').innerText = 'Invalid username or password';
    }
});

// Check if user is authenticated
if (sessionStorage.getItem('isLoggedIn')) {
    // Redirect to dashboard if already authenticated
    window.location.href = 'dashboard';
}*/
