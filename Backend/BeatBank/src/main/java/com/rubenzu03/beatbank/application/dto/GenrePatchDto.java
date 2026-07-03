package com.rubenzu03.beatbank.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Schema(description = "Partial genre update payload — all fields are optional")
public record GenrePatchDto(
        @Schema(description = "Genre name", example = "Rock") String name,
        @Schema(description = "Genre description") String description
) implements Serializable {}
