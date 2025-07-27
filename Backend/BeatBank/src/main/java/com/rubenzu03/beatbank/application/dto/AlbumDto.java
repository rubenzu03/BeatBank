package com.rubenzu03.beatbank.application.dto;

import com.rubenzu03.beatbank.domain.Album;
import com.rubenzu03.beatbank.domain.Song;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.rubenzu03.beatbank.domain.Album}
 */
public record AlbumDto(Long id, String name, String releaseDate, String coverImageUrl, String genre, String description, List<SongDto> songs) implements Serializable {
    public AlbumDto(Album album) {
        this(
            album.getId(),
            album.getName(),
            album.getReleaseDate(),
            album.getCoverImageUrl(),
            album.getGenre(),
            album.getDescription(),
            album.getSongs() == null ? null : album.getSongs().stream()
                .map(SongDto::new)
                .toList()
        );
    }
}
