package org.movier.repository.native_repo;

import org.movier.model.dto.MyMovieSearchDTO;
import org.movier.model.responce.MyMovieSimpleInfoDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class MyMovieRepositoryJdbc {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    public MyMovieRepositoryJdbc(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public List<MyMovieSimpleInfoDTO> findAllByFiltersSimpleInfo(MyMovieSearchDTO dto, PageRequest pageable) {
//        StringBuilder sql = new StringBuilder("""
//            SELECT m.id, m.icon_url AS posterPath, m.title, m.vote_average, m.release_date, m.vote_count
//            FROM my_movie m
//            LEFT JOIN movie_genre mg ON m.id = mg.id_movie
//            LEFT JOIN my_genre g ON mg.id_genre = g.id
//            WHERE (:title IS NULL OR LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%')))
//            AND (:from IS NULL OR m.release_date >= :from)
//            AND (:to IS NULL OR m.release_date <= :to)
//            AND (:genres IS NULL OR g.id IN (:genres))
//            GROUP BY m.id, m.icon_url, m.title, m.vote_average, m.release_date, m.vote_count
//            LIMIT :limit OFFSET :offset
//        """);
//
//        MapSqlParameterSource params = new MapSqlParameterSource();
//        params.addValue("title", dto.getTitle() != null ? "%" + dto.getTitle() + "%" : null, Types.VARCHAR);
//        params.addValue("from", dto.getFrom(), Types.DATE);
//        params.addValue("to", dto.getTo(), Types.DATE);
//        params.addValue("genres", dto.getGenres() != null && !dto.getGenres().isEmpty() ? dto.getGenres() : null, Types.ARRAY);
//        params.addValue("limit", pageable.getPageSize(), Types.INTEGER);
//        params.addValue("offset", pageable.getOffset(), Types.INTEGER);
//
//        return jdbcTemplate.query(sql, params, (rs, rowNum) ->
//                new MyMovieSimpleInfoDTO(
//                        rs.getLong("id"),
//                        rs.getString("posterPath"),
//                        rs.getString("title"),
//                        rs.getFloat("vote_average"),
//                        rs.getDate("release_date").toLocalDate(),
//                        rs.getInt("vote_count")
//                )
//        );
//    }
}
