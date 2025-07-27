package com.rubenzu03.beatbank.application.dto;

import com.rubenzu03.beatbank.domain.Artist;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link Artist}
 */
public record ArtistDto(Long id, String name, List<SongDto> songs, String description) implements Serializable {
    public ArtistDto(Artist artist){
        this(
            artist.getId(),
            artist.getName(),
            artist.getSongs() == null ? null : artist.getSongs().stream()
                .map(SongDto::new)
                .toList(),
            artist.getDescription()
        );
    }
}