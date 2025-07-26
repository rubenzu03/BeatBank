package com.rubenzu03.beatbank.persistence;

import com.rubenzu03.beatbank.domain.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {
    Song findSongById(Long id);
}
