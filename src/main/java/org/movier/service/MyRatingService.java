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

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

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

    public MyMovie adjustRatingParams(MyMovie movie) {
        List<Float> values = myRatingRepository.findAllRatingsByMovieId(movie.getId());

        float numOfValues = (float) values.size();
        float numOfVotes = (float) movie.getVote_count();

        Float finalNumOfValues = numOfValues;
        Float avgValues = values.stream().reduce(Float::sum)
                .map(num->num/ finalNumOfValues).orElse(0.0f);

        Float avgVotes = movie.getVote_average();

        movie.setVote_count(movie.getVote_count() + (int) numOfValues);

        if(numOfValues>numOfVotes){
            numOfValues = numOfValues/numOfVotes;
            numOfVotes = 1;

        }else if(numOfValues<numOfVotes){
            numOfVotes = numOfVotes/numOfValues;
            numOfValues = 1;
        }else {
            movie.setVote_average((avgValues+avgVotes)/2);
            return movie;
        }
        movie.setVote_average((numOfValues*avgValues+numOfVotes*avgVotes)/numOfVotes+numOfValues);
        return movie;
    }
}
