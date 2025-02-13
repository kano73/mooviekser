package org.movier.repository;

import org.movier.model.entity.MyGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MyGenreRepository extends JpaRepository<MyGenre, Integer> {
    @Query(value = "SELECT 1 FROM my_genre LIMIT 1", nativeQuery = true)
    Integer existsAnyGenre();

    @Transactional
    @Override
    <S extends MyGenre> List<S> saveAll(Iterable<S> entities);
}
