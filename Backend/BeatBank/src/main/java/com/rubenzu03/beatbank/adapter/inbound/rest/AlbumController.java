package com.rubenzu03.beatbank.adapter.inbound.rest;

import com.rubenzu03.beatbank.application.dto.AlbumDto;
import com.rubenzu03.beatbank.application.dto.AlbumPatchDto;
import com.rubenzu03.beatbank.application.dto.SongDto;
import com.rubenzu03.beatbank.application.port.inbound.AlbumUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    private final AlbumUseCase albumUseCase;

    public AlbumController(AlbumUseCase albumUseCase) {
        this.albumUseCase = albumUseCase;
    }

    @GetMapping
    @Operation(summary = "Get all albums")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved albums")
    @ResponseStatus(HttpStatus.OK)
    public Page<AlbumDto> getAllAlbums(@PageableDefault(size = 20) Pageable pageable) {
        return albumUseCase.getAllAlbums(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get album by ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved album")
    @ResponseStatus(HttpStatus.OK)
    public AlbumDto getAlbumById(@PathVariable Long id) {
        return albumUseCase.getAlbumById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new album")
    @ApiResponse(responseCode = "201", description = "Successfully created album")
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumDto createAlbum(@Valid @RequestBody AlbumDto albumDto) {
        return albumUseCase.createAlbum(albumDto);
    }

    @PostMapping("/{id}/songs")
    @Operation(summary = "Add a song to an album")
    @ApiResponse(responseCode = "200", description = "Successfully added song to album")
    @ResponseStatus(HttpStatus.OK)
    public AlbumDto addSongToAlbum(@PathVariable Long id, @Valid @RequestBody SongDto songDto) {
        return albumUseCase.addSongToAlbum(id, songDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing album")
    @ApiResponse(responseCode = "200", description = "Successfully updated album")
    @ResponseStatus(HttpStatus.OK)
    public AlbumDto updateAlbum(@PathVariable Long id, @Valid @RequestBody AlbumDto albumDto) {
        return albumUseCase.updateAlbum(id, albumDto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partially update an existing album")
    @ApiResponse(responseCode = "200", description = "Successfully partially updated album")
    @ResponseStatus(HttpStatus.OK)
    public AlbumDto patchAlbum(@PathVariable Long id, @RequestBody AlbumPatchDto patch) {
        return albumUseCase.patchAlbum(id, patch);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an album by ID")
    @ApiResponse(responseCode = "200", description = "Successfully deleted album")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAlbumById(@PathVariable Long id) {
        albumUseCase.deleteAlbumById(id);
    }
}
