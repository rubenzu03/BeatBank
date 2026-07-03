package com.rubenzu03.beatbank.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.List;

public record AlbumDto(Long id, @NotBlank String name, String releaseDate, String coverImageUrl, String description, GenreDto genre,
                       @Valid List<SongDto> songs) implements Serializable {
}
