package org.movier.service;

import jakarta.validation.Valid;
import org.movier.config.security.AuthenticatedMyUserService;
import org.movier.exceptions.CommentNotFoundException;
import org.movier.exceptions.MovieDoesNotExistsException;
import org.movier.exceptions.UserDontHaveRightsForActionException;
import org.movier.model.dto.MyCommentDTO;
import org.movier.model.dto.MyCommentDeleteDTO;
import org.movier.model.entity.MyComment;
import org.movier.model.entity.MyMovie;
import org.movier.model.entity.MyUser;
import org.movier.model.enums.RoleEnum;
import org.movier.model.responce.MyCommentResponse;
import org.movier.repository.MyCommentRepository;
import org.movier.repository.MyMovieRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MyCommentService {
    private final MyCommentRepository myCommentRepository;
    private final AuthenticatedMyUserService auth;
    private final MyMovieRepository myMovieRepository;

    public MyCommentService(MyCommentRepository myCommentRepository, AuthenticatedMyUserService auth, MyMovieRepository myMovieRepository) {
        this.myCommentRepository = myCommentRepository;
        this.auth = auth;
        this.myMovieRepository = myMovieRepository;
    }

    public boolean save(@Valid MyCommentDTO dto) {
        MyUser user = auth.getCurrentUserAuthenticated();

        if(!myMovieRepository.existsById(dto.getMovieId())){
            throw new MovieDoesNotExistsException("no movie found with id: "+dto.getMovieId());
        }

        MyMovie movie = new MyMovie();
        movie.setId(dto.getMovieId());

        MyComment myComment = new MyComment();
        myComment.setText(dto.getText());
        myComment.setAuthor(user);
        myComment.setMovie(movie);
        myComment.setTimestamp(LocalDateTime.now());

        myCommentRepository.save(myComment);

        return true;
    }

    public boolean deleteComment(@Valid Long commentId) {
        MyUser user = auth.getCurrentUserAuthenticated();
        MyComment comment = myCommentRepository.findById(commentId)
                .orElseThrow(()->new CommentNotFoundException("No comment found with id:" + commentId));

        if(comment.getAuthor().equals(user) || user.getRole() == RoleEnum.ADMIN){
            myCommentRepository.deleteById(comment.getId());
            return true;
        } else{
            throw new UserDontHaveRightsForActionException("You dont have rights to delete this comment");
        }
    }

    public List<MyCommentResponse> findAllCommentsForMovie(@Valid Long movieId) {
        if(!myMovieRepository.existsById(movieId)){
            throw new MovieDoesNotExistsException("no movie found with id: "+movieId);
        }
        return myCommentRepository.findAllByMoviePublicInfo(movieId);
    }
}
