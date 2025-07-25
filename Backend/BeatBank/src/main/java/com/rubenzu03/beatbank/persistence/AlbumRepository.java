package com.rubenzu03.beatbank.persistence;

import com.rubenzu03.beatbank.domain.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}
