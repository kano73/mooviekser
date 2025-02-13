package org.movier.controller;

import jakarta.validation.Valid;
import org.movier.model.dto.FavoriteDTO;
import org.movier.service.FavoriteService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("/favorite")
    public String addToFavorite(@Valid @RequestBody FavoriteDTO dto) {
        return favoriteService.addToFavorite(dto) ? "Added" : "Not added";
    }

    @DeleteMapping("/favorite")
    public String removeFromFavorite(@Valid @RequestBody FavoriteDTO dto) {
        return favoriteService.removeFromFavorite(dto) ? "Removed" : "Not removed";
    }
}
