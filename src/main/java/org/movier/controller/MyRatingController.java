package org.movier.controller;

import jakarta.validation.Valid;
import org.movier.model.dto.MyRateDTO;
import org.movier.model.dto.WatchedDTO;
import org.movier.service.MyRatingService;
import org.movier.service.WatchedService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRatingController {


    private final WatchedService watchedService;

    public MyRatingController(WatchedService watchedService) {
        this.watchedService = watchedService;
    }

    @PostMapping("/rate")
    public String rate(@Valid @RequestBody WatchedDTO dto) {
        return watchedService.save(dto)? "success" : "fail";
    }
}
