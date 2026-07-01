package com.rubenzu03.beatbank.application.port.inbound;

import com.rubenzu03.beatbank.application.dto.AlbumDto;
import com.rubenzu03.beatbank.application.dto.AlbumPatchDto;
import com.rubenzu03.beatbank.application.dto.SongDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AlbumUseCase {
    Page<AlbumDto> getAllAlbums(Pageable pageable);
    AlbumDto getAlbumById(Long id);
    AlbumDto createAlbum(AlbumDto albumDto);
    AlbumDto updateAlbum(Long id, AlbumDto albumDto);
    AlbumDto patchAlbum(Long id, AlbumPatchDto patch);
    AlbumDto addSongToAlbum(Long id, SongDto songDto);
    void deleteAlbumById(Long id);
}
