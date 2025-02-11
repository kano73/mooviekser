package org.movier.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="my_comment")
public class MyComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="text", nullable = false)
    private String text;

    @Column(name="timestamp", nullable = false)
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private MyUser author;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private MyMovie movie;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MyComment myComment = (MyComment) o;
        return id == myComment.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
