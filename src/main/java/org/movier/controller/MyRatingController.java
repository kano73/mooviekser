package org.movier.controller;

import jakarta.validation.Valid;
import org.movier.model.dto.MyRatingDTO;
import org.movier.service.MyRatingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRatingController {
    private final MyRatingService myRatingService;

    public MyRatingController(MyRatingService myRatingService) {
        this.myRatingService = myRatingService;
    }

    @PostMapping("/rate")
    public String rate(@Valid @RequestBody MyRatingDTO dto) {
        return myRatingService.saveRating(dto) ? "success" : "fail";
    }
}
