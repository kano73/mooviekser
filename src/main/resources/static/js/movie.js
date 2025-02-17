document.addEventListener("DOMContentLoaded", function () {
    const movieId = new URLSearchParams(window.location.search).get("id");
    const commentsContainer = document.querySelector('.comments');

    function getComments() {
        fetch(`/comment?movieId=${movieId}`)
            .then(response => response.json())
            .then(data => {
                if (data && data.length > 0) {
                    displayComments(data);
                } else {
                    commentsContainer.innerHTML = "<p>No comments available.</p>";
                }
            })
            .catch((error) => {
                console.error('Error:', error);
                commentsContainer.innerHTML = "<p>Error loading comments.</p>";
            });
    }


    function displayComments(comments) {
        commentsContainer.innerHTML = '';
        comments.forEach(comment => {
            const commentDiv = document.createElement('div');
            commentDiv.classList.add('comment');
            commentDiv.innerHTML = `
                <p><strong>${comment.authorName}</strong>: ${comment.text}</p>
                <em>${new Date(comment.date[0], comment.date[1] - 1, comment.date[2], comment.date[3], comment.date[4], comment.date[5], comment.date[6]).toLocaleString()}</em>

            `;
            commentsContainer.insertBefore(commentDiv, commentsContainer.firstChild);
        });
    }

    getComments();

    const commentForm = document.getElementById('commentForm');
    commentForm.addEventListener('submit', function (e) {
        e.preventDefault();

        const commentText = document.querySelector('#comment').value.trim();
        if (!commentText) {
            alert("Please enter a comment.");
            return;
        }

        const newComment = {
            movieId: movieId,
            text: commentText,
        };

        fetch('/comment', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(newComment),
        })
            .then(response => response.text())
            .then(data => {
                alert(data);
            })
            .catch((error) => {
                console.error('Error:', error);
                alert("Error adding comment.");
            });
    });

    document.getElementById('ratingForm').addEventListener('submit', function(event) {
        event.preventDefault();

        const rating = parseFloat(document.getElementById('rating').value);

        if (rating >= 0.1 && rating <= 10.0) {
            fetch(`/rate`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ rating: rating, movieId: movieId }),
            })
                .then(response => response.text())
                .then(data => {
                    alert(data);
                })
                .catch((error) => {
                    console.error('error:', error);
                    alert(error.message);
                });
        } else {
            alert("pls choose valid value");
        }
    });

    document.getElementById('watchedBtn').addEventListener('click', function(event) {
        event.preventDefault();

        fetch(`/watched?movieId=${movieId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            }
        })
            .then(response => response.text())
            .then(data => {
                alert(data);
            })
            .catch((error) => {
                console.error('error:', error);
                alert(error.message);
            });


    })
});
