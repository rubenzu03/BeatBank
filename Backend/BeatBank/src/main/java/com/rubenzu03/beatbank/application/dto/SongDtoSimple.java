package com.rubenzu03.beatbank.application.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record SongDtoSimple(Long id, @NotBlank String name, String duration, Long plays) implements Serializable {
}
