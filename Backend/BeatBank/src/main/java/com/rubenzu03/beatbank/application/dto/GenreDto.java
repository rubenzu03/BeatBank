package com.rubenzu03.beatbank.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.List;

public record GenreDto(Long id, @NotBlank String name, String description, @Valid List<SongDto> songs) implements Serializable {
}
