package org.movier.service;

import org.movier.config.security.AuthenticatedMyUserService;
import org.movier.exceptions.MovieDoesNotExistsException;
import org.movier.model.dto.MyMovieSearchDTO;
import org.movier.model.entity.MyMovie;
import org.movier.model.entity.MyUser;
import org.movier.model.entity.Watched;
import org.movier.model.responce.MyMovieSimpleInfoDTO;
import org.movier.repository.MyMovieRepository;
import org.movier.repository.WatchedRepository;
import org.movier.repository.specification.MyMovieSpecification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:application.properties")
public class MyMovieService {

    private final WatchedRepository watchedRepository;
    private final AuthenticatedMyUserService auth;
    private final MyMovieRepository myMovieRepository;
    private final FavoriteService favoriteService;

    @Value("${movies.page.size}")
    private Integer pageSize;

    public MyMovieService(AuthenticatedMyUserService auth, MyMovieRepository myMovieRepository, FavoriteService favoriteService, WatchedRepository watchedRepository) {
        this.auth = auth;
        this.myMovieRepository = myMovieRepository;
        this.favoriteService = favoriteService;
        this.watchedRepository = watchedRepository;
    }

    public MyMovie findById(Long id) {
        return myMovieRepository.findByIdWithGenres(id).orElseThrow(()-> new MovieDoesNotExistsException("no movie with id " + id));
    }

    public List<MyMovieSimpleInfoDTO> findAllWatchedMovies() {
        MyUser user = auth.getCurrentUserAuthenticated();
        return watchedRepository.findAllMoviesByUser(user);
    }

    @Transactional
    public List<MyMovieSimpleInfoDTO> findAllPageable(int page) {
        if(page < 1) {
            page = 1;
        }
        return myMovieRepository.findAllSimpleInfo(PageRequest.of(page-1, pageSize));
    }

    @Transactional
    public List<MyMovieSimpleInfoDTO> findAllByParams(int page, MyMovieSearchDTO dto) {
        if(page < 1) {
            page = 1;
        }

        Specification<MyMovie> spec = Specification
                .where(MyMovieSpecification.hasTitle(dto.getTitle()))
                .and(MyMovieSpecification.hasReleaseDateFrom(dto.getFrom()))
                .and(MyMovieSpecification.hasReleaseDateTo(dto.getTo()))
                .and(MyMovieSpecification.hasGenres(dto.getGenres()));

        List<MyMovie> movies = myMovieRepository.findAll(spec, PageRequest.of(page - 1, pageSize)).getContent();

        return movies.stream()
                .map(m -> new MyMovieSimpleInfoDTO(m.getId(), m.getPosterPath(), m.getTitle(),
                        m.getVoteAverage(), m.getReleaseDate(), m.getVoteCount()))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<MyMovie> findAllFavoriteMoviesForUser(int page) {
        MyUser user = auth.getCurrentUserAuthenticated();
        return favoriteService.getAllFavoriteMoviesForUser(page);
    }
}
