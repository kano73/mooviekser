"use strict";

document.addEventListener("DOMContentLoaded", function () {
    const select = document.getElementById("genres");

    select.addEventListener("change", function () {
        Array.from(select.options).forEach(option => {
            if (option.selected) {
                if (option.dataset.selected === "true") {
                    option.selected = false;
                    option.dataset.selected = "false";
                    option.classList.remove("selected");
                } else {
                    option.dataset.selected = "true";
                    option.classList.add("selected");
                }
            }
        });
    });

    function getFormData() {
        const form = document.querySelector("form");
        const formData = new FormData(form);

        let jsonData = {};

        formData.forEach((value, key) => {
            if (value.trim() !== "") {
                if (jsonData[key]) {
                    if (!Array.isArray(jsonData[key])) {
                        jsonData[key] = [jsonData[key]];
                    }
                    jsonData[key].push(value);
                } else {
                    jsonData[key] = value;
                }
            }
        });

        const selectedGenres = Array.from(select.options)
            .filter(option => option.dataset.selected === "true")
            .map(option => option.value);

        if (selectedGenres.length > 0) {
            jsonData["genres"] = selectedGenres;
        }

        return JSON.stringify(jsonData);
    }

    const btnSubmit = document.getElementById("btnSubmit");
    const prevPageBtn = document.querySelector(".prvPgBtn");
    const nextPageBtn = document.querySelector(".nxtPgBtn");

    const pageNumElement = document.querySelector('.pageNum');
    let currentPage = parseInt(pageNumElement.textContent);

    function sendRequest(page) {
        let filters = getFormData();

        console.log(filters);

        let url = filters && filters !== "{}" ? "/movies" : "/allMovies";

        url += `?page=${page}`;

        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: filters ? filters : null,
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

    btnSubmit.addEventListener("click", function (event) {
        event.preventDefault();
        sendRequest(currentPage);
    });

    prevPageBtn.addEventListener("click", function () {
        if (currentPage > 1) {
            currentPage--;
            pageNumElement.textContent = currentPage.toString();
            sendRequest(currentPage);
        }
    });

    nextPageBtn.addEventListener("click", function () {
        currentPage++;
        pageNumElement.textContent = currentPage.toString();
        sendRequest(currentPage);
    });

    sendRequest(currentPage);
});


