package com.rubenzu03.beatbank.application.dto;

import com.rubenzu03.beatbank.SongDto;
import com.rubenzu03.beatbank.domain.Song;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.rubenzu03.beatbank.domain.Album}
 */
@Value
public class AlbumDto implements Serializable {
    Long id;
    String name;
    String releaseDate;
    String coverImageUrl;
    String genre;
    String description;
    List<SongDto> songs;
}