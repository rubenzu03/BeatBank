package com.rubenzu03.beatbank.adapter.inbound.rest;

import com.rubenzu03.beatbank.application.dto.ArtistDto;
import com.rubenzu03.beatbank.application.dto.ArtistPatchDto;
import com.rubenzu03.beatbank.application.port.inbound.ArtistUseCase;
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
@RequestMapping("/api/artists")
public class ArtistController {

    private final ArtistUseCase artistUseCase;

    public ArtistController(ArtistUseCase artistUseCase) {
        this.artistUseCase = artistUseCase;
    }

    @GetMapping
    @Operation(summary = "Get all artists")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved artists")
    @ResponseStatus(HttpStatus.OK)
    public Page<ArtistDto> getAllArtists(@PageableDefault(size = 20) Pageable pageable) {
        return artistUseCase.getAllArtists(pageable);
    }

    @Transactional
    @PostMapping
    @Operation(summary = "Create a new artist")
    @ApiResponse(responseCode = "201", description = "Successfully created artist")
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistDto createArtist(@Valid @RequestBody ArtistDto artistDto) {
        return artistUseCase.createArtist(artistDto);
    }

    @Transactional
    @PatchMapping("/{id}")
    @Operation(summary = "Update an existing artist")
    @ApiResponse(responseCode = "200", description = "Successfully updated artist")
    @ResponseStatus(HttpStatus.OK)
    public ArtistDto patchArtist(@PathVariable Long id, @RequestBody ArtistPatchDto patch) {
        return artistUseCase.patchArtist(id, patch);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an artist by ID")
    @ApiResponse(responseCode = "200", description = "Successfully deleted artist")
    @ResponseStatus(HttpStatus.OK)
    public void deleteArtistById(@PathVariable Long id) {
        artistUseCase.deleteArtistById(id);
    }
}
