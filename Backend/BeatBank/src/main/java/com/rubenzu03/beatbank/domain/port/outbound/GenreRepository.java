package com.rubenzu03.beatbank.domain.port.outbound;

import com.rubenzu03.beatbank.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    List<Genre> findAll();
    Optional<Genre> findById(Long id);
    Genre save(Genre genre);
    void deleteById(Long id);
}
