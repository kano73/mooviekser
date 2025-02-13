package org.movier.controller;

import jakarta.validation.Valid;
import org.movier.model.dto.MyCommentDTO;
import org.movier.model.dto.MyCommentDeleteDTO;
import org.movier.service.MyCommentService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


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
}
