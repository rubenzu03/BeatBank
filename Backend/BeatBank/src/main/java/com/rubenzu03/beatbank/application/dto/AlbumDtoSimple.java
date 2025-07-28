package com.rubenzu03.beatbank.application.dto;

import com.rubenzu03.beatbank.domain.Album;
import java.io.Serializable;

public record AlbumDtoSimple(Long id, String name, String releaseDate) implements Serializable {
    public AlbumDtoSimple(Album album) {
        this(album.getId(), album.getName(), album.getReleaseDate());
    }
}

