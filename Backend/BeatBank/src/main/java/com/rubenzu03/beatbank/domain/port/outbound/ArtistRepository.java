package com.rubenzu03.beatbank.domain.port.outbound;

import com.rubenzu03.beatbank.domain.Artist;

import java.util.List;
import java.util.Optional;

public interface ArtistRepository {
    List<Artist> findAll();
    Optional<Artist> findById(Long id);
    Artist save(Artist artist);
    void deleteById(Long id);
    boolean existsById(Long id);
}
