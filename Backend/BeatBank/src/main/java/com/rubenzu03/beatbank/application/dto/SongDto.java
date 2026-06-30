package com.rubenzu03.beatbank.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;

public record SongDto(Long id, @NotBlank String name, String duration, Long plays, @Valid AlbumDto album,
                      @Valid List<ArtistDtoSimple> artists) implements Serializable {
}
