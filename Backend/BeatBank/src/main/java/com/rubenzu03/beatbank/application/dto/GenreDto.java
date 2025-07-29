package com.rubenzu03.beatbank.application.dto;

import com.rubenzu03.beatbank.domain.Genre;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.rubenzu03.beatbank.domain.Genre}
 */
public record GenreDto(Long id, String name, String description, List<SongDto> songs) implements Serializable {

    public GenreDto (Genre genre) {
        this(
            genre.getId(),
            genre.getName(),
            genre.getDescription(),
            genre.getSongs() == null ? null : genre.getSongs().stream()
                .map(SongDto::new)
                .toList()
        );
    }
}