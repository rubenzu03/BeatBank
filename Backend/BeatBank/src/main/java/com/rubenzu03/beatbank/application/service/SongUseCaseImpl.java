package com.rubenzu03.beatbank.application.service;

import com.rubenzu03.beatbank.application.dto.ArtistDto;
import com.rubenzu03.beatbank.application.dto.SongDto;
import com.rubenzu03.beatbank.application.exception.ResourceNotFoundException;
import com.rubenzu03.beatbank.application.port.inbound.SongUseCase;
import com.rubenzu03.beatbank.domain.Artist;
import com.rubenzu03.beatbank.domain.Song;
import com.rubenzu03.beatbank.domain.port.outbound.AlbumRepository;
import com.rubenzu03.beatbank.domain.port.outbound.ArtistRepository;
import com.rubenzu03.beatbank.domain.port.outbound.SongRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SongUseCaseImpl implements SongUseCase {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final DtoMapper mapper;

    public SongUseCaseImpl(SongRepository songRepository, ArtistRepository artistRepository, AlbumRepository albumRepository, DtoMapper mapper) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
        this.mapper = mapper;
    }

    @Override
    public List<SongDto> getAllSongs(){
        return mapper.toSongDtoList(songRepository.findAll());
    }

    @Override
    public SongDto getSongById(Long id) {
        Song song = songRepository.findSongById(id);
        if (song == null) {
            throw new ResourceNotFoundException("Song", id);
        }
        return mapper.toSongDto(song);
    }

    @Override
    public SongDto createSong(SongDto songDto){
        Song song = new Song(songDto);
        songRepository.save(song);
        return mapper.toSongDto(song);
    }

    @Override
    public SongDto updateSong(Long id, SongDto songDto) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song", id));
        song.updateSong(songDto);
        songRepository.save(song);
        return mapper.toSongDto(song);
    }

    @Override
    public void deleteSongById(Long id) {
        if (!songRepository.existsById(id)) {
            throw new ResourceNotFoundException("Song", id);
        }
        songRepository.deleteById(id);
    }

    @Override
    public SongDto addArtistToSong(Long id, ArtistDto artistDto){
        Song song = songRepository.findSongById(id);
        Artist artist = new Artist(artistDto);
        artist.addSong(song);
        song.addArtist(artistDto);
        songRepository.save(song);
        return mapper.toSongDto(song);
    }

    @Override
    public void deleteArtistFromSong(Long songId, Long artistId) {
        Song song = songRepository.findSongById(songId);
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", artistId));

        if (song.getArtists().contains(artist)) {
            song.getArtists().remove(artist);
            artist.getSongs().remove(song);
            songRepository.save(song);
            artistRepository.save(artist);
        } else {
            throw new IllegalArgumentException("Artist not associated with this song");
        }
    }
}
