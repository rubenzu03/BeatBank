package com.rubenzu03.beatbank.application.port.inbound;

import com.rubenzu03.beatbank.application.dto.ArtistDto;
import com.rubenzu03.beatbank.application.dto.ArtistPatchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArtistUseCase {
    Page<ArtistDto> getAllArtists(Pageable pageable);
    ArtistDto getArtistById(Long id);
    ArtistDto createArtist(ArtistDto artistDto);
    ArtistDto patchArtist(Long id, ArtistPatchDto patch);
    void deleteArtistById(Long id);
}
