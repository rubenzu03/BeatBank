package com.rubenzu03.beatbank.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Schema(description = "Album cover URL update payload")
public record CoverUpdateDto(
        @Schema(description = "New cover image URL", example = "https://example.com/cover.jpg") String coverImageUrl
) implements Serializable {}
