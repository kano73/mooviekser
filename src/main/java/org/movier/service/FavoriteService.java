package org.movier.service;

import jakarta.validation.Valid;
import org.movier.config.security.AuthenticatedMyUserService;
import org.movier.exceptions.MovieDoesNotExistsException;
import org.movier.model.dto.FavoriteDTO;
import org.movier.model.entity.Favorite;
import org.movier.model.entity.MyMovie;
import org.movier.model.entity.MyUser;
import org.movier.repository.FavoriteRepository;
import org.movier.repository.MyMovieRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final AuthenticatedMyUserService auth;
    private final MyMovieRepository myMovieRepository;

    public FavoriteService(FavoriteRepository favoriteRepository, AuthenticatedMyUserService auth, MyMovieRepository myMovieRepository) {
        this.favoriteRepository = favoriteRepository;
        this.auth = auth;
        this.myMovieRepository = myMovieRepository;
    }

    public boolean addToFavorite(@Valid FavoriteDTO dto) {
        MyUser user = auth.getCurrentUserAuthenticated();
        MyMovie movie = myMovieRepository.findById(dto.getMovieId())
                .orElseThrow(()-> new MovieDoesNotExistsException("Movie not found"));
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setMovie(movie);

        favoriteRepository.save(favorite);
        return true;
    }

    public boolean removeFromFavorite(@Valid FavoriteDTO dto) {
        MyUser user = auth.getCurrentUserAuthenticated();
        MyMovie movie = myMovieRepository.findById(dto.getMovieId())
                .orElseThrow(()-> new MovieDoesNotExistsException("Movie not found"));
        favoriteRepository.deleteByUserAndMovie(user, movie);
        return true;
    }

    public List<MyMovie> getAllFavoriteMoviesForUser(int page ) {
        MyUser user = auth.getCurrentUserAuthenticated();
        if (page<1){
            page = 1;
        }
        return favoriteRepository.findAllByUser(user, PageRequest.of(page,25)).stream()
                .map(Favorite::getMovie).collect(Collectors.toList());
    }
}
