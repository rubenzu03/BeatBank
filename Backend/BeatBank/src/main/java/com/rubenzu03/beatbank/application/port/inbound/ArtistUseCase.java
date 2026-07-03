package com.rubenzu03.beatbank.application.port.inbound;

import com.rubenzu03.beatbank.application.dto.ArtistDto;
import com.rubenzu03.beatbank.application.dto.ArtistPatchDto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArtistUseCase {
    Page<ArtistDto> getAllArtists(Pageable pageable);
    ArtistDto getArtistById(Long id);
    @Transactional
    ArtistDto createArtist(ArtistDto artistDto);
    @Transactional
    ArtistDto patchArtist(Long id, ArtistPatchDto patch);
    @Transactional
    void deleteArtistById(Long id);
}
