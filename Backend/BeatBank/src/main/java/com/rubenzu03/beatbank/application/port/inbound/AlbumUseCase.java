package com.rubenzu03.beatbank.application.port.inbound;

import com.rubenzu03.beatbank.application.dto.AlbumDto;
import com.rubenzu03.beatbank.application.dto.AlbumPatchDto;
import com.rubenzu03.beatbank.application.dto.SongDto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AlbumUseCase {
    Page<AlbumDto> getAllAlbums(Pageable pageable);
    AlbumDto getAlbumById(Long id);
    @Transactional
    AlbumDto createAlbum(AlbumDto albumDto);
    @Transactional
    AlbumDto updateAlbum(Long id, AlbumDto albumDto);
    @Transactional
    AlbumDto patchAlbum(Long id, AlbumPatchDto patch);
    @Transactional
    AlbumDto addSongToAlbum(Long id, SongDto songDto);
    @Transactional
    void deleteAlbumById(Long id);

    @Transactional
    AlbumDto updateAlbumCover(Long id, String coverImageUrl);
}
