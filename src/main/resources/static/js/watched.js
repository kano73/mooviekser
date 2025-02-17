"use strict";

document.addEventListener("DOMContentLoaded", function () {
    function sendRequest() {
        fetch("/getWatched", {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }
        })
            .then(response => response.json())
            .then(data => {
                if (!data || data.length === 0) {
                    document.querySelector('.movies').innerHTML = "<p>No movies found</p>";
                    return;
                }
                showMovies(data);
            })
            .catch((error) => {
                console.error('Error:', error);
                alert("error:" + error);
            });
    }

    function showMovies(movies) {
        if (movies) {}
        const resultsContainer = document.querySelector('div.movies');
        resultsContainer.innerHTML = '';

        movies.forEach(movie => {
            const movieDiv = document.createElement('div');

            const [year, month, day] = movie.release_date;
            let releaseDateStr = `${year}/${String(month).padStart(2, '0')}/${String(day).padStart(2, '0')}`;

            movieDiv.classList.add('movie-div');
            movieDiv.innerHTML = `
                <a href='/movie?id=${movie.id}'>
                    <img src="https://image.tmdb.org/t/p/w300_and_h450_bestv2${movie.poster_path}" alt="poster">
                </a>
                <p><span>${movie.title}</span></p>
                <p>Rating: <span>${movie.vote_average}</span></p>
                <p><span>${releaseDateStr}</span></p>
            `;
            resultsContainer.appendChild(movieDiv);
        });
    }

    sendRequest();
});


