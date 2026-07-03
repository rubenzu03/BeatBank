package com.rubenzu03.beatbank.adapter.outbound.persistence;

import com.rubenzu03.beatbank.domain.Song;
import com.rubenzu03.beatbank.domain.port.outbound.SongRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataSongRepository extends JpaRepository<Song, Long>, SongRepository {
    Song findSongById(Long id);

    @Query("SELECT s FROM songs s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Song> searchSongs(@Param("query") String query, Pageable pageable);
}
