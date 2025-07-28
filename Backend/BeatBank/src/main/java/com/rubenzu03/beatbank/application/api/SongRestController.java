package com.rubenzu03.beatbank.application.api;

import com.rubenzu03.beatbank.application.SongService;
import com.rubenzu03.beatbank.application.dto.AlbumDto;
import com.rubenzu03.beatbank.application.dto.ArtistDto;
import com.rubenzu03.beatbank.application.dto.SongDto;
import com.rubenzu03.beatbank.domain.Artist;
import jakarta.el.MethodNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SongRestController {

    private final SongService songService;

    public SongRestController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/songs")
    @ResponseStatus(HttpStatus.OK)
    public List<SongDto> getAllSongs(){
        return songService.getAllSongs();
    }

    @GetMapping("/songs/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SongDto getSongById(@PathVariable Long id){
        return songService.getSongById(id);
    }

    @Transactional
    @PostMapping("/songs")
    @ResponseStatus(HttpStatus.CREATED)
    public SongDto createSong(@RequestBody SongDto songDto){
        return songService.createSong(songDto);
    }

    @Transactional
    @PutMapping("/songs/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SongDto updateSong(@PathVariable Long id, @RequestBody SongDto songDto){
        return songService.updateSong(id, songDto);
    }
    
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteSongById(@RequestParam Long id) {
        songService.deleteSongById(id);
    }

    @Transactional
    @PostMapping("/songs/{id}/artists")
    @ResponseStatus(HttpStatus.OK)
    public SongDto addArtistToSong(@PathVariable Long id, @RequestBody ArtistDto artistDto){
        return songService.addArtistToSong(id,artistDto);
    }

    @Transactional
    @DeleteMapping("/songs/{id}/artists/{artistId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteArtistFromSong(@PathVariable Long id, @PathVariable Long artistId) {
        songService.deleteArtistFromSong(id, artistId);
    }


}
