package com.rubenzu03.beatbank.persistence;

import com.rubenzu03.beatbank.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
