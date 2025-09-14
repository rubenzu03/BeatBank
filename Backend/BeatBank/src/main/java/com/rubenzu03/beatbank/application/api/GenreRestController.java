package com.rubenzu03.beatbank.application.api;

import com.rubenzu03.beatbank.application.GenreService;
import com.rubenzu03.beatbank.application.dto.GenreDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/genres")
public class GenreRestController {

    private final GenreService genreService;

    public GenreRestController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GenreDto> getAllGenres() {
        return genreService.getAllGenres();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping("/{id}")
    public GenreDto getGenreById(@PathVariable Long id) {
        return genreService.getGenreById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenreDto createGenre(@RequestBody GenreDto genreDto) {
        return genreService.createGenre(genreDto);
    }

   /* @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping("/{id}")
    public void deleteGenreById(@PathVariable Long id) {
        genreService.deleteGenreById(id);
    }*/
}
