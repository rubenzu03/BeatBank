package com.rubenzu03.beatbank.domain.port.outbound;

import com.rubenzu03.beatbank.domain.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SongRepository {
    List<Song> findAll();
    Page<Song> findAll(Pageable pageable);
    Optional<Song> findById(Long id);
    Song findSongById(Long id);
    Page<Song> searchSongs(String query, Pageable pageable);
    Song save(Song song);
    void deleteById(Long id);
    boolean existsById(Long id);
}
