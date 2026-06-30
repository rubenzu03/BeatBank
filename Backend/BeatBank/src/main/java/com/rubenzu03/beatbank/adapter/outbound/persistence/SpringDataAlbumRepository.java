package com.rubenzu03.beatbank.adapter.outbound.persistence;

import com.rubenzu03.beatbank.domain.Album;
import com.rubenzu03.beatbank.domain.port.outbound.AlbumRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataAlbumRepository extends JpaRepository<Album, Long>, AlbumRepository {
}
