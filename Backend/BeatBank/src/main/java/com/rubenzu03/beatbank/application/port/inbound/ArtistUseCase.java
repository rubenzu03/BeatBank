package com.rubenzu03.beatbank.application.port.inbound;

import com.rubenzu03.beatbank.application.dto.ArtistDto;

import java.util.List;

public interface ArtistUseCase {
    List<ArtistDto> getAllArtists();
    ArtistDto getArtistById(Long id);
    ArtistDto createArtist(ArtistDto artistDto);
    void deleteArtistById(Long id);
}
