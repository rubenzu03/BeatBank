package com.rubenzu03.beatbank.application.port.inbound;

import com.rubenzu03.beatbank.application.dto.ArtistDto;
import com.rubenzu03.beatbank.application.dto.SongDto;
import com.rubenzu03.beatbank.application.dto.SongPatchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SongUseCase {
    Page<SongDto> getAllSongs(Pageable pageable);
    SongDto getSongById(Long id);
    SongDto createSong(SongDto songDto);
    SongDto updateSong(Long id, SongDto songDto);
    SongDto patchSong(Long id, SongPatchDto patch);
    void deleteSongById(Long id);
    SongDto addArtistToSong(Long id, ArtistDto artistDto);
    void deleteArtistFromSong(Long songId, Long artistId);
}
