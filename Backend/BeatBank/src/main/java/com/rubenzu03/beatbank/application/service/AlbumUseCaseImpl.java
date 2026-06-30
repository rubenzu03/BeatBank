package com.rubenzu03.beatbank.application.service;

import com.rubenzu03.beatbank.application.dto.AlbumDto;
import com.rubenzu03.beatbank.application.dto.SongDto;
import com.rubenzu03.beatbank.application.port.inbound.AlbumUseCase;
import com.rubenzu03.beatbank.domain.Album;
import com.rubenzu03.beatbank.domain.Song;
import com.rubenzu03.beatbank.domain.port.outbound.AlbumRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlbumUseCaseImpl implements AlbumUseCase {

    private final AlbumRepository albumRepository;

    public AlbumUseCaseImpl(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    public List<AlbumDto> getAllAlbums(){
        return albumRepository.findAll().stream().map(AlbumDto::new).collect(Collectors.toList());
    }

    @Override
    public AlbumDto getAlbumById(Long id) {
        return albumRepository.findById(id)
                .map(AlbumDto::new)
                .orElse(null);
    }

    @Override
    public AlbumDto createAlbum(AlbumDto albumDto) {
        Album album = new Album(albumDto);
        albumRepository.save(album);
        return new AlbumDto(album);
    }

    @Override
    public AlbumDto updateAlbum(Long id, AlbumDto albumDto) {
        Album album = albumRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Album not found"));
        album.updateAlbum(albumDto);
        albumRepository.save(album);
        return new AlbumDto(album);
    }

    @Override
    public AlbumDto addSongToAlbum(Long id, SongDto songDto) {
        Album album = albumRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Album not found"));
        Song newSong = new Song(songDto);
        album.addSong(newSong);
        albumRepository.save(album);
        return new AlbumDto(album);
    }
}
