package com.rubenzu03.beatbank.application;

import com.rubenzu03.beatbank.SongDto;
import com.rubenzu03.beatbank.domain.Song;
import com.rubenzu03.beatbank.persistence.SongRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongService {

    private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public List<SongDto> getAllSongs(){
        return songRepository.findAll().stream().map(SongDto::new).collect(Collectors.toList());
    }

    public SongDto getSongById(Long id) {
        Song song = songRepository.findSongById(id);
        return new SongDto(song);
    }

    public SongDto createSong(SongDto songDto){
        Song song = new Song(songDto);
        songRepository.save(song);
        return new SongDto(song);
    }

    public SongDto updateSong(Long id, SongDto songDto){
        Song song = songRepository.findById(id).get();
        song.updateSong(songDto);
        //songRepository.save(song);
        return new SongDto(song);
    }


}
