package org.movier.controller;

import jakarta.validation.Valid;
import org.movier.model.dto.WatchedDTO;
import org.movier.service.WatchedService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WatchedController {

    private final WatchedService watchedService;

    public WatchedController(WatchedService watchedService) {
        this.watchedService = watchedService;
    }

    @PostMapping("/watched")
    public String addToWatched(@Valid @RequestBody WatchedDTO watchedDTO) {
        return watchedService.save(watchedDTO) ? "susses": "fail";
    }

    @DeleteMapping("/watched")
    public String removeFromWatched(@Valid @RequestBody WatchedDTO watchedDTO) {
        return watchedService.remove(watchedDTO) ? "susses" : "fail";
    }
}
