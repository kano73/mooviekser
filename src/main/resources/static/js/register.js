"use strict";

document.getElementById('registerForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const username = document.getElementById('username').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;

    if(confirmPassword!==password){
        alert("Passwords do not match!");
        return;
    }

    const requestData = {
        username:username,
        password:password,
        email:email
    };

    const submitButton = document.getElementById('btnSubmit');
    submitButton.disabled = true;

    fetch(urlToSend, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(requestData),
    })
        .then(response => response.text())
        .then(data => {
            alert(data);
        })
        .catch((error) => {
            console.error('Error:', error);
            alert("error:"+error);
        });

    submitButton.disabled = false;
});