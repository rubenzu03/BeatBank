package com.rubenzu03.beatbank.application.dto;

import com.rubenzu03.beatbank.SongDto;
import com.rubenzu03.beatbank.domain.Song;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.rubenzu03.beatbank.domain.Genre}
 */
@Value
public class GenreDto implements Serializable {
    Long id;
    String name;
    String description;
    List<SongDto> songs;
}