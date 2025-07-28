package com.rubenzu03.beatbank.application.dto;

import com.rubenzu03.beatbank.domain.Artist;
import java.io.Serializable;

public record ArtistDtoSimple(Long id, String name) implements Serializable {
    public ArtistDtoSimple(Artist artist) {
        this(artist.getId(), artist.getName());
    }
}

