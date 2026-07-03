package com.rubenzu03.beatbank.adapter.inbound.rest;

import com.rubenzu03.beatbank.application.dto.ArtistDto;
import com.rubenzu03.beatbank.application.dto.ArtistPatchDto;
import com.rubenzu03.beatbank.application.dto.PagedResponse;
import com.rubenzu03.beatbank.application.port.inbound.ArtistUseCase;
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
@RequestMapping("/api/artists")
@Tag(name = "Artists", description = "Artist management endpoints")
public class ArtistController {

    private final ArtistUseCase artistUseCase;

    public ArtistController(ArtistUseCase artistUseCase) {
        this.artistUseCase = artistUseCase;
    }

    @GetMapping
    @Operation(summary = "Get all artists", description = "Returns a paginated list of all artists")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved artists")
    @ResponseStatus(HttpStatus.OK)
    public PagedResponse<ArtistDto> getAllArtists(@Parameter(description = "Pagination parameters") @PageableDefault(size = 20) Pageable pageable) {
        Page<ArtistDto> page = artistUseCase.getAllArtists(pageable);
        return new PagedResponse<>(page);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get artist by ID", description = "Returns a single artist by its ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved artist")
    @ApiResponse(responseCode = "404", description = "Artist not found")
    @ResponseStatus(HttpStatus.OK)
    public ArtistDto getArtistById(@PathVariable @Parameter(description = "Artist ID") Long id) {
        return artistUseCase.getArtistById(id);
    }

    @Transactional
    @PostMapping
    @Operation(summary = "Create a new artist", description = "Creates an artist and returns the created entity")
    @ApiResponse(responseCode = "201", description = "Successfully created artist")
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistDto createArtist(@Valid @RequestBody ArtistDto artistDto) {
        return artistUseCase.createArtist(artistDto);
    }

    @Transactional
    @PatchMapping("/{id}")
    @Operation(summary = "Partially update an artist", description = "Updates only the provided fields of an artist")
    @ApiResponse(responseCode = "200", description = "Successfully updated artist")
    @ApiResponse(responseCode = "404", description = "Artist not found")
    @ResponseStatus(HttpStatus.OK)
    public ArtistDto patchArtist(@PathVariable Long id, @RequestBody ArtistPatchDto patch) {
        return artistUseCase.patchArtist(id, patch);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an artist", description = "Deletes an artist by its ID")
    @ApiResponse(responseCode = "200", description = "Successfully deleted artist")
    @ApiResponse(responseCode = "404", description = "Artist not found")
    @ResponseStatus(HttpStatus.OK)
    public void deleteArtistById(@PathVariable Long id) {
        artistUseCase.deleteArtistById(id);
    }
}
