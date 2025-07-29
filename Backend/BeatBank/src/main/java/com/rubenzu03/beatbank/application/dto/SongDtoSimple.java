package com.rubenzu03.beatbank.application.dto;

import com.rubenzu03.beatbank.domain.Song;
import java.io.Serializable;

public record SongDtoSimple(Long id, String name, String duration, Long plays) implements Serializable {
    public SongDtoSimple(Song song) {
        this(song.getId(), song.getName(), song.getDuration(), song.getPlays());
    }
}

