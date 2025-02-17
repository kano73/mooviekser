"use strict";

const originalUsername = document.getElementById('username').value;
const originalEmail = document.getElementById('email').value;

document.getElementById('changeInfoForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const username = document.getElementById('username').value;
    const email = document.getElementById('email').value;

    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;

    const oldPassword = document.getElementById('oldPassword').value;

    if (oldPassword === "") {
        alert("Old password is required");
        return;
    }

    let updated = false;
    const requestData = {};

    if(confirmPassword !== "" || password !== ""){
        if(confirmPassword !== password){
            alert("New passwords do not match!");
            return;
        }
        requestData.password = password;
        updated = true;
    }
    if(originalUsername !== username){
        requestData.username = username;
        updated = true;
    }
    if(originalEmail !== email){
        requestData.email = email;
        updated = true;
    }
    //ya utochka
    if(!updated){
        alert("No changes found");
        return;
    }
    requestData.oldPassword = oldPassword;

    const submitButton = document.getElementById('btnSubmit');
    submitButton.disabled = true;

    fetch('/change_profile', {
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
            alert("error: "+error);
        });

    submitButton.disabled = false;
});