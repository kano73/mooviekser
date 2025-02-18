package org.movier.service;

import jakarta.validation.constraints.NotNull;
import org.movier.config.security.AuthenticatedMyUserService;
import org.movier.exceptions.UserNotFoundException;
import org.movier.model.entity.MyGenre;
import org.movier.model.entity.MyMovie;
import org.movier.model.entity.MyUser;
import org.movier.repository.MyGenreRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class PageBuilderService {
    private final MyGenreRepository myGenreRepository;
    private final MyMovieService myMovieService;
    private final WatchedService watchedService;
    private final AuthenticatedMyUserService authenticatedMyUserService;
    private final MyRatingService myRatingService;

    public PageBuilderService(MyGenreRepository myGenreRepository,
                              MyMovieService myMovieService,
                              WatchedService watchedService,
                              AuthenticatedMyUserService authenticatedMyUserService,
                              MyRatingService myRatingService) {
        this.myGenreRepository = myGenreRepository;
        this.myMovieService = myMovieService;
        this.watchedService = watchedService;
        this.authenticatedMyUserService = authenticatedMyUserService;
        this.myRatingService = myRatingService;
    }

    public void buildMovies(Model model) {
        List<MyGenre> genres = myGenreRepository.findAll();
        model.addAttribute("genres", genres);
    }

    public void buildMovieById(Model model, @NotNull Long id) {
        MyMovie movie = myMovieService.findById(id);
        boolean isWatched = false;
        try{
            MyUser user = authenticatedMyUserService.getCurrentUserAuthenticated();
            isWatched = watchedService.isUserWatchedThisMovie(id, user);
            Float ratingValue = myRatingService.getValueByUser_IdAndMovie_Id(user.getId(),movie.getId());

            model.addAttribute("usersId", user.getId());
            model.addAttribute("ratingValue", ratingValue);
        }catch (UserNotFoundException _){}
        model.addAttribute("isWatched", isWatched);
        model.addAttribute("movie", movie);
    }
}
