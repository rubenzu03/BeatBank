package com.rubenzu03.beatbank.application.port.inbound;

import com.rubenzu03.beatbank.application.dto.AlbumDto;
import com.rubenzu03.beatbank.application.dto.SongDto;

import java.util.List;

public interface AlbumUseCase {
    List<AlbumDto> getAllAlbums();
    AlbumDto getAlbumById(Long id);
    AlbumDto createAlbum(AlbumDto albumDto);
    AlbumDto updateAlbum(Long id, AlbumDto albumDto);
    AlbumDto addSongToAlbum(Long id, SongDto songDto);
}
