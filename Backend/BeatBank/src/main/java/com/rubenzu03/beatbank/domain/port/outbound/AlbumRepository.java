package com.rubenzu03.beatbank.domain.port.outbound;

import com.rubenzu03.beatbank.domain.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository {
    List<Album> findAll();
    Page<Album> findAll(Pageable pageable);
    Optional<Album> findById(Long id);
    Album save(Album album);
    void deleteById(Long id);
    boolean existsById(Long id);
}
