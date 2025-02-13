package org.movier.service;

import jakarta.validation.Valid;
import org.movier.config.security.AuthenticatedMyUserService;
import org.movier.exceptions.MovieDoesNotExistsException;
import org.movier.model.dto.MyRateDTO;
import org.movier.model.entity.MyMovie;
import org.movier.model.entity.MyRating;
import org.movier.model.entity.MyUser;
import org.movier.repository.MyMovieRepository;
import org.movier.repository.MyRatingRepository;
import org.springframework.stereotype.Service;

@Service
public class MyRatingService {

    private final MyRatingRepository myRatingRepository;
    private final AuthenticatedMyUserService auth;
    private final MyMovieRepository myMovieRepository;

    public MyRatingService(MyRatingRepository myRatingRepository, AuthenticatedMyUserService auth, MyMovieRepository myMovieRepository) {
        this.myRatingRepository = myRatingRepository;
        this.auth = auth;
        this.myMovieRepository = myMovieRepository;
    }

    public boolean save(@Valid MyRateDTO dto) {
        MyUser user = auth.getCurrentUserAuthenticated();

        if(!myMovieRepository.existsById(dto.getMovieId())){
            throw new MovieDoesNotExistsException("no movie found with id: "+dto.getMovieId());
        }

        MyMovie movie = new MyMovie();
        movie.setId(dto.getMovieId());

        MyRating rating = new MyRating();
        rating.setAuthor(user);
        rating.setMovie(movie);
        rating.setValue(dto.getRate());

        myRatingRepository.save(rating);

        return true;
    }
}
