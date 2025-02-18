package org.movier.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import org.movier.exceptions.MyParsingJsonException;
import org.movier.exceptions.MyRequestException;
import org.movier.exceptions.TMDBAPIException;
import org.movier.model.entity.MyGenre;
import org.movier.model.entity.MyMovie;
import org.movier.repository.MyGenreRepository;
import org.movier.repository.MyMovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
@PropertySource("classpath:application.properties")
public class TMDBdataGetterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TMDBdataGetterService.class);

    private final RestTemplate restTemplate;
    private final MyMovieRepository myMovieRepository;
    private final ObjectMapper objectMapper;
    private final MyGenreRepository myGenreRepository;

    public TMDBdataGetterService(RestTemplate restTemplate,
                                 MyMovieRepository myMovieRepository,
                                 ObjectMapper objectMapper,
                                 MyGenreRepository myGenreRepository) {
        this.restTemplate = restTemplate;
        this.myMovieRepository = myMovieRepository;
        this.objectMapper = objectMapper;
        this.myGenreRepository = myGenreRepository;

        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Value("${tmdb.key}")
    private String API_KEY;
    private static final String BASE_URL = "https://api.themoviedb.org/3";

    @PostConstruct
    public void fulfillDBifNotFull() {
        Integer numOfGenres = myGenreRepository.existsAnyGenre();
        if( numOfGenres == null || numOfGenres==0){
            myGenreRepository.saveAll(getAllGenres());
        }
        Integer numOfMovies = myMovieRepository.existsAnyMovie();
        if (numOfMovies == null || numOfMovies==0) {
            myMovieRepository.saveAll(getPopularMovies());
        }
    }

    @Scheduled(cron = "0 47 14 * * ?")
    public void getLatestMovies() {
        LocalDate latestDate = myMovieRepository.getLatestDate();
        myMovieRepository.saveAll(getMoviesReleasedAfter(latestDate));
    }

    private List<MyMovie> getPopularMovies(){
        try(ExecutorService executor = Executors.newFixedThreadPool(10)){
            List<Future<List<MyMovie>>> futures = executor.invokeAll(getCallableList());

            List<MyMovie> allMovies = new ArrayList<>();
            for (Future<List<MyMovie>> future : futures) {
                allMovies.addAll(future.get());
            }
            return allMovies;
        } catch (ExecutionException | InterruptedException e) {
            LOGGER.error("Exception while getting new movies");
            throw new TMDBAPIException("Exception while getting new movies:");
        }
    }

    private List<Callable<List<MyMovie>>> getCallableList() {
        List<Callable<List<MyMovie>>> tasks = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            int page = i;
            tasks.add(() -> {
                String jsonResponse = makeRequest("/movie/popular" , page);
                try {
                    JsonNode rootNode = objectMapper.readTree(jsonResponse);
                    JsonNode resultsNode = rootNode.path("results");
                    return objectMapper.readValue(
                            resultsNode.toString(),
                            new TypeReference<>() {}
                    );
                } catch (Exception e) {
                    throw new MyParsingJsonException("Error while parsing JSON for");
                }
            });
        }
        return tasks;
    }

    public List<MyMovie> getMoviesReleasedAfter(LocalDate date) {
        String urlTemplate = BASE_URL + "/discover/movie" +
                "?api_key=" + API_KEY +
                "&primary_release_date.gte=" + date +
                "&language=en-US" +
                "&sort_by=primary_release_date.asc";

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(urlTemplate);

        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", "application/json");
        headers.add("Authorization", "Bearer " + API_KEY);

        List<MyMovie> movies = new ArrayList<>();
        JsonNode resultsNode;
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    String.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                JsonNode rootNode = objectMapper.readTree(response.getBody());
                resultsNode = rootNode.path("results");
            } else {
                throw new MyRequestException("error API: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new MyRequestException("Error while making request");
        }
        try {
            if (resultsNode.isArray()) {
                for (JsonNode node : resultsNode) {
                    MyMovie movie = objectMapper.treeToValue(node, MyMovie.class);
                    movies.add(movie);
                }
            }
        } catch (Exception e) {
            throw new MyParsingJsonException("error while parsing JSON");
        }

        return movies;
    }


    private List<MyGenre> getAllGenres() {
        String strResponse = makeRequest("/genre/movie/list",-1);

        try {
            JsonNode rootNode = objectMapper.readTree(strResponse);
            JsonNode resultsNode = rootNode.path("genres");
            return objectMapper.readValue(
                    resultsNode.toString(),
                    new TypeReference<>() {}
            );
        } catch (Exception e) {
            throw new MyParsingJsonException("Error while parsing JSON");
        }
    }

    private String makeRequest(String urlPart, int page) {
        String urlTemplate = BASE_URL + urlPart + "?language=en-US";
        if (page != -1) {
            urlTemplate += "&page=" + page;
        }

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(urlTemplate);

        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", "application/json");
        headers.add("Authorization", "Bearer " + API_KEY);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    String.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                throw new MyRequestException("error API: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new MyRequestException("Error while making request");
        }
    }
}
