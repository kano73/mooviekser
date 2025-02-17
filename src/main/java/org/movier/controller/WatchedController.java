package org.movier.controller;

import jakarta.validation.Valid;
import org.movier.model.dto.WatchedDTO;
import org.movier.model.responce.MyMovieSimpleInfoDTO;
import org.movier.service.MyMovieService;
import org.movier.service.WatchedService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WatchedController {

    private final WatchedService watchedService;
    private final MyMovieService myMovieService;

    public WatchedController(WatchedService watchedService, MyMovieService myMovieService) {
        this.watchedService = watchedService;
        this.myMovieService = myMovieService;
    }

    @PostMapping("/watched")
    public String addToWatched(@Valid @RequestParam("movieId") Long movieId) {
        return watchedService.saveOrDelete(movieId) ? "susses": "fail";
    }

    @GetMapping("/watched")
    public List<MyMovieSimpleInfoDTO> getWatchedMovies() {
        return myMovieService.findAllWatchedMovies();
    }
}
