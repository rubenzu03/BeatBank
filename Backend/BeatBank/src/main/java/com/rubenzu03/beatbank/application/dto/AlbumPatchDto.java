package com.rubenzu03.beatbank.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Schema(description = "Partial album update payload — all fields are optional")
public record AlbumPatchDto(
        @Schema(description = "Album name", example = "A Night at the Opera") String name,
        @Schema(description = "Release date", example = "1975-11-21") String releaseDate,
        @Schema(description = "Cover image URL", example = "https://example.com/cover.jpg") String coverImageUrl,
        @Schema(description = "Album description") String description
) implements Serializable {}
