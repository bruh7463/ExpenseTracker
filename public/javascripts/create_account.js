// create_account.js
/
document.getElementById('create-account-form').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent default form submission

    // Get form input values
    var username = document.getElementById('username').value;
    var password = document.getElementById('password').value;
    var securityQuestion = document.getElementById('security-question').value;
    var securityAnswer = document.getElementById('security-answer').value;

    // Dummy validation logic
    if (username && password && securityQuestion && securityAnswer) {
        // Proceed with account creation (replace with actual logic)
        alert('Account created successfully!');
    } else {
        // Display error message
        document.getElementById('error-message').innerText = 'Please fill in all fields';
    }
});/
