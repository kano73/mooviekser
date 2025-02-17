package org.movier.repository.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.movier.model.entity.MyGenre;
import org.movier.model.entity.MyMovie;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

public class MyMovieSpecification {

    public static Specification<MyMovie> hasTitle(String title) {
        return (root, query, criteriaBuilder) ->
                title == null || title.isEmpty()
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<MyMovie> hasReleaseDateFrom(LocalDate from) {
        return (root, query, criteriaBuilder) ->
                from == null
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.greaterThanOrEqualTo(root.get("releaseDate"), from);
    }

    public static Specification<MyMovie> hasReleaseDateTo(LocalDate to) {
        return (root, query, criteriaBuilder) ->
                to == null
                        ? criteriaBuilder.conjunction()
                        : criteriaBuilder.lessThanOrEqualTo(root.get("releaseDate"), to);
    }

    public static Specification<MyMovie> hasGenres(List<Long> genreIds) {
        return (root, query, criteriaBuilder) -> {
            if (genreIds == null || genreIds.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            Join<MyMovie, MyGenre> genresJoin = root.join("genres", JoinType.LEFT);
            assert query != null;
            query.distinct(true);
            return genresJoin.get("id").in(genreIds);
        };
    }
}


