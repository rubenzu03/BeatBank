package com.rubenzu03.beatbank.adapter.inbound.rest;

import com.rubenzu03.beatbank.application.dto.ArtistDto;
import com.rubenzu03.beatbank.application.dto.SongDto;
import com.rubenzu03.beatbank.application.dto.SongPatchDto;
import com.rubenzu03.beatbank.application.port.inbound.SongUseCase;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SongController {

    private final SongUseCase songUseCase;

    public SongController(SongUseCase songUseCase) {
        this.songUseCase = songUseCase;
    }

    @GetMapping("/songs")
    @ResponseStatus(HttpStatus.OK)
    public Page<SongDto> getAllSongs(@PageableDefault(size = 20) Pageable pageable){
        return songUseCase.getAllSongs(pageable);
    }

    @GetMapping("/songs/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SongDto getSongById(@PathVariable Long id){
        return songUseCase.getSongById(id);
    }

    @Transactional
    @PostMapping("/songs")
    @ResponseStatus(HttpStatus.CREATED)
    public SongDto createSong(@Valid @RequestBody SongDto songDto){
        return songUseCase.createSong(songDto);
    }

    @Transactional
    @PutMapping("/songs/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SongDto updateSong(@PathVariable Long id, @Valid @RequestBody SongDto songDto){
        return songUseCase.updateSong(id, songDto);
    }

    @Transactional
    @PatchMapping("/songs/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SongDto patchSong(@PathVariable Long id, @RequestBody SongPatchDto patch){
        return songUseCase.patchSong(id, patch);
    }

    @DeleteMapping("/songs/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteSongById(@PathVariable Long id) {
        songUseCase.deleteSongById(id);
    }

    @Transactional
    @PostMapping("/songs/{id}/artists")
    @ResponseStatus(HttpStatus.OK)
    public SongDto addArtistToSong(@PathVariable Long id, @Valid @RequestBody ArtistDto artistDto){
        return songUseCase.addArtistToSong(id, artistDto);
    }

    @Transactional
    @DeleteMapping("/songs/{id}/artists/{artistId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteArtistFromSong(@PathVariable Long id, @PathVariable Long artistId) {
        songUseCase.deleteArtistFromSong(id, artistId);
    }
}
