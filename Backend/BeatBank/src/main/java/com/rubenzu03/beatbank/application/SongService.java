package com.rubenzu03.beatbank.application;

import com.rubenzu03.beatbank.application.dto.ArtistDto;
import com.rubenzu03.beatbank.application.dto.SongDto;
import com.rubenzu03.beatbank.domain.Artist;
import com.rubenzu03.beatbank.domain.Song;
import com.rubenzu03.beatbank.persistence.AlbumRepository;
import com.rubenzu03.beatbank.persistence.ArtistRepository;
import com.rubenzu03.beatbank.persistence.SongRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongService {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;

    public SongService(SongRepository songRepository, ArtistRepository artistRepository, AlbumRepository albumRepository) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
    }

    public List<SongDto> getAllSongs(){
        return songRepository.findAll().stream().map(SongDto::new).collect(Collectors.toList());
    }

    public SongDto getSongById(Long id) {
        Song song = songRepository.findSongById(id);
        if (song == null) {
            return null;
        }
        return new SongDto(song);
    }

    public SongDto createSong(SongDto songDto){
        Song song = new Song(songDto);
        songRepository.save(song);
        return new SongDto(song);
    }

    public SongDto updateSong(Long id, SongDto songDto) {
        Song song = songRepository.findById(id).get();
        song.updateSong(songDto);
        songRepository.save(song);
        return new SongDto(song);
    }

    public void deleteSongById(Long id) {
        if (songRepository.existsById(id)) {
            songRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Song with id " + id + " does not exist.");
        }
    }

    public SongDto addArtistToSong(Long id, @RequestBody ArtistDto artistDto){
        Song song = songRepository.findSongById(id);
        Artist artist = new Artist(artistDto);
        artist.addSong(song);
        song.addArtist(new ArtistDto(artist));
        songRepository.save(song);
        return new SongDto(song);
    }


    public void deleteArtistFromSong(Long songId, Long artistId) {
        Song song = songRepository.findSongById(songId);
        Artist artist = artistRepository.findById(artistId).orElseThrow(() -> new IllegalArgumentException("Artist not found"));

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
