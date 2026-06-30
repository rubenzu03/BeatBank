package com.rubenzu03.beatbank.application.port.inbound;

import com.rubenzu03.beatbank.application.dto.GenreDto;

import java.util.List;

public interface GenreUseCase {
    List<GenreDto> getAllGenres();
    GenreDto getGenreById(Long id);
    GenreDto createGenre(GenreDto genreDto);
    void deleteGenreById(Long id);
}
