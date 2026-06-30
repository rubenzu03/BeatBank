package com.rubenzu03.beatbank.application.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record ArtistDtoSimple(Long id, @NotBlank String name) implements Serializable {
}
