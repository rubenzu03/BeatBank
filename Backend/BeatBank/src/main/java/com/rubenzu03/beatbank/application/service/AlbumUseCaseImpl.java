package com.rubenzu03.beatbank.application.service;

import com.rubenzu03.beatbank.application.dto.AlbumDto;
import com.rubenzu03.beatbank.application.dto.AlbumPatchDto;
import com.rubenzu03.beatbank.application.dto.SongDto;
import com.rubenzu03.beatbank.application.exception.ResourceNotFoundException;
import com.rubenzu03.beatbank.application.port.inbound.AlbumUseCase;
import com.rubenzu03.beatbank.application.port.outbound.DtoMapper;
import com.rubenzu03.beatbank.domain.Album;
import com.rubenzu03.beatbank.domain.Song;
import com.rubenzu03.beatbank.domain.port.outbound.AlbumRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AlbumUseCaseImpl implements AlbumUseCase {

    private final AlbumRepository albumRepository;
    private final DtoMapper mapper;

    public AlbumUseCaseImpl(AlbumRepository albumRepository, DtoMapper mapper) {
        this.albumRepository = albumRepository;
        this.mapper = mapper;
    }

    @Override
    public Page<AlbumDto> getAllAlbums(Pageable pageable){
        return albumRepository.findAll(pageable).map(mapper::toAlbumDto);
    }

    @Override
    public AlbumDto getAlbumById(Long id) {
        return albumRepository.findById(id)
                .map(mapper::toAlbumDto)
                .orElseThrow(() -> new ResourceNotFoundException("Album", id));
    }

    @Override
    public AlbumDto createAlbum(AlbumDto albumDto) {
        Album album = new Album(albumDto.name(), albumDto.releaseDate(), albumDto.coverImageUrl(), albumDto.description(), albumDto.genre());
        albumRepository.save(album);
        return mapper.toAlbumDto(album);
    }

    @Override
    public AlbumDto updateAlbum(Long id, AlbumDto albumDto) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Album", id));
        album.updateAlbum(albumDto.name(), albumDto.releaseDate(), albumDto.coverImageUrl(), albumDto.description(), albumDto.genre());
        albumRepository.save(album);
        return mapper.toAlbumDto(album);
    }

    @Override
    public AlbumDto patchAlbum(Long id, AlbumPatchDto patch) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Album", id));
        if (patch.name() != null) album.setName(patch.name());
        if (patch.releaseDate() != null) album.setReleaseDate(patch.releaseDate());
        if (patch.coverImageUrl() != null) album.setCoverImageUrl(patch.coverImageUrl());
        if (patch.description() != null) album.setDescription(patch.description());
        albumRepository.save(album);
        return mapper.toAlbumDto(album);
    }

    @Override
    public AlbumDto addSongToAlbum(Long id, SongDto songDto) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Album", id));
        Song newSong = new Song(songDto.name(), songDto.duration(), songDto.plays());
        album.addSong(newSong);
        albumRepository.save(album);
        return mapper.toAlbumDto(album);
    }

    @Override
    public void deleteAlbumById(Long id) {
        if (!albumRepository.existsById(id)) {
            throw new ResourceNotFoundException("Album", id);
        }
        albumRepository.deleteById(id);
    }
}
