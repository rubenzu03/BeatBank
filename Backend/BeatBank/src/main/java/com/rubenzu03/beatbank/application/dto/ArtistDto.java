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
}