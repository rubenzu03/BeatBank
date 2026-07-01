package com.rubenzu03.beatbank.application.port.inbound;

import com.rubenzu03.beatbank.application.dto.GenreDto;
import com.rubenzu03.beatbank.application.dto.GenrePatchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GenreUseCase {
    Page<GenreDto> getAllGenres(Pageable pageable);
    GenreDto getGenreById(Long id);
    GenreDto createGenre(GenreDto genreDto);
    GenreDto patchGenre(Long id, GenrePatchDto patch);
    void deleteGenreById(Long id);
}
