package com.rubenzu03.beatbank.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

@Schema(description = "Minimal artist representation (id + name only)")
public record ArtistDtoSimple(
        @Schema(description = "Unique identifier", example = "1") Long id,
        @NotBlank @Schema(description = "Artist name", example = "Queen") String name
) implements Serializable {}
