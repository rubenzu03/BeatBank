package com.rubenzu03.beatbank.application.port.inbound;

import com.rubenzu03.beatbank.application.dto.ArtistDto;
import com.rubenzu03.beatbank.application.dto.ArtistPatchDto;

import java.util.List;

public interface ArtistUseCase {
    List<ArtistDto> getAllArtists();
    ArtistDto getArtistById(Long id);
    ArtistDto createArtist(ArtistDto artistDto);
    ArtistDto patchArtist(Long id, ArtistPatchDto patch);
    void deleteArtistById(Long id);
}
