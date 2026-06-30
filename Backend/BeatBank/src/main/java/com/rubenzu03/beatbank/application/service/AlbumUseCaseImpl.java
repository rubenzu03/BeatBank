package com.rubenzu03.beatbank.application.service;

import com.rubenzu03.beatbank.application.dto.AlbumDto;
import com.rubenzu03.beatbank.application.dto.SongDto;
import com.rubenzu03.beatbank.application.exception.ResourceNotFoundException;
import com.rubenzu03.beatbank.application.port.inbound.AlbumUseCase;
import com.rubenzu03.beatbank.domain.Album;
import com.rubenzu03.beatbank.domain.Song;
import com.rubenzu03.beatbank.domain.port.outbound.AlbumRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumUseCaseImpl implements AlbumUseCase {

    private final AlbumRepository albumRepository;
    private final DtoMapper mapper;

    public AlbumUseCaseImpl(AlbumRepository albumRepository, DtoMapper mapper) {
        this.albumRepository = albumRepository;
        this.mapper = mapper;
    }

    @Override
    public List<AlbumDto> getAllAlbums(){
        return mapper.toAlbumDtoList(albumRepository.findAll());
    }

    @Override
    public AlbumDto getAlbumById(Long id) {
        return albumRepository.findById(id)
                .map(mapper::toAlbumDto)
                .orElseThrow(() -> new ResourceNotFoundException("Album", id));
    }

    @Override
    public AlbumDto createAlbum(AlbumDto albumDto) {
        Album album = new Album(albumDto);
        albumRepository.save(album);
        return mapper.toAlbumDto(album);
    }

    @Override
    public AlbumDto updateAlbum(Long id, AlbumDto albumDto) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Album", id));
        album.updateAlbum(albumDto);
        albumRepository.save(album);
        return mapper.toAlbumDto(album);
    }

    @Override
    public AlbumDto addSongToAlbum(Long id, SongDto songDto) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Album", id));
        Song newSong = new Song(songDto);
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
