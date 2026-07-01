package com.rubenzu03.beatbank.application.dto;

import java.io.Serializable;

public record AlbumPatchDto(String name, String releaseDate, String coverImageUrl, String description) implements Serializable {
}
