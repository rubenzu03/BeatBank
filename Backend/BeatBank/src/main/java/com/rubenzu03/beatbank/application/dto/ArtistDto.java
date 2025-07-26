package com.rubenzu03.beatbank.application.dto;

import com.rubenzu03.beatbank.domain.Artist;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link Artist}
 */
@Value
public class ArtistDto implements Serializable {
    Long id;
    String name;
    List<SongDto> songs;
    String description;

    public ArtistDto(Artist artist){
        this.id = artist.getId();
        this.name = artist.getName();
        this.description = artist.getDescription();
        this.songs = artist.getSongs() == null ? null : artist.getSongs().stream()
            .map(SongDto::new)
            .toList();
    }
}