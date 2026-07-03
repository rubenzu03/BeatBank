package com.rubenzu03.beatbank.domain.port.outbound;

import com.rubenzu03.beatbank.domain.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    List<Genre> findAll();
    Page<Genre> findAll(Pageable pageable);
    Optional<Genre> findById(Long id);
    Genre save(Genre genre);
    void deleteById(Long id);
}
