package com.rubenzu03.beatbank.adapter.outbound.persistence;

import com.rubenzu03.beatbank.domain.Artist;
import com.rubenzu03.beatbank.domain.port.outbound.ArtistRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataArtistRepository extends JpaRepository<Artist, Long>, ArtistRepository {
}
