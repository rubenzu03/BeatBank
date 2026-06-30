package com.rubenzu03.beatbank.application.port.inbound;

import com.rubenzu03.beatbank.application.dto.ArtistDto;
import com.rubenzu03.beatbank.application.dto.SongDto;

import java.util.List;

public interface SongUseCase {
    List<SongDto> getAllSongs();
    SongDto getSongById(Long id);
    SongDto createSong(SongDto songDto);
    SongDto updateSong(Long id, SongDto songDto);
    void deleteSongById(Long id);
    SongDto addArtistToSong(Long id, ArtistDto artistDto);
    void deleteArtistFromSong(Long songId, Long artistId);
}
