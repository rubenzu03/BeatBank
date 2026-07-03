package com.rubenzu03.beatbank.adapter.outbound.persistence;

import com.rubenzu03.beatbank.domain.Genre;
import com.rubenzu03.beatbank.domain.port.outbound.GenreRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataGenreRepository extends JpaRepository<Genre, Long>, GenreRepository {
}
