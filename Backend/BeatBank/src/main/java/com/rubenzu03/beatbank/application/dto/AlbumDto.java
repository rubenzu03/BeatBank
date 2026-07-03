package com.rubenzu03.beatbank.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.List;

@Schema(description = "Full album representation with genre and songs")
public record AlbumDto(
        @Schema(description = "Unique identifier", example = "1") Long id,
        @NotBlank @Schema(description = "Album name", example = "A Night at the Opera") String name,
        @Schema(description = "Release date", example = "1975-11-21") String releaseDate,
        @Schema(description = "Cover image URL", example = "https://example.com/cover.jpg") String coverImageUrl,
        @Schema(description = "Album description") String description,
        @Valid @Schema(description = "Genre of the album") GenreDto genre,
        @Valid @Schema(description = "Songs in this album") List<SongDto> songs
) implements Serializable {}
