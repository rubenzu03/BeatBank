package com.rubenzu03.beatbank.application.dto;

import com.rubenzu03.beatbank.application.dto.AlbumDto;
import com.rubenzu03.beatbank.domain.Album;
import com.rubenzu03.beatbank.domain.Artist;
import com.rubenzu03.beatbank.domain.Song;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.rubenzu03.beatbank.domain.Song}
 */
public record SongDto(Long id, String name, String duration, Album album,
                      List<Artist> artists) implements Serializable {
    public SongDto(Song x) {
        this(x.getId(), x.getName(), x.getDuration(), x.getAlbum(), x.getArtists());
    }
}