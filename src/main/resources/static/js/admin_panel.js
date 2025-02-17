"use strict";

document.getElementById('banForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const username = document.getElementById('username').value;

    const submitButton = document.getElementById('btnSubmit');
    submitButton.disabled = true;

    fetch('/banUser', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(username),
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

document.getElementById('unbanForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const username = document.getElementById('username2').value;

    const submitButton = document.getElementById('btnSubmit2');
    submitButton.disabled = true;

    fetch('/unbanUser', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(username),
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

document.getElementById('addToAdminForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const username = document.getElementById('username1').value;

    const submitButton = document.getElementById('btnSubmit1');
    submitButton.disabled = true;

    fetch('/addAdmin', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(username),
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