package com.rubenzu03.beatbank.domain.port.outbound;

import com.rubenzu03.beatbank.domain.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ArtistRepository {
    List<Artist> findAll();
    Page<Artist> findAll(Pageable pageable);
    Optional<Artist> findById(Long id);
    Artist save(Artist artist);
    void deleteById(Long id);
    boolean existsById(Long id);
}
