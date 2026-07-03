package com.rubenzu03.beatbank.application.port.inbound;

import com.rubenzu03.beatbank.application.dto.ArtistDto;
import com.rubenzu03.beatbank.application.dto.SongDto;
import com.rubenzu03.beatbank.application.dto.SongPatchDto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SongUseCase {
    Page<SongDto> getAllSongs(Pageable pageable);
    SongDto getSongById(Long id);
    @Transactional
    SongDto createSong(SongDto songDto);
    @Transactional
    SongDto updateSong(Long id, SongDto songDto);
    @Transactional
    SongDto patchSong(Long id, SongPatchDto patch);
    void deleteSongById(Long id);
    @Transactional
    SongDto addArtistToSong(Long id, ArtistDto artistDto);
    @Transactional
    void deleteArtistFromSong(Long songId, Long artistId);

    Page<SongDto> searchSongs(String query, Pageable pageable);

    @Transactional
    SongDto incrementPlays(Long id);
}
