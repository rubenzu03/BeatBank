package com.rubenzu03.beatbank.domain.port.outbound;

import com.rubenzu03.beatbank.domain.Album;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository {
    List<Album> findAll();
    Optional<Album> findById(Long id);
    Album save(Album album);
}
