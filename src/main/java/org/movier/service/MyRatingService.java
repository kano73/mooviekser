package org.movier.service;

import jakarta.validation.Valid;
import org.movier.config.security.AuthenticatedMyUserService;
import org.movier.exceptions.MovieDoesNotExistsException;
import org.movier.model.dto.MyRatingDTO;
import org.movier.model.entity.MyMovie;
import org.movier.model.entity.MyRating;
import org.movier.model.entity.MyUser;
import org.movier.repository.MyMovieRepository;
import org.movier.repository.MyRatingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyRatingService {

    private final MyRatingRepository myRatingRepository;
    private final AuthenticatedMyUserService auth;
    private final MyMovieRepository myMovieRepository;

    public MyRatingService(MyRatingRepository myRatingRepository,
                           AuthenticatedMyUserService auth,
                           MyMovieRepository myMovieRepository) {
        this.myRatingRepository = myRatingRepository;
        this.auth = auth;
        this.myMovieRepository = myMovieRepository;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public boolean save(@Valid MyRatingDTO rateDto) {
        MyUser user = auth.getCurrentUserAuthenticated();

        MyMovie movie = myMovieRepository.findById(rateDto.getMovieId())
                .orElseThrow(()->new MovieDoesNotExistsException("no movie found with id: "+rateDto.getMovieId()));

        Float oldRating = myRatingRepository.findFirstValueByAuthor_IdAndMovie_Id(user.getId(), movie.getId())
                .orElse(null);

        if(oldRating != null) {
            System.out.println("deleting");
            myRatingRepository.deleteByAuthor_IdAndMovie_Id(user.getId(), movie.getId());
            adjustRatingUpdate(movie, rateDto, oldRating);
        }else{
            adjustRatingAdd(movie, rateDto);
        }

        MyRating rating = new MyRating();
        rating.setAuthor(user);
        rating.setMovie(movie);
        rating.setValue(rateDto.getRating());

        myMovieRepository.updateVoteById(movie.getVoteCount(), movie.getVoteAverage(), movie.getId());
        myRatingRepository.save(rating);

        return true;
    }

    private void adjustRatingAdd(MyMovie movie, MyRatingDTO dto) {
        movie.setVoteAverage( ( (movie.getVoteAverage()* movie.getVoteCount() ) + dto.getRating() )/
                                                (movie.getVoteCount()+1));
        movie.setVoteCount(movie.getVoteCount()+1);
    }

    private void adjustRatingUpdate(MyMovie movie, MyRatingDTO dto, Float oldRating) {
        movie.setVoteAverage(((movie.getVoteAverage()* movie.getVoteCount())-oldRating+dto.getRating())
                / movie.getVoteCount());
    }

    public Float getValueByUser_IdAndMovie_Id(Long userId, Long movieId) {
        return myRatingRepository.findFirstValueByAuthor_IdAndMovie_Id(userId, movieId)
                .orElse(null);
    }
}
