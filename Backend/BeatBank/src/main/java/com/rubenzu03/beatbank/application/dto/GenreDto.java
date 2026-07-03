package com.rubenzu03.beatbank.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.List;

@Schema(description = "Full genre representation with songs")
public record GenreDto(
        @Schema(description = "Unique identifier", example = "1") Long id,
        @NotBlank @Schema(description = "Genre name", example = "Rock") String name,
        @Schema(description = "Genre description") String description,
        @Valid @Schema(description = "Songs in this genre") List<SongDto> songs
) implements Serializable {}
