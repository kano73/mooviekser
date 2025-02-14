package org.movier.service;

import org.movier.config.security.AuthenticatedMyUserService;
import org.movier.model.dto.MyMovieSearchDTO;
import org.movier.model.entity.MyMovie;
import org.movier.model.entity.MyUser;
import org.movier.model.entity.Watched;
import org.movier.repository.MyMovieRepository;
import org.movier.repository.WatchedRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyMovieService {

    private final WatchedRepository watchedRepository;
    private final AuthenticatedMyUserService auth;
    private final MyMovieRepository myMovieRepository;
    private final MyRatingService myRatingService;
    private final FavoriteService favoriteService;

    public MyMovieService(WatchedRepository watchedRepository, AuthenticatedMyUserService auth, MyMovieRepository myMovieRepository, MyRatingService myRatingService, FavoriteService favoriteService) {
        this.watchedRepository = watchedRepository;
        this.auth = auth;
        this.myMovieRepository = myMovieRepository;
        this.myRatingService = myRatingService;
        this.favoriteService = favoriteService;
    }

    public List<MyMovie> findAllWatchedMoviesForUser() {
        MyUser user = auth.getCurrentUserAuthenticated();
        return watchedRepository.findAllByUser(user).stream()
                .map(Watched::getMovie)
                .map(myRatingService::adjustRatingParams)
                .collect(Collectors.toList());
    }

    public List<MyMovie> findAllPageable(int page) {
        if(page < 1) {
            page = 1;
        }
        return myMovieRepository.findAll(PageRequest.of(page-1, 25)).stream()
                .map(myRatingService::adjustRatingParams)
                .collect(Collectors.toList());
    }

    public List<MyMovie> findAllByParams(int page, MyMovieSearchDTO dto) {
        if(page < 1) {
            page = 1;
        }

        if(dto.getFrom() == null && dto.getTo() != null) {
            dto.setFrom(LocalDate.MIN);
        }else if(dto.getFrom() != null && dto.getTo() == null) {
            dto.setFrom(LocalDate.MAX);
        }

        return myMovieRepository.findAllByFilters(
                dto.getTitle(),dto.getFrom(),dto.getTo(),dto.getGenres() ,PageRequest.of(page-1, 25)).stream()
                .map(myRatingService::adjustRatingParams)
                .collect(Collectors.toList());
    }

    public List<MyMovie> findAllFavoriteMoviesForUser(int page) {
        MyUser user = auth.getCurrentUserAuthenticated();
        return favoriteService.getAllFavoriteMoviesForUser(page).stream()
                .map(myRatingService::adjustRatingParams)
                .collect(Collectors.toList());
    }

}
