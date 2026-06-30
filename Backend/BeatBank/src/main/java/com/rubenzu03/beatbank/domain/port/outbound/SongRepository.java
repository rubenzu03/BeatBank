package com.rubenzu03.beatbank.domain.port.outbound;

import com.rubenzu03.beatbank.domain.Song;

import java.util.List;
import java.util.Optional;

public interface SongRepository {
    List<Song> findAll();
    Optional<Song> findById(Long id);
    Song findSongById(Long id);
    Song save(Song song);
    void deleteById(Long id);
    boolean existsById(Long id);
}
