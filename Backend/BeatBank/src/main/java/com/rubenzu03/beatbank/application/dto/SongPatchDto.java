package com.rubenzu03.beatbank.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Schema(description = "Partial song update payload — all fields are optional")
public record SongPatchDto(
        @Schema(description = "Song name", example = "Bohemian Rhapsody") String name,
        @Schema(description = "Duration in mm:ss format", example = "5:55") String duration,
        @Schema(description = "Number of plays", example = "1000000") Long plays
) implements Serializable {}
