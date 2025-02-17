package org.movier.controller;

import jakarta.validation.Valid;
import org.movier.model.dto.MyCommentDTO;
import org.movier.model.dto.MyCommentDeleteDTO;
import org.movier.model.entity.MyComment;
import org.movier.model.responce.MyCommentResponse;
import org.movier.service.MyCommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class MyCommentController {

    private final MyCommentService myCommentService;

    public MyCommentController(MyCommentService myCommentService) {
        this.myCommentService = myCommentService;
    }

    @PostMapping("/comment")
    public String addComment(@Valid @RequestBody MyCommentDTO dto) {
        return myCommentService.save(dto) ? "susses" : "fail";
    }

    @DeleteMapping
    public String deleteComment(@Valid @RequestBody MyCommentDeleteDTO dto) {
        return myCommentService.deleteComment(dto) ? "susses" : "fail";
    }

    @GetMapping("/comment")
    public List<MyCommentResponse> getAllCommentsForMovie(@RequestParam("movieId") Long movieId) {
        return myCommentService.findAllCommentsForMovie(movieId);
    }
}
