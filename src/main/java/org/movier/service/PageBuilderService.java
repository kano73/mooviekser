package org.movier.service;

import jakarta.validation.constraints.NotNull;
import org.movier.model.entity.MyGenre;
import org.movier.model.entity.MyMovie;
import org.movier.repository.MyGenreRepository;
import org.movier.repository.WatchedRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class PageBuilderService {
    private final MyGenreRepository myGenreRepository;
    private final MyMovieService myMovieService;
    private final WatchedRepository watchedRepository;
    private final WatchedService watchedService;

    public PageBuilderService(MyGenreRepository myGenreRepository, MyMovieService myMovieService, WatchedRepository watchedRepository, WatchedService watchedService) {
        this.myGenreRepository = myGenreRepository;
        this.myMovieService = myMovieService;
        this.watchedRepository = watchedRepository;
        this.watchedService = watchedService;
    }

    public void buildMovies(Model model) {
        List<MyGenre> genres = myGenreRepository.findAll();
        model.addAttribute("genres", genres);
    }

    public void buildMovieById(Model model, @NotNull Long id) {
        MyMovie movie = myMovieService.findById(id);
        try{
            Boolean isWatched = watchedService.isUserWatchedThisMovie(id);
            model.addAttribute("isWatched", isWatched);
        }catch (Exception e){
            model.addAttribute("isWatched", false);
        }
        model.addAttribute("movie", movie);
    }
}
