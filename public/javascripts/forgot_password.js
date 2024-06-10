// forgot_password.js

document.getElementById('forgot-password-form').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent default form submission

    // Get user's answer and new password
    var answer = document.getElementById('answer').value;
    var newPassword = document.getElementById('new-password').value;
    var confirmPassword = document.getElementById('confirm-password').value;

    // Dummy validation logic (replace with actual validation)
    if (answer === 'blue') { // Replace 'blue' with the user's actual security answer
        if (newPassword === confirmPassword) {
            // Passwords match, reset password
            alert('Password reset successful!');
            // Here you can add code to send the new password to the server
            // For this example, we'll just reset the form
            document.getElementById('forgot-password-form').reset();
        } else {
            // Passwords don't match, show error message
            document.getElementById('error-message').textContent = 'Passwords do not match!';
        }
    } else {
        // Incorrect answer, show error message
        document.getElementById('error-message').textContent = 'Incorrect answer to security question!';
    }
});
