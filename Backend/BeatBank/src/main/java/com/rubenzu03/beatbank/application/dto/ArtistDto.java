package com.rubenzu03.beatbank.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.List;

@Schema(description = "Full artist representation with songs")
public record ArtistDto(
        @Schema(description = "Unique identifier", example = "1") Long id,
        @NotBlank @Schema(description = "Artist name", example = "Queen") String name,
        @Valid @Schema(description = "Songs by this artist") List<SongDtoSimple> songs,
        @Schema(description = "Artist biography") String description
) implements Serializable {}
