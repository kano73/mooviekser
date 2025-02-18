package org.movier.service;

import jakarta.validation.constraints.NotNull;
import org.movier.config.security.AuthenticatedMyUserService;
import org.movier.exceptions.MovieDoesNotExistsException;
import org.movier.model.entity.MyMovie;
import org.movier.model.entity.MyUser;
import org.movier.model.entity.Watched;
import org.movier.repository.MyMovieRepository;
import org.movier.repository.WatchedRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class WatchedService {

    private final WatchedRepository watchedRepository;
    private final AuthenticatedMyUserService auth;
    private final MyMovieRepository myMovieRepository;

    public WatchedService(WatchedRepository watchedRepository,
                          AuthenticatedMyUserService authenticatedMyUserService,
                          MyMovieRepository myMovieRepository) {
        this.watchedRepository = watchedRepository;
        this.auth = authenticatedMyUserService;
        this.myMovieRepository = myMovieRepository;
    }

    @Transactional
    public boolean saveOrDelete(Long movieId) {
        MyUser user = auth.getCurrentUserAuthenticated();
        if(!myMovieRepository.existsById(movieId)) {
            throw new MovieDoesNotExistsException("Movie does not exist with id " + movieId);
        }
        Optional<Watched> watchedOpt = watchedRepository.findByUserAndMovie_Id(user, movieId);
        if(watchedOpt.isPresent()) {
            watchedRepository.deleteByUserAndMovie_Id(user, movieId);
        }else {
            MyMovie movie = new MyMovie();
            movie.setId(movieId);

            Watched watched = new Watched();
            watched.setUser(user);
            watched.setMovie(movie);

            watchedRepository.save(watched);
        }
        return true;
    }

    public Boolean isUserWatchedThisMovie(@NotNull Long movieId, MyUser user) {
        myMovieRepository.findById(movieId)
                .orElseThrow(()->new MovieDoesNotExistsException("Movie does not exist with id " + movieId));

        return watchedRepository.findByUserAndMovie_Id(user, movieId).isPresent();
    }
}
