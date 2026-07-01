package com.rubenzu03.beatbank.adapter.inbound.rest;

import com.rubenzu03.beatbank.application.dto.GenreDto;
import com.rubenzu03.beatbank.application.dto.GenrePatchDto;
import com.rubenzu03.beatbank.application.port.inbound.GenreUseCase;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreUseCase genreUseCase;

    public GenreController(GenreUseCase genreUseCase) {
        this.genreUseCase = genreUseCase;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<GenreDto> getAllGenres(@PageableDefault(size = 20) Pageable pageable) {
        return genreUseCase.getAllGenres(pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GenreDto getGenreById(@PathVariable Long id) {
        return genreUseCase.getGenreById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenreDto createGenre(@Valid @RequestBody GenreDto genreDto) {
        return genreUseCase.createGenre(genreDto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GenreDto patchGenre(@PathVariable Long id, @RequestBody GenrePatchDto patch) {
        return genreUseCase.patchGenre(id, patch);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGenreById(@PathVariable Long id) {
        genreUseCase.deleteGenreById(id);
    }
}
