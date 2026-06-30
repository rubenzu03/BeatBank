package com.rubenzu03.beatbank.adapter.inbound.rest;

import com.rubenzu03.beatbank.application.dto.GenreDto;
import com.rubenzu03.beatbank.application.port.inbound.GenreUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/genres")
public class GenreController {

    private final GenreUseCase genreUseCase;

    public GenreController(GenreUseCase genreUseCase) {
        this.genreUseCase = genreUseCase;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GenreDto> getAllGenres() {
        return genreUseCase.getAllGenres();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping("/{id}")
    public GenreDto getGenreById(@PathVariable Long id) {
        return genreUseCase.getGenreById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenreDto createGenre(@RequestBody GenreDto genreDto) {
        return genreUseCase.createGenre(genreDto);
    }

   /* @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping("/{id}")
    public void deleteGenreById(@PathVariable Long id) {
        genreUseCase.deleteGenreById(id);
    }*/
}
