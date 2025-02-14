package org.movier.service;

import jakarta.validation.Valid;
import org.movier.config.security.AuthenticatedMyUserService;
import org.movier.exceptions.MovieDoesNotExistsException;
import org.movier.model.dto.WatchedDTO;
import org.movier.model.entity.MyMovie;
import org.movier.model.entity.MyUser;
import org.movier.model.entity.Watched;
import org.movier.repository.MyMovieRepository;
import org.movier.repository.WatchedRepository;
import org.springframework.stereotype.Service;

@Service
public class WatchedService {

    private final WatchedRepository watchedRepository;
    private final AuthenticatedMyUserService auth;
    private final MyMovieRepository myMovieRepository;

    public WatchedService(WatchedRepository watchedRepository, AuthenticatedMyUserService authenticatedMyUserService, MyMovieRepository myMovieRepository) {
        this.watchedRepository = watchedRepository;
        this.auth = authenticatedMyUserService;
        this.myMovieRepository = myMovieRepository;
    }

    public boolean save(@Valid WatchedDTO dto) {
        MyUser user = auth.getCurrentUserAuthenticated();
        if(!myMovieRepository.existsById(dto.getMovieId())) {
            throw new MovieDoesNotExistsException("Movie does not exist with id " + dto.getMovieId());
        }
        MyMovie movie = new MyMovie();
        movie.setId(dto.getMovieId());

        Watched watched = new Watched();
        watched.setUser(user);
        watched.setMovie(movie);

        watchedRepository.save(watched);
        return true;
    }

    public Long countWatchedFourMovie(Long movieId) {
        MyMovie movie = myMovieRepository.findById(movieId)
                .orElseThrow(()->new MovieDoesNotExistsException("Movie does not exist with id " + movieId));
        return watchedRepository.countFavoriteByMovie(movie);
    }

    public boolean remove(@Valid WatchedDTO dto) {
        MyUser user = auth.getCurrentUserAuthenticated();
        if(!myMovieRepository.existsById(dto.getMovieId())) {
            throw new MovieDoesNotExistsException("Movie does not exist with id " + dto.getMovieId());
        }
        MyMovie movie = new MyMovie();
        movie.setId(dto.getMovieId());

        watchedRepository.deleteByUserAndMovie(user, movie);
        return true;
    }
}
