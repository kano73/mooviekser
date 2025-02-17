package org.movier.model.responce;


import java.time.LocalDateTime;

public class MyCommentResponse {

    private String text;
    private String authorName;
    private Long authorId;
    private LocalDateTime date;

    public MyCommentResponse() {
    }

    public MyCommentResponse(String text, String authorName, Long authorId, LocalDateTime date) {
        this.text = text;
        this.authorName = authorName;
        this.authorId = authorId;
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
