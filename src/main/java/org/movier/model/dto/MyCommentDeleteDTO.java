package org.movier.model.dto;

import jakarta.validation.constraints.NotNull;

public class MyCommentDeleteDTO {
    @NotNull
    private Long commentId;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }
}
