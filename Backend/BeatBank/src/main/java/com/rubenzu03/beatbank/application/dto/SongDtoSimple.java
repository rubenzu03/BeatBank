package com.rubenzu03.beatbank.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

@Schema(description = "Lightweight song representation without relations")
public record SongDtoSimple(
        @Schema(description = "Unique identifier", example = "1") Long id,
        @NotBlank @Schema(description = "Song name", example = "Bohemian Rhapsody") String name,
        @Schema(description = "Duration in mm:ss format", example = "5:55") String duration,
        @Schema(description = "Number of plays", example = "1000000") Long plays
) implements Serializable {}
