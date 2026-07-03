package com.rubenzu03.beatbank.adapter.inbound.rest;

import com.rubenzu03.beatbank.application.dto.AlbumDto;
import com.rubenzu03.beatbank.application.dto.AlbumPatchDto;
import com.rubenzu03.beatbank.application.dto.PagedResponse;
import com.rubenzu03.beatbank.application.dto.SongDto;
import com.rubenzu03.beatbank.application.port.inbound.AlbumUseCase;
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
@RequestMapping("/api/albums")
@Tag(name = "Albums", description = "Album management endpoints")
public class AlbumController {

    private final AlbumUseCase albumUseCase;

    public AlbumController(AlbumUseCase albumUseCase) {
        this.albumUseCase = albumUseCase;
    }

    @GetMapping
    @Operation(summary = "Get all albums", description = "Returns a paginated list of all albums")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved albums")
    @ResponseStatus(HttpStatus.OK)
    public PagedResponse<AlbumDto> getAllAlbums(@Parameter(description = "Pagination parameters") @PageableDefault(size = 20) Pageable pageable) {
        Page<AlbumDto> page = albumUseCase.getAllAlbums(pageable);
        return new PagedResponse<>(page);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get album by ID", description = "Returns a single album by its ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved album")
    @ApiResponse(responseCode = "404", description = "Album not found")
    @ResponseStatus(HttpStatus.OK)
    public AlbumDto getAlbumById(@PathVariable @Parameter(description = "Album ID") Long id) {
        return albumUseCase.getAlbumById(id);
    }

    @Transactional
    @PostMapping
    @Operation(summary = "Create a new album", description = "Creates an album and returns the created entity")
    @ApiResponse(responseCode = "201", description = "Successfully created album")
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumDto createAlbum(@Valid @RequestBody AlbumDto albumDto) {
        return albumUseCase.createAlbum(albumDto);
    }

    @Transactional
    @PostMapping("/{id}/songs")
    @Operation(summary = "Add song to album", description = "Creates a new song and associates it with the album")
    @ApiResponse(responseCode = "200", description = "Successfully added song to album")
    @ApiResponse(responseCode = "404", description = "Album not found")
    @ResponseStatus(HttpStatus.OK)
    public AlbumDto addSongToAlbum(@PathVariable Long id, @Valid @RequestBody SongDto songDto) {
        return albumUseCase.addSongToAlbum(id, songDto);
    }

    @Transactional
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing album", description = "Replaces all fields of an album")
    @ApiResponse(responseCode = "200", description = "Successfully updated album")
    @ApiResponse(responseCode = "404", description = "Album not found")
    @ResponseStatus(HttpStatus.OK)
    public AlbumDto updateAlbum(@PathVariable Long id, @Valid @RequestBody AlbumDto albumDto) {
        return albumUseCase.updateAlbum(id, albumDto);
    }

    @Transactional
    @PatchMapping("/{id}")
    @Operation(summary = "Partially update an album", description = "Updates only the provided fields of an album")
    @ApiResponse(responseCode = "200", description = "Successfully partially updated album")
    @ApiResponse(responseCode = "404", description = "Album not found")
    @ResponseStatus(HttpStatus.OK)
    public AlbumDto patchAlbum(@PathVariable Long id, @RequestBody AlbumPatchDto patch) {
        return albumUseCase.patchAlbum(id, patch);
    }

    @Transactional
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an album", description = "Deletes an album by its ID")
    @ApiResponse(responseCode = "200", description = "Successfully deleted album")
    @ApiResponse(responseCode = "404", description = "Album not found")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAlbumById(@PathVariable Long id) {
        albumUseCase.deleteAlbumById(id);
    }
}
