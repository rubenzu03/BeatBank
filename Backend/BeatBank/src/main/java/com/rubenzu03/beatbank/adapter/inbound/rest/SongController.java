package com.rubenzu03.beatbank.adapter.inbound.rest;

import com.rubenzu03.beatbank.application.dto.ArtistDto;
import com.rubenzu03.beatbank.application.dto.PagedResponse;
import com.rubenzu03.beatbank.application.dto.SongDto;
import com.rubenzu03.beatbank.application.dto.SongPatchDto;
import com.rubenzu03.beatbank.application.port.inbound.SongUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "Songs", description = "Song management endpoints")
public class SongController {

    private final SongUseCase songUseCase;

    public SongController(SongUseCase songUseCase) {
        this.songUseCase = songUseCase;
    }

    @GetMapping("/songs")
    @Operation(summary = "Get all songs", description = "Returns a paginated list of all songs")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved songs")
    @ResponseStatus(HttpStatus.OK)
    public PagedResponse<SongDto> getAllSongs(@Parameter(description = "Pagination parameters") @PageableDefault(size = 20) Pageable pageable){
        Page<SongDto> page = songUseCase.getAllSongs(pageable);
        return new PagedResponse<>(page);
    }

    @GetMapping("/songs/search")
    @Operation(summary = "Search songs", description = "Full-text search on song names")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved search results")
    @ResponseStatus(HttpStatus.OK)
    public PagedResponse<SongDto> searchSongs(@RequestParam @Parameter(description = "Search query") String q,
                                              @PageableDefault(size = 20) Pageable pageable) {
        Page<SongDto> page = songUseCase.searchSongs(q, pageable);
        return new PagedResponse<>(page);
    }

    @GetMapping("/songs/{id}")
    @Operation(summary = "Get song by ID", description = "Returns a single song by its ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved song")
    @ApiResponse(responseCode = "404", description = "Song not found")
    @ResponseStatus(HttpStatus.OK)
    public SongDto getSongById(@PathVariable @Parameter(description = "Song ID") Long id){
        return songUseCase.getSongById(id);
    }

    @Transactional
    @PostMapping("/songs")
    @Operation(summary = "Create a new song", description = "Creates a song and returns the created entity")
    @ApiResponse(responseCode = "201", description = "Successfully created song")
    @ResponseStatus(HttpStatus.CREATED)
    public SongDto createSong(@Valid @RequestBody SongDto songDto){
        return songUseCase.createSong(songDto);
    }

    @Transactional
    @PutMapping("/songs/{id}")
    @Operation(summary = "Update an existing song", description = "Replaces all fields of a song")
    @ApiResponse(responseCode = "200", description = "Successfully updated song")
    @ApiResponse(responseCode = "404", description = "Song not found")
    @ResponseStatus(HttpStatus.OK)
    public SongDto updateSong(@PathVariable Long id, @Valid @RequestBody SongDto songDto){
        return songUseCase.updateSong(id, songDto);
    }

    @Transactional
    @PatchMapping("/songs/{id}")
    @Operation(summary = "Partially update a song", description = "Updates only the provided fields of a song")
    @ApiResponse(responseCode = "200", description = "Successfully updated song")
    @ApiResponse(responseCode = "404", description = "Song not found")
    @ResponseStatus(HttpStatus.OK)
    public SongDto patchSong(@PathVariable Long id, @RequestBody SongPatchDto patch){
        return songUseCase.patchSong(id, patch);
    }

    @Transactional
    @PostMapping("/songs/{id}/play")
    @Operation(summary = "Increment play count", description = "Increments the play count of a song by 1")
    @ApiResponse(responseCode = "200", description = "Play count incremented")
    @ApiResponse(responseCode = "404", description = "Song not found")
    @ResponseStatus(HttpStatus.OK)
    public SongDto incrementPlays(@PathVariable @Parameter(description = "Song ID") Long id) {
        return songUseCase.incrementPlays(id);
    }

    @DeleteMapping("/songs/{id}")
    @Operation(summary = "Delete a song", description = "Deletes a song by its ID")
    @ApiResponse(responseCode = "200", description = "Successfully deleted song")
    @ApiResponse(responseCode = "404", description = "Song not found")
    @ResponseStatus(HttpStatus.OK)
    public void deleteSongById(@PathVariable Long id) {
        songUseCase.deleteSongById(id);
    }

    @Transactional
    @PostMapping("/songs/{id}/artists")
    @Operation(summary = "Add artist to song", description = "Creates a new artist and associates it with the song")
    @ApiResponse(responseCode = "200", description = "Successfully added artist to song")
    @ApiResponse(responseCode = "404", description = "Song not found")
    @ResponseStatus(HttpStatus.OK)
    public SongDto addArtistToSong(@PathVariable Long id, @Valid @RequestBody ArtistDto artistDto){
        return songUseCase.addArtistToSong(id, artistDto);
    }

    @Transactional
    @DeleteMapping("/songs/{id}/artists/{artistId}")
    @Operation(summary = "Remove artist from song", description = "Removes an artist's association from a song")
    @ApiResponse(responseCode = "200", description = "Successfully removed artist from song")
    @ApiResponse(responseCode = "404", description = "Song or artist not found")
    @ResponseStatus(HttpStatus.OK)
    public void deleteArtistFromSong(@PathVariable Long id, @PathVariable Long artistId) {
        songUseCase.deleteArtistFromSong(id, artistId);
    }
}
