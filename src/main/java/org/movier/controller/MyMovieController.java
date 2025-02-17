package org.movier.controller;

import jakarta.validation.constraints.NotNull;
import org.movier.model.dto.MyMovieSearchDTO;
import org.movier.model.responce.MyMovieSimpleInfoDTO;
import org.movier.service.MyMovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MyMovieController {

    private final MyMovieService myMovieService;

    public MyMovieController(MyMovieService myMovieService) {
        this.myMovieService = myMovieService;
    }

    @PostMapping("/allMovies")
    public List<MyMovieSimpleInfoDTO> allMovies(@NotNull @RequestParam("page") Integer page) {
        return myMovieService.findAllPageable(page);
    }

    @PostMapping("/movies")
    public List<MyMovieSimpleInfoDTO> allMoviesFilter(@NotNull @RequestParam("page") Integer page,
                                                      @RequestBody MyMovieSearchDTO filter) {
        return myMovieService.findAllByParams(page, filter);
    }
}
