package com.rubenzu03.beatbank.adapter.outbound.persistence;

import com.rubenzu03.beatbank.domain.Song;
import com.rubenzu03.beatbank.domain.port.outbound.SongRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataSongRepository extends JpaRepository<Song, Long>, SongRepository {
    Song findSongById(Long id);
}
