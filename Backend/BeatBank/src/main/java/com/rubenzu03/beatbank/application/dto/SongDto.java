package com.rubenzu03.beatbank.application.dto;

import com.rubenzu03.beatbank.domain.Album;
import com.rubenzu03.beatbank.domain.Artist;
import com.rubenzu03.beatbank.domain.Song;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.rubenzu03.beatbank.domain.Song}
 */
public record SongDto(Long id, String name, String duration, AlbumDto album,
                      List<ArtistDto> artists) implements Serializable {
    public SongDto(Song x) {
        this(x.getId(), x.getName(), x.getDuration(),
            x.getAlbum() == null ? null : new AlbumDto(x.getAlbum()),
            x.getArtists() == null ? null : x.getArtists().stream()
                .map(ArtistDto::new)
                .toList());
    }
}