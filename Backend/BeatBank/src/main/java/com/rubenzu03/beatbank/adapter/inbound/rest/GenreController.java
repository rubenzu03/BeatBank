package com.rubenzu03.beatbank.adapter.inbound.rest;

import com.rubenzu03.beatbank.application.dto.GenreDto;
import com.rubenzu03.beatbank.application.dto.GenrePatchDto;
import com.rubenzu03.beatbank.application.dto.PagedResponse;
import com.rubenzu03.beatbank.application.port.inbound.GenreUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/genres")
@Tag(name = "Genres", description = "Genre management endpoints")
public class GenreController {

    private final GenreUseCase genreUseCase;

    public GenreController(GenreUseCase genreUseCase) {
        this.genreUseCase = genreUseCase;
    }

    @GetMapping
    @Operation(summary = "Get all genres", description = "Returns a paginated list of all genres")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved genres")
    @ResponseStatus(HttpStatus.OK)
    public PagedResponse<GenreDto> getAllGenres(@Parameter(description = "Pagination parameters") @PageableDefault(size = 20) Pageable pageable) {
        Page<GenreDto> page = genreUseCase.getAllGenres(pageable);
        return new PagedResponse<>(page);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get genre by ID", description = "Returns a single genre by its ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved genre")
    @ApiResponse(responseCode = "404", description = "Genre not found")
    @ResponseStatus(HttpStatus.OK)
    public GenreDto getGenreById(@PathVariable @Parameter(description = "Genre ID") Long id) {
        return genreUseCase.getGenreById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new genre", description = "Creates a genre and returns the created entity")
    @ApiResponse(responseCode = "201", description = "Successfully created genre")
    @ResponseStatus(HttpStatus.CREATED)
    public GenreDto createGenre(@Valid @RequestBody GenreDto genreDto) {
        return genreUseCase.createGenre(genreDto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partially update a genre", description = "Updates only the provided fields of a genre")
    @ApiResponse(responseCode = "200", description = "Successfully updated genre")
    @ApiResponse(responseCode = "404", description = "Genre not found")
    @ResponseStatus(HttpStatus.OK)
    public GenreDto patchGenre(@PathVariable Long id, @RequestBody GenrePatchDto patch) {
        return genreUseCase.patchGenre(id, patch);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a genre", description = "Deletes a genre by its ID")
    @ApiResponse(responseCode = "200", description = "Successfully deleted genre")
    @ApiResponse(responseCode = "404", description = "Genre not found")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGenreById(@PathVariable Long id) {
        genreUseCase.deleteGenreById(id);
    }
}
