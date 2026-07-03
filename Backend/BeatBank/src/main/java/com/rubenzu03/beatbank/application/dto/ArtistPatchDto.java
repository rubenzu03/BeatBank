package com.rubenzu03.beatbank.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Schema(description = "Partial artist update payload — all fields are optional")
public record ArtistPatchDto(
        @Schema(description = "Artist name", example = "Queen") String name,
        @Schema(description = "Artist biography") String description
) implements Serializable {}
