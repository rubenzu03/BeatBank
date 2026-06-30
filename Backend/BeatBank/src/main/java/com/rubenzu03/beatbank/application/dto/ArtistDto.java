package com.rubenzu03.beatbank.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.List;

public record ArtistDto(Long id, @NotBlank String name, @Valid List<SongDtoSimple> songs, String description) implements Serializable {
}
