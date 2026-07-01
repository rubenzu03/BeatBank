package com.rubenzu03.beatbank.adapter.inbound.rest;

import com.rubenzu03.beatbank.application.dto.ArtistDto;
import com.rubenzu03.beatbank.application.dto.SongDto;
import com.rubenzu03.beatbank.application.dto.SongPatchDto;
import com.rubenzu03.beatbank.application.port.inbound.SongUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Get all songs")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved songs")
    @ResponseStatus(HttpStatus.OK)
    public Page<SongDto> getAllSongs(@PageableDefault(size = 20) Pageable pageable){
        return songUseCase.getAllSongs(pageable);
    }

    @GetMapping("/songs/{id}")
    @Operation(summary = "Get song by ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved song")
    @ResponseStatus(HttpStatus.OK)
    public SongDto getSongById(@PathVariable Long id){
        return songUseCase.getSongById(id);
    }

    @Transactional
    @PostMapping("/songs")
    @Operation(summary = "Create a new song")
    @ApiResponse(responseCode = "201", description = "Successfully created song")
    @ResponseStatus(HttpStatus.CREATED)
    public SongDto createSong(@Valid @RequestBody SongDto songDto){
        return songUseCase.createSong(songDto);
    }

    @Transactional
    @PutMapping("/songs/{id}")
    @Operation(summary = "Update an existing song")
    @ApiResponse(responseCode = "200", description = "Successfully updated song")
    @ResponseStatus(HttpStatus.OK)
    public SongDto updateSong(@PathVariable Long id, @Valid @RequestBody SongDto songDto){
        return songUseCase.updateSong(id, songDto);
    }

    @Transactional
    @PatchMapping("/songs/{id}")
    @Operation(summary = "Update an existing song")
    @ApiResponse(responseCode = "200", description = "Successfully updated song")
    @ResponseStatus(HttpStatus.OK)
    public SongDto patchSong(@PathVariable Long id, @RequestBody SongPatchDto patch){
        return songUseCase.patchSong(id, patch);
    }

    @DeleteMapping("/songs/{id}")
    @Operation(summary = "Delete a song by ID")
    @ApiResponse(responseCode = "200", description = "Successfully deleted song")
    @ResponseStatus(HttpStatus.OK)
    public void deleteSongById(@PathVariable Long id) {
        songUseCase.deleteSongById(id);
    }

    @Transactional
    @PostMapping("/songs/{id}/artists")
    @Operation(summary = "Add an artist to a song")
    @ApiResponse(responseCode = "200", description = "Successfully added artist to song")
    @ResponseStatus(HttpStatus.OK)
    public SongDto addArtistToSong(@PathVariable Long id, @Valid @RequestBody ArtistDto artistDto){
        return songUseCase.addArtistToSong(id, artistDto);
    }

    @Transactional
    @DeleteMapping("/songs/{id}/artists/{artistId}")
    @Operation(summary = "Delete an artist from a song")
    @ApiResponse(responseCode = "200", description = "Successfully deleted artist from song")
    @ResponseStatus(HttpStatus.OK)
    public void deleteArtistFromSong(@PathVariable Long id, @PathVariable Long artistId) {
        songUseCase.deleteArtistFromSong(id, artistId);
    }
}
