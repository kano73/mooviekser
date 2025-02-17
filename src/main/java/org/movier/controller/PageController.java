package org.movier.controller;

import jakarta.validation.constraints.NotNull;
import org.movier.service.PageBuilderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {
    private final PageBuilderService pageBuilderService;

    public PageController(PageBuilderService pageBuilderService) {
        this.pageBuilderService = pageBuilderService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {return "register";}


    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @GetMapping("/change_profile")
    public String change_profile() {
        return "change_profile";
    }

    @GetMapping("/admin_panel")
    public String adminPanel() {
        return "admin_panel";
    }

    @GetMapping("/search")
    public String movies(Model model) {
        pageBuilderService.buildMovies(model);
        return "search";
    }

    @GetMapping("/movie")
    public String getMovie(@NotNull @RequestParam("id") Long id, Model model) {
        pageBuilderService.buildMovieById(model,id);
        return "movie";
    }

    @GetMapping("/watched")
    public String getWatched(){
        return "watched";
    }
}
